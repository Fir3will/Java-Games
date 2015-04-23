package main.events;

import modhandler.ModContainer;

public class LoadEvent extends Event
{
	public final ModContainer container;

	public LoadEvent(ModContainer container)
	{
		this.container = container;
	}
}
