package com.volmit.volume.bukkit.pawn;

import java.lang.reflect.InvocationTargetException;

import com.volmit.volume.bukkit.task.SR;
import com.volmit.volume.bukkit.task.TICK;

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
					TICK.tick++;
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
		base.getAttachedPawns().clear();
		base.getPawns().clear();
	}

	public void tick() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		base.tick();
	}

	public PawnObject getBase()
	{
		return base;
	}
}
