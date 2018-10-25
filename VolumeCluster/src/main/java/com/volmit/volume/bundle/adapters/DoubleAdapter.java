package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class DoubleAdapter extends BaseAdapter<Double>
{
	public DoubleAdapter()
	{
		super(Double.class);
	}

	@Override
	public String deflate(Double t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Double inflate(String k)
	{
		return Double.valueOf(k);
	}
}
