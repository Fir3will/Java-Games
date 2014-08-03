package modhandler;

import main.games.Games;
import main.options.Options;

public abstract class IEventHandler
{
	public void init()
	{}

	public void onGamesMenuStartup(Games games)
	{}

	public void onOptionStartup(Options options)
	{}

	public void onShutdown()
	{}
}
