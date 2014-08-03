package customcode;

import main.games.Games;
import modhandler.IEventHandler;

public class CEventHandler extends IEventHandler
{
	@Override
	public void onGamesMenuStartup(Games games)
	{
		//games.addGame("Vec Test", "Testing", CEventHandler.al);

		games.addGame(MyGame.class, 400, 200);
	}
}
