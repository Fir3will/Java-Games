package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import main.games.Games;
import main.utils.Keys;
import main.utils.helper.Sound;
import main.utils.math.Vector2F;

public class PlayerVT extends SpriteVT implements Serializable
{
	private int reloadDelay, healthDelay;
	private int[] bullets = new int[WeaponVT.weapons.length];
	protected int health = 50;
	private boolean reload;
	private Vector2F lastPos = new Vector2F(200, 300);
	public WeaponVT wep;

	public PlayerVT(TestFrame main)
	{
		super(main, 200, 300, 20, 20);
		wep = WeaponVT.m1911;

		for (int i = 0; i < bullets.length; i++)
		{
			bullets[i] = WeaponVT.weapons[i].maxAmmo;
		}
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.setColor(Color.BLUE);
		g2d.draw(getBounds());
		g2d.drawRect((int) getX(), (int) getY(), 1, 1);
		g2d.drawString("Ammo: " + bullets[wep.id] + "/" + wep.maxAmmo, 5, 30);
		g2d.drawString("Weapon Specs For: " + wep.name, 5, 45);
		g2d.drawString("Fire Rate: " + wep.bulletsUsed, 5, 60);
		g2d.drawString("Clip Size: " + wep.maxAmmo, 5, 75);
		SpriteVT sprite = this.getClosestTo(ZombieVT.class);

		if (sprite != null)
		{
			g2d.drawLine((int) sprite.getX(), (int) sprite.getY(), (int) getX(), (int) getY());
		}
	}

	@Override
	public void run()
	{
		if (reload)
		{
			if (reloadDelay++ >= 30)
			{
				bullets[wep.id] = wep.maxAmmo;
				reloadDelay = 0;
				reload = false;
				Sound.playSound(Sound.GUN_RELOAD, false, 0.0F);
			}
		}
		if (bullets[wep.id] == 0)
		{
			if (reloadDelay++ >= 100)
			{
				bullets[wep.id] = wep.maxAmmo;
				reloadDelay = 0;
				reload = false;
				Sound.playSound(Sound.GUN_RELOAD, false, 0.0F);
			}
		}
	}

	@Override
	public void collidedWith(SpriteVT sprite)
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

	public void keyPressed(Keys key)
	{
		WeaponVT.keyPressed(this, key);

		if (key == Keys.KEY_W)
		{
			yV = -1;
		}
		if (key == Keys.KEY_A)
		{
			xV = -1;
		}
		if (key == Keys.KEY_S)
		{
			yV = 1;
		}
		if (key == Keys.KEY_D)
		{
			xV = 1;
		}
		if (key == Keys.KEY_R)
		{
			reload = true;
		}
		if (key == Keys.KEY_SPACE)
		{
			if (wep.isAutomatic)
			{
				fire();
			}
		}
	}

	public void keyReleased(Keys key)
	{
		if (key == Keys.KEY_W || key == Keys.KEY_S)
		{
			yV = 0;
		}
		if (key == Keys.KEY_D || key == Keys.KEY_A)
		{
			xV = 0;
		}
		if (key == Keys.KEY_P)
		{
			main.isPaused = !main.isPaused;
		}
		if (key == Keys.KEY_E)
		{
			main.restart();
			Games.startGame("Zombies");
		}
		if (key == Keys.KEY_SPACE)
		{
			if (!wep.isAutomatic)
			{
				fire();
			}
		}
	}

	private void fire()
	{
		if (lastPos != null && bullets[wep.id] > 0)
		{
			bullets[wep.id] -= wep.bulletsUsed;
			lastPos = getClosestTo(ZombieVT.class) != null ? getClosestTo(ZombieVT.class).getLocation() : lastPos;
			wep.fireAt(main, getLocation(), lastPos);
		}
	}

	private static final long serialVersionUID = 1L;
}
