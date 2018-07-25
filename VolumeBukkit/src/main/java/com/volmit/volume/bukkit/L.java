package com.volmit.volume.bukkit;

import java.util.logging.Level;

/**
 * Basic logging using the plugin logger
 *
 * @author cyberpwn
 *
 */
public class L
{
	/**
	 * Indicates log level INFO (log)
	 *
	 * @param s
	 *            list of objects to print.
	 */
	public static void l(Object... s)
	{
		VolumePlugin.vpi.getLogger().log(Level.INFO, combine(s));
	}

	/**
	 * Indicates log level WARNING (warn)
	 *
	 * @param s
	 *            list of objects to print
	 */
	public static void w(Object... s)
	{
		VolumePlugin.vpi.getLogger().log(Level.WARNING, combine(s));
	}

	/**
	 * Indicates log level SEVERE (failure)
	 *
	 * @param s
	 *            list of objects to print
	 */
	public static void f(Object... s)
	{
		VolumePlugin.vpi.getLogger().log(Level.SEVERE, combine(s));
	}

	/**
	 * Indicates log level FINEST (verbose)
	 *
	 * @param s
	 *            list of objects to print
	 */
	public static void v(Object... s)
	{
		VolumePlugin.vpi.getLogger().log(Level.FINEST, combine(s));
	}

	private static String combine(Object... s)
	{
		if(s.length == 0)
		{
			return "";
		}

		if(s.length == 1)
		{
			return s[0].toString();
		}

		StringBuilder sb = new StringBuilder();

		for(Object i : s)
		{
			sb.append(i);
		}

		return sb.toString();
	}
}
