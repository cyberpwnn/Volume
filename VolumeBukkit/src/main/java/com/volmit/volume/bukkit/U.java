package com.volmit.volume.bukkit;

import java.io.File;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.PawnObject;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.bukkit.util.data.PawnClusterPort;
import com.volmit.volume.bukkit.util.data.YAMLClusterPort;
import com.volmit.volume.cluster.DataCluster;
import com.volmit.volume.lang.collections.GList;

public class U
{
	public static void register(Listener l)
	{
		Bukkit.getPluginManager().registerEvents(l, VolumePlugin.vpi);
	}

	public static void unregister(Listener l)
	{
		HandlerList.unregisterAll(l);
	}

	public static <T extends IService> T getService(Class<? extends T> t)
	{
		return VolumePlugin.vpi.getService(t);
	}

	public static GList<IPawn> getChildren(IPawn pawn)
	{
		GList<IPawn> pa = new GList<IPawn>();
		PawnObject pw = getPawnObject(pawn);

		if(pw == null)
		{
			L.l("Unable to find pawn " + pawn.getClass().getSimpleName());
			return pa;
		}

		for(PawnObject i : pw.getPawns().v())
		{
			pa.add(i.getInstance());
		}

		for(PawnObject i : pw.getAttachedPawns())
		{
			pa.add(i.getInstance());
		}

		return pa;
	}

	public static void read(File f, IPawn pawn) throws Exception
	{
		if(!f.exists())
		{
			DataCluster cc = toDataCluster(pawn);
			String s = new YAMLClusterPort().fromCluster(cc).saveToString();
			String r = YAMLClusterPort.applyComments(cc, s);
			PrintWriter pw = new PrintWriter(f);
			pw.println(r);
			pw.close();
		}

		FileConfiguration fc = new YamlConfiguration();
		fc.load(f);
		DataCluster cFile = new YAMLClusterPort().toCluster(fc);
		DataCluster cDefault = new PawnClusterPort(pawn).toCluster();

		for(String i : cDefault.k())
		{
			if(!cFile.has(i))
			{
				cFile.set(i, (Object) cDefault.get(i));
			}

			if(cDefault.hasComment(i))
			{
				cFile.setComment(i, cDefault.getComment(i));
			}
		}

		new PawnClusterPort(pawn).fromCluster(cFile);
		String s = new YAMLClusterPort().fromCluster(cFile).saveToString();
		String r = YAMLClusterPort.applyComments(cFile, s);
		PrintWriter pw = new PrintWriter(f);
		pw.println(r);
		pw.close();
	}

	public static PawnObject getPawnObject(IPawn pawn)
	{
		return getPawnObject(VolumePlugin.vpi.getPawnManager().getBase(), pawn);
	}

	public static PawnObject getPawnObject(PawnObject from, IPawn pawn)
	{
		PawnObject o = null;

		for(PawnObject i : from.getPawns().v())
		{
			if(i.getInstance().equals(pawn))
			{
				return i;
			}

			else
			{
				o = getPawnObject(i, pawn);
			}
		}

		if(o == null)
		{
			for(PawnObject i : from.getAttachedPawns())
			{
				if(i.getInstance().equals(pawn))
				{
					return i;
				}

				else
				{
					o = getPawnObject(i, pawn);
				}
			}
		}

		return o;
	}

	public static void writeYML(IPawn pawn, File file) throws Exception
	{
		DataCluster cc = toDataCluster(pawn);
		String s = new YAMLClusterPort().fromCluster(cc).saveToString();
		String r = YAMLClusterPort.applyComments(cc, s);
		PrintWriter pw = new PrintWriter(file);
		pw.println(r);
		pw.close();
	}

	public static DataCluster toDataCluster(IPawn pawn) throws Exception
	{
		return new PawnClusterPort(pawn).toCluster();
	}

	public static void readYML(IPawn pawn, File f) throws Exception
	{
		FileConfiguration fc = new YamlConfiguration();
		fc.load(f);
		fromDataCluster(pawn, new YAMLClusterPort().toCluster(fc));
	}

	public static void fromDataCluster(IPawn pawn, DataCluster cc) throws Exception
	{
		new PawnClusterPort(pawn).fromCluster(cc);
	}
}
