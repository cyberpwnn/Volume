package com.volmit.volume.bukkit.util.net;

import org.bukkit.Bukkit;

/**
 * Protocol enum of all minecraft protocols (release channel)
 *
 * @author cyberpwn
 */
public enum Protocol
{
	/**
	 * Represents the absolute latest (even newer undocumented versions are below
	 * this. This is not a REAL version.
	 */
	LATEST(10000, "Latest"),

	/**
	 * Release 1.12.2<br/>
	 * Protocol: 340<br/>
	 * Netty: true<br/>
	 * NMS: v1_12_R1
	 */
	R1_12_2(340, "1.12.2", "v1_12_R1"),

	/**
	 * Release 1.12.2-PRE<br/>
	 * Protocol: 339<br/>
	 * Netty: true<br/>
	 * NMS: v1_12_R1
	 */
	R1_12_2_PRE(339, "1.12.2-PRE", "v1_12_R1"),

	/**
	 * Release 1.12.1<br/>
	 * Protocol: 338<br/>
	 * Netty: true<br/>
	 * NMS: v1_12_R1
	 */
	R1_12_1(338, "1.12.1", "v1_12_R1"),

	/**
	 * Release 1.12<br/>
	 * Protocol: 335<br/>
	 * Netty: true<br/>
	 * NMS: v1_12_R1
	 */
	R1_12(335, "1.12", "v1_12_R1"),

	/**
	 * Release 1.11.2<br/>
	 * Protocol: 316<br/>
	 * Netty: true<br/>
	 * NMS: v1_11_R1
	 */
	R1_11_2(316, "1.11.2", "v1_11_R1"),

	/**
	 * Release 1.11.1<br/>
	 * Protocol: 316<br/>
	 * Netty: true<br/>
	 * NMS: v1_11_R1
	 */
	R1_11_1(316, "1.11.1", "v1_11_R1"),

	/**
	 * Release 1.11<br/>
	 * Protocol: 315<br/>
	 * Netty: true<br/>
	 * NMS: v1_11_R1
	 */
	R1_11(315, "1.11", "v1_11_R1"),

	/**
	 * Release 1.10.2<br/>
	 * Protocol: 210<br/>
	 * Netty: true<br/>
	 * NMS: v1_10_R1
	 */
	R1_10_2(210, "1.10.2", "v1_10_R1"),

	/**
	 * Release 1.10.1<br/>
	 * Protocol: 210<br/>
	 * Netty: true<br/>
	 * NMS: v1_10_R1
	 */
	R1_10_1(210, "1.10.1", "v1_10_R1"),

	/**
	 * Release 1.10<br/>
	 * Protocol: 210<br/>
	 * Netty: true<br/>
	 * NMS: v1_10_R1
	 */
	R1_10(210, "1.10", "v1_10_R1"),

	/**
	 * Release 1.9.4<br/>
	 * Protocol: 110<br/>
	 * Netty: true<br/>
	 * NMS: v1_9_R2
	 */
	R1_9_4(110, "1.9.4", "v1_9_R2"),

	/**
	 * Release 1.9.3<br/>
	 * Protocol: 110<br/>
	 * Netty: true<br/>
	 * NMS: v1_9_R2
	 */
	R1_9_3(110, "1.9.3", "v1_9_R2"),

	/**
	 * Release 1.9.2<br/>
	 * Protocol: 109<br/>
	 * Netty: true<br/>
	 * NMS: v1_9_R1
	 */
	R1_9_2(109, "1.9.2", "v1_9_R1"),

	/**
	 * Release 1.9.1<br/>
	 * Protocol: 108<br/>
	 * Netty: true<br/>
	 * NMS: v1_9_R1
	 */
	R1_9_1(108, "1.9.1", "v1_9_R1"),

	/**
	 * Release 1.9<br/>
	 * Protocol: 107<br/>
	 * Netty: true<br/>
	 * NMS: v1_9_R1
	 */
	R1_9(107, "1.9", "v1_9_R1"),

