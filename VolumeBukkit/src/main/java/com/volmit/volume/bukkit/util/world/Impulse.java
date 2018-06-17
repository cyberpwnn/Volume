package com.volmit.volume.bukkit.util.world;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import com.volmit.volume.bukkit.util.physics.VectorMath;

public class Impulse
{
	private double radius;
	private double forceMax;
	private double forceMin;

	public Impulse(double radius)
	{
		this.radius = radius;
		this.forceMax = 1;
		this.forceMin = 0;
	}

	public Impulse radius(double radius)
	{
		this.radius = radius;
		return this;
	}

	public Impulse force(double force)
	{
		this.forceMax = force;
		return this;
	}

	public Impulse force(double forceMax, double forceMin)
	{
		this.forceMax = forceMax;
		this.forceMin = forceMin;
		return this;
	}

	public void punch(Location at)
	{
		Area a = new Area(at, radius);

		for(Entity i : a.getNearbyEntities())
		{
			Vector force = VectorMath.direction(at, i.getLocation());

			if(forceMin < forceMax)
			{
				double distance = i.getLocation().distance(at);
				force.multiply(((1D - (distance / radius)) * (forceMax - forceMin)) + forceMin);
			}

			i.setVelocity(i.getVelocity().add(force));
		}
	}
}
