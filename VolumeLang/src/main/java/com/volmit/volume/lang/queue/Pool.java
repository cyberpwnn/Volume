package com.volmit.volume.lang.queue;

public interface Pool
{
	public void shove(Runnable op);

	public void shutDown();

	public void shutDownNow();
}
