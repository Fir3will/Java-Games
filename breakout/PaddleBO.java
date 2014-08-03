package breakout;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class PaddleBO extends SpriteBO implements CommonsBO
{
	int dx;

	public PaddleBO()
	{

		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/Breakout/paddle.png"));
		image = ii.getImage();

		width = image.getWidth(null);
		heigth = image.getHeight(null);

		resetState();

	}

	public void keyPressed(KeyEvent e)
	{

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT)
		{
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT)
		{
			dx = 2;
		}
	}

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
	}

	public void move()
	{
		x += dx;
		if (x <= 2)
		{
			x = 2;
		}
		if (x >= CommonsBO.PADDLE_RIGHT)
		{
			x = CommonsBO.PADDLE_RIGHT;
		}
	}

	public void resetState()
	{
		x = 200;
		y = 360;
	}
}