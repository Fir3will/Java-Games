package main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import main.Vars;

public class ReaderTag
{
	private SaveType type;
	private boolean isCreated;

	public ReaderTag(SaveType type)
	{
		this.type = type;

		isCreated = new File(Vars.extension + "saves/" + Vars.playerName + "/" + type.getFileName()).exists();
	}

	public Object read()
	{
		if (!isCreated) return null;

		Object obj = null;
		try
		{
			File file = new File(Vars.extension + "saves/" + Vars.playerName + "/" + type.getFileName());
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			in.available();
			obj = in.readObject();
			in.close();
		}
		catch (Exception i)
		{
			System.err.println("Reason: " + i.getMessage());
			i.printStackTrace();
		}

		return obj;
	}

	public int size()
	{
		int size = 0;
		if (!isCreated) return 0;
		try
		{
			File file = new File(Vars.extension + "saves/" + Vars.playerName + "/" + type.getFileName());
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			size = in.available();
			in.close();
		}
		catch (Exception e)
		{
			System.err.println("Error loading ReaderTag.size()");
			e.printStackTrace();
		}
		return size;
	}
}
