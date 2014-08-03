package main.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import main.Vars;

public class WriterTag
{
	public final SaveType type;
	private final ArrayList<Object> obs = new ArrayList<Object>();

	public WriterTag(SaveType type)
	{
		this.type = type;

		File file = new File(Vars.extension + "saves/" + Vars.playerName + "/" + type.getFileName());
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void write(Object obj)
	{
		try
		{
			File file = new File(Vars.extension + "saves/" + Vars.playerName + "/" + type.getFileName());
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(obj);
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Error Loading Object! Class: " + obj.getClass().getName() + ", Object: " + obj);
			e.printStackTrace();
		}
		obs.add(obj);
	}

	public void writeTag(WriterTag tag)
	{
		for (Object obj : obs)
		{
			tag.write(obj);
		}
	}
}