	/**
	 * Release 1.8.9<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_9(47, "1.8.9", "v1_8_R3"),

	/**
	 * Release 1.8.8<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_8(47, "1.8.8", "v1_8_R3"),

	/**
	 * Release 1.8.7<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_7(47, "1.8.7", "v1_8_R3"),

	/**
	 * Release 1.8.6<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_6(47, "1.8.6", "v1_8_R3"),

	/**
	 * Release 1.8.5<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_5(47, "1.8.5", "v1_8_R3"),

	/**
	 * Release 1.8.4<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_4(47, "1.8.4", "v1_8_R3"),

	/**
	 * Release 1.8.3<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_3(47, "1.8.3", "v1_8_R3"),

	/**
	 * Release 1.8.2<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_2(47, "1.8.2", "v1_8_R3"),

	/**
	 * Release 1.8.1<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8_1(47, "1.8.1", "v1_8_R3"),

	/**
	 * Release 1.8<br/>
	 * Protocol: 47<br/>
	 * Netty: true<br/>
	 * NMS: v1_8_R3
	 */
	R1_8(47, "1.8", "v1_8_R3"),

	/**
	 * Release 1.7.10<br/>
	 * Protocol: 5<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_10(5, "1.7.10"),

	/**
	 * Release 1.7.9<br/>
	 * Protocol: 5<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_9(5, "1.7.9"),

	/**
	 * Release 1.7.8<br/>
	 * Protocol: 5<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_8(5, "1.7.8"),

	/**
	 * Release 1.7.7<br/>
	 * Protocol: 5<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_7(5, "1.7.7"),

	/**
	 * Release 1.7.6<br/>
	 * Protocol: 5<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_6(5, "1.7.6"),

	/**
	 * Release 1.7.5<br/>
	 * Protocol: 4<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_5(4, "1.7.5"),

	/**
	 * Release 1.7.4<br/>
	 * Protocol: 4<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_4(4, "1.7.4"),

	/**
	 * Release 1.7.3<br/>
	 * Protocol: 4<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_3(4, "1.7.3"),

	/**
	 * Release 1.7.2<br/>
	 * Protocol: 4<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_2(4, "1.7.2"),

	/**
	 * Release 1.7.1<br/>
	 * Protocol: 4<br/>
	 * Netty: true<br/>
	 * NMS: Not Supported
	 */
	R1_7_1(4, "1.7.1"),

	/**
	 * BETA 1.6.4<br/>
	 * Protocol: 78<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_6_4(78, "1.6.4", true),

	/**
	 * BETA 1.6.3<br/>
	 * Protocol: 77<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_6_3(77, "1.6.3", true),

	/**
	 * BETA 1.6.2<br/>
	 * Protocol: 74<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_6_2(74, "1.6.2", true),

	/**
	 * BETA 1.6.1<br/>
	 * Protocol: 73<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_6_1(73, "1.6.1", true),

	/**
	 * BETA 1.5.2<br/>
	 * Protocol: 61<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_5_2(61, "1.5.2", true),

	/**
	 * BETA 1.5.1<br/>
	 * Protocol: 60<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_5_1(60, "1.5.1", true),

	/**
	 * BETA 1.5<br/>
	 * Protocol: 60<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_5(60, "1.5", true),

	/**
	 * BETA 1.4.7<br/>
	 * Protocol: 51<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_4_7(51, "1.4.7", true),

	/**
	 * BETA 1.4.6<br/>
	 * Protocol: 51<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_4_6(51, "1.4.6", true),

	/**
	 * BETA 1.4.5<br/>
	 * Protocol: 49<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_4_5(49, "1.4.5", true),

	/**
	 * BETA 1.4.4<br/>
	 * Protocol: 49<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_4_4(49, "1.4.4", true),

	/**
	 * BETA 1.4.2<br/>
	 * Protocol: 47<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_4_2(47, "1.4.2", true),

	/**
	 * BETA 1.3.2<br/>
	 * Protocol: 39<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_3_2(39, "1.3.2", true),

	/**
	 * BETA 1.3.1<br/>
	 * Protocol: 39<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_3_1(39, "1.3.1", true),

	/**
	 * BETA 1.2.5<br/>
	 * Protocol: 29<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_2_5(29, "1.2.5", true),

	/**
	 * BETA 1.2.4<br/>
	 * Protocol: 29<br/>
	 * Netty: FALSE<br/>
	 * NMS: Not Supported
	 */
	B1_2_4(29, "1.2.4", true),

	/**
	 * Represents the least possible version
	 */
	EARLIEST(0),

	/**
	 * Represents an unknown version
	 */
	UNKNOWN(-10000);

