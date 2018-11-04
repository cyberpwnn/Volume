package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class ShortAdapter extends BaseAdapter<Short>
{
	public ShortAdapter()
	{
		super(Short.class);
	}

	@Override
	public String deflate(Short t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Short inflate(String k)
	{
		return Short.valueOf(k);
	}
}
