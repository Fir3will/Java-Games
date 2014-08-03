package spaceinvaders;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class BossAlienSI
{
	/**
	 * The Height of the Alien Boss's Image
	 */
	private int height;

	/**
	 * The Alien Boss's Image used to Render it
	 */
	private Image image;

	private String imagePath = "alienBoss";

	/**
	 * Is it time for the Boss to start traveling up again
	 */
	private boolean isGoingUp = true;

	/**
	 * The Array of Missiles used when the Alien Boss Fires
	 */
	private ArrayList<MissleSI> missiles;

	/**
	 * The time until the Alien Boss fires again
	 */
	private int shotTicker = 0;

	/**
	 * Is the Alien Boss Visible, Used when the Alien Boss is killed, to display
	 * Score
	 */
	private boolean visible;

	/**
	 * The Width of the Alien Boss's Image
	 */
	private int width;

	/**
	 * The X Position for the Alien Boss
	 */
	private int x;

	/**
	 * The Y Position for the Alien Boss
	 */
	private int y;

	/**
	 * BossAlienSI Constructor, Spawns the Boss in
	 * 
	 * @param x
	 *            -The X Position to spawn the Boss
	 * @param y
	 *            -The Y Position to spawn the Boss
	 */
	public BossAlienSI(int x, int y)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/SpaceInvaders/" + imagePath + ".png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		missiles = new ArrayList<MissleSI>();
		visible = true;
		this.x = x + 300;
		this.y = y;
	}

	/**
	 * What is the Alien Boss's Bounding Box. The Limit to until being
	 * considered intersecting
	 * 
	 * @return rectangle- New Rectangle using: x, y, width, height
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Returns the Alien Boss's Image
	 * 
	 * @return image- Alien Boss's Image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Returns the Array of Missiles fired by the Alien Boss
	 * 
	 * @return missiles- Alien Boss's Missiles
	 */
	public ArrayList<MissleSI> getMissiles()
	{
		return missiles;
	}

	/**
	 * Returns the Alien Boss's X Position
	 * 
	 * @return x- Alien Boss's Position
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Alien Boss's Y Position
	 * 
	 * @return y- Alien Boss's Position
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Is the Alien Boss visible
	 * 
	 * @return visible- Alien Boss's Visibility
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Called to move the Alien Boss and apparently Fire the Missiles
	 */
	public void move()
	{
		if (!isVisible())
		{
			imagePath = "destroyedAlien";
		}

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

		if (shotTicker < 50)
		{
			shotTicker++;
		}

		if (shotTicker >= 50)
		{
			missiles.add(new MissleSI(x + width, y + height / 2 + 20, -1, -1));
			missiles.add(new MissleSI(x + width, y + height / 2 + 20, -1, -2));
			missiles.add(new MissleSI(x + width, y + height / 2, -1));
			missiles.add(new MissleSI(x + width, y + height / 2 - 20, -1, 2));
			missiles.add(new MissleSI(x + width, y + height / 2 - 20, -1, 1));
			shotTicker = 0;
		}

		if (x < 600)
		{
			x = 600;
		}
		else
		{
			x--;
		}
	}

	/**
	 * Set this Alien Boss Visible or Not
	 * 
	 * @param visible
	 *            -Is the Alien Boss Visible
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}