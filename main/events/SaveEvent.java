package main.events;

import main.Save;
import main.saving.DataTag;

public class SaveEvent extends Event
{
	public final Save save;

	public SaveEvent(Save save)
	{
		this.save = save;
	}

	public static class OnSaveEvent extends SaveEvent
	{
		public final DataTag tag;

		public OnSaveEvent(Save save, DataTag tag)
		{
			super(save);
			this.tag = tag;
		}
	}

	public static class OnLoadEvent extends SaveEvent
	{
		public final DataTag tag;

		public OnLoadEvent(Save save, DataTag tag)
		{
			super(save);
			this.tag = tag;
		}
	}
}
