package com.volmit.volume.bukkit.command;

import java.util.Set;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import com.volmit.volume.bukkit.util.text.C;

public class VolumeSender implements CommandSender
{
	private CommandSender s;
	private String tag;

	public VolumeSender(CommandSender s)
	{
		tag = "";
		this.s = s;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public String getTag()
	{
		return tag;
	}

	@Override
	public boolean isPermissionSet(String name)
	{
		return s.isPermissionSet(name);
	}

	@Override
	public boolean isPermissionSet(Permission perm)
	{
		return s.isPermissionSet(perm);
	}

	@Override
	public boolean hasPermission(String name)
	{
		return s.hasPermission(name);
	}

	@Override
	public boolean hasPermission(Permission perm)
	{
		return s.hasPermission(perm);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
	{
		return s.addAttachment(plugin, name, value);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin)
	{
		return s.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
	{
		return s.addAttachment(plugin, name, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks)
	{
		return s.addAttachment(plugin, ticks);
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment)
	{
		s.removeAttachment(attachment);
	}

	@Override
	public void recalculatePermissions()
	{
		s.recalculatePermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return s.getEffectivePermissions();
	}

	@Override
	public boolean isOp()
	{
		return s.isOp();
	}

	@Override
	public void setOp(boolean value)
	{
		s.setOp(value);
	}

	@Override
	public void sendMessage(String message)
	{
		;
		s.sendMessage(C.translateAlternateColorCodes('&', getTag()) + message);
	}

	@Override
	public void sendMessage(String[] messages)
	{
		s.sendMessage(C.translateAlternateColorCodes('&', getTag()) + messages);
	}

	@Override
	public Server getServer()
	{
		return s.getServer();
	}

	@Override
	public String getName()
	{
		return s.getName();
	}

	@Override
	public Spigot spigot()
	{
		return s.spigot();
	}
}
