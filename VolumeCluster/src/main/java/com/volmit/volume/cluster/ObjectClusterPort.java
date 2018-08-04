package com.volmit.volume.cluster;

import java.lang.reflect.Field;

public class ObjectClusterPort<T> implements IClusterPort<T>
{
	private T o;

	public ObjectClusterPort(T initial)
	{
		o = initial;
	}

	public ObjectClusterPort()
	{
		this(null);
	}

	@Override
	public DataCluster toCluster(T t) throws Exception
	{
		DataCluster cc = new DataCluster();

		for(Field i : t.getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(CFG.class))
			{
				cc.set(i.getAnnotation(CFG.class).value(), i.get(this));
			}
		}

		return cc;
	}

	@Override
	public T fromCluster(DataCluster c) throws Exception
	{
		for(Field i : o.getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(CFG.class))
			{
				String id = i.getAnnotation(CFG.class).value();

				if(c.has(id))
				{
					i.set(this, c.get(id));
				}
			}
		}

		return o;
	}
}
