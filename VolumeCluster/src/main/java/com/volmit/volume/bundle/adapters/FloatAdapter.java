package com.volmit.volume.bundle.adapters;

import com.volmit.volume.bundle.adapter.BaseAdapter;

public class FloatAdapter extends BaseAdapter<Float>
{
	public FloatAdapter()
	{
		super(Float.class);
	}

	@Override
	public String deflate(Float t) 
	{
		return String.valueOf(t);
	}

	@Override
	public Float inflate(String k)
	{
		return Float.valueOf(k);
	}
}
