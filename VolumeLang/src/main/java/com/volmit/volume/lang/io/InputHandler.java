package com.volmit.volume.lang.io;

import java.io.InputStream;

@FunctionalInterface
public interface InputHandler
{
	public void read(InputStream in);
}
