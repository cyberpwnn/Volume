package com.volmit.volume.bukkit.task;

public class SR implements Runnable
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

	public SR(Runnable runnable)
	{
		this(0, runnable);
	}

	public SR(int interval, Runnable runnable)
	{
		this(interval, 0, runnable);
	}

	public SR(int interval, int delay, Runnable runnable)
	{
		id = m.syncRepeating(delay, interval, runnable);
	}

	public void cancel()
	{
		m.cancel(id);
	}

	public int getId()
	{
		return id;
	}

	@Override
	public void run()
	{

	}
}
