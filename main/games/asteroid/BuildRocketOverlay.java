package main.games.asteroid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class BuildRocketOverlay extends SpaceOverlay
{
	public BuildRocketOverlay(AsteroidMission game, int maxWidth, int maxHeight)
	{
		super(game, maxWidth, maxHeight);
	}

	@Override
	public void drawOverlay(Graphics2D g2d, int mouseX, int mouseY, boolean mouseOver)
	{
		Rectangle r = getBounds();
		r.grow(5, 5);
		g2d.clearRect(r.x, r.y, r.width, r.height);
		r.grow(-5, -5);
		g2d.draw(r);
		r.grow(1, 1);
		g2d.draw(r);
		Rectangle2D rect = g2d.getFontMetrics().getStringBounds("B", g2d);
		int wd2 = (int) (rect.getWidth() / 2);
		int hd2 = (int) (rect.getHeight() / 2);
		g2d.drawString("B", (int) r.getCenterX() - wd2, (int) r.getCenterY() + hd2);
	}

	@Override
	public Rectangle getBounds()
	{
		return new Rectangle(game.width - 30, game.height - 50, 20, 20);
	}

	@Override
	public void buttonClicked(int mouseX, int mouseY, boolean inBounds)
	{
		game.buildingMode = inBounds ? !game.buildingMode : game.buildingMode;
	}
}
