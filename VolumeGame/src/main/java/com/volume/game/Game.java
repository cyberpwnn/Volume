package com.volume.game;

public interface Game extends Ticked
{
	public String getName();

	public void setPlayerLimit(int max);

	public int getPlayerLimit();

	public boolean hasPlayerLimit();

	public void setUnlimitedPlayerLimit();
}
