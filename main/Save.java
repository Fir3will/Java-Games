package main;

import java.awt.Color;
import java.io.File;
import java.util.List;
import main.events.SaveEvent;
import main.events.SaveEvent.OnLoadEvent;
import main.events.SaveEvent.OnSaveEvent;
import main.saving.DataTag;
import main.utils.file.FileHelper;
import main.utils.helper.JavaHelp;
import modhandler.Loader;

public class Save
{
	public File PLAYER_DIR;
	private DataTag tag;
	private final List<String> stats;

	public Save()
	{
		stats = JavaHelp.newArrayList();
		addStatistic("Difficulty: " + Save.DIFFICULTY);
		Loader.addEventToAll(new SaveEvent(this));
	}

	public DataTag getTag()
	{
		return tag;
	}

	public boolean canLogin()
	{
		PLAYER_DIR = new File(Vars.SAVE_DIR + Save.PLAYER_NAME);

		if (PLAYER_DIR.exists())
		{
			FileHelper.createFile(PLAYER_DIR.toString(), true);
			final String pass = Save.FILE_PASSWORD;
			load();
			if (pass.equals(tag.getString("Password", ""))) return true;
			else return false;
		}
		else
		{
			FileHelper.createFile(PLAYER_DIR.toString(), true);
			tag = new DataTag();
			save();
			return true;
		}
	}

	public void load()
	{
		tag = DataTag.loadFrom(new File(Vars.SAVE_DIR + Save.PLAYER_NAME + "/player.dat"));
		Save.DIFFICULTY = tag.getInteger("Difficulty", 0);
		Save.PLAYER_NAME = tag.getString("Player Name", "Player");
		Save.SOUND_ON = tag.getBoolean("Is Sound", true);

		final int r = tag.getInteger("Color R", 255);
		final int g = tag.getInteger("Color G", 255);
		final int b = tag.getInteger("Color B", 255);
		final int a = tag.getInteger("Color A", 0);
		Save.PLAYER_COLOR = new Color(r, g, b, a);
		Loader.addEventToAll(new OnLoadEvent(this, tag));
	}

	public void save()
	{
		tag.setInteger("Difficulty", Save.DIFFICULTY);
		tag.setString("Password", Save.FILE_PASSWORD);
		tag.setString("Player Name", Save.PLAYER_NAME);
		tag.setBoolean("Is Sound", Save.SOUND_ON);
		tag.setInteger("Color R", Save.PLAYER_COLOR.getRed());
		tag.setInteger("Color G", Save.PLAYER_COLOR.getGreen());
		tag.setInteger("Color B", Save.PLAYER_COLOR.getBlue());
		tag.setInteger("Color A", Save.PLAYER_COLOR.getAlpha());
		Loader.addEventToAll(new OnSaveEvent(this, tag));
		DataTag.saveTo(new File(Vars.SAVE_DIR + Save.PLAYER_NAME + "/player.dat"), tag);
	}

	public void addStatistic(String statistic)
	{
		if (statistic == null || stats.contains(statistic)) return;
		stats.add(statistic);
	}

	public static int DIFFICULTY = 1;
	public static Color PLAYER_COLOR = Color.LIGHT_GRAY;
	public static String FILE_PASSWORD;
	public static String PLAYER_NAME;
	public static boolean SOUND_ON = true;
}
