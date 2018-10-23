package com.volmit.volume.net.cdn;

import java.net.MalformedURLException;
import java.net.URL;

public class CDNRepository
{
	private String url;
	private String module;

	public CDNRepository(String repo)
	{
		this(repo.split(":")[0], repo.split(":")[1]);
	}

	public CDNRepository(String url, String module)
	{
		this.url = url;
		this.module = module;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public URL getURL()
	{
		try
		{
			return new URL(url + "/" + module + "/");
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public URL getHash()
	{
		try
		{
			return new URL(url + "/" + module + "/" + module + ".sha");
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public URL getListing()
	{
		try
		{
			return new URL(url + "/" + module + "/" + module + ".json");
		}

		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
