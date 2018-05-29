package com.volmit.volume.memoryagent;

import java.lang.instrument.Instrumentation;

public class VolumeAgent
{
	private static Instrumentation instrumentation;

	public static void premain(String args, Instrumentation inst)
	{
		System.out.println("[Volume AGENT]: Starting Agent");
		instrumentation = inst;
	}

	public static Instrumentation getInstrumentation()
	{
		return instrumentation;
	}

	public static boolean isOnline()
	{
		return instrumentation != null;
	}

	public static long getObjectSize(Object o)
	{
		return instrumentation.getObjectSize(o);
	}
}