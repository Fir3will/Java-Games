package main.game.sprites;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class RectangleBounds extends SpriteBounds
{
	public RectangleBounds(Sprite sprite, float width, float height)
	{
		super(sprite);
		this.width = width;
		this.height = height;
		sprite.setScale(width, height);
	}

	@Override
	public Shape getShape()
	{
		return new Rectangle2D.Float(x + sprite.getX(), y + sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}
}
