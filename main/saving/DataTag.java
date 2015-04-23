package main.saving;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import main.utils.helper.JavaHelp;

public class DataTag implements Serializable, Cloneable
{
	private final Map<String, int[]> intArrays;
	private final Map<String, String[]> stringArrays;
	private final Map<String, boolean[]> booleanArrays;
	private final Map<String, byte[]> byteArrays;
	private final Map<String, float[]> floatArrays;
	private final Map<String, short[]> shortArrays;
	private final Map<String, double[]> doubleArrays;
	private final Map<String, long[]> longArrays;
	private final Map<String, Integer> ints;
	private final Map<String, String> strings;
	private final Map<String, Boolean> booleans;
	private final Map<String, Byte> bytes;
	private final Map<String, Float> floats;
	private final Map<String, Short> shorts;
	private final Map<String, Double> doubles;
	private final Map<String, Long> longs;
	private final Map<String, DataTag> savables;
	private final Map<String, DataTag> tags;

	public DataTag()
	{
		ints = JavaHelp.newHashMap();
		strings = JavaHelp.newHashMap();
		booleans = JavaHelp.newHashMap();
		bytes = JavaHelp.newHashMap();
		floats = JavaHelp.newHashMap();
		shorts = JavaHelp.newHashMap();
		doubles = JavaHelp.newHashMap();
		longs = JavaHelp.newHashMap();
		intArrays = JavaHelp.newHashMap();
		stringArrays = JavaHelp.newHashMap();
		booleanArrays = JavaHelp.newHashMap();
		byteArrays = JavaHelp.newHashMap();
		floatArrays = JavaHelp.newHashMap();
		shortArrays = JavaHelp.newHashMap();
		doubleArrays = JavaHelp.newHashMap();
		longArrays = JavaHelp.newHashMap();
		savables = JavaHelp.newHashMap();
		tags = JavaHelp.newHashMap();
	}

	public boolean isEmpty()
	{
		for (int i = 0; i < getMaps().length; i++)
		{
			if (!getMaps()[i].isEmpty()) return false;
		}
		return true;
	}

	public boolean clear()
	{
		for (int i = 0; i < getMaps().length; i++)
		{
			getMaps()[i].clear();
		}
		return true;
	}

	public void setSavable(String name, Savable savable)
	{
		DataTag t = new DataTag();
		savable.saveToTag(t);
		savables.put(name, t);
	}

	public DataTag getSavable(String name)
	{
		return savables.get(name);
	}

	public void setTag(String name, DataTag tag)
	{
		tags.put(name, tag);
	}

	public void setInteger(String name, int value)
	{
		ints.put(name, value);
	}

	public void setString(String name, String value)
	{
		strings.put(name, value);
	}

	public void setBoolean(String name, boolean value)
	{
		booleans.put(name, value);
	}

	public void setByte(String name, byte value)
	{
		bytes.put(name, value);
	}

	public void setFloat(String name, float value)
	{
		floats.put(name, value);
	}

	public void setShort(String name, short value)
	{
		shorts.put(name, value);
	}

	public void setDouble(String name, double value)
	{
		doubles.put(name, value);
	}

	public void setLong(String name, long value)
	{
		longs.put(name, value);
	}

	public void setIntegerArray(String name, int[] value)
	{
		intArrays.put(name, value);
	}

	public void setStringArray(String name, String[] value)
	{
		stringArrays.put(name, value);
	}

	public void setBooleanArray(String name, boolean[] value)
	{
		booleanArrays.put(name, value);
	}

	public void setByteArray(String name, byte[] value)
	{
		byteArrays.put(name, value);
	}

	public void setFloatArray(String name, float[] value)
	{
		floatArrays.put(name, value);
	}

	public void setShortArray(String name, short[] value)
	{
		shortArrays.put(name, value);
	}

	public void setDoubleArray(String name, double[] value)
	{
		doubleArrays.put(name, value);
	}

	public void setLongArray(String name, long[] value)
	{
		longArrays.put(name, value);
	}

	public int getInteger(String name, int def)
	{
		return ints.containsKey(name) ? ints.get(name) : def;
	}

