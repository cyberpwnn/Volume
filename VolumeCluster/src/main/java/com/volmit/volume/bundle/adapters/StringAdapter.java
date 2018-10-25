package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class StringAdapter extends BaseAdapter<String>
{
	public StringAdapter()
	{
		super(String.class);
	}

	@Override
	public String deflate(String t) 
	{
		return String.valueOf(t);
	}

	@Override
	public String inflate(String k)
	{
		return String.valueOf(k);
	}
}
