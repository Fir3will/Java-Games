package breakout;

import javax.swing.ImageIcon;

public class BallBO extends SpriteBO implements CommonsBO
{

	private int xdir;
	private int ydir;

	public BallBO()
	{

		xdir = 1;
		ydir = -1;

		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/Snake/dot.png"));
		image = ii.getImage();

		width = image.getWidth(null);
		heigth = image.getHeight(null);

		resetState();
	}

	public int getYDir()
	{
		return ydir;
	}

	public void move()
	{
		x += xdir;
		y += ydir;

		if (x == 0)
		{
			setXDir(1);
		}

		if (x == CommonsBO.BALL_RIGHT)
		{
			setXDir(-1);
		}

		if (y == 0)
		{
			setYDir(1);
		}
	}

	public void resetState()
	{
		x = 230;
		y = 355;
	}

	public void setXDir(int x)
	{
		xdir = x;
	}

	public void setYDir(int y)
	{
		ydir = y;
	}
}