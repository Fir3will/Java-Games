package main.games.asteroid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;
import main.game.sprites.CircleBounds;
import main.game.sprites.Sprite;
import main.utils.helper.JavaHelp;
import main.utils.math.Vector2F;

public class SpriteSpaceObject extends Sprite
{
	public int[] xPoints, yPoints;
	public int ticks;
	public float radius;
	public float mass;
	public String name = "";
	public final List<SpriteSpaceObject> influences;

	public SpriteSpaceObject(String name, float x, float y, float mass, float radius)
	{
		super();
		influences = JavaHelp.newArrayList();
		setLocation(x, y);
		this.mass = mass;
		this.name = name;
		this.radius = radius;
		terminalVelocity = Integer.MAX_VALUE;
		bounds = new CircleBounds(this, radius * 2, radius * 2);
		xPoints = new int[72];
		yPoints = new int[72];
	}

	@Override
	public void updateSprite()
	{
		getAcceleration().zero();
		for (SpriteSpaceObject s : influences)
		{
			if (s != this)
			{
				getAcceleration().addLocal(getAccelerationBetween(getLocation(), s.getLocation(), s.mass));
			}
		}
	}

	public Vector2F getAccelerationBetween(Vector2F pos1, Vector2F pos2, float mass2)
	{
		Vector2F sub = pos2.subtract(pos1);
		return sub.normalizeLocal().multLocal(100 * mass2 / sub.lengthSquared());
	}

	@Override
	public void draw(Graphics2D g2d)
	{
		ticks++;
		Vector2F vel = getVelocity().clone();
		Vector2F loc = getLocation().clone();
		xPoints = new int[72];
		yPoints = new int[72];
		for (int i = 0; i < 72; i++)
		{
			xPoints[i] = (int) loc.x;
			yPoints[i] = (int) loc.y;
			for (SpriteSpaceObject s : influences)
			{
				if (s != this)
				{
					for (int j = 0; j < 800F; j++)
					{
						vel.addLocal(getAccelerationBetween(loc, s.getLocation(), s.mass));
						loc.addLocal(vel);
					}
				}
			}
		}
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawPolyline(xPoints, yPoints, 72);
		g2d.setColor(Color.BLACK);
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(getRotation().getDegrees()), getLocation().x + getWidth() / 2, getLocation().y + getHeight() / 2);
		drawSprite(g2d);
		g2d.setTransform(old);
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		if (!(this instanceof SpriteRocket))
		{
			g2d.drawOval((int) getX() - (int) radius, (int) getY() - (int) radius, (int) radius * 2, (int) radius * 2);
		}
	}

	@Override
	public boolean intersects(Sprite s)
	{
		return getDistanceTo(s) < radius;
	}
}