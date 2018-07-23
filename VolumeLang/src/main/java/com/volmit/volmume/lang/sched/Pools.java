package com.volmit.volmume.lang.sched;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinWorkerThread;

public class Pools
{
	public ExecutorService newForkJoinPool(int threadCount, String prefix)
	{
		return new ForkJoinPool(threadCount, new ForkJoinWorkerThreadFactory()
		{
			@Override
			public ForkJoinWorkerThread newThread(ForkJoinPool pool)
			{
				final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
				worker.setName(prefix + worker.getPoolIndex());
				return worker;
			}
		}, new UncaughtExceptionHandler()
		{
			@Override
			public void uncaughtException(Thread t, Throwable e)
			{
				System.out.println("Exception in " + t.getName() + "\n" + e.getMessage() + "\n" + e.toString());
			}
		}, true);
	}
}
