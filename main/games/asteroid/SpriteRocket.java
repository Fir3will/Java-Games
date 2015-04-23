package main.games.asteroid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import main.game.sprites.PolyBounds;
import main.game.sprites.Sprite;
import main.utils.Keys;
import main.utils.helper.JavaHelp;

public class SpriteRocket extends SpriteSpaceObject
{
	public final AsteroidMission game;
	public final Map<Keys, Boolean> keys;
	public final PolyBounds b;
	private final List<RocketPart> parts;

	public SpriteRocket(AsteroidMission game)
	{
		super("Hooman", 2700F, 0F, -1E10F, 0.0F);
		//super("Hooman", 12000F, 0F, -1E10F, 0.0F);
		this.game = game;
		parts = JavaHelp.newArrayList();
		bounds = b = new PolyBounds(this, 12, 24);
		keys = JavaHelp.newHashMap();
		for (Keys key : Keys.values())
		{
			keys.put(key, false);
		}
	}

	public void removeRocketPart(int x, int y)
	{
		Iterator<RocketPart> itr = parts.iterator();
		while (itr.hasNext())
		{
			RocketPart part = itr.next();
			Rectangle r = new Rectangle(part.posX, part.posY, part.width, part.height);
			if (r.contains(x, y))
			{
				itr.remove();
			}
		}
	}

	public boolean isSpaceOccupied(int x, int y)
	{
		Iterator<RocketPart> itr = parts.iterator();
		while (itr.hasNext())
		{
			RocketPart part = itr.next();
			Rectangle r = new Rectangle(part.posX, part.posY, part.width, part.height);
			if (r.contains(x, y)) return true;
		}
		return false;
	}

	public void addRocketPart(int x, int y, RocketPart part)
	{
		if (!isSpaceOccupied(x, y))
		{
			part.posX = x;
			part.posY = y;
			parts.add(part);
		}
	}

	@Override
	public void updateSprite()
	{
		super.updateSprite();
		if (keys.get(Keys.KEY_A))
		{
			getRotation().addLocal(2F / game.speed);
		}
		if (keys.get(Keys.KEY_D))
		{
			getRotation().addLocal(-2F / game.speed);
		}
		b.points.clear();
		b.width = getWidth();
		b.height = getHeight();
		//getRotation().set(getVelocity());
		//getRotation().addLocal(90F);
		for (RocketPart part : parts)
		{
			b.addPolygon(part.bounds);
			part.update(getVelocity(), getLocation(), getAcceleration(), getRotation(), keys);
		}
	}

	public void keyPressed(Keys key)
	{
		keys.put(key, true);
	}

	public void keyReleased(Keys key)
	{
		keys.put(key, false);
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		super.drawSprite(g2d);
		g2d.drawRect((int) getX() - 6, (int) getY() - 12, 12, 24);
		g2d.drawOval((int) getX() - 6, (int) getY() - 6, 12, 12);
		for (RocketPart part : parts)
		{
			AffineTransform t = g2d.getTransform();
			g2d.translate(getX(), getY());
			part.draw(g2d);
			g2d.setTransform(t);
		}
	}

	@Override
	public boolean intersects(Sprite s)
	{
		return false;
	}
}
