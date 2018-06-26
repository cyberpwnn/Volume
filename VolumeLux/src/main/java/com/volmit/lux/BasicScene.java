package com.volmit.lux;

import java.awt.Color;

import com.volmit.lux.model.IScene;
import com.volmit.volume.lang.collections.GList;

public class BasicScene implements IScene
{
	private GList<Color> colors;
	private double brightness;

	public BasicScene()
	{
		colors = new GList<Color>();
		brightness = 1;
	}

	@Override
	public GList<Color> getColors()
	{
		return colors;
	}

	@Override
	public double getBrightness()
	{
		return brightness;
	}
}
