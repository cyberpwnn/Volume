package com.volmit.volume.pawn;

import java.lang.reflect.InvocationTargetException;

import com.volmit.volume.task.SR;

public class PawnManager
{
	private PawnObject base;
	private SR r;

	public PawnManager(IPawn base) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		this.base = new PawnObject(base);
	}

	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		base.start();
		r = new SR()
		{
			@Override
			public void run()
			{
				try
				{
					tick();
				}

				catch(Throwable e)
				{
					e.printStackTrace();
				}
			}
		};
	}

	public void stop() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		r.cancel();
		base.stop();
	}

	public void tick() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		base.tick();
	}
}
