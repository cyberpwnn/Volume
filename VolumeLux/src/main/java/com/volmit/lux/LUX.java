package com.volmit.lux;

import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.lang.collections.GMap;

public class LUX
{
	private static boolean init = false;
	private static PHHueSDK sdk;
	private static GList<PHAccessPoint> accessPointsFound;
	private static PHBridge sb;
	private static GMap<PHLight, PHLightState> queue = new GMap<PHLight, PHLightState>();
	private static Thread tq = new Thread("LUX Queue")
	{
		@Override
		public void run()
		{
			while(init)
			{
				try
				{
					Thread.sleep(150);

					if(queue.isEmpty())
					{
						continue;
					}

					PHLight p = queue.k().pop();
					PHLightState s = queue.get(p);
					queue.remove(p);
					sdk.getSelectedBridge().updateLightState(p, s);
				}

				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	};

	private static PHSDKListener listener = new PHSDKListener()
	{
		@Override
		public void onAccessPointsFound(List<PHAccessPoint> points)
		{
			accessPointsFound = new GList<PHAccessPoint>(points);
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint ap)
		{

		}

		@Override
		public void onBridgeConnected(PHBridge bridge, String username)
		{
			sb = bridge;
		}

		@Override
		public void onCacheUpdated(List<Integer> ia, PHBridge bridge)
		{

		}

		@Override
		public void onConnectionLost(PHAccessPoint ap)
		{

		}

		@Override
		public void onConnectionResumed(PHBridge bridge)
		{

		}

		@Override
		public void onError(int code, String err)
		{

		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> errs)
		{

		}
	};

	public static PHBridge authenticate(PHAccessPoint ap)
	{
		sb = null;
		sdk.startPushlinkAuthentication(ap);
		blockSB();
		System.out.println("Authenticated");
		sdk.addBridge(sb);
		sdk.setSelectedBridge(sb);
		sdk.enableHeartbeat(sb, 1000);
		return sb;
	}

	public static GList<PHLight> getLights()
	{
		PHBridge bridge = sdk.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		return new GList<PHLight>(cache.getAllLights());
	}

	public static GList<PHAccessPoint> findAccessPoints()
	{
		accessPointsFound = null;
		PHBridgeSearchManager sm = (PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
		sm.search(true, true);
		blockAP();
		return accessPointsFound;
	}

	private static void blockAP()
	{
		while(accessPointsFound == null)
		{
			try
			{
				Thread.sleep(50);
			}

			catch(InterruptedException e)
			{

			}
		}
	}

	private static void blockSB()
	{
		while(sb == null)
		{
			try
			{
				Thread.sleep(50);
			}

			catch(InterruptedException e)
			{

			}
		}
	}

	public static void init()
	{
		if(!init)
		{
			init = true;
			sdk = PHHueSDK.getInstance();
			sdk.getNotificationManager().registerSDKListener(listener);
			tq.start();
		}
	}

	public static void destroy()
	{
		if(init)
		{
			init = false;
			sdk.getNotificationManager().unregisterSDKListener(listener);
			tq.interrupt();
		}
	}

	public static void queue(PHLight light, PHLightState s)
	{
		queue.put(light, s);
	}

	public static PHHueSDK getSDK()
	{
		return sdk;
	}
}
