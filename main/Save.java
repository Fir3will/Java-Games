package main;

import java.awt.Color;
import java.io.File;
import main.utils.CompoundTag;
import main.utils.file.FileHelper;
import testing.ItemTS;

public class Save
{
	public File PLAYER_DIR;
	private CompoundTag tag;

	public CompoundTag getTag()
	{
		return tag;
	}

	public boolean canLogin()
	{
		PLAYER_DIR = new File(Vars.SAVE_DIR + PLAYER_NAME);

		if (PLAYER_DIR.exists())
		{
			FileHelper.createFile(PLAYER_DIR.toString(), true);
			String pass = FILE_PASSWORD;
			tag = new CompoundTag(new File(PLAYER_DIR, "/player.dat"));
			load();
			if (pass.equals(tag.getString("Password"))) return true;
			else return false;
		}
		else
		{
			FileHelper.createFile(PLAYER_DIR.toString(), true);
			tag = new CompoundTag(new File(Vars.SAVE_DIR + PLAYER_NAME + "/player.dat"));
			save();
			return true;
		}
	}

	public void load()
	{
		TS_DEFENCE_XP = tag.getInteger("TS Defence XP");
		TS_ATTACK_XP = tag.getInteger("TS Attack XP");
		TS_X = tag.getInteger("TS X");
		TS_Y = tag.getInteger("TS Y");
		TS_COINS = tag.getInteger("TS Coins");
		TN_MAX_SCORE = tag.getInteger("TN SCORE");
		DIFFICULTY = tag.getInteger("Difficulty");
		PLAYER_NAME = tag.getString("Player Name");
		PLAYER_COLOR = (Color) tag.getSerializable("Player Color");
		SOUND_ON = tag.getBoolean("Is Sound");

		for (int i = 0; i < TS_ITEMS.length; i++)
		{
			TS_ITEMS[i] = ItemTS.getIDItem(tag.getInteger("TS Item-" + i));
		}
	}

	public void save()
	{
		tag.setInteger("TS Defence XP", TS_DEFENCE_XP);
		tag.setInteger("TS Attack XP", TS_ATTACK_XP);
		tag.setInteger("TS X", TS_X);
		tag.setInteger("TS Y", TS_Y);
		tag.setInteger("TS Coins", TS_COINS);
		tag.setInteger("TN SCORE", TN_MAX_SCORE);
		tag.setInteger("Difficulty", DIFFICULTY);
		tag.setString("Password", FILE_PASSWORD);
		tag.setString("Player Name", PLAYER_NAME);
		tag.setSerializable("Player Color", PLAYER_COLOR);
		tag.setBoolean("Is Sound", SOUND_ON);

		for (int i = 0; i < TS_ITEMS.length; i++)
		{
			tag.setInteger("TS Item-" + i, TS_ITEMS[i] == null ? 0 : TS_ITEMS[i].itemID);
		}
	}

	public static int TS_DEFENCE_XP = 1;
	public static int TS_ATTACK_XP = 1;
	public static int TS_HEALTH = 50;
	public static int TS_Y = 400;
	public static int TS_X = 400;
	public static int TS_COINS = 0;
	public static int TN_MAX_SCORE = 1;
	public static int DIFFICULTY = 1;
	public static ItemTS[] TS_ITEMS = new ItemTS[8];
	public static Color PLAYER_COLOR = Color.LIGHT_GRAY;
	public static String FILE_PASSWORD;
	public static String PLAYER_NAME;
	public static boolean SOUND_ON = true;
}
