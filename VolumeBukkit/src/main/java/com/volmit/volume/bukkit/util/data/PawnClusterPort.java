package com.volmit.volume.bukkit.util.data;

import java.lang.reflect.Field;

import com.volmit.volume.bukkit.pawn.Comment;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.Node;
import com.volmit.volume.cluster.DataCluster;
import com.volmit.volume.cluster.IClusterPort;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.reflect.V;

public class PawnClusterPort implements IClusterPort<IPawn>
{
	private IPawn pawn;

	public PawnClusterPort(IPawn pawn)
	{
		this.pawn = pawn;
	}

	public DataCluster toCluster() throws Exception
	{
		return toCluster(pawn);
	}

	@Override
	public DataCluster toCluster(IPawn t) throws Exception
	{
		Class<?> c = t.getClass();
		DataCluster cc = new DataCluster();

		for(Field i : c.getDeclaredFields())
		{
			if(i.isAnnotationPresent(Node.class))
			{
				String key = i.getAnnotation(Node.class).value();
				String comment = null;

				if(i.isAnnotationPresent(Comment.class))
				{
					comment = i.getAnnotation(Comment.class).value();
				}

				cc.set(key, (Object) new V(t).get(i.getName()));

				if(comment != null)
				{
					cc.setComment(key, comment);
				}
			}
		}

		return cc;
	}

	@Override
	public IPawn fromCluster(DataCluster c) throws Exception
	{
		IPawn p = pawn;
		GList<Field> ff = new GList<Field>(pawn.getClass().getDeclaredFields());

		for(String i : c.k())
		{
			for(Field j : ff)
			{
				if(j.isAnnotationPresent(Node.class))
				{
					if(j.getAnnotation(Node.class).value().equals(i))
					{
						new V(p).set(j.getName(), c.get(i));
					}
				}
			}
		}

		return p;
	}
}
