package com.volmit.volume.lang.io;

import java.io.File;
import java.net.URL;

import com.volmit.volume.lang.format.F;
import com.volmit.volume.lang.io.DL.DownloadFlag;
import com.volmit.volume.lang.io.DL.DownloadState;

public class Test {
	public static void main(String[] a)
	{
		try {
			DL.DoubleBufferedDownload d = new DL.DoubleBufferedDownload(new URL("https://dl.google.com/dl/android/studio/install/3.1.4.0/android-studio-ide-173.4907809-mac.dmg"), new File("file.dat"), DownloadFlag.CALCULATE_SIZE);
			d.monitor((s, pct, elap, est, bps, iobps, sz, dld, bs, bu) -> System.out.println(s.toString() + " " + F.pc(pct) + " Elapsed: " + F.timeLong(elap, 1) + " Estim: " + F.timeLong(est, 1) + " BPS: " + F.fileSize(bps) + " (IO: " + F.fileSize(iobps) + ") " + F.fileSize(dld) + " of " + F.fileSize(sz) + " Buffer: " + F.f(bs)));
			d.start();
			
			while(d.isState(DownloadState.DOWNLOADING))
			{
				d.downloadChunk();
			}
		} 
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
