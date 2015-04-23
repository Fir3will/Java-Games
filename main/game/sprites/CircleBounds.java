package main.game.sprites;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class CircleBounds extends SpriteBounds
{
	public CircleBounds(Sprite sprite, float width, float height)
	{
		super(sprite);
		this.width = width;
		this.height = height;
		sprite.setScale(width, height);
	}

	@Override
	public Shape getShape()
	{
		return new Ellipse2D.Float(x + sprite.getX() - sprite.getWidth() / 2, y + sprite.getY() - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
	}
}
