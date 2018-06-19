package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.Chunk;

import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.nms.NMSSVC;
import com.volmit.volume.bukkit.task.A;
import com.volmit.volume.bukkit.task.S;
import com.volmit.volume.bukkit.task.SR;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;
import com.volmit.volume.lang.collections.GSet;

public class ChunkSendQueue
{
	private GList<Chunk> c = new GList<Chunk>();
	private GMap<Chunk, GSet<Integer>> sections;
	private boolean running;
	private SR s;
	private int interval;
	private int volume;

	public ChunkSendQueue(int interval, int volume)
	{
		this.interval = interval;
		this.volume = volume;
		c = new GList<Chunk>();
		sections = new GMap<Chunk, GSet<Integer>>();
		running = false;
	}

	public void start()
	{
		s = new SR(interval)
		{
			@Override
			public void run()
			{
				if(!sections.isEmpty())
				{
					int l = volume;

					while(l > 0 && !sections.isEmpty())
					{
						Chunk c = sections.k().pop();
						GSet<Integer> s = sections.get(c);

						if(s.isEmpty())
						{
							sections.remove(c);
							continue;
						}

						U.getService(NMSSVC.class).updateSections(c, s);
						sections.remove(c);
						l--;
					}
				}

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

	public void queueSection(Chunk c, int section)
	{
		if(!sections.containsKey(c))
		{
			sections.put(c, new GSet<Integer>());
		}

		sections.get(c).add(section);
	}
}
