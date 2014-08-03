package main.utils;

public class Rand
{
	public static boolean isPercent(int percent)
	{
		if (percent > nextInt(100)) return true;
		return false;
	}

	public static boolean nextBoolean()
	{
		return Math.random() > 0.5D;
	}

	public static double nextDouble()
	{
		return Math.random();
	}

	public static float nextFloat()
	{
		return (float) Math.random();
	}

	public static int nextInt()
	{
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	public static int nextInt(int n)
	{
		if (n <= 0)
		{
			IllegalArgumentException e = new IllegalArgumentException("n Must be a positive!");
			e.printStackTrace();
		}

		if (n == 1) return nextInt(2) - 1;

		return (int) (Math.random() * n + 1);
	}

	public static long nextLong()
	{
		return (long) (Math.random() * Long.MAX_VALUE);
	}

	public static short nextShort()
	{
		return (short) (Math.random() * Short.MAX_VALUE);
	}
}