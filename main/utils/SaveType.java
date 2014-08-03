package main.utils;

public enum SaveType
{
	LEVEL, SPRITES, MISC;

	public String getFileName()
	{
		return new String(name().toLowerCase() + ".dat");
	}
}
