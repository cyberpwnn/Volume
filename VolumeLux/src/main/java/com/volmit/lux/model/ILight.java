package com.volmit.lux.model;

import java.awt.Color;

public interface ILight
{
	public Color getColor();

	public void setColor(Color color);

	public void setColor(Color color, int transitionTime);

	public void setBrightness(double pct);

	public double getBrightness();

	public String getName();
}
