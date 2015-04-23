package main.games.zombies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.utils.helper.JavaHelp;
import main.utils.math.Vector2F;

public class SpritePlayer extends Sprite
{
	public List<SpriteBullet> bullets;
	public int shotCounter;
	public int health;
	public float lookX, lookY;
	public float xV, yV;
	public final Zombies game;

	public SpritePlayer(Zombies game)
	{
		super();
		setScale(new Vector2F(20, 20));
		setLocation(new Vector2F(300, 400));
		this.game = game;
		health = 20;
		bullets = JavaHelp.newArrayList();
	}

	@Override
	public void setTasks()
	{}

	@Override
	public void updateSprite()
	{
		final float distance = new Vector2F(lookX + xV, lookY + yV).distance(getLocation());
		if (distance <= 300.0F)
		{
			lookX += xV;
			lookY += yV;
		}
		else
		{
			xV = 0;
			yV = 0;
		}
		shotCounter++;
		if (health <= 0)
		{
			setDead(true);
		}
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		g2d.setColor(Color.RED);
		getBounds().drawShape(g2d);
		g2d.drawString("Health: " + health, 5, 15);
		g2d.drawOval(-300, -300, 600, 600);

		g2d.drawOval((int) lookX - 2, (int) lookY - 2, 4, 4);
	}

	public void shootBullet(Vector2F destination)
	{
		if (shotCounter < 20) return;
		final SpriteBullet bullet = new SpriteBullet(this, getLocation(), destination, 1.0F);
		game.sprites.add(bullet);
		shotCounter = 0;
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
		// tag.setInteger("Health", health);
		tag.setFloat("Look Vec X", lookX);
		tag.setFloat("Look Vec Y", lookY);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
		// health = tag.getInteger("Health");
		lookX = tag.getFloat("Look Vec X", getLocation().x);
		lookY = tag.getFloat("Look Vec Y", getLocation().y);
	}
}
