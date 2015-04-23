package main.utils.math;

public final class Rotation
{
	public static final Rotation Deg0 = new Rotation(0.0F);
	public static final Rotation Deg45 = new Rotation(45.0F);
	public static final Rotation Deg90 = new Rotation(90.0F);
	public static final Rotation Deg135 = new Rotation(135.0F);
	public static final Rotation Deg180 = new Rotation(180.0F);
	public static final Rotation Deg225 = new Rotation(225.0F);
	public static final Rotation Deg270 = new Rotation(270.0F);
	public static final Rotation Deg315 = new Rotation(315.0F);
	private float degrees;

	public Rotation()
	{
		degrees = 0;
	}

	public Rotation(float degrees)
	{
		this.degrees = clamp0to360(degrees);
	}

	public Vector2F toVector2F()
	{
		return new Vector2F(FastMath.cos(FastMath.DEG_TO_RAD * degrees), FastMath.sin(FastMath.DEG_TO_RAD * degrees)).normalizeLocal();
	}

	public Rotation set(Rotation rotation)
	{
		degrees = rotation.degrees;
		return this;
	}

	public Rotation set(float degrees)
	{
		this.degrees = clamp0to360(degrees);
		return this;
	}

	public Rotation addLocal(float degrees)
	{
		this.degrees = clamp0to360(this.degrees + degrees);
		return this;
	}

	public Rotation subtractLocal(float degrees)
	{
		this.degrees = clamp0to360(this.degrees - degrees);
		return this;
	}

	public Rotation add(float degrees)
	{
		return new Rotation(clamp0to360(this.degrees + degrees));
	}

	public Rotation subtract(float degrees)
	{
		return new Rotation(clamp0to360(this.degrees - degrees));
	}

	public Rotation addLocal(Rotation rotation)
	{
		degrees = clamp0to360(degrees + rotation.degrees);
		return this;
	}

	public Rotation subtractLocal(Rotation rotation)
	{
		degrees = clamp0to360(degrees - rotation.degrees);
		return this;
	}

	public Rotation add(Rotation rotation)
	{
		return new Rotation(clamp0to360(degrees + rotation.degrees));
	}

	public Rotation subtract(Rotation rotation)
	{
		return new Rotation(clamp0to360(degrees - rotation.degrees));
	}

	public Rotation set(Vector2F v)
	{
		set(FastMath.RAD_TO_DEG * v.getAngle());
		return this;
	}

	public float getDegrees()
	{
		return degrees;
	}

	private float clamp0to360(float degrees)
	{
		if (degrees > 360) return clamp0to360(degrees - 360);
		if (degrees < 0) return clamp0to360(degrees + 360);
		return degrees;
	}

	@Override
	public Rotation clone()
	{
		return new Rotation(degrees);
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof Rotation ? ((Rotation) o).degrees == degrees : false;
	}

	@Override
	public int hashCode()
	{
		return Float.floatToIntBits(degrees);
	}

	@Override
	public String toString()
	{
		return "(" + degrees + ")";
	}
}
