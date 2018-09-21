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
			DL.Download d = new DL.Download(new URL("http://mirror.filearena.net/pub/speed/SpeedTest_2048MB.dat"), new File("file.dat"), DownloadFlag.CALCULATE_SIZE);
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
