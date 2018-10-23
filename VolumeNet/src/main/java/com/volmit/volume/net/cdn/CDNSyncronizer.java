package com.volmit.volume.net.cdn;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.io.VIO;
import com.volmit.volume.lang.json.JSONArray;
import com.volmit.volume.lang.json.JSONException;
import com.volmit.volume.lang.json.JSONObject;
import com.volmit.volume.net.Download;

public abstract class CDNSyncronizer
{
	private File directory;
	private CDNRepository repo;
	private ExecutorService e;
	private String lastLog;
	private double lastProgress;

	public CDNSyncronizer(File directory, CDNRepository repo)
	{
		this.directory = directory;
		this.repo = repo;
		lastLog = "Hang On";
		lastProgress = 0;
	}

	public abstract void onProgressUpdate(double progress, String log);

	public void l(double progress)
	{
		lastProgress = progress;
		onProgressUpdate(progress, lastLog);
	}

	public void l(String log)
	{
		lastLog = log;
		onProgressUpdate(lastProgress, log);
	}

	public void l(double progress, String log)
	{
		l(progress);
		l(log);
	}

	public void l(int done, int of)
	{
		l((double) done / (double) of);
	}

	public void l(int done, int of, String log)
	{
		l(done, of);
		l(log);
	}

	public void createListing()
	{
		File listing = new File(directory, repo.getModule() + ".json");
		listing.getParentFile().mkdirs();
		JSONObject j = new JSONObject();
		JSONArray ja = new JSONArray();
		GList<File> cursors = new GList<File>();
		cursors.add(directory);

		while(cursors.isEmpty())
		{
			File f = cursors.pop();
			Path dir = Paths.get(directory.getAbsolutePath());
			Path df = Paths.get(f.getAbsolutePath());
			String relative = dir.relativize(df).toAbsolutePath().toString();

			if(f.isDirectory())
			{
				cursors.add(f.listFiles());
			}

			else if(!listing.equals(f))
			{
				ja.put(relative);
				System.out.println("+ " + relative);
			}
		}

		j.put("listing", ja);

		try
		{
			VIO.writeAll(listing, j.toString(0));
		}

		catch(JSONException e)
		{
			e.printStackTrace();
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void validate(Runnable callback) throws JSONException, IOException
	{
		e = Executors.newWorkStealingPool(16);

		e.submit(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					boolean clean = false;
					JSONObject jlisting = new JSONObject();
					JSONObject jremoteListing = new JSONObject();
					l("Checking Local Repository");
					if(!directory.exists())
					{
						l("Repo empty, creating new repo at " + directory.getAbsolutePath());
						directory.mkdirs();
					}

					File listing = new File(directory, repo.getModule() + ".json");

					if(!listing.exists())
					{
						clean = true;
						l("Couldn't find listing, marking as empty and cleaning");
					}

					else
					{
						jlisting = new JSONObject(VIO.readAll(listing));
					}

					l("Downloading remote listing");
					new Download(repo.getListing().toString(), listing).download();
					jremoteListing = new JSONObject(VIO.readAll(listing));

					if(!jremoteListing.equals(jlisting))
					{
						l("Listings do not match, resyncronizing");
						clean = true;
					}

					if(clean)
					{
						VIO.delete(directory);
						directory.mkdirs();
						resync(jremoteListing);
					}
				}

				catch(Exception e)
				{

				}
			}
		});
	}

	private void resync(JSONObject l)
	{
		l("Preparing for resync");
		e.submit(new Runnable()
		{
			@Override
			public void run()
			{
				GList<String> f = new GList<String>();
				JSONArray lx = l.getJSONArray("listing");
				l(0, lx.length());

				for(int i = 0; i < lx.length(); i++)
				{
					String v = lx.getString(i);
					f.add(v);
				}

				while(!f.isEmpty())
				{
					String v = f.pop();

					try
					{
						e.submit(() ->
						{
							try
							{
								new Download(repo.getURL().toString() + v, new File(directory, v)).download();
								l(lx.length() - f.size(), v);
							}

							catch(MalformedURLException e1)
							{
								e1.printStackTrace();
							}

							catch(IOException e1)
							{
								e1.printStackTrace();
							}
						});
					}

					catch(RejectedExecutionException e)
					{
						f.add(v);

						try
						{
							Thread.sleep(10);
						}

						catch(InterruptedException e1)
						{
							e1.printStackTrace();
						}
					}

					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}
}
