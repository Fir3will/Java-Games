package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import main.utils.helper.Sound;
import main.utils.math.Vector2F;

public class BulletVT extends SpriteVT implements Serializable
{
	private final Vector2F destination;
	private final float speed, damage;
	private int ticksExisted;

	public BulletVT(TestFrame main, Vector2F location, Vector2F destination, float speed, float damage)
	{
		super(main, (int) location.x, (int) location.y, 1, 1);
		Sound.playSound(Sound.GUNSHOT, false, 0.0F);
		this.destination = destination;
		this.speed = speed;
		this.damage = damage;
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
		this.moveTo(destination, speed);

		if (ticksExisted++ > 100)
		{
			setDestroyed(true);
		}
	}

	@Override
	public void collidedWith(SpriteVT sprite)
	{
		if (sprite instanceof ZombieVT)
		{
			((ZombieVT) sprite).health -= damage;
			setDestroyed(true);
			Sound.playSound(Sound.FLESH_SHOT, false, 0.0F);
		}
	}

	private static final long serialVersionUID = 1L;
}
