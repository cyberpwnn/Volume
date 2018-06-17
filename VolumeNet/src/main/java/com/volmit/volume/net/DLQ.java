package com.volmit.volume.net;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.net.DL.DLState;

public class DLQ
{
	private GList<DL> running;
	private GList<DL> queue;
	private int maxThreads;
	public long bps = 0;
	public double progress;
	public long totalSize;
	public static long downloadedSize;
	private int k;
	private int c;

	public DLQ(int maxThreads)
	{
		c = 0;
		k = 0;
		this.maxThreads = maxThreads;
		this.running = new GList<DL>();
		this.queue = new GList<DL>();
	}

	public void q(URL url, File f, long size, long modified, Runnable r)
	{
		if(f.exists() && f.length() == size)
		{
			r.run();
		}

		queue.add(new DL(url, f, size)
		{
			@Override
			public void onUpdate()
			{
				if(state.equals(DLState.FINISHED))
				{
					r.run();
				}

				if(state.equals(DLState.FINISHED) && modified > 0)
				{
					dest.setLastModified(modified);
				}

				update(this);
			}
		});

		totalSize += size;
		k++;
	}

	public void q(String url, File f, long size, long modified)
	{
		try
		{
			q(new URL(url), f, size, modified, new Runnable()
			{
				@Override
				public void run()
				{

				}
			});
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	public void q(String url, File f)
	{
		try
		{
			q(new URL(url), f, NET.getFileSize(new URL(url)), -1, new Runnable()
			{
				@Override
				public void run()
				{

				}
			});
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	public void q(String url, File f, long size, Runnable r)
	{
		try
		{
			q(new URL(url), f, size, -1, r);
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

	}

	public void q(String url, File f, long size)
	{
		try
		{
			q(new URL(url), f, size, -1, new Runnable()
			{
				@Override
				public void run()
				{

				}
			});
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	public void flush()
	{
		while((!queue.isEmpty() || !running.isEmpty()) && (k != c))
		{
			if(!queue.isEmpty())
			{
				while(running.size() < maxThreads && !queue.isEmpty())
				{
					DL d = queue.pop();
					d.start();
					running.add(d);
				}
			}

			try
			{
				Thread.sleep(1);
			}

			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void update(DL d)
	{
		bps = 0;

		try
		{
			for(DL i : running)
			{
				bps += i.abps.getAverage();
			}
		}

		catch(Exception e)
		{

		}

		if(running.size() > 0)
		{
			bps /= running.size();
		}

		progress = (double) downloadedSize / (double) totalSize;

		if(d.state.equals(DLState.FAILED))
		{
			running.remove(d);
		}

		if(d.state.equals(DLState.FINISHED))
		{
			c++;
			running.remove(d);
		}
	}
}
