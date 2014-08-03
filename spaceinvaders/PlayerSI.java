package spaceinvaders;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class PlayerSI
{
	/**
	 * The X Velocity for the Player
	 */
	private int dx;

	/**
	 * The Y Velocity for the Player
	 */
	private int dy;

	/**
	 * The Height of the Player's Image
	 */
	private int height;

	/**
	 * The Player's Image used to Render it
	 */
	private Image image;

	/**
	 * The Array of Missiles used when the Player Fires
	 */
	private ArrayList<MissleSI> missiles;

	/**
	 * Is the Player Visible, Used when the player is killed, to display Score
	 */
	private boolean visible;

	/**
	 * The Width of the Player's Image
	 */
	private int width;

	/**
	 * The X Position for the Player
	 */
	private int x;

	/**
	 * The Y Position for the Player
	 */
	private int y;

	/**
	 * PlayerSI Constructor Here, Variables like
	 * <code>height, width, x, y,</code> and <code>image</code> are Defined
	 */
	public PlayerSI()
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/SpaceInvaders/player.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		missiles = new ArrayList<MissleSI>();
		visible = true;
		x = 40;
		y = 260;
	}

	/**
	 * Fires a Missile at the Player's X, Y Position. Also adds it the the
	 * Player's Missile Array
	 */
	public void fire()
	{
		missiles.add(new MissleSI(x + width, y + height / 2));
	}

	/**
	 * What is the Player's Bounding Box. The Limit to until being considered
	 * intersecting
	 * 
	 * @return rectangle- New Rectangle using: x, y, width, height
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Returns the player's Image
	 * 
	 * @return image- Player's Image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Returns the Array of Missiles fired by the Player
	 * 
	 * @return missiles- Player's Missiles
	 */
	public ArrayList<MissleSI> getMissiles()
	{
		return missiles;
	}

	/**
	 * Returns the player's X Position
	 * 
	 * @return x- Player's Position
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the player's Y Position
	 * 
	 * @return y- Player's Position
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Is the Player visible
	 * 
	 * @return visible- Player's Visibility
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * When a Key, on the Keyboard, is Pressed, this is Called
	 * 
	 * @param e
	 *            KeyEvent(Java)
	 */
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE)
		{
			fire();
		}

		if (key == KeyEvent.VK_LEFT)
		{
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT)
		{
			dx = 1;
		}

		if (key == KeyEvent.VK_UP)
		{
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN)
		{
			dy = 1;
		}
	}

	/**
	 * When a Key, on the Keyboard, is Released after being Pressed, this is
	 * Called
	 * 
	 * @param e
	 *            KeyEvent(Java)
	 */
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
		{
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT)
		{
			dx = 0;
		}

		if (key == KeyEvent.VK_UP)
		{
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN)
		{
			dy = 0;
		}
	}

	/**
	 * Called to move the player
	 */
	public void move()
	{
		x += dx;
		y += dy;

		if (x < 1)
		{
			x = 1;
		}
		if (y < 1)
		{
			y = 1;
		}

		if (x >= 800 - width)
		{
			x = 800 - width;
		}
		if (y >= 600 - height - 20)
		{
			y = 600 - height - 20;
		}
	}

	/**
	 * Set this Player Visible or Not
	 * 
	 * @param visible
	 *            -Is the Player Visible
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}