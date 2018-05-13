package com.volmit.volume.pawn;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.reflect.V;
import com.volmit.volume.task.A;
import com.volmit.volume.task.TICK;

public class PawnObject
{
	private GMap<Field, PawnObject> pawns;
	private GList<Method> starts;
	private GList<Method> stops;
	private GMap<Method, Integer> ticks;
	private GList<Method> astarts;
	private GList<Method> astops;
	private GMap<Method, Integer> aticks;
	private IPawn instance;

	public PawnObject(Class<? extends IPawn> pawn) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		this(pawn.getConstructor().newInstance());
	}

	@SuppressWarnings("unchecked")
	public PawnObject(IPawn ins) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		pawns = new GMap<Field, PawnObject>();
		starts = new GList<Method>();
		astarts = new GList<Method>();
		stops = new GList<Method>();
		astops = new GList<Method>();
		ticks = new GMap<Method, Integer>();
		aticks = new GMap<Method, Integer>();
		instance = ins;
		Class<? extends IPawn> pawn = instance.getClass();

		for(Field i : pawn.getDeclaredFields())
		{
			if(i.isAnnotationPresent(Instance.class))
			{
				new V(instance).set(i.getName(), instance);
			}

			else if(i.isAnnotationPresent(Control.class))
			{
				pawns.put(i, new PawnObject((Class<? extends IPawn>) i.getType()));
				new V(instance).set(i.getName(), pawns.get(i).instance);

				for(Field j : pawns.get(i).instance.getClass().getDeclaredFields())
				{
					if(j.isAnnotationPresent(Parent.class))
					{
						new V(pawns.get(i).instance).set(j.getName(), instance);
					}
				}
			}
		}

		for(Method i : pawn.getDeclaredMethods())
		{
			if(i.isAnnotationPresent(Start.class))
			{
				i.setAccessible(true);

				if(i.isAnnotationPresent(Async.class))
				{
					astarts.add(i);
				}

				else
				{
					starts.add(i);
				}
			}

			if(i.isAnnotationPresent(Stop.class))
			{
				i.setAccessible(true);

				if(i.isAnnotationPresent(Async.class))
				{
					astops.add(i);
				}

				else
				{
					stops.add(i);
				}
			}

			if(i.isAnnotationPresent(Tick.class))
			{
				i.setAccessible(true);

				if(i.isAnnotationPresent(Async.class))
				{
					aticks.put(i, i.getAnnotation(Tick.class).value());
				}

				else
				{
					ticks.put(i, i.getAnnotation(Tick.class).value());
				}
			}
		}
	}

	public void tick() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for(Method i : ticks.k())
		{
			if(TICK.tick % ticks.get(i) == 0)
			{
				i.invoke(instance);
			}
		}

		if(!aticks.isEmpty())
		{
			new A()
			{
				@Override
				public void run()
				{
					for(Method i : ticks.k())
					{
						if(TICK.tick % ticks.get(i) == 0)
						{
							try
							{
								i.invoke(instance);
							}

							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			};
		}
	}

	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for(Field i : pawns.k())
		{
			pawns.get(i).start();
		}

		for(Method i : starts)
		{
			i.invoke(instance);
		}

		if(!astarts.isEmpty())
		{
			new A()
			{
				@Override
				public void run()
				{
					for(Method i : astarts)
					{
						try
						{
							i.invoke(instance);
						}

						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			};
		}
	}

	public void stop() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		for(Field i : pawns.k())
		{
			pawns.get(i).stop();
		}

		for(Method i : stops)
		{
			i.invoke(instance);
		}

		if(!astops.isEmpty())
		{
			new A()
			{
				@Override
				public void run()
				{
					for(Method i : astops)
					{
						try
						{
							i.invoke(instance);
						}

						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			};
		}
	}

	public GMap<Field, PawnObject> getPawns()
	{
		return pawns;
	}

	public GList<Method> getStarts()
	{
		return starts;
	}

	public GList<Method> getStops()
	{
		return stops;
	}

	public GMap<Method, Integer> getTicks()
	{
		return ticks;
	}

	public GList<Method> getAstarts()
	{
		return astarts;
	}

	public GList<Method> getAstops()
	{
		return astops;
	}

	public GMap<Method, Integer> getAticks()
	{
		return aticks;
	}

	public IPawn getInstance()
	{
		return instance;
	}
}
