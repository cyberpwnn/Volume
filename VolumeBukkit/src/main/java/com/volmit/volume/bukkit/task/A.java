package com.volmit.volume.bukkit.task;

public class A implements Runnable
{
	public static TaskManager m;

	public A()
	{
		m.async(this);
	}

	public A(long delay)
	{
		m.async(delay, this);
	}

	public A(Runnable runnable)
	{
		m.async(runnable);
	}

	public A(long delay, Runnable runnable)
	{
		m.async(delay, runnable);
	}

	@Override
	public void run()
	{

	}
}
