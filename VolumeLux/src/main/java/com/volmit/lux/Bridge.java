package com.volmit.lux;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHLight;
import com.volmit.lux.model.IBridge;
import com.volmit.lux.model.ILight;
import com.volmit.lux.model.IScene;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GListAdapter;

public class Bridge implements IBridge
{
	private PHAccessPoint ap;

	public Bridge(PHAccessPoint accessPoint)
	{
		ap = accessPoint;
	}

	@Override
	public void authenticate()
	{
		LUX.authenticate(ap);
	}

	@Override
	public GList<ILight> getLights()
	{
		return new GList<ILight>(new GListAdapter<PHLight, ILight>()
		{
			@Override
			public ILight onAdapt(PHLight from)
			{
				return new Light(from);
			}
		}.adapt(LUX.getLights()));
	}

	@Override
	public void playScene(IScene s)
	{
		for(ILight i : getLights())
		{
			i.setBrightness(s.getBrightness());
			i.setColor(s.getColors().pickRandom(), 50);
		}
	}
}
