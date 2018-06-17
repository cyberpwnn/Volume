package com.volmit.volume.lang.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PressurePool implements Pool
{
	private ExecutorService svc;

	public PressurePool(int threads)
	{
		svc = Executors.newWorkStealingPool(threads);
	}

	@Override
	public void shove(Runnable op)
	{
		svc.execute(op);
	}

	@Override
	public void shutDown()
	{
		svc.shutdown();
	}

	@Override
	public void shutDownNow()
	{
		svc.shutdownNow();
	}
}
