package com.volume.game;

public class VGame implements Game
{
	private final String name;
	private int playerLimit;

	public VGame(String name)
	{
		this.name = name;
		playerLimit = -1;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setPlayerLimit(int max)
	{
		playerLimit = max;
	}

	@Override
	public int getPlayerLimit()
	{
		return playerLimit;
	}

	@Override
	public boolean hasPlayerLimit()
	{
		return playerLimit != -1;
	}

	@Override
	public void setUnlimitedPlayerLimit()
	{
		setPlayerLimit(-1);
	}

	@Override
	public void tick()
	{

	}

	@Override
	public int getTickRate()
	{
		return 1;
	}
}
