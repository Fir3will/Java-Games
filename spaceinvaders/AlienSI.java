package spaceinvaders;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class AlienSI
{
	/**
	 * Is this Alien a bit faster
	 */
	private int alienState;

	/**
	 * The Height of the Alien's Image
	 */
	private int height;

	/**
	 * The Alien's Image used to Render it
	 */
	private Image image;

	/**
	 * Is it time for the Alien to start going up
	 */
	private boolean isGoingUp;

	/**
	 * Is the Alien Visible, Used when the Alien is killed, to display Score
	 */
	private boolean visible;

	/**
	 * The Width of the Alien's Image
	 */
	private int width;

	/**
	 * The X Position for the Alien
	 */
	private int x;

	/**
	 * The Y Position for the Alien
	 */
	private int y;

	public AlienSI(int x, int y)
	{
		this(x, y, 0);
	}

	public AlienSI(int x, int y, int alienState)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/SpaceInvaders/alien.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		this.x = x + 300;
		this.y = y;
		setAlienState(alienState);
	}

	/**
	 * @return the alienState
	 */
	public int getAlienState()
	{
		return alienState;
	}

	/**
	 * What is the Alien's Bounding Box. The Limit to until being considered
	 * intersecting
	 * 
	 * @return rectangle- New Rectangle using: x, y, width, height
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Returns the Alien's Image
	 * 
	 * @return image- Alien's Image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Returns the Alien's X Position
	 * 
	 * @return x- Alien's Position
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Alien's Y Position
	 * 
	 * @return y- Alien's Position
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Is the Alien visible
	 * 
	 * @return visible- Alien's Visibility
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Called to move the Alien
	 */
	public void move()
	{
		int chance = (int) (Math.random() * 500);

		if (x < 0)
		{
			x = 820;
		}

		if (getAlienState() == 1)
		{
			if (y == 600 - height)
			{
				isGoingUp = false;
			}

			if (y == 0)
			{
				isGoingUp = true;
			}

			if (isGoingUp)
			{
				y++;
			}
			else if (!isGoingUp)
			{
				y--;
			}
		}
		if (getAlienState() == 2)
		{
			if (y == 600 - height)
			{
				isGoingUp = false;
			}

			if (y == 0)
			{
				isGoingUp = true;
			}

			if (isGoingUp)
			{
				y++;
			}
			else if (!isGoingUp)
			{
				y--;
			}

			if (x < chance)
			{
				x = chance;
			}
		}

		x--;
	}

	/**
	 * @param alienState
	 *            the alienState to set
	 */
	public void setAlienState(int alienState)
	{
		this.alienState = alienState;
	}

	/**
	 * Set this Alien Visible or Not
	 * 
	 * @param visible
	 *            -Is the Alien Visible
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}