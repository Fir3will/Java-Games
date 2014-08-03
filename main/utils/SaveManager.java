package main.utils;

import main.Login;
import main.utils.file.JavaFileHelper;
import modhandler.Mod;
import modhandler.SimpleMod;

public abstract class SaveManager
{
	public abstract void readFromTag(ReaderTag tag);

	public abstract void writeToTag(WriterTag tag);

	public final void writeToTag(SaveType type)
	{
		if (Login.loggedIn)
		{
			writeToTag(new WriterTag(type));
		}
	}

	public final void readFromTag(SaveType type)
	{
		if (Login.loggedIn)
		{
			readFromTag(new ReaderTag(type));
		}
	}

	public final void writeToTag()
	{
		if (Login.loggedIn)
		{
			try
			{
				Class<?> clazz = JavaFileHelper.classThatCalledAt(3);
				if (clazz.getAnnotation(Mod.class) != null)
				{
					writeToTag(new WriterTag(((SimpleMod) clazz.newInstance()).CUSTOM));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public final void readFromTag()
	{
		if (Login.loggedIn)
		{
			try
			{
				Class<?> clazz = JavaFileHelper.classThatCalledAt(3);
				if (clazz.getAnnotation(Mod.class) != null)
				{
					readFromTag(new ReaderTag(((SimpleMod) clazz.newInstance()).CUSTOM));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
