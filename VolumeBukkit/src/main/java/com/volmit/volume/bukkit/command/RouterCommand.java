package com.volmit.volume.bukkit.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RouterCommand extends org.bukkit.command.Command
{
	private CommandExecutor ex;

	public RouterCommand(ICommand realCommand, CommandExecutor ex)
	{
		super(realCommand.getNode().toLowerCase());
		setAliases(realCommand.getNodes());

		this.ex = ex;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args)
	{
		return ex.onCommand(sender, this, commandLabel, args);
	}
}
