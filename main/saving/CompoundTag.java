package main.saving;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import main.Save;
import main.Vars;
import main.game.Game;
import main.game.NewGame;
import main.utils.file.FileHelper;

public class CompoundTag implements Serializable, Cloneable
{
	public static final File PLAYER_FOLDER = new File(Vars.SAVE_DIR + Save.PLAYER_NAME + "/");
	private static final long serialVersionUID = 1L;
	private final File file;

	public CompoundTag(Game game)
	{
		this(new File(CompoundTag.PLAYER_FOLDER, "/" + (game.getClass().isAnnotationPresent(NewGame.class) ? game.getClass().getAnnotation(NewGame.class).name() : "NULL") + ".dat"));
	}

	public CompoundTag(File saveTo)
	{
		file = saveTo;
		init();
	}

	public void init()
	{
		FileHelper.createFile(file.getPath());
	}

	public DataTag load()
	{
		DataTag tag = null;
		try
		{
			final ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			tag = (DataTag) in.readObject();
			in.close();
		}
		catch (final Exception i)
		{
			if (!i.getClass().equals(EOFException.class))
			{
				System.err.println("Exception: " + i.getClass().getName());
				i.printStackTrace();
			}
		}

		return tag == null ? new DataTag() : tag;
	}

	public void save(DataTag tag)
	{
		try
		{
			file.delete();
			file.createNewFile();
			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(tag);
			out.close();
		}
		catch (final IOException e)
		{
			System.err.println("Exception: " + e.getClass().getName());
			e.printStackTrace();
		}
	}
}
