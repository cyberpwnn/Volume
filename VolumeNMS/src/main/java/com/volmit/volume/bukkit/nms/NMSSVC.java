package com.volmit.volume.bukkit.nms;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.volmit.volume.bukkit.L;
import com.volmit.volume.bukkit.nms.adapter.NMSA10;
import com.volmit.volume.bukkit.nms.adapter.NMSA11;
import com.volmit.volume.bukkit.nms.adapter.NMSA12;
import com.volmit.volume.bukkit.nms.adapter.NMSA8;
import com.volmit.volume.bukkit.nms.adapter.NMSA92;
import com.volmit.volume.bukkit.nms.adapter.NMSA94;
import com.volmit.volume.bukkit.pawn.Start;
import com.volmit.volume.bukkit.pawn.Stop;
import com.volmit.volume.bukkit.service.IService;
import com.volmit.volume.bukkit.util.net.Protocol;
import com.volmit.volume.bukkit.util.net.ProtocolRange;

public class NMSSVC implements IService, IAdapter
{
	private IAdapter ia;

	@Start
	public void start()
	{
		Protocol cp = Protocol.getProtocolVersion();
		ia = null;

		if(Protocol.R1_8.to(Protocol.R1_8_9).contains(cp))
		{
			ia = new NMSA8();
		}

		else if(Protocol.R1_9.to(Protocol.R1_9_2).contains(cp))
		{
			ia = new NMSA92();
		}

		else if(Protocol.R1_9_3.to(Protocol.R1_9_4).contains(cp))
		{
			ia = new NMSA94();
		}

		else if(Protocol.R1_10.to(Protocol.R1_10_2).contains(cp))
		{
			ia = new NMSA10();
		}

		else if(Protocol.R1_11.to(Protocol.R1_11_2).contains(cp))
		{
			ia = new NMSA11();
		}

		else if(Protocol.R1_12.to(Protocol.R1_12_2).contains(cp))
		{
			ia = new NMSA12();
		}

		L.l("NMS Adapter: " + (ia == null ? "NONE" : ia.getClass().getCanonicalName()));
	}

	public boolean hasBinding()
	{
		return ia != null;
	}

	@Stop
	public void stop()
	{

	}

	@Override
	public ProtocolRange getSupportedProtocol()
	{
		if(!hasBinding())
		{
			return null;
		}

		return ia.getSupportedProtocol();
	}

	@Override
	public void addPacketHandler(IPacketHandler h)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.addPacketHandler(h);
	}

	@Override
	public void removePacketHandler(IPacketHandler h)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.removePacketHandler(h);
	}

	@Override
	public void sendPacket(Object packet)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.sendPacket(packet);
	}

	@Override
	public void sendPacket(Object packet, World world)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.sendPacket(packet, world);
	}

	@Override
	public void sendPacket(Object packet, Player player)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.sendPacket(packet, player);
	}

	@Override
	public void sendPacket(Object packet, Location location)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.sendPacket(packet, location);
	}

	@Override
	public void sendPacket(Object packet, Chunk chunk)
	{
		if(!hasBinding())
		{
			return;
		}

		ia.sendPacket(packet, chunk);
	}

	@Override
	public int getViewDistance(Player player)
	{
		if(!hasBinding())
		{
			return -1;
		}

		return ia.getViewDistance(player);
	}

	@Override
	public boolean canSee(Player player, Location location)
	{
		if(!hasBinding())
		{
			return false;
		}

		return ia.canSee(player, location);
	}

	@Override
	public boolean canSee(Player player, Chunk chunk)
	{
		if(!hasBinding())
		{
			return false;
		}

		return ia.canSee(player, chunk);
	}

	@Override
	public void sendPickup(Entity drop, Entity who)
	{
		if(!hasBinding())
		{
			return;
		}
		ia.sendPickup(drop, who);
	}
}
