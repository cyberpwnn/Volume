package com.volmit.volume.bukkit.util.data;

import com.volmit.volume.lang.collections.GList;

/**
 * Alphabet military style
 *
 * @author cyberpwn
 */
public enum Alphabet
{
	/**
	 * A
	 */
	ALPHA,

	/**
	 * B
	 */
	BRAVO,

	/**
	 * C
	 */
	CHARLIE,

	/**
	 * D
	 */
	DELTA,

	/**
	 * E
	 */
	ECHO,

	/**
	 * F
	 */
	FOXTROT,

	/**
	 * G
	 */
	GOLF,

	/**
	 * H
	 */
	HOTEL,

	/**
	 * I
	 */
	INDIA,

	/**
	 * J
	 */
	JULIET,

	/**
	 * K
	 */
	KILO,

	/**
	 * L
	 */
	LIMA,

	/**
	 * M
	 */
	MIKE,

	/**
	 * N
	 */
	NOVEMBER,

	/**
	 * O
	 */
	OSCAR,

	/**
	 * P
	 */
	PAPA,

	/**
	 * Q
	 */
	QUEBEC,

	/**
	 * R
	 */
	ROMEO,

	/**
	 * S
	 */
	SIERRA,

	/**
	 * T
	 */
	TANGO,

	/**
	 * U
	 */
	UNIFORM,

	/**
	 * V
	 */
	VICTOR,

	/**
	 * W
	 */
	WISKEY,

	/**
	 * X
	 */
	XRAY,

	/**
	 * Y
	 */
	YANKEE,

	/**
	 * Z
	 */
	ZULU;

	/**
	 * Get the lower case form of the char
	 *
	 * @return the lower case letter representation
	 */
	public char getChar()
	{
		return this.toString().substring(0, 1).toLowerCase().toCharArray()[0];
	}

	/**
	 * ROMEO ALPHA DELTA INDIA OSCAR
	 *
	 * @param msg
	 * @return MIKE SIERRA GOLF
	 */
	public static String radioTalk(String msg)
	{
		String total = "";

		for(Character i : msg.toCharArray())
		{
			total = total + fromChar(i).toString().toLowerCase() + " ";
		}

		return total;
	}

	/**
	 * From char to alphabet
	 *
	 * @param c
	 *            the char
	 * @return the alphabet representation
	 */
	public static Alphabet fromChar(char c)
	{
		for(Alphabet a : values())
		{
			if(a.getChar() == Character.toLowerCase(c))
			{
				return a;
			}
		}

		return null;
	}

	/**
	 * Get the alphabet in a list of chars lowercased
	 *
	 * @return the alphabet
	 */
	public static GList<Character> getAlphabet()
	{
		GList<Character> al = new GList<Character>();

		for(Alphabet a : values())
		{
			al.add(a.getChar());
		}

		return al;
	}
}
