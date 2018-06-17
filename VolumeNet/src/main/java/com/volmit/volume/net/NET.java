package com.volmit.volume.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NET
{
	public static long getFileSize(URL url)
	{
		HttpURLConnection conn = null;

		try
		{
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.getInputStream();
			long k = conn.getContentLength();
			return k;
		}

		catch(IOException e)
		{
			return -1;
		}

		finally
		{
			conn.disconnect();
		}
	}
}
