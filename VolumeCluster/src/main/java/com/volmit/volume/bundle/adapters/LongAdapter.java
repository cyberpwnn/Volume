package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class LongAdapter extends BaseAdapter<Long>
{
	public LongAdapter()
	{
		super(Long.class);
	}

	@Override
	public String deflate(Long t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Long inflate(String k)
	{
		return Long.valueOf(k);
	}
}
