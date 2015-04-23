package main.utils.helper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import main.utils.math.Vector2F;

public class GraphicHelper
{
	public static void draw3DRect(Graphics2D g, Color color, int x, int y, int size, int distance)
	{
		final Color defaultColor = g.getColor();
		g.setColor(color);
		g.drawRect(x, y, size, size);
		g.drawRect(x + distance, y + distance, size, size);
		g.drawLine(x, y, x + distance, y + distance);
		g.drawLine(x + size, y, x + distance + size, y + distance);
		g.drawLine(x + size, y + size, x + distance + size, y + distance + size);
		g.drawLine(x, y + size, x + distance, y + distance + size);
		g.setColor(defaultColor);
	}

	public static void createScreenShot(File file, int x, int y, int w, int h)
	{
		try
		{
			final Robot r = new Robot();
			final BufferedImage bi = r.createScreenCapture(new Rectangle(x, y, w, h));
			ImageIO.write(bi, "png", file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void drawVector2F(float x, float y, Vector2F v, Graphics2D g2d)
	{
		g2d.drawLine((int) x, (int) y, (int) v.x, (int) v.y);
	}
}