	private int version;
	private String packageVersion;
	private String versionName;
	private boolean netty;

	private Protocol(int version, String versionName, boolean beta)
	{
		this(version, versionName, "UNKNOWN", beta);
	}

	private Protocol(int version)
	{
		this(version, "UNKNOWN", "UNKNOWN", false);
	}

	private Protocol(int version, String versionName)
	{
		this(version, versionName, "UNKNOWN", false);
	}

	private Protocol(int version, String versionName, String packageVersion)
	{
		this(version, versionName, packageVersion, false);
	}

	private Protocol(int version, String versionName, String packageVersion, boolean beta)
	{
		this.version = version;
		this.versionName = versionName;
		this.packageVersion = packageVersion;
		netty = beta;

		if(beta)
		{
			version -= 1000;
		}
	}

	/**
	 * Check if the given protocol is supported on the current version by checking
	 * the package. So on a server running 1.8.9, Using
	 * Protocol.R1_8_3.hasPackageSupport() would return true since both versions
	 * have the same package version.
	 *
	 * @return true if package support works on the given version on this server
	 */
	public boolean hasPackageSupport()
	{
		try
		{
			Class.forName("net.minecraft.server." + packageVersion + ".Block");
			return true;
		}

		catch(Throwable e)
		{

		}

		return false;
	}

	/**
	 * Get the package version (NMS) for the given version
	 *
	 * @return the package version or "UNKNOWN"
	 */
	public String getPackageVersion()
	{
		return packageVersion;
	}

	@Override
	public String toString()
	{
		return versionName;
	}

	/**
	 * Get the supported nms version by protocol based on the server version
	 *
	 * @return returns the supported protocol version, or Protocol.UNKNOWN
	 */
	public static Protocol getSupportedNMSVersion()
	{
		for(Protocol i : values())
		{
			if(i.isActualVersion() && i.isServerVersion() && i.hasPackageSupport())
			{
				return i;
			}
		}

		return Protocol.UNKNOWN;
	}

	/**
	 * Get the server protocol version
	 *
	 * @return the version of the server
	 */
	public static Protocol getProtocolVersion()
	{
		for(Protocol i : values())
		{
			if(i.isServerVersion())
			{
				return i;
			}
		}

		return Protocol.UNKNOWN;
	}

	/**
	 * Create a protocol range. For example Protocol.R1_7_10.to(Protocol.R1_9_4)
	 * would create a range from 1.7.10 to 1.9.4
	 *
	 * @param p
	 *            the protocol as the "to" or maximum.
	 * @return the protocol range object
	 */
	public ProtocolRange to(Protocol p)
	{
		return new ProtocolRange(this, p);
	}

	/**
	 * Check if the protocol version is the server version
	 *
	 * @return true if it does
	 */
	public boolean isServerVersion()
	{
		return Bukkit.getBukkitVersion().startsWith(getVersionString());
	}

	/**
	 * Get the version string of the given protocol (same as toString())
	 *
	 * @return basically toString()
	 */
	public String getVersionString()
	{
		return toString();
	}

	/**
	 * Check if this version supports netty.
	 *
	 * @return True if it does, False if it does not or beta
	 */
	public boolean isNettySupported()
	{
		return !netty;
	}

	/**
	 * Check if the given protocol is an actual version. UNKNOWN, LATEST, and
	 * EARLIEST are NOT actual versions.
	 *
	 * @return UNKNOWN, LATEST, and EARLIEST would return false. Otherwise true.
	 */
	public boolean isActualVersion()
	{
		return toString().contains(".");
	}

	/**
	 * Get the version (not a valid protocol number)
	 *
	 * @return the meta version
	 */
	public int getVersion()
	{
		if(isActualVersion() && !isNettySupported())
		{
			return getMetaVersion() + 1000;
		}

		return getMetaVersion();
	}

	/**
	 * Get the cversion (not a valid protocol number)
	 *
	 * @return the meta version
	 */
	public int getCVersion()
	{
		if(isActualVersion() && !isNettySupported())
		{
			return getMetaVersion();
		}

		return getMetaVersion() + 1000;
	}

	/**
	 * Returns the protocol version
	 *
	 * @return the correct protocol version
	 */
	public int getMetaVersion()
	{
		return version;
	}
}