	public String getString(String name, String def)
	{
		return strings.containsKey(name) ? strings.get(name) : def;
	}

	public boolean getBoolean(String name, boolean def)
	{
		return booleans.containsKey(name) ? booleans.get(name) : def;
	}

	public byte getByte(String name, byte def)
	{
		return bytes.containsKey(name) ? bytes.get(name) : def;
	}

	public float getFloat(String name, float def)
	{
		return floats.containsKey(name) ? floats.get(name) : def;
	}

	public short getShort(String name, short def)
	{
		return shorts.containsKey(name) ? shorts.get(name) : def;
	}

	public double getDouble(String name, double def)
	{
		return doubles.containsKey(name) ? doubles.get(name) : def;
	}

	public long getLong(String name, long def)
	{
		return longs.containsKey(name) ? longs.get(name) : def;
	}

	public int[] getIntegerArray(String name)
	{
		return intArrays.containsKey(name) ? intArrays.get(name) : new int[0];
	}

	public String[] getStringArray(String name)
	{
		return stringArrays.containsKey(name) ? stringArrays.get(name) : new String[0];
	}

	public boolean[] getBooleanArray(String name)
	{
		return booleanArrays.containsKey(name) ? booleanArrays.get(name) : new boolean[0];
	}

	public byte[] getByteArray(String name)
	{
		return byteArrays.containsKey(name) ? byteArrays.get(name) : new byte[0];
	}

	public float[] getFloatArray(String name)
	{
		return floatArrays.containsKey(name) ? floatArrays.get(name) : new float[0];
	}

	public short[] getShortArray(String name)
	{
		return shortArrays.containsKey(name) ? shortArrays.get(name) : new short[0];
	}

	public double[] getDoubleArray(String name)
	{
		return doubleArrays.containsKey(name) ? doubleArrays.get(name) : new double[0];
	}

	public long[] getLongArray(String name)
	{
		return longArrays.containsKey(name) ? longArrays.get(name) : new long[0];
	}

	public DataTag copy()
	{
		final DataTag tag = new DataTag();
		Map<String, Object>[] mapsA = getMaps();
		Map<String, Object>[] mapsB = tag.getMaps();
		for (int i = 0; i < mapsA.length; i++)
		{
			mapsB[i].putAll(mapsA[i]);
		}
		return tag;
	}

	public DataTag getTag(String name)
	{
		return tags.get(name) == null ? new DataTag() : tags.get(name);
	}

	@Override
	public DataTag clone()
	{
		try
		{
			return (DataTag) super.clone();
		}
		catch (final CloneNotSupportedException e)
		{
			e.printStackTrace(); // apparently, not possible
		}
		return null;
	}

	public static DataTag loadFrom(File file)
	{
		final CompoundTag comp = new CompoundTag(file);
		return comp.load();
	}

	public static void saveTo(File file, DataTag tag)
	{
		final CompoundTag comp = new CompoundTag(file);
		comp.save(tag);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DataTag)
		{
			final DataTag tag = (DataTag) obj;
			Map<?, ?>[] mapsA = getMaps();
			Map<?, ?>[] mapsB = tag.getMaps();
			for (int i = 0; i < mapsA.length; i++)
			{
				if (!mapsA[i].equals(mapsB[i])) return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		for (Map<?, ?> map : getMaps())
		{
			hash += map.hashCode();
		}
		return hash;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object>[] getMaps()
	{
		Map<?, ?>[] maps = new HashMap[18];
		maps[0] = ints;
		maps[1] = longs;
		maps[2] = doubles;
		maps[3] = shorts;
		maps[4] = strings;
		maps[5] = floats;
		maps[6] = bytes;
		maps[7] = booleans;
		maps[8] = intArrays;
		maps[9] = longArrays;
		maps[10] = doubleArrays;
		maps[11] = shortArrays;
		maps[12] = stringArrays;
		maps[13] = floatArrays;
		maps[14] = byteArrays;
		maps[15] = booleanArrays;
		maps[16] = savables;
		maps[17] = tags;
		return (Map<String, Object>[]) maps;
	}

	private static final long serialVersionUID = 1L;
}
