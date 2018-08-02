package com.volmit.volume.lang.sched;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.volmit.volume.lang.collections.GList;

public class OP
{
	private GList<GList<EOP>> ops;

	public OP()
	{
		ops = new GList<GList<EOP>>();
	}

	public void run(ExecutorService svc) throws InterruptedException
	{
		int at = 0;

		if(ops.hasIndex(at))
		{
			execute(at, svc);
		}
	}

	private void execute(int index, ExecutorService svc) throws InterruptedException
	{
		GList<Future<?>> f = new GList<Future<?>>();

		for(EOP i : ops.get(index))
		{
			f.add(svc.submit(() -> i.run()));
		}

		while(true)
		{
			boolean sleep = false;

			for(Future<?> i : f)
			{
				if(!i.isDone())
				{
					sleep = true;
					break;
				}
			}

			if(sleep)
			{
				Thread.sleep(5);
			}

			else
			{
				break;
			}
		}

		if(ops.hasIndex(index + 1))
		{
			execute(index + 1, svc);
		}
	}

	public OP then(EOP eop)
	{
		ops.add(new GList<EOP>().qadd(eop));
		return this;
	}

	public OP and(EOP eop)
	{
		if(ops.isEmpty())
		{
			return then(eop);
		}

		ops.get(ops.last()).add(eop);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> OP itr(RTV<Iterable<T>> in, EF<T> func)
	{
		Iterable<?>[] m = {null};
		then(() -> m[0] = in.run()).then(() ->
		{
			for(Object i : m[0])
			{
				then(() -> func.run((T) i));
			}
		});

		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> OP itrp(RTV<Iterable<T>> in, EF<T> func)
	{
		Iterable<?>[] m = {null};
		then(() -> m[0] = in.run()).then(() ->
		{
			boolean t = false;

			for(Object i : m[0])
			{
				if(!t)
				{
					then(() -> func.run((T) i));
					t = true;
				}

				else
				{
					and(() -> func.run((T) i));
				}
			}
		});

		return this;
	}
}
