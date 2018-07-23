package com.volmit.volume.bukkit.nms.adapter;

import org.bukkit.Location;

import com.volmit.volume.bukkit.U;
import com.volmit.volume.bukkit.nms.NMSSVC;
import com.volmit.volume.bukkit.task.A;
import com.volmit.volume.bukkit.util.world.MaterialBlock;
import com.volmit.volume.lang.collections.GMap;

public class EditQueue
{
	private GMap<Location, MaterialBlock> queue;
	private boolean flushing;
	private long count;

	public EditQueue()
	{
		count = 0;
		flushing = false;
		queue = new GMap<Location, MaterialBlock>();
	}

	public void queue(Location l, MaterialBlock mb)
	{
		queue.put(l, mb);
		count++;
	}

	public void startFlushing()
	{
		flushing = true;

		new A()
		{
			@Override
			public void run()
			{
				while(flushing)
				{
					GMap<Location, MaterialBlock> p = new GMap<Location, MaterialBlock>(queue);

					for(Location i : p.keySet())
					{
						U.getService(NMSSVC.class).setBlock(i, p.get(i));
						U.getService(NMSSVC.class).queueChunkUpdate(i.getChunk());
						queue.remove(i);
					}

				}
			}
		};
	}

	public void complete()
	{
		flushing = false;
		flush();
	}

	public void flush()
	{
		for(Location i : queue.keySet())
		{
			U.getService(NMSSVC.class).setBlock(i, queue.get(i));
			U.getService(NMSSVC.class).queueChunkUpdate(i.getChunk());
		}

		queue.clear();
	}

	public long getCount()
	{
		return count;
	}
}
