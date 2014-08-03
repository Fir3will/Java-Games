package breakout;

import java.awt.Image;
import java.awt.Rectangle;

public class SpriteBO
{
	protected int heigth;
	protected Image image;
	protected int width;
	protected int x;
	protected int y;

	public int getHeight()
	{
		return heigth;
	}

	Image getImage()
	{
		return image;
	}

	Rectangle getRect()
	{
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public int getWidth()
	{
		return width;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}