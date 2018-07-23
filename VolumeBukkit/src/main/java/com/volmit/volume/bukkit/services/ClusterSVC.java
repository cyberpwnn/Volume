package com.volmit.volume.bukkit.services;

import java.io.File;
import java.util.function.Function;

import com.volmit.volume.bukkit.L;
import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.pawn.Async;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.pawn.Stop;
import com.volmit.volume.bukkit.pawn.Tick;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.lang.collections.GMap;

public class ClusterSVC implements IService
{
	public GMap<File, IPawn> pawns;
	public GMap<File, Function<File, Boolean>> rrs;
	public GMap<File, Long> vals;

	@Start
	public void onStart()
	{
		pawns = new GMap<File, IPawn>();
		rrs = new GMap<File, Function<File, Boolean>>();
		vals = new GMap<File, Long>();
	}

	@Stop
	public void onStop()
	{

	}

	@Async
	@Tick(5)
	public void onTick()
	{
		for(File i : pawns.k())
		{
			IPawn p = pawns.get(i);

			if(!i.exists() || !vals.containsKey(i) || vals.get(i) != i.lastModified() + i.length())
			{
				try
				{
					U.read(i, p);
					vals.put(i, i.lastModified() + i.length());
					L.l("Injected Config: " + p.getClass().getSimpleName() + " -> " + i.getPath());
				}

				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		for(File i : vals.k())
		{
			if(!pawns.containsKey(i))
			{
				if(!i.exists() || !vals.containsKey(i) || vals.get(i) != i.lastModified() + i.length())
				{
					try
					{
						vals.put(i, i.lastModified() + i.length());
						L.l("Injected Config -> " + i.getPath());
						rrs.get(i).apply(i);
					}

					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void unbind(File file)
	{
		pawns.remove(file);
		vals.remove(file);
		rrs.remove(file);
	}

	public File getFile(IPawn pawn)
	{
		for(File i : pawns.k())
		{
			if(pawns.get(i).equals(pawn))
			{
				return i;
			}
		}

		return null;
	}

	public void unbind(IPawn pawn)
	{
		File ff = getFile(pawn);
		unbind(ff);
		L.l("Unbound Config " + pawn.getClass().getSimpleName() + " -/> " + ff.getPath());
	}

	public void bind(File file, IPawn pawn)
	{
		file.getParentFile().mkdirs();
		L.l("Binding Config " + pawn.getClass().getSimpleName() + " -> " + file.getPath());
		pawns.put(file, pawn);
	}

	public void bind(File file, Function<File, Boolean> r)
	{
		file.getParentFile().mkdirs();
		L.l("Binding Config " + rrs.getClass().getSimpleName() + " -> " + file.getPath());
		rrs.put(file, r);
		vals.put(file, file.lastModified() + file.length());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pawns == null) ? 0 : pawns.hashCode());
		result = prime * result + ((vals == null) ? 0 : vals.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
		{
			return true;
		}
		if(obj == null)
		{
			return false;
		}
		if(getClass() != obj.getClass())
		{
			return false;
		}
		ClusterSVC other = (ClusterSVC) obj;
		if(pawns == null)
		{
			if(other.pawns != null)
			{
				return false;
			}
		}
		else if(!pawns.equals(other.pawns))
		{
			return false;
		}
		if(vals == null)
		{
			if(other.vals != null)
			{
				return false;
			}
		}
		else if(!vals.equals(other.vals))
		{
			return false;
		}
		return true;
	}
}
