package main.games.asteroid;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Map;
import main.saving.DataTag;
import main.saving.Savable;
import main.utils.Keys;
import main.utils.math.Rotation;
import main.utils.math.Vector2F;

public abstract class RocketPart implements Savable
{
	public final int width, height;
	public int posX, posY;
	public final PartType type;
	public final String name, info;
	public final float drag, mass;
	public final Polygon bounds;

	public RocketPart(DataTag tag)
	{
		name = tag.getString("Name", "NULL");
		type = PartType.values()[tag.getInteger("Part Type", -1)];
		drag = tag.getFloat("Drag", 0.2F);
		mass = tag.getFloat("Mass", 1F);
		width = tag.getInteger("Width", 1);
		height = tag.getInteger("Height", 1);
		info = tag.getString("Info", "No Information");
		bounds = new Polygon();
		for (int i = 0; i < tag.getInteger("Points", 0); i++)
		{
			int[] points = tag.getIntegerArray("Point-" + i);
			bounds.addPoint(points[0], points[1]);
		}
	}

	public abstract void update(Vector2F velocity, Vector2F location, Vector2F acceleration, Rotation rotation, Map<Keys, Boolean> keys);

	public void draw(Graphics2D g2d)
	{
		AffineTransform t = g2d.getTransform();
		g2d.translate(posX, posY);
		g2d.draw(bounds);
		g2d.setTransform(t);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		posX = tag.getInteger("Pos X", 0);
		posY = tag.getInteger("Pos Y", 0);
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		tag.setInteger("Pos X", posX);
		tag.setInteger("Pos Y", posY);
	}

	public static enum PartType
	{
		ENGINE,
		TANK,
		WING;
	}
}
