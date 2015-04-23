package main.game.sprites;

import java.io.Serializable;
import main.utils.math.Rotation;
import main.utils.math.Vector2F;

public final class Control implements Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;
	private final Rotation rotation = new Rotation();
	private final Vector2F location = new Vector2F();
	private final Vector2F acceleration = new Vector2F();
	private final Vector2F velocity = new Vector2F();
	private final Vector2F scale = new Vector2F(1, 1);

	public Control()
	{
		location.set(Vector2F.ZERO);
		velocity.set(Vector2F.ZERO);
		acceleration.set(Vector2F.ZERO);
		scale.set(Vector2F.UNIT_XY);
		rotation.set(Rotation.Deg0);
	}

	public Control setLocation(Vector2F trans)
	{
		location.set(trans);
		return this;
	}

	public Control setLocation(float x, float y)
	{
		location.set(x, y);
		return this;
	}

	public Control setLocation(float pos)
	{
		location.set(pos, pos);
		return this;
	}

	public Control setLocationX(float x)
	{
		location.setX(x);
		return this;
	}

	public Control setLocationY(float y)
	{
		location.setY(y);
		return this;
	}

	public Control setAcceleration(float x, float y)
	{
		acceleration.set(x, y);
		return this;
	}

	public Control setAcceleration(float pos)
	{
		acceleration.set(pos, pos);
		return this;
	}

	public Control setAccelerationX(float x)
	{
		acceleration.setX(x);
		return this;
	}

	public Control setAccelerationY(float y)
	{
		acceleration.setY(y);
		return this;
	}

	public Control setScale(float x, float y)
	{
		scale.set(x, y);
		return this;
	}

	public Control setScale(Vector2F scale)
	{
		this.scale.set(scale);
		return this;
	}

	public Control setScale(float scale)
	{
		this.scale.set(scale, scale);
		return this;
	}

	public Control setScaleX(float x)
	{
		scale.setX(x);
		return this;
	}

	public Control setScaleY(float y)
	{
		scale.setY(y);
		return this;
	}

	public Control setVelocity(float x, float y)
	{
		velocity.set(x, y);
		return this;
	}

	public Control setVelocity(Vector2F velocity)
	{
		this.velocity.set(velocity);
		return this;
	}

	public Control setVelocity(float velocity)
	{
		this.velocity.set(velocity, velocity);
		return this;
	}

	public Control setVelocityX(float x)
	{
		velocity.setX(x);
		return this;
	}

	public Control setVelocityY(float y)
	{
		velocity.setY(y);
		return this;
	}

	public Control setRotation(Rotation rotation)
	{
		this.rotation.set(rotation);
		return this;
	}

	public Control setRotation(int rot)
	{
		rotation.set(rot);
		return this;
	}

	public Vector2F getLocation()
	{
		return location;
	}

	public Vector2F getVelocity()
	{
		return velocity;
	}

	public Vector2F getScale()
	{
		return scale;
	}

	public Vector2F getAcceleration()
	{
		return acceleration;
	}

	public Rotation getRotation()
	{
		return rotation;
	}

	public Control set(Control control)
	{
		location.set(control.location);
		scale.set(control.scale);
		velocity.set(control.velocity);
		acceleration.set(control.acceleration);
		rotation.set(control.rotation);
		return this;
	}

	@Override
	public Control clone()
	{
		Control tq = new Control();
		tq.scale.set(scale.clone());
		tq.location.set(location.clone());
		tq.velocity.set(velocity.clone());
		tq.acceleration.set(acceleration.clone());
		tq.rotation.set(rotation.clone());
		return tq;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Control)
		{
			Control c = (Control) o;
			if (!c.acceleration.equals(acceleration)) return false;
			if (!c.location.equals(location)) return false;
			if (!c.rotation.equals(rotation)) return false;
			if (!c.scale.equals(scale)) return false;
			if (!c.velocity.equals(velocity)) return false;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return acceleration.hashCode() + location.hashCode() + rotation.hashCode() + scale.hashCode() + velocity.hashCode();
	}

	@Override
	public String toString()
	{
		return "(A: " + acceleration + ", L: " + location + ", R: " + rotation + ", S: " + scale + ", V: " + velocity + ")";
	}
}
