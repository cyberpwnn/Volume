package com.volmit.volume.task;

public abstract class AR implements Runnable
{
	public static TaskManager m;
	private int id = 0;

	public AR()
	{
		this(0);
	}

	public AR(int interval)
	{
		this(interval, 0);
	}

	public AR(int interval, int delay)
	{
		id = m.asyncRepeating(delay, interval, this);
	}

	public void cancel()
	{
		m.cancel(id);
	}

	public int getId()
	{
		return id;
	}

}
