package main.utils;

import java.util.Random;

public class Rand
{
	private static final Random rand;
	static
	{
		rand = new Random(System.currentTimeMillis());
	}

	private Rand()
	{}

	public static boolean nextBoolean()
	{
		return Rand.rand.nextBoolean();
	}

	public static boolean isPercent(float percent)
	{
		if (percent > nextInt(100)) return true;
		return false;
	}

	public static double nextDouble()
	{
		return Rand.rand.nextDouble();
	}

	public static float nextFloat()
	{
		return Rand.rand.nextFloat();
	}

	public static synchronized double nextGaussian()
	{
		return Rand.rand.nextGaussian();
	}

	public static int nextInt()
	{
		return Rand.rand.nextInt();
	}

	public static int nextInt(int n)
	{
		return Rand.rand.nextInt(n);
	}

	public static long nextLong()
	{
		return Rand.rand.nextLong();
	}

	public static void setSeed(long seed)
	{
		Rand.rand.setSeed(seed);
	}

	public static void nextBytes(byte[] bytes)
	{
		Rand.rand.nextBytes(bytes);
	}

	public static byte[] nextBytes(int length)
	{
		final byte[] bytes = new byte[length];
		Rand.rand.nextBytes(bytes);
		return bytes;
	}

	public static byte nextByte()
	{
		return nextBytes(1)[0];
	}

	public static String nextString()
	{
		return nextString(100, false);
	}

	public static char nextChar()
	{
		return Character.forDigit(nextInt(Character.MAX_RADIX), Character.MAX_RADIX);
	}

	public static String nextString(int maxLength, boolean fixedLength)
	{
		String chars = "";
		final int len = fixedLength ? maxLength : nextInt(maxLength);
		for (int i = 0; i < len; i++)
		{
			chars += nextChar();
		}
		return chars;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Rand;
	}

	@Override
	public int hashCode()
	{
		return this.getClass().getName().length();
	}
}