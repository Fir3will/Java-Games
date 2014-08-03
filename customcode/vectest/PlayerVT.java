package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import main.utils.math.FastMath;
import main.utils.math.Vector2F;

public class PlayerVT extends EntityVT implements Serializable
{
	private float cX, cY;
	private int bullets = 7, reloadDelay, healthDelay;
	protected int health = 50;
	private boolean reload;
	private Vector2F lastPos = new Vector2F(200, 300);

	public PlayerVT(TestFrame main)
	{
		super(main, 200, 300, 20, 20);
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.setColor(Color.BLUE);
		g2d.draw(getBounds());
		g2d.drawRect((int) getX(), (int) getY(), 1, 1);
		g2d.drawString("Ammo: " + bullets + "/7", 5, 15);
		g2d.drawString("Health: " + health, 5, 30);
		g2d.drawString("Health Delay: " + healthDelay, 5, 75);
		EntityVT sprite = this.getClosestTo(ZombieVT.class);

		if (sprite != null)
		{
			g2d.drawLine((int) sprite.getX(), (int) sprite.getY(), (int) getX(), (int) getY());
		}

		g2d.drawString("^", getCrossHairX(), getCrossHairY());
	}

	private float getCrossHairX()
	{
		return FastMath.cos(cX);
	}

	private float getCrossHairY()
	{
		return FastMath.cos(cY);
	}

	@Override
	public void run()
	{
		if (reload)
		{
			bullets = 7;
			reloadDelay = 0;
			reload = false;
		}
		if (bullets == 0)
		{
			reloadDelay++;

			if (reloadDelay >= 100)
			{
				bullets = 7;
				reloadDelay = 0;
				reload = false;
			}
		}
	}

	@Override
	public void collidedWith(EntityVT sprite)
	{
		if (sprite instanceof ZombieVT)
		{
			healthDelay++;

			if (healthDelay >= 50)
			{
				health--;
				healthDelay = 0;
			}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W)
		{
			yV = -1;
		}
		if (key == KeyEvent.VK_A)
		{
			xV = -1;
		}
		if (key == KeyEvent.VK_S)
		{
			yV = 1;
		}
		if (key == KeyEvent.VK_D)
		{
			xV = 1;
		}
		if (key == KeyEvent.VK_LEFT)
		{
			cX++;
		}
		if (key == KeyEvent.VK_RIGHT)
		{
			cY--;
		}
		if (key == KeyEvent.VK_R)
		{
			reload = true;
		}
		if (key == KeyEvent.VK_SPACE)
		{
			if (lastPos != null && bullets > 0)
			{
				bullets--;
				lastPos = getClosestTo(ZombieVT.class) != null ? getClosestTo(ZombieVT.class).getLocation() : lastPos;
				new BulletVT(main, getLocation(), lastPos);
				reload = true;
			}
		}
		if (key == KeyEvent.VK_P)
		{
			main.isPaused = !main.isPaused;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
		{
			yV = 0;
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_A)
		{
			xV = 0;
		}
	}

	private static final long serialVersionUID = 1L;
}
