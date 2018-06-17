package com.volmit.volume.bukkit.command;

import java.lang.reflect.Field;

import org.bukkit.command.CommandSender;

import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.reflect.V;

public class VirtualCommand
{
	private ICommand command;
	private String tag;

	private GMap<GList<String>, VirtualCommand> children;

	public VirtualCommand(ICommand command)
	{
		this(command, "");
	}

	public VirtualCommand(ICommand command, String tag)
	{
		this.command = command;
		children = new GMap<GList<String>, VirtualCommand>();
		this.tag = tag;

		if(command.getClass().isAnnotationPresent(CommandTag.class))
		{
			this.tag = command.getClass().getAnnotation(CommandTag.class).value();
		}

		for(Field i : command.getClass().getDeclaredFields())
		{
			if(i.isAnnotationPresent(Command.class))
			{
				try
				{
					ICommand cmd = (ICommand) i.getType().getConstructor().newInstance();
					new V(command).set(i.getName(), cmd);
					children.put(cmd.getAllNodes(), new VirtualCommand(cmd, tag));
				}

				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public ICommand getCommand()
	{
		return command;
	}

	public GMap<GList<String>, VirtualCommand> getChildren()
	{
		return children;
	}

	public boolean hit(CommandSender sender, GList<String> chain)
	{
		VolumeSender vs = new VolumeSender(sender);
		vs.setTag(tag);

		if(chain.isEmpty())
		{
			return command.handle(vs, new String[0]);
		}

		String nl = chain.get(0);

		for(GList<String> i : children.k())
		{
			for(String j : i)
			{
				if(j.equalsIgnoreCase(nl))
				{
					VirtualCommand cmd = children.get(i);
					GList<String> c = chain.copy();
					c.remove(0);
					if(cmd.hit(sender, c))
					{
						return true;
					}
				}
			}
		}

		return command.handle(vs, chain.toArray(new String[chain.size()]));
	}
}
