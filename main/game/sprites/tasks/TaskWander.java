package main.game.sprites.tasks;

import main.game.sprites.Sprite;
import main.utils.Rand;

public class TaskWander extends AITaskBase
{
	private final float speed;
	private final int maxX, maxY, howOften;

	public TaskWander(int priority, Sprite owner, float speed, int maxX, int maxY, int howOften)
	{
		super(priority, owner);
		this.speed = speed;
		this.maxX = maxX;
		this.maxY = maxY;
		this.howOften = howOften;
	}

	@Override
	public void executeTask()
	{
		if (!owner.getPathHelper().hasPath() && Rand.nextInt(howOften) == 0)
		{
			final byte b = Rand.nextByte();
			final byte b1 = Rand.nextByte();
			final float f = owner.getX() + b < 0 ? 1 : owner.getX() + b;
			final float f1 = owner.getY() + b1 < 0 ? 1 : owner.getY() + b1;
			owner.getPathHelper().moveTo(f > maxX ? maxX - 1 : f, f1 > maxY ? maxY - 1 : f1, speed);
		}

		if (owner.getX() > maxX)
		{
			owner.getLocation().x = 20;
		}
		if (owner.getX() < 0)
		{
			owner.getLocation().x = maxX - 20;
		}
		if (owner.getY() > maxY)
		{
			owner.getLocation().y = 20;
		}
		if (owner.getY() < 0)
		{
			owner.getLocation().y = maxY - 20;
		}
	}
}
