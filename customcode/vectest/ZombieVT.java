package customcode.vectest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import main.utils.Rand;
import main.utils.helper.Sound;
import main.utils.math.Vector2F;

public class ZombieVT extends SpriteVT
{
	public Vector2F pos;
	protected int health = 9;

	public ZombieVT(TestFrame main)
	{
		super(main, Rand.nextInt(100) + (Rand.nextBoolean() ? 300 : 0), Rand.nextInt(100) + (Rand.nextBoolean() ? 500 : 0), 20, 20);
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.setColor(health >= 7 ? Color.GREEN : health < 7 && health > 3 ? Color.YELLOW : Color.RED);
		g2d.draw(getBounds());
		g2d.drawRect((int) getX(), (int) getY(), 1, 1);
	}

	@Override
	public void run()
	{
		if (health <= 0)
		{
			main.levelObj.enemiesKilled++;
			Sound.playSound(Sound.ZOMBIE_MOAN, false, 0.0F);
			setDestroyed(true);
		}

		moveTo(this.getClosestTo(PlayerVT.class), 1.3F + (int) (Rand.nextFloat() * 100) / 100);
	}
}
