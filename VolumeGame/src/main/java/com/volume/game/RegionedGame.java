package com.volume.game;

public interface RegionedGame extends Game
{
	public GameSpace getGameSpace();

	public void setGameSpace(GameSpace space);
}
