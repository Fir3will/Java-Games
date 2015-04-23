package main.events;

import main.options.Options;

public class OptionEvent extends Event
{
	public final Options options;

	public OptionEvent(Options options)
	{
		this.options = options;
	}
}
