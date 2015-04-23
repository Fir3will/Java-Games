package main.utils.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.utils.Rand;

public class JavaHelp
{
	private JavaHelp()
	{}

	public static <T> boolean areEqual(T[] a1, T[] a2, boolean strict)
	{
		int l = Math.min(a1.length, a2.length);
		for (int i = 0; i < l; i++)
		{
			T a = a1[i];
			T b = a2[i];
			if (a != null && b != null && a.equals(b))
			{
				continue;
			}
			return false;
		}
		return strict ? a1.length == a2.length : true;
	}

	public static <T> T[] growArray(T[] obj)
	{
		return growArray(obj, 1);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] growArray(T[] obj, int amountToGrow)
	{
		Object[] tmp = new Object[obj.length + amountToGrow];
		for (int i = 0; i < obj.length; i++)
		{
			tmp[i] = obj[i];
		}
		return (T[]) tmp;
	}

	public static <T> T[] copy(T[] array)
	{
		return Arrays.copyOf(array, array.length);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[][] split(T[] source, int by)
	{
		Object[][] objs = new Object[by][0];
		for (int i = 0; i < source.length; i++)
		{
			objs[i % by][i] = source[i];
		}
		return (T[][]) objs;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] growArrayByAt(T[] obj, int indexToGrowAt, int amountToGrow)
	{
		Object[] tmp = new Object[obj.length + amountToGrow];
		for (int i = indexToGrowAt + 1; i < obj.length + amountToGrow; i++)
		{
			tmp[i] = obj[i];
		}
		return (T[]) tmp;
	}

	public static <T> T[] growArrayAt(T[] obj, int indexToGrowAt)
	{
		return growArrayByAt(obj, indexToGrowAt, 1);
	}

	public static <T> List<T> newArrayList()
	{
		return new ArrayList<T>();
	}

	public static <T, V> Map<T, V> newHashMap()
	{
		return new HashMap<T, V>();
	}

	public static int[] toIntArray(List<Integer> list)
	{
		int[] ints = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			ints[i] = list.get(i);
		}
		return ints;
	}

	public static <T> T getRandomElementFrom(T[] objs)
	{
		if (objs != null && objs.length > 0) return objs[Rand.nextInt(objs.length - 1)];
		return null;
	}

	public static boolean isVowel(char ch)
	{
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U';
	}

	public static boolean startsWithVowel(String string)
	{
		return string.length() > 0 && isVowel(string.charAt(0));
	}

	public static <T> String toString(T[] objects, StringExtractor<T> e)
	{
		String s = objects.getClass().getSimpleName() + "(";
		for (int i = 0; i < objects.length; i++)
		{
			s += e.toString(objects[i]) + (i == objects.length - 1 ? "" : ", ");
		}
		return s + ")";
	}

	public static <T> String toString(T[] objects)
	{
		String s = objects.getClass().getSimpleName() + "(";
		for (int i = 0; i < objects.length; i++)
		{
			s += objects[i] + (i == objects.length - 1 ? "" : ", ");
		}
		return s + ")";
	}

	public static interface StringExtractor<T>
	{
		public String toString(T obj);
	}
}
