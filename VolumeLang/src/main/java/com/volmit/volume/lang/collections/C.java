package com.volmit.volume.lang.collections;

public class C<T>
{
	private T t;

	public C(T t)
	{
		s(t);
	}

	public C()
	{
		this(null);
	}

	public T g()
	{
		return t;
	}

	public void s(T t)
	{
		this.t = t;
	}
}
