package com.volmit.volume.net;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.volmit.volume.math.Average;

public abstract class DL extends Thread
{
	public enum DLState
	{
		IDLE, DOWNLOADING, FAILED, FINISHED;
	}

	private static int mid = 0;
	protected URL url;
	protected File dest;
	protected long size;
	protected long downloaded;
	protected long lastDownloaded;
	protected long ms;
	protected long bps;
	protected Average abps;
	protected DLState state;
	protected int kid;

	public DL(URL url, File dest)
	{
		this.kid = ++mid;
		this.ms = 0;
		this.url = url;
		this.dest = dest;
		this.bps = 0;
		this.size = -1;
		this.downloaded = 0;
		this.lastDownloaded = 0;
		this.state = DLState.IDLE;
		this.abps = new Average(8);
	}

	public DL(URL url, File dest, long size)
	{
		this.kid = ++mid;
		this.ms = 0;
		this.url = url;
		this.dest = dest;
		this.bps = 0;
		this.size = size;
		this.downloaded = 0;
		this.lastDownloaded = 0;
		this.state = DLState.IDLE;
		this.abps = new Average(8);
	}

	@Override
	public void run()
	{
		try
		{
			if((dest.exists() && size > -1 && dest.length() == size) || (dest.exists() && size <= 0))
			{
				state = DLState.FINISHED;
				onUpdate();
				return;
			}

			ms = System.currentTimeMillis();
			state = DLState.DOWNLOADING;
			size = NET.getFileSize(url);
			dest.getParentFile().mkdirs();
			dest.createNewFile();

			FileOutputStream fos = new FileOutputStream(dest);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(4000);
			conn.setReadTimeout(4000);

			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[32040];
			int n;

			while((n = is.read(buffer)) > 0)
			{
				fos.write(buffer, 0, n);
				lastDownloaded = downloaded;
				downloaded += n;
				size = size < downloaded ? downloaded - 1 : size;
				long time = 1 + (System.currentTimeMillis() - ms);
				bps = (downloaded / time) * 1024;
				abps.put(bps);
				DLQ.downloadedSize += n;

				onUpdate();
			}

			fos.close();
			is.close();
			state = DLState.FINISHED;
			onUpdate();
		}

		catch(IOException e)
		{
			state = DLState.FAILED;
			e.printStackTrace();
			onUpdate();
		}
	}

	public abstract void onUpdate();

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abps == null) ? 0 : abps.hashCode());
		result = prime * result + (int) (bps ^ (bps >>> 32));
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + (int) (downloaded ^ (downloaded >>> 32));
		result = prime * result + kid;
		result = prime * result + (int) (lastDownloaded ^ (lastDownloaded >>> 32));
		result = prime * result + (int) (ms ^ (ms >>> 32));
		result = prime * result + (int) (size ^ (size >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		DL other = (DL) obj;
		if(abps == null)
		{
			if(other.abps != null)
				return false;
		}
		else if(!abps.equals(other.abps))
			return false;
		if(bps != other.bps)
			return false;
		if(dest == null)
		{
			if(other.dest != null)
				return false;
		}
		else if(!dest.equals(other.dest))
			return false;
		if(downloaded != other.downloaded)
			return false;
		if(kid != other.kid)
			return false;
		if(lastDownloaded != other.lastDownloaded)
			return false;
		if(ms != other.ms)
			return false;
		if(size != other.size)
			return false;
		if(state != other.state)
			return false;
		if(url == null)
		{
			if(other.url != null)
				return false;
		}
		else if(!url.equals(other.url))
			return false;
		return true;
	}
}
