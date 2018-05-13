package com.volmit.volume.task;

public abstract class SR implements Runnable
{
	public static TaskManager m;
	private int id = 0;

	public SR()
	{
		this(0);
	}

	public SR(int interval)
	{
		this(interval, 0);
	}

	public SR(int interval, int delay)
	{
		id = m.syncRepeating(delay, interval, this);
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
