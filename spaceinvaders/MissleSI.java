package spaceinvaders;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class MissleSI
{

	private int BOARD_HEIGHT = 690;
	private final int BOARD_WIDTH = 890;
	private Image image;
	private int MISSILE_INCLINE = 0;

	private int MISSILE_SPEED;// = 2;
	boolean visible;
	private int width, height;
	private int x, y;

	public MissleSI(int x, int y)
	{
		this(x, y, 2, 0);
	}

	public MissleSI(int x, int y, int speedX)
	{
		this(x, y, speedX, 0);
	}

	public MissleSI(int x, int y, int speedX, int speedY)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/SpaceInvaders/missle.png"));
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		this.x = x;
		this.y = y;
		MISSILE_SPEED = speedX;
		MISSILE_INCLINE = speedY;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public Image getImage()
	{
		return image;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void move()
	{
		x += MISSILE_SPEED;
		if (x > BOARD_WIDTH)
		{
			visible = false;
		}

		y += MISSILE_INCLINE;
		if (y > BOARD_HEIGHT || y < -20)
		{
			visible = false;
		}
	}

	public void setVisible(Boolean visible)
	{
		this.visible = visible;
	}
}