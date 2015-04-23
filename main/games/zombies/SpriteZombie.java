package main.games.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import main.game.sprites.Sprite;
import main.game.sprites.tasks.TaskWander;
import main.saving.DataTag;
import main.utils.math.Vector2F;

public class SpriteZombie extends Sprite
{
	public final TaskWander taskWander;
	public final Zombies game;
	public int health;

	public SpriteZombie(Zombies game, float posX, float posY)
	{
		super();
		setScale(new Vector2F(20, 20));
		setLocation(new Vector2F(posX, posY));
		health = 10;
		this.game = game;
		taskWander = new TaskWander(0, this, 0.7F, 400, 600, 1);
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		g2d.setColor(Color.GREEN);
		getBounds().drawShape(g2d);
		if (pathHelper.hasPath())
		{
			g2d.drawOval((int) pathHelper.getPath().destX - 5, (int) pathHelper.getPath().destY - 5, 10, 10);
			g2d.drawLine(0, 0, (int) pathHelper.getPath().destX, (int) pathHelper.getPath().destY);
		}
	}

	@Override
	public void updateSprite()
	{
		final SpritePlayer sprite = this.getClosestSprite(SpritePlayer.class, game.sprites.toArray(new Sprite[0]));
		if (sprite == null)
		{
			tasks.addTask(taskWander);
		}
		else
		{
			tasks.removeTask(taskWander);
			getPathHelper().moveTo(sprite.getX(), sprite.getY(), 0.5F);
		}
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
		tag.setInteger("Health", health);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
		health = tag.getInteger("Health", 10) <= 0 ? 1 : tag.getInteger("Health", 10);
	}
}
