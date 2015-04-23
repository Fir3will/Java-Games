package main.game.sprites;

import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.utils.math.Vector2F;

public class PolyBounds extends SpriteBounds
{
	public final List<Vector2F> points;

	public PolyBounds(Sprite sprite, float width, float height, Vector2F... points)
	{
		super(sprite);
		this.width = width;
		this.height = height;
		this.points = new ArrayList<Vector2F>(Arrays.asList(points));
	}

	public void include(PolyBounds bounds)
	{
		points.addAll(bounds.points);
	}

	public void addPoint(float x, float y)
	{
		points.add(new Vector2F(x, y));
	}

	public void addPolygon(Polygon polygon)
	{
		for (int i = 0; i < polygon.npoints; i++)
		{
			addPoint(polygon.xpoints[i], polygon.ypoints[i]);
		}
	}

	@Override
	public Shape getShape()
	{
		Polygon p = new Polygon();
		for (Vector2F v : points)
		{
			p.addPoint((int) (x + (int) v.x), (int) (y + (int) v.y));
		}
		return p;
	}
}
