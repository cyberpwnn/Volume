package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class BooleanAdapter extends BaseAdapter<Boolean>
{
	public BooleanAdapter()
	{
		super(Boolean.class);
	}

	@Override
	public String deflate(Boolean t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Boolean inflate(String k)
	{
		return Boolean.valueOf(k);
	}
}
