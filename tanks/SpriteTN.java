package tanks;

import java.awt.Rectangle;
import java.io.Serializable;
import javax.swing.ImageIcon;
import main.utils.math.Vector2F;

public abstract class SpriteTN implements Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean destroyed;
	protected int height;
	protected ImageIcon image;
	protected int width;
	protected int x, y, xV, yV;

	public SpriteTN(String path)
	{
		image = new ImageIcon(this.getClass().getResource(path));
		width = image.getImage().getWidth(null);
		height = image.getImage().getHeight(null);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public int getHeight()
	{
		return height;
	}

	public ImageIcon getImage()
	{
		return image;
	}

	public int getWidth()
	{
		return width;
	}

	public int getX()
	{
		return x;
	}

	public int getXV()
	{
		return xV;
	}

	public int getY()
	{
		return y;
	}

	public int getYV()
	{
		return yV;
	}

	public boolean isDestroyed()
	{
		return destroyed;
	}

	public void setDestroyed(boolean isDestroyed)
	{
		destroyed = isDestroyed;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setXV(int xV)
	{
		this.xV = xV;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setYV(int yV)
	{
		this.yV = yV;
	}

	public Vector2F getLocation()
	{
		return new Vector2F(x, y);
	}

	public void spriteInit()
	{
		x += xV;
		y += yV;

		if (x <= 0)
		{
			x = 0;
		}
		if (x >= 400 - width)
		{
			x = 400 - width;
		}
		if (y <= 0)
		{
			y = 0;
		}
		if (y >= 380 - height)
		{
			y = 380 - height;
		}
	}
}
