package com.volmit.volmume.lang.sched;

@FunctionalInterface
public interface EF<T>
{
	public void run(T t);
}
