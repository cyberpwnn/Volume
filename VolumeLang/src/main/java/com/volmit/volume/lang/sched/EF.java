package com.volmit.volume.lang.sched;

@FunctionalInterface
public interface EF<T>
{
	public void run(T t);
}
