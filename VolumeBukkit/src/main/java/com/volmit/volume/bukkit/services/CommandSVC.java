package com.volmit.volume.bukkit.services;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import com.volmit.volume.bukkit.L;
import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.VolumePlugin;
import com.volmit.volume.bukkit.command.CommandTag;
import com.volmit.volume.bukkit.command.ICommand;
import com.volmit.volume.bukkit.command.RouterCommand;
import com.volmit.volume.bukkit.command.VirtualCommand;
import com.volmit.volume.bukkit.pawn.IPawn;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.reflect.V;

public class CommandSVC implements CommandExecutor, IService
{
	private GMap<GList<String>, VirtualCommand> commands;

	@Start
	public void onStart()
	{
		commands = new GMap<GList<String>, VirtualCommand>();
	}

	public void register(IPawn pawn)
	{
		if(pawn == null)
		{
			return;
		}

		for(Field i : pawn.getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(com.volmit.volume.bukkit.command.Command.class))
			{
				try
				{
					String tag = "";

					if(pawn.getClass().isAnnotationPresent(CommandTag.class))
					{
						tag = pawn.getClass().getAnnotation(CommandTag.class).value();
					}

					String ct = i.getAnnotation(com.volmit.volume.bukkit.command.Command.class).value();

					if(ct.length() > 0)
					{
						tag = ct;
					}

					ICommand cmd = (ICommand) i.getType().getConstructor().newInstance();
					new V(pawn).set(i.getName(), cmd);
					U.getPawnObject(this).attach(cmd);
					commands.put(cmd.getAllNodes(), new VirtualCommand(cmd, tag));
					PluginCommand cc = VolumePlugin.vpi.getCommand(cmd.getNode().toLowerCase());

					if(cc != null)
					{
						cc.setExecutor(this);
					}

					else
					{
						L.l("The command /" + cmd.getNode().toLowerCase() + " does not exist. Using magical powers to force register.");
						((CommandMap) new V(Bukkit.getServer()).get("commandMap")).register("", new RouterCommand(cmd, this));
					}
				}

				catch(Exception e)
				{
					e.printStackTrace();
					L.l("Unable to register commands for " + pawn.getClass().getSimpleName());
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		GList<String> chain = new GList<String>().qadd(args);

		for(GList<String> i : commands.k())
		{
			for(String j : i)
			{
				if(j.equalsIgnoreCase(label))
				{
					VirtualCommand cmd = commands.get(i);

					if(cmd.hit(sender, chain.copy()))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
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
		CommandSVC other = (CommandSVC) obj;
		if(commands == null)
		{
			if(other.commands != null)
			{
				return false;
			}
		}
		else if(!commands.equals(other.commands))
		{
			return false;
		}
		return true;
	}
}
