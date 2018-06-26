package com.volmit.lux.model;

import java.awt.Color;

import com.volmit.volume.lang.collections.GList;

public interface IScene
{
	public GList<Color> getColors();

	public double getBrightness();
}
