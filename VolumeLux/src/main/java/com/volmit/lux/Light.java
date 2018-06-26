package com.volmit.lux;

import java.awt.Color;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLight.PHLightColorMode;
import com.philips.lighting.model.PHLightState;
import com.volmit.lux.model.ILight;
import com.volmit.volume.math.M;

public class Light implements ILight
{
	private PHLight light;
	private int brightness = 254;

	public Light(PHLight light)
	{
		this.light = light;
	}

	@Override
	public Color getColor()
	{
		return Color.BLACK;
	}

	@Override
	public void setColor(Color cc)
	{
		setColor(cc, 50);
	}

	public static double[] getRGBtoXY(Color c)
	{
		double[] normalizedToOne = new double[3];
		float cred, cgreen, cblue;
		cred = c.getRed();
		cgreen = c.getGreen();
		cblue = c.getBlue();
		normalizedToOne[0] = (cred / 255);
		normalizedToOne[1] = (cgreen / 255);
		normalizedToOne[2] = (cblue / 255);
		float red, green, blue;

		if(normalizedToOne[0] > 0.04045)
		{
			red = (float) Math.pow((normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
		}
		else
		{
			red = (float) (normalizedToOne[0] / 12.92);
		}

		if(normalizedToOne[1] > 0.04045)
		{
			green = (float) Math.pow((normalizedToOne[1] + 0.055) / (1.0 + 0.055), 2.4);
		}

		else
		{
			green = (float) (normalizedToOne[1] / 12.92);
		}

		if(normalizedToOne[2] > 0.04045)
		{
			blue = (float) Math.pow((normalizedToOne[2] + 0.055) / (1.0 + 0.055), 2.4);
		}

		else
		{
			blue = (float) (normalizedToOne[2] / 12.92);
		}

		float X = (float) (red * 0.649926 + green * 0.103455 + blue * 0.197109);
		float Y = (float) (red * 0.234327 + green * 0.743075 + blue * 0.022598);
		float Z = (float) (red * 0.0000000 + green * 0.053077 + blue * 1.035763);
		float x = X / (X + Y + Z);
		float y = Y / (X + Y + Z);
		double[] xy = new double[2];
		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	@Override
	public String getName()
	{
		return light.getName();
	}

	@Override
	public void setColor(Color color, int transitionTime)
	{
		PHLightState s = light.getLastKnownLightState();

		if(!s.isOn())
		{
			s.setOn(true);
		}

		s.setColorMode(PHLightColorMode.COLORMODE_XY);
		double[] ff = getRGBtoXY(color);
		s.setX((float) ff[0]);
		s.setY((float) ff[1]);

		s.setTransitionTime(transitionTime);

		if(s.getBrightness() != getBrightness() * 254)
		{
			s.setBrightness((int) (getBrightness() * 254));
		}

		LUX.queue(light, s);
	}

	@Override
	public void setBrightness(double pct)
	{
		brightness = (int) (M.clip(pct, 0D, 1D));
	}

	@Override
	public double getBrightness()
	{
		return brightness;
	}
}
