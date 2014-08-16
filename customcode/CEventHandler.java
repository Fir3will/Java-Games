package customcode;

import main.games.Games;
import modhandler.IEventHandler;
import customcode.vectest.TestFrame;

public class CEventHandler extends IEventHandler
{
	@Override
	public void onGamesMenuStartup(Games games)
	{
		games.addGame(TestFrame.class, 400, 600);
		games.addGame(Game.class, 400, 200);
	}
}
