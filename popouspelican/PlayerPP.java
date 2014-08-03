package popouspelican;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class PlayerPP
{
	public int x, y, xVel, yVel;
	public Image image;

	public PlayerPP(int X, int Y)
	{
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/PopousPelican/pelican.png"));
		image = ii.getImage();

		x = X;
		y = Y;
	}

	public void draw(Graphics2D g, ImageObserver i)
	{
		Color color = g.getColor();

		g.setColor(Color.RED);
		g.drawImage(image, x, y, i);

		g.setColor(color);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y + image.getHeight(null) / 2, image.getWidth(null), 1);
	}

	public void init()
	{
		x += xVel;
		y += yVel;

		if (x < 0)
		{
			x = 0;
		}
		if (y < 0)
		{
			y = 0;
		}
		if (y > 575)
		{
			y = 575;
		}
		if (x > 780)
		{
			x = 780;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_D)
		{
			xVel = 2;
		}

		if (key == KeyEvent.VK_A)
		{
			xVel = -2;
		}

		if (key == KeyEvent.VK_W)
		{
			yVel = -2;
		}

		if (key == KeyEvent.VK_S)
		{
			yVel = 2;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_D || key == KeyEvent.VK_A)
		{
			xVel = 0;
		}

		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
		{
			yVel = 0;
		}
	}
}
