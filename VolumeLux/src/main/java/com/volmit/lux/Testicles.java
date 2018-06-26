package com.volmit.lux;

import java.awt.Color;

import com.volmit.lux.model.IBridge;
import com.volmit.lux.model.IScene;

public class Testicles
{
	public static void main(String[] a)
	{
		try
		{
			LUX.init();
			IBridge b = new Bridge(LUX.findAccessPoints().get(0));
			System.out.println("Found Bridge. Push the FUCKN' BUTTON");
			b.authenticate();

			while(true)
			{
				try
				{
					Thread.sleep(1000);
					IScene s = new BasicScene();
					s.getColors().add(Color.yellow);
					b.playScene(s);
				}

				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
}
