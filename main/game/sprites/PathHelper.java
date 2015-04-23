package main.game.sprites;

import main.saving.DataTag;
import main.saving.Savable;
import main.utils.math.Vector2F;

public class PathHelper implements Savable
{
	private SpritePath path;
	public final Sprite owner;

	public PathHelper(Sprite owner)
	{
		this.owner = owner;
	}

	public boolean hasPath()
	{
		return path != null;
	}

	public SpritePath getPath()
	{
		return path;
	}

	public SpritePath getPathTo(float x, float y)
	{
		return new SpritePath(owner.getX(), owner.getY(), x, y);
	}

	public boolean moveTo(float x, float y, float speed)
	{
		final SpritePath path = getPathTo(x, y);
		if (path.equals(this.path)) return false;
		path.setVelocity(owner, speed);
		this.path = path;
		return true;
	}

	public void updatePath()
	{
		if (path != null)
		{
			final float x = path.destX;
			final float y = path.destY;
			final Vector2F dest = new Vector2F(x, y);
			if (owner.getLocation().distance(dest) <= 10.0F)
			{
				owner.setVelocity(new Vector2F());
				path = null;
			}
		}
	}

	public void setPath(SpritePath path)
	{
		this.path = path;
	}

	@Override
	public void loadFromTag(DataTag t)
	{
		DataTag tag = t.getTag("Path Helper");
		boolean hasPath = tag.getBoolean("Has Path", false);
		if (hasPath)
		{
			float posX = tag.getFloat("Pos X", 0.0F);
			float posY = tag.getFloat("Pos Y", 0.0F);
			float destX = tag.getFloat("Dest X", 0.0F);
			float destY = tag.getFloat("Dest Y", 0.0F);
			setPath(new SpritePath(posX, posY, destX, destY));
		}
	}

	@Override
	public void saveToTag(DataTag t)
	{
		DataTag tag = new DataTag();
		tag.setBoolean("Has Path", hasPath());
		if (hasPath())
		{
			tag.setFloat("Pos X", path.posX);
			tag.setFloat("Pos Y", path.posY);
			tag.setFloat("Dest X", path.destX);
			tag.setFloat("Dest Y", path.destY);
		}
		t.setTag("Path Helper", tag);
	}
}
