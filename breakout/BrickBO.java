package breakout;

import javax.swing.ImageIcon;

public class BrickBO extends SpriteBO
{

	boolean destroyed;

	public BrickBO(int x, int y)
	{
		this.x = x;
		this.y = y;

		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/Breakout/brick.png"));
		image = ii.getImage();

		width = image.getWidth(null);
		heigth = image.getHeight(null);

		destroyed = false;
	}

	public boolean isDestroyed()
	{
		return destroyed;
	}

	public void setDestroyed(boolean destroyed)
	{
		this.destroyed = destroyed;
	}

}