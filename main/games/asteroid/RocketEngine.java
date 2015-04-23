package main.games.asteroid;

import java.util.Map;
import main.saving.DataTag;
import main.utils.Keys;
import main.utils.math.Rotation;
import main.utils.math.Vector2F;

public class RocketEngine extends RocketPart implements Comparable<RocketEngine>
{
	public boolean on;
	public final boolean controllable;
	public final float thrust;

	public RocketEngine(DataTag tag)
	{
		super(tag);
		thrust = tag.getFloat("Thrust", 1F);
		controllable = tag.getBoolean("Controllable", true);
	}

	@Override
	public void update(Vector2F velocity, Vector2F location, Vector2F acceleration, Rotation rotation, Map<Keys, Boolean> keys)
	{
		if (controllable && keys.containsKey(Keys.KEY_U) && keys.get(Keys.KEY_U) || !controllable)
		{
			velocity.addLocal(rotation.add(90).toVector2F().mult(thrust / 10000F));
		}
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
	}

	@Override
	public int compareTo(RocketEngine o)
	{
		return Float.compare(thrust, o.thrust);
	}
}
