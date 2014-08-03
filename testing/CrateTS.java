package testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class CrateTS extends SpriteTS
{
	public CrateTS()
	{
		super(500, 500, 20, 20);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		Color defaultColor = g2d.getColor();

		g2d.setColor(Color.RED);
		g2d.drawRect(x, y, width, height);
		g2d.setColor(defaultColor);
	}

	@Override
	public void run()
	{

	}
}
