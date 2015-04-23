package main.events;

import main.game.sprites.Sprite;
import main.saving.DataTag;

public class SpriteEvent extends Event
{
	public final Sprite sprite;

	public SpriteEvent(Sprite sprite)
	{
		this.sprite = sprite;
	}

	public static class LoadSpriteEvent extends SpriteEvent
	{
		public final DataTag tag;

		public LoadSpriteEvent(Sprite sprite, DataTag tag)
		{
			super(sprite);
			this.tag = tag;
		}
	}

	public static class SaveSpriteEvent extends SpriteEvent
	{
		public final DataTag tag;

		public SaveSpriteEvent(Sprite sprite, DataTag tag)
		{
			super(sprite);
			this.tag = tag;
		}
	}

	public static class UpdateSpriteEvent extends SpriteEvent
	{
		public UpdateSpriteEvent(Sprite sprite)
		{
			super(sprite);
		}
	}

	public static class KillSpriteEvent extends SpriteEvent
	{
		public boolean isDead;

		public KillSpriteEvent(Sprite sprite, boolean isDead)
		{
			super(sprite);
			this.isDead = isDead;
		}
	}
}
