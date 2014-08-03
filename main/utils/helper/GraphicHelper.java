package main.utils.helper;

import java.awt.Color;
import java.awt.Graphics2D;

public class GraphicHelper
{
	public static void draw3DRect(Graphics2D g, Color color, int x, int y, int size, int distance)
	{
		Color defaultColor = g.getColor();
		g.setColor(color);
		g.drawRect(x, y, size, size);
		g.drawRect(x + distance, y + distance, size, size);
		g.drawLine(x, y, x + distance, y + distance);
		g.drawLine(x + size, y, x + distance + size, y + distance);
		g.drawLine(x + size, y + size, x + distance + size, y + distance + size);
		g.drawLine(x, y + size, x + distance, y + distance + size);
		g.setColor(defaultColor);
	}
}
