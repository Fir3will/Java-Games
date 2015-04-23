package main.game.sprites.tasks;

import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.saving.Savable;

public abstract class AITaskBase implements Savable
{
	public final int priority;
	public boolean disabled;
	public final Sprite owner;

	public AITaskBase(int priority, Sprite owner)
	{
		this.owner = owner;
		this.priority = priority;
	}

	public abstract void executeTask();

	@Override
	public void loadFromTag(DataTag tag)
	{}

	@Override
	public void saveToTag(DataTag tag)
	{}
}
