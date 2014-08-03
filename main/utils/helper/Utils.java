package main.utils.helper;

public class Utils
{
	private static void exit()
	{
		System.exit(-1);
	}

	public static boolean isEmptyString(String str, boolean showError)
	{
		boolean bool = false;

		if (Utils.isNull(str, true))
		{

		}

		if (str.replaceAll(" ", "").isEmpty())
		{
			bool = true;

			if (showError)
			{
				IllegalArgumentException as = new IllegalArgumentException("The Parameter Cannot Be Empty!");
				as.printStackTrace();
				exit();
			}
		}

		return bool;
	}

	public static boolean isInRange(float num, float max, float min, boolean showError)
	{
		boolean bool = false;

		if (num > max || num < min)
		{
			if (showError)
			{
				IllegalArgumentException as = new IllegalArgumentException("The Number Has to be more than " + max + " and less than " + min + "!");
				as.printStackTrace();
				exit();
			}
		}

		return bool;
	}

	public static boolean isNull(Object obj, boolean showError)
	{
		boolean bool = false;

		if (obj == null)
		{
			bool = true;

			if (showError)
			{
				IllegalArgumentException as = new IllegalArgumentException("The Parameter Cannot Be Null!");
				as.printStackTrace();
				exit();
			}
		}

		return bool;
	}

	public static int toExponent(int original, int exponent)
	{
		int org = original;

		for (int i = 0; i < exponent - 1; i++)
		{
			original *= org;
		}

		return original;
	}
}
