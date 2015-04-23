package main.events;

import main.game.Games;

public class GameEvent extends Event
{
	public final Games games;

	public GameEvent(Games games)
	{
		this.games = games;
	}
}
