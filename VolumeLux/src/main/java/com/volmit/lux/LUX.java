package com.volmit.lux;

import java.awt.Color;
import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLight.PHLightColorMode;
import com.philips.lighting.model.PHLightState;
import com.volmit.volume.lang.collections.FinalInteger;
import com.volmit.volume.lang.collections.GList;
import com.volmit.volume.math.noise.SimplexNoiseGenerator;

public class LUX
{
	private static List<PHAccessPoint> acf = null;
	private static PHHueSDK sdk = PHHueSDK.getInstance();
	private static Boolean auth = null;
	private static SimplexNoiseGenerator smg;

	public static void main(String[] a)
	{
		smg = new SimplexNoiseGenerator(1234);
		sdk = PHHueSDK.getInstance();
		sdk.getNotificationManager().registerSDKListener(listener);
		System.out.println("Herro prese");
		FinalInteger vi = new FinalInteger(0);

		for(PHAccessPoint i : findBridges())
		{
			System.out.println("Found Bridge: " + i.getIpAddress());
			new Thread("AP Push Link " + i.getIpAddress())
			{
				@Override
				public void run()
				{
					if(startPushAuthentication(i))
					{
						vi.set(1);
					}
				}
			}.start();
		}

		System.out.println("Push the button or else.");

		while(vi.get() == 0)
		{
			try
			{
				Thread.sleep(50);
			}

			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		System.out.println("Bridge Selected: " + sdk.getSelectedBridge().toString());
		List<PHLight> l = getLights();

		System.out.println("Found " + l.size() + " Lights");

		for(PHLight i : l)
		{
			System.out.println("  " + i.getName() + " (" + i.getLightType().name() + ") HUE: " + i.getLastKnownLightState().getHue() + " BRI: " + i.getLastKnownLightState().getBrightness() + " SAT: " + i.getLastKnownLightState().getSaturation());
		}

		new Thread()
		{
			@Override
			public void run()
			{
				while(true)
				{

					try
					{
						Thread.sleep(1000);
					}

					catch(InterruptedException e)
					{
						e.printStackTrace();
					}

					int k = 0;

					for(PHLight i : getLights())
					{
						k++;
						float n = (float) ((float) (smg.noise(System.currentTimeMillis() / (20000 * k)) + 1D) / 2D);
						Color cc = new Color(Color.HSBtoRGB(n, 1, 1));
						PHLightState s = i.getLastKnownLightState();
						s.setOn(true);
						s.setColorMode(PHLightColorMode.COLORMODE_HUE_SATURATION);
						double[] f = getRGBtoXY(cc);
						s.setHue(com.philips.lighting.hue.sdk.utilities.impl.Color.rgb(cc.getRed(), cc.getGreen(), cc.getBlue()));
						s.setBrightness(254);
						s.setTransitionTime(1000);
						sdk.getSelectedBridge().updateLightState(i, s);
					}
				}
			}
		}.start();
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

	public static List<PHLight> getLights()
	{
		PHBridge bridge = sdk.getSelectedBridge();
		PHBridgeResourcesCache cache = bridge.getResourceCache();
		List<PHLight> lights = cache.getAllLights();

		return lights;
	}

	public static void connectAll()
	{
		for(PHAccessPoint i : findBridges())
		{
			new Thread("AP Push Link " + i.getIpAddress())
			{
				@Override
				public void run()
				{
					startPushAuthentication(i);
				}
			}.start();
		}
	}

	public static List<PHBridge> getBridges()
	{
		return sdk.getAllBridges();
	}

	public static void selectBridge(PHBridge b)
	{
		sdk.setSelectedBridge(b);
	}

	public static PHBridge getBridge()
	{
		return sdk.getSelectedBridge();
	}

	public static GList<PHAccessPoint> findBridges()
	{
		PHBridgeSearchManager sm = (PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE);
		sm.search(true, true);

		while(acf == null)
		{
			try
			{
				Thread.sleep(50);
			}

			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		GList<PHAccessPoint> k = new GList<PHAccessPoint>(acf);
		acf = null;
		return k;
	}

	public static boolean startPushAuthentication(PHAccessPoint ap)
	{
		sdk.startPushlinkAuthentication(ap);

		while(auth == null)
		{
			try
			{
				Thread.sleep(50);
			}

			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		boolean m = new Boolean(auth);
		auth = null;

		return m;
	}

	private static PHSDKListener listener = new PHSDKListener()
	{
		@Override
		public void onAccessPointsFound(List<PHAccessPoint> accessPointsList)
		{
			acf = accessPointsList;
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint accessPoint)
		{

		}

		@Override
		public void onBridgeConnected(PHBridge bridge, String username)
		{
			sdk.setSelectedBridge(bridge);
			sdk.enableHeartbeat(bridge, 1000);
			auth = true;
		}

		@Override
		public void onCacheUpdated(List<Integer> arg0, PHBridge arg1)
		{

		}

		@Override
		public void onConnectionLost(PHAccessPoint arg0)
		{

		}

		@Override
		public void onConnectionResumed(PHBridge arg0)
		{

		}

		@Override
		public void onError(int code, final String message)
		{

		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> parsingErrorsList)
		{

		}
	};
}
