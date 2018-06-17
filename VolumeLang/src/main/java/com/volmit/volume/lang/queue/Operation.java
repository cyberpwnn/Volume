package com.volmit.volume.lang.queue;

public interface Operation extends Runnable
{
	public int getPriority();

	public String id();
}
