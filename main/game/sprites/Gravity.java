package main.game.sprites;

public enum Gravity
{
	EARTH(9.81F),
	MOON(1.622F),
	MARS(3.711F),
	VENUS(8.87F),
	JUPITER(24.79F),
	MERCURY(3.7F);

	public final float gravity;
	public final String name;

	private Gravity(float gravity)
	{
		this.gravity = gravity;
		name = name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
	}

	public static int size()
	{
		return values().length;
	}

	public static Gravity get(int i)
	{
		return values()[i];
	}
}
