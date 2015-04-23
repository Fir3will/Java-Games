package main.game.sprites;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;

public abstract class SpriteBounds
{
	public float x, y, width, height;
	public Sprite sprite;

	public SpriteBounds(Sprite sprite)
	{
		this.sprite = sprite;
		x = sprite.getX();
		y = sprite.getY();
	}

	public boolean intersects(SpriteBounds bound)
	{
		Area areaA = new Area(getShape());
		areaA.intersect(new Area(bound.getShape()));
		return !areaA.isEmpty();
	}

	public abstract Shape getShape();

	public void drawShape(Graphics2D g2d)
	{
		g2d.draw(getShape());
	}
}
