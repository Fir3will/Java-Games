package main.games.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.utils.math.Vector2F;

public class SpriteBullet extends Sprite
{
	public final Sprite shooter;
	public float speed;
	public Vector2F dest;
	public int killCounter;

	public SpriteBullet(Sprite shooter, Vector2F pos, Vector2F dest, float speed)
	{
		super();
		setLocation(pos);
		setScale(new Vector2F(1, 1));
		pathHelper.moveTo(dest.x, dest.y, speed);
		this.dest = dest;
		this.speed = speed;
		this.shooter = shooter;
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		final Color color = g2d.getColor();
		g2d.setColor(Color.BLACK);
		getBounds().drawShape(g2d);
		g2d.setColor(color);
	}

	@Override
	public void updateSprite()
	{
		if (!pathHelper.hasPath() && killCounter++ > 100)
		{
			setDead(true);
		}
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
		tag.setFloat("Speed", speed);
		tag.setFloat("Dest X", dest.x);
		tag.setFloat("Dest Y", dest.y);
		tag.setInteger("Kill Counter", killCounter);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
		speed = tag.getFloat("Speed", 1.0F);
		dest = new Vector2F(tag.getFloat("Dest X", 0.0F), tag.getFloat("Dest Y", 0.0F));
		killCounter = tag.getInteger("Kill Counter", 0);
	}
}
