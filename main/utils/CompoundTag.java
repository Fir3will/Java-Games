package main.utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import main.Save;
import main.Vars;
import main.games.GamePanel;
import main.games.NewGame;
import main.utils.file.FileHelper;
import modhandler.SimpleMod;

public class CompoundTag implements Serializable, Cloneable
{
	public static final File PLAYER_FOLDER = new File(Vars.SAVE_DIR + Save.PLAYER_NAME + "/");
	private static final long serialVersionUID = 1L;
	private File file;
	private HashMap<String, Integer> ints;
	private HashMap<String, String> strings;
	private HashMap<String, Boolean> booleans;
	private HashMap<String, Byte> bytes;
	private HashMap<String, Float> floats;
	private HashMap<String, Short> shorts;
	private HashMap<String, Double> doubles;
	private HashMap<String, Long> longs;
	private HashMap<String, CompoundTag> tags;
	private HashMap<String, Serializable> objs;

	private HashMap<String, int[]> intArrays;
	private HashMap<String, String[]> stringArrays;
	private HashMap<String, boolean[]> booleanArrays;
	private HashMap<String, byte[]> byteArrays;
	private HashMap<String, float[]> floatArrays;
	private HashMap<String, short[]> shortArrays;
	private HashMap<String, double[]> doubleArrays;
	private HashMap<String, long[]> longArrays;
	private HashMap<String, Serializable[]> objArrays;

	public CompoundTag(GamePanel game)
	{
		this(new File(PLAYER_FOLDER, "/" + (game.getClass().isAnnotationPresent(NewGame.class) ? game.getClass().getAnnotation(NewGame.class).name() : "NULL") + ".dat"));
	}

	public CompoundTag(File saveTo)
	{
		file = saveTo;
		set();
		init();
	}

	public CompoundTag(CompoundTag tag)
	{
		FileHelper.createFile(new File(tag.file.getParentFile(), tag.file.getName().replaceAll(".dat", "")).toString(), true);
		file = new File(tag.file.getParentFile(), tag.file.getName().replaceAll(".dat", "") + "/" + tag.file.getName().replaceAll(".dat", "") + " tag - " + tag.tags.size());
		set();
		init();
	}

	public CompoundTag(SimpleMod mod)
	{
		this(new File(PLAYER_FOLDER, mod.getModid() + ".dat"));
	}

	private void set()
	{
		ints = new HashMap<String, Integer>();
		strings = new HashMap<String, String>();
		booleans = new HashMap<String, Boolean>();
		bytes = new HashMap<String, Byte>();
		floats = new HashMap<String, Float>();
		shorts = new HashMap<String, Short>();
		doubles = new HashMap<String, Double>();
		longs = new HashMap<String, Long>();
		tags = new HashMap<String, CompoundTag>();
		objs = new HashMap<String, Serializable>();
		intArrays = new HashMap<String, int[]>();
		stringArrays = new HashMap<String, String[]>();
		booleanArrays = new HashMap<String, boolean[]>();
		byteArrays = new HashMap<String, byte[]>();
		floatArrays = new HashMap<String, float[]>();
		shortArrays = new HashMap<String, short[]>();
		doubleArrays = new HashMap<String, double[]>();
		longArrays = new HashMap<String, long[]>();
		objArrays = new HashMap<String, Serializable[]>();
	}

	public void init()
	{
		FileHelper.createFile(file.getPath());
		load();
	}

	private void check(String name)
	{
		if (name == null) throw new IllegalArgumentException("Name Cannot be Null!", new NullPointerException());
	}

	public void setInteger(String name, int value)
	{
		check(name);
		ints.put(name, value);
		save();
	}

	public void setString(String name, String value)
	{
		check(name);
		strings.put(name, value);
		save();
	}

	public void setBoolean(String name, boolean value)
	{
		check(name);
		booleans.put(name, value);
		save();
	}

	public void setByte(String name, byte value)
	{
		check(name);
		bytes.put(name, value);
		save();
	}

	public void setFloat(String name, float value)
	{
		check(name);
		floats.put(name, value);
		save();
	}

	public void setShort(String name, short value)
	{
		check(name);
		shorts.put(name, value);
		save();
	}

	public void setDouble(String name, double value)
	{
		check(name);
		doubles.put(name, value);
		save();
	}

	public void setLong(String name, long value)
	{
		check(name);
		longs.put(name, value);
		save();
	}

	public void setTag(String name, CompoundTag value)
	{
		check(name);
		tags.put(name, value);
		save();
	}

	public void setSerializable(String name, Serializable obj)
	{
		check(name);
		objs.put(name, obj);
		save();
	}

	public void setIntegerArray(String name, int[] value)
	{
		check(name);
		intArrays.put(name, value);
		save();
	}

	public void setStringArray(String name, String[] value)
	{
		check(name);
		stringArrays.put(name, value);
		save();
	}

	public void setBooleanArray(String name, boolean[] value)
	{
		check(name);
		booleanArrays.put(name, value);
		save();
	}

	public void setByteArray(String name, byte[] value)
	{
		check(name);
		byteArrays.put(name, value);
		save();
	}

	public void setFloatArray(String name, float[] value)
	{
		check(name);
		floatArrays.put(name, value);
		save();
	}

	public void setShortArray(String name, short[] value)
	{
		check(name);
		shortArrays.put(name, value);
		save();
	}

	public void setDoubleArray(String name, double[] value)
	{
		check(name);
		doubleArrays.put(name, value);
		save();
	}

	public void setLongArray(String name, long[] value)
	{
		check(name);
		longArrays.put(name, value);
		save();
	}

	public void setSerializableArray(String name, Serializable[] value)
	{
		check(name);
		objArrays.put(name, value);
		save();
	}

	public int getInteger(String name)
	{
		return ints.containsKey(name) ? ints.get(name) : 0;
	}

	public String getString(String name)
	{
		return strings.containsKey(name) ? strings.get(name) : "";
	}

	public boolean getBoolean(String name)
	{
		return booleans.containsKey(name) ? booleans.get(name) : false;
	}

	public byte getByte(String name)
	{
		return bytes.containsKey(name) ? bytes.get(name) : 0;
	}

	public float getFloat(String name)
	{
		return floats.containsKey(name) ? floats.get(name) : 0.0F;
	}

	public short getShort(String name)
	{
		return shorts.containsKey(name) ? shorts.get(name) : 0;
	}

	public double getDouble(String name)
	{
		return doubles.containsKey(name) ? doubles.get(name) : 0.0D;
	}

	public long getLong(String name)
	{
		return longs.containsKey(name) ? longs.get(name) : 0L;
	}

	public CompoundTag getTag(String name)
	{
		return tags.containsKey(name) ? tags.get(name).load() : new CompoundTag(this);
	}

	public Serializable getSerializable(String name)
	{
		return objs.containsKey(name) ? objs.get(name) : null;
	}

	public int[] getIntegerArray(String name)
	{
		return intArrays.containsKey(name) ? intArrays.get(name) : null;
	}

	public String[] getStringArray(String name)
	{
		return stringArrays.containsKey(name) ? stringArrays.get(name) : null;
	}

	public boolean[] getBooleanArray(String name)
	{
		return booleanArrays.containsKey(name) ? booleanArrays.get(name) : null;
	}

	public byte[] getByteArray(String name)
	{
		return byteArrays.containsKey(name) ? byteArrays.get(name) : null;
	}

	public float[] getFloatArray(String name)
	{
		return floatArrays.containsKey(name) ? floatArrays.get(name) : null;
	}

	public short[] getShortArray(String name)
	{
		return shortArrays.containsKey(name) ? shortArrays.get(name) : null;
	}

	public double[] getDoubleArray(String name)
	{
		return doubleArrays.containsKey(name) ? doubleArrays.get(name) : null;
	}

	public long[] getLongArray(String name)
	{
		return longArrays.containsKey(name) ? longArrays.get(name) : null;
	}

	public Serializable[] getSerializableArray(String name)
	{
		return objArrays.containsKey(name) ? objArrays.get(name) : null;
	}

	private CompoundTag load()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			CompoundTag obj = (CompoundTag) in.readObject();

			ints = obj.ints;
			strings = obj.strings;
			booleans = obj.booleans;
			bytes = obj.bytes;
			floats = obj.floats;
			shorts = obj.shorts;
			doubles = obj.doubles;
			longs = obj.longs;
			tags = obj.tags;
			objs = obj.objs;
			intArrays = obj.intArrays;
			stringArrays = obj.stringArrays;
			booleanArrays = obj.booleanArrays;
			byteArrays = obj.byteArrays;
			floatArrays = obj.floatArrays;
			shortArrays = obj.shortArrays;
			doubleArrays = obj.doubleArrays;
			longArrays = obj.longArrays;
			objArrays = obj.objArrays;

			in.close();
		}
		catch (Exception i)
		{
			if (!i.getClass().equals(EOFException.class))
			{
				System.err.println("Exception: " + i.getClass().getName());
				i.printStackTrace();
			}
		}

		return this;
	}

	private CompoundTag save()
	{
		try
		{
			file.delete();
			file.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(this);
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Exception: " + e.getClass().getName());
			e.printStackTrace();
		}

		return this;
	}
}
