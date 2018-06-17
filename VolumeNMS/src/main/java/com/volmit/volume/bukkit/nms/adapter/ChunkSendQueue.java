package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.Chunk;

import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.nms.NMSSVC;
import com.volmit.volume.bukkit.task.A;
import com.volmit.volume.bukkit.task.S;
import com.volmit.volume.bukkit.task.SR;
import com.volmit.volume.lang.collections.GList;

public class ChunkSendQueue
{
	private GList<Chunk> c = new GList<Chunk>();
	private boolean running;
	private SR s;
	private int interval;
	private int volume;

	public ChunkSendQueue(int interval, int volume)
	{
		this.interval = interval;
		this.volume = volume;
		c = new GList<Chunk>();
		running = false;
	}

	public void start()
	{
		s = new SR(interval)
		{
			@Override
			public void run()
			{
				if(c.isEmpty() || running)
				{
					return;
				}

				int l = volume;
				GList<Chunk> tosend = new GList<Chunk>();

				while(!c.isEmpty() && l > 0)
				{
					l--;
					tosend.add(c.pop());
				}

				running = true;
				new A()
				{
					@Override
					public void run()
					{
						for(Chunk i : tosend)
						{

							new S()
							{
								@Override
								public void run()
								{
									U.getService(NMSSVC.class).relight(i);
									U.getService(NMSSVC.class).sendChunkMap(new AbstractChunk(i), i);
								}
							};
						}

						running = false;
					}
				};
			}
		};
	}

	public void stop()
	{
		s.cancel();
	}

	public boolean isRunning()
	{
		return running;
	}

	public void queue(Chunk c)
	{
		if(!this.c.contains(c))
		{
			this.c.add(c);
		}
	}
}
