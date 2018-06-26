package com.volmit.lux.model;

import com.volmit.volume.lang.collections.GList;

public interface IBridge
{
	public GList<ILight> getLights();

	public void authenticate();

	public void playScene(IScene s);
}
