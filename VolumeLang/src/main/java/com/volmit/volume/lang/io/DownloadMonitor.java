package com.volmit.volume.lang.io;

import com.volmit.volume.lang.io.DL.DownloadState;

@FunctionalInterface
public interface DownloadMonitor 
{
	public void onUpdate(DownloadState state, double progress, long elapsed, long estimated, long bps, long iobps, long size, long downloaded, long buffer, double bufferuse);
}
