package spaceinvaders;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class PickupSI
{
	/**
	 * The Height of the Pickup's Image
	 */
	private int height;

	/**
	 * The Pickup's Image used to Render it
	 */
	private Image image;

	/**
	 * Is the Pickup Visible, Used when the Pickup is killed, to display Score
	 */
	private boolean visible;

	/**
	 * The Width of the Pickup's Image
	 */
	private int width;

	/**
	 * The X Position for the Pickup
	 */
	private int x;

	/**
	 * The Y Position for the Pickup
	 */
	private int y;

	/**
	 * The Pickup Name and the Pickup X, Y Positions
	 * 
	 * @param pickupName
	 *            -Pickup Name
	 * @param x
	 *            -X Position to spawn at
	 * @param y
	 *            -Y Position to spawn at
	 */
	public PickupSI(String pickupName, int x, int y)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/SpaceInvaders/" + pickupName + "PU.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		this.x = x;
		this.y = y;
	}

	/**
	 * What is the Pickup's Bounding Box. The Limit to until being considered
	 * intersecting
	 * 
	 * @return rectangle- New Rectangle using: x, y, width, height
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Returns the Pickup's Image
	 * 
	 * @return image- Pickup's Image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Returns the Pickup's X Position
	 * 
	 * @return x- Pickup's Position
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Pickup's Y Position
	 * 
	 * @return y- Pickup's Position
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Is the Pickup visible
	 * 
	 * @return visible- Pickup's Visibility
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * Set this Pickup Visible or Not
	 * 
	 * @param visible
	 *            -Is the Pickup Visible
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}