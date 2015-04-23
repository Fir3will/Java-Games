package main.game.sprites;

import main.utils.math.Vector2F;

public class SpritePath
{
	public final float posX, posY, destX, destY;
	private float velX, velY;

	public SpritePath(float posX, float posY, float destX, float destY)
	{
		this.posX = posX;
		this.posY = posY;
		this.destX = destX;
		this.destY = destY;
		final Vector2F loc = new Vector2F(posX, posY);
		final Vector2F dest = new Vector2F(destX, destY);
		velX = loc.clone().setY(0.0F).distance(dest.clone().setY(0.0F));
		velY = loc.clone().setX(0.0F).distance(dest.clone().setX(0.0F));
		if (posX > destX)
		{
			velX = -Math.abs(velX);
		}
		if (posY > destY)
		{
			velY = -Math.abs(velY);
		}
		if (posX <= destX)
		{
			velX = Math.abs(velX);
		}
		if (posY <= destY)
		{
			velY = Math.abs(velY);
		}
		velX *= 0.05F;
		velY *= 0.05F;
	}

	public Vector2F getVel(float speedModifier)
	{
		final Vector2F loc = new Vector2F(posX, posY);
		final Vector2F dest = new Vector2F(destX, destY);
		final Vector2F vel = getVelocityFor(loc, dest);
		if (loc.x > dest.x)
		{
			vel.x = -Math.abs(vel.x);
		}
		if (loc.y > dest.y)
		{
			vel.y = -Math.abs(vel.y);
		}
		if (loc.x <= dest.x)
		{
			vel.x = Math.abs(vel.x);
		}
		if (loc.y <= dest.y)
		{
			vel.y = Math.abs(vel.y);
		}
		vel.divideLocal(vel.length());
		vel.multLocal(speedModifier);
		vel.multLocal(3.0F);
		return vel;
	}

	public void setVelocity(Sprite sprite, float speedModifier)
	{
		final Vector2F vel = getVel(speedModifier);
		sprite.setVelocity(vel);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof SpritePath)
		{
			if (((SpritePath) obj).destX != destX) return false;
			if (((SpritePath) obj).destY != destY) return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return new Vector2F(destX, destY).hashCode() * new Vector2F(velX, velY).hashCode();
	}

	public static Vector2F getVelocityFor(Vector2F loc, Vector2F dest)
	{
		final float posX = loc.x;
		final float posY = loc.y;
		final float destX = dest.x;
		final float destY = dest.y;
		final Vector2F vel = new Vector2F(loc.clone().setY(0.0F).distance(dest.clone().setY(0.0F)), loc.clone().setX(0.0F).distance(dest.clone().setX(0.0F)));
		if (posX > destX)
		{
			vel.x = -Math.abs(vel.x);
		}
		if (posY > destY)
		{
			vel.y = -Math.abs(vel.y);
		}
		if (posX <= destX)
		{
			vel.x = Math.abs(vel.x);
		}
		if (posY <= destY)
		{
			vel.y = Math.abs(vel.y);
		}
		return vel.multLocal(0.05F);
	}
}
