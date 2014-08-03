package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import main.utils.math.Vector2F;

public class BulletVT extends EntityVT implements Serializable
{
	private final Vector2F destination;

	public BulletVT(TestFrame main, Vector2F location, Vector2F destination)
	{
		super(main, (int) location.x, (int) location.y, 1, 1);
		this.destination = destination;
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.setColor(Color.BLACK);
		g2d.draw(getBounds());
	}

	@Override
	public void run()
	{
		this.moveTo(destination, 5.0F);

		if (y > 600 || y < 0 || x > 400 || x < 0 || this.getDistanceTo(destination) < 10.0F)
		{
			setDestroyed(true);
		}
	}

	@Override
	public void collidedWith(EntityVT sprite)
	{
		if (sprite instanceof ZombieVT)
		{
			((ZombieVT) sprite).health--;
			setDestroyed(true);
		}
	}

	private static final long serialVersionUID = 1L;
}
