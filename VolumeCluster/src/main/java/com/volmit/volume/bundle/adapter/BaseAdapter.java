package com.volmit.volume.bundle.adapter;

public abstract class BaseAdapter<T> implements NodeAdapter<T>
{
	private final Class<?> type;
	
	public BaseAdapter(Class<?> type)
	{
		this.type = type;
	}

	@Override
	public abstract String deflate(T t);

	@Override
	public abstract T inflate(String k);

	@Override
	public Class<?> getManagedClass()
	{
		return type;
	}
}
