package testing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import main.utils.InfoLocker;

public abstract class SpriteTS
{
	private Image image = null;
	protected int width, height, x, y, xV, yV;
	protected boolean canPass = false;
	public static int boardWidth = 800, boardHeight = 600;
	private boolean isDestroyed = false;
	private InfoLocker locker;

	public SpriteTS(int X, int Y, int WIDTH, int HEIGHT)
	{
		width = WIDTH;
		height = HEIGHT;
		x = X;
		y = Y;
		setLocker(new InfoLocker());
	}

	public SpriteTS(int X, int Y, String path)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		x = X;
		y = Y;
		setLocker(new InfoLocker());
	}

	public abstract void drawSprite(Graphics2D g2d, ImageObserver obs);

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public Image getImage()
	{
		return image;
	}

	public InfoLocker getLocker()
	{
		return locker;
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

		if (!canPass)
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

	public abstract void run();

	public void setDestroyed(boolean d)
	{
		isDestroyed = d;
	}

	protected void setLocker(InfoLocker locker)
	{
		this.locker = locker;
	}
}
