package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import main.utils.Rand;
import main.utils.math.Vector2F;

public class ZombieVT extends EntityVT implements Serializable
{
	public Vector2F pos;
	protected int health = 3;

	public ZombieVT(TestFrame main)
	{
		super(main, Rand.nextInt(400), Rand.nextInt(600), 20, 20);
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.setColor(health == 3 ? Color.GREEN : health == 2 ? Color.YELLOW : Color.RED);
		g2d.draw(getBounds());
		g2d.drawRect((int) getX(), (int) getY(), 1, 1);
		g2d.setColor(new Color(255, 0, 255));

		if (pos != null)
		{
			// g2d.drawString("+", pos.getX() - 4, pos.getY() + 4);
		}
	}

	@Override
	public void run()
	{
		if (health <= 0)
		{
			main.levelObj.enemiesKilled++;
			setDestroyed(true);
		}

		pos = main.player.getLocation();
		moveTo(pos, 1.3F + Rand.nextFloat() * 2.0F);
	}

	private static final long serialVersionUID = 1L;
}
