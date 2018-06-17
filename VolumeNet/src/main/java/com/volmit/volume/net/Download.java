package com.volmit.volume.net;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download
{
	private URL url;
	private File destination;

	public Download(String url, File destination) throws MalformedURLException
	{
		this.url = new URL(url);
		this.destination = destination;
	}

	public void download() throws IOException
	{
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		destination.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(destination);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}
}
