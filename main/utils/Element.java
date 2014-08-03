package main.utils;

public class Element
{
	private final String line;

	public Element(String line)
	{
		this.line = line;
	}

	public boolean isBoolean()
	{
		try
		{
			Boolean.parseBoolean(line);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isInt()
	{
		try
		{
			Integer.parseInt(line);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isDouble()
	{
		try
		{
			Double.parseDouble(line);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isByte()
	{
		try
		{
			Byte.parseByte(line);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isString()
	{
		return !isBoolean() && !isInt() && !isDouble() && !isByte();
	}

	public int getInt()
	{
		return getInt(0);
	}

	public int getInt(int def)
	{
		return isInt() ? Integer.parseInt(line) : def;
	}

	public boolean getBoolean()
	{
		return getBoolean(false);
	}

	public boolean getBoolean(boolean def)
	{
		return isBoolean() ? Boolean.parseBoolean(line) : def;
	}

	public double getDouble()
	{
		return getDouble(0.0D);
	}

	public double getDouble(double def)
	{
		return isDouble() ? Double.parseDouble(line) : def;
	}

	public byte getByte()
	{
		return getByte((byte) 0);
	}

	public byte getByte(byte def)
	{
		return isByte() ? Byte.parseByte(line) : def;
	}

	public String getString()
	{
		return getString("~Default~");
	}

	public String getString(String def)
	{
		return isString() ? line : def;
	}

	@Override
	public String toString()
	{
		return line;
	}
}