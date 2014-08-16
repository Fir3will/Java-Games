package testing;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import javax.swing.ImageIcon;
import main.utils.CompoundTag;
import main.utils.SaveManager;

public abstract class SpriteTS implements SaveManager, Serializable
{
	private static final long serialVersionUID = 1L;

	private ImageIcon image = null;
	protected int width, height, x, y, xV, yV;
	public static int boardWidth = 800, boardHeight = 600;
	private boolean isDestroyed, canPass;
	protected String path;

	public SpriteTS(int X, int Y, int WIDTH, int HEIGHT)
	{
		width = WIDTH;
		height = HEIGHT;
		x = X;
		y = Y;
	}

	public SpriteTS(int X, int Y, String path)
	{
		this.path = path;
		image = new ImageIcon(this.getClass().getResource(path));
		width = image.getIconWidth();
		height = image.getIconHeight();
		x = X;
		y = Y;
	}

	public abstract void drawSprite(Graphics2D g2d, ImageObserver obs);

	public abstract void run();

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public ImageIcon getImage()
	{
		return image;
	}

	public boolean isCanPass()
	{
		return canPass;
	}

	public void setCanPass(boolean canPass)
	{
		this.canPass = canPass;
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

	public void init(int bw, int bh)
	{
		SpriteTS.boardWidth = bw;
		SpriteTS.boardHeight = bh;

		if (!isCanPass())
		{
			if (x > SpriteTS.boardWidth - width)
			{
				x = SpriteTS.boardWidth - width;
			}

			if (x < 0)
			{
				x = 0;
			}

			if (y > SpriteTS.boardHeight - height)
			{
				y = SpriteTS.boardHeight - height;
			}

			if (y < 0)
			{
				y = 0;
			}
		}
	}

	public boolean isDestroyed()
	{
		return isDestroyed;
	}

	public void setDestroyed(boolean d)
	{
		isDestroyed = d;
	}

	@Override
	public void readFromTag(CompoundTag tag)
	{}

	@Override
	public void writeToTag(CompoundTag tag)
	{}
}