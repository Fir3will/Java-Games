package testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class CrateTS extends SpriteTS
{
	private static final long serialVersionUID = 1L;

	public CrateTS(int x, int y)
	{
		super(x, y, 20, 20);
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
