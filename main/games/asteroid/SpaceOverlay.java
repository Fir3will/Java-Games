package main.games.asteroid;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class SpaceOverlay
{
	public final AsteroidMission game;
	public final int maxWidth, maxHeight;

	public SpaceOverlay(AsteroidMission game, int maxWidth, int maxHeight)
	{
		this.game = game;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
	}

	public abstract void drawOverlay(Graphics2D g2d, int mouseX, int mouseY, boolean mouseOver);

	public abstract void buttonClicked(int mouseX, int mouseY, boolean inBounds);

	public void updateOverlay(int speed)
	{}

	public abstract Rectangle getBounds();

	public boolean inBounds(Point p)
	{
		return inBounds(getBounds(), p);
	}

	public boolean inBounds(Rectangle bounds, Point p)
	{
		return p.x >= bounds.x && p.x <= bounds.width + bounds.x && p.y >= bounds.y && p.y <= bounds.height + bounds.y;
	}
}
