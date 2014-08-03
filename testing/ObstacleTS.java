package testing;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class ObstacleTS extends SpriteTS
{
	public static void cannotCollide(SpriteTS wall, SpriteTS entity)
	{
		if (wall.x > entity.x)
		{
			entity.xV = 0;
			entity.x -= 1;
		}
		if (wall.y > entity.y)
		{
			entity.yV = 0;
			entity.y -= 1;
		}
		if (wall.x < entity.x)
		{
			entity.xV = 0;
			entity.x += 1;
		}
		if (wall.y < entity.y)
		{
			entity.yV = 0;
			entity.y += 1;
		}
	}

	public ObstacleTS(int X, int Y)
	{
		super(X, Y, "/Images/Testing/wall2.png");
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.drawImage(getImage(), x, y, obs);
	}

	@Override
	public void run()
	{

	}
}
