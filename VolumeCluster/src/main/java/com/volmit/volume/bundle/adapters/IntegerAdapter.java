package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class IntegerAdapter extends BaseAdapter<Integer>
{
	public IntegerAdapter()
	{
		super(Integer.class);
	}

	@Override
	public String deflate(Integer t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Integer inflate(String k)
	{
		return Integer.valueOf(k);
	}
}
