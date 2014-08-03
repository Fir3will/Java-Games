package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import main.utils.Main;
import main.utils.file.FileHelper;
import main.utils.file.JavaFileHelper;
import testing.ItemTS;
import testing.ItemTS.ItemsTS;

public class SaveFile
{
	public Path file;
	public BufferedReader reader;

	public boolean fileCheck()
	{
		FileHelper.createFile(Vars.extension + "saves/" + Vars.playerName, true);
		FileHelper.createFile(Vars.extension + "saves/" + Vars.playerName + ".zip");

		file = Paths.get(Vars.extension + "saves/" + Vars.playerName + "/" + Vars.playerName + ".dat");
		JavaFileHelper.unzip(new File(Vars.extension + "saves/" + Vars.playerName + ".zip"), new File(Vars.extension + "saves/" + Vars.playerName));

		if (!Files.exists(file))
		{
			System.err.println("File " + file + " Doesn't Exist!");
			System.err.println("Creating new File: " + file);
			FileHelper.createFile(file.toString());
			reader = FileHelper.getFileReader(file.toString());
			init();
			writeValues();
			return true;
		}
		else
		{
			reader = FileHelper.getFileReader(file.toString());
			System.out.print("File " + file + " Exists, Validating Password: ");
			String pass;

			String[] lines = FileHelper.getFileContents(file.toString());

			for (int i = 0; i < lines.length; i++)
			{
				if (lines[i].startsWith("FilePassword: "))
				{
					pass = lines[i].replaceAll("FilePassword: ", "");

					if (pass.equals(Vars.saveFilePassword))
					{
						System.err.println("::Correct Password! :}> \"Good Guy\"");
						return true;
					}
					else
					{
						System.err.println("::Incorrect Password! >:{] \"Douchebag\"");
						return false;
					}
				}
			}
		}

		return false;
	}

	public void init()
	{
		for (int i = 0; i < Main.mods.length; i++)
		{
			if (!Main.mods[i].isDisabled())
			{
				Main.mods[i].postInit();
			}
		}

		reader = FileHelper.getFileReader(file.toString());
		setValues();
	}

	public void saveGame()
	{
		String status = "";
		if (Vars.edited)
		{
			status = "Did not successfully save because (Vars.edited == true)";
		}
		else
		{
			FileHelper.resetFile(file.toString());

			writeValues();

			JavaFileHelper.zip(new File(Vars.extension + "saves/" + Vars.playerName), new File(Vars.extension + "saves/" + Vars.playerName + ".zip"));
			FileHelper.deleteDirectory(new File(Vars.extension + "saves/" + Vars.playerName));

			status = "Successfully Saved!";
		}

		System.out.println("Save Status: " + status);
	}

	public void setValues()
	{
		String[] lines = FileHelper.getFileContents(file.toString());
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;

		for (int i = 0; i < lines.length; i++)
		{
			if (lines[i].contains("~"))
			{
				lines[i] = lines[i].replaceAll("~", "" + (Integer.MAX_VALUE - 1));
				// 2000000000);
			}
			if (lines[i].startsWith("PlayerName: "))
			{
				Vars.playerName = lines[i].replaceAll("PlayerName: ", "");
			}
			if (lines[i].startsWith("SIMaxRound: "))
			{
				Vars.siMaxRound = Integer.parseInt(lines[i].replaceAll("SIMaxRound: ", ""));
			}
			if (lines[i].startsWith("DifficultyLevel: "))
			{
				Vars.difficultyLevel = lines[i].replaceAll("DifficultyLevel: ", "");
			}
			if (lines[i].startsWith("SNMaxScore: "))
			{
				Vars.snMaxScore = Integer.parseInt(lines[i].replaceAll("SNMaxScore: ", ""));
			}
			if (lines[i].startsWith("TNMaxScore: "))
			{
				Vars.tnMaxScore = Integer.parseInt(lines[i].replaceAll("TNMaxScore: ", ""));
			}
			if (lines[i].startsWith("PPMaxScore: "))
			{
				Vars.ppMaxScore = Integer.parseInt(lines[i].replaceAll("PPMaxScore: ", ""));
			}
			if (lines[i].startsWith("TSCoins: "))
			{
				Vars.tsCoins = Integer.parseInt(lines[i].replaceAll("TSCoins: ", ""));
			}
			if (lines[i].startsWith("TSAttackXP: "))
			{
				Vars.tsAttackXP = Integer.parseInt(lines[i].replaceAll("TSAttackXP: ", ""));
			}
			if (lines[i].startsWith("TSDefenceXP: "))
			{
				Vars.tsDefenceXP = Integer.parseInt(lines[i].replaceAll("TSDefenceXP: ", ""));
			}
			if (lines[i].startsWith("TSHealth: "))
			{
				Vars.tsHealth = Integer.parseInt(lines[i].replaceAll("TSHealth: ", ""));
			}
			if (lines[i].startsWith("TSY: "))
			{
				Vars.tsY = Integer.parseInt(lines[i].replaceAll("TSY: ", ""));
			}
			if (lines[i].startsWith("TSX: "))
			{
				Vars.tsX = Integer.parseInt(lines[i].replaceAll("TSX: ", ""));
			}
			if (lines[i].startsWith("TSPlayerItems: "))
			{
				new ItemsTS();

				String[] split = lines[i].replaceAll("TSPlayerItems: ", "").split(" ");

				for (int j = 0; j < 8; j++)
				{
					Vars.tsPlayerItems[j] = ItemTS.getIDItem(Integer.parseInt(split[j]));
				}
			}

			if (lines[i].startsWith("PlayerColorR: "))
			{
				r = Integer.parseInt(lines[i].replaceAll("PlayerColorR: ", ""));
			}
			if (lines[i].startsWith("PlayerColorG: "))
			{
				g = Integer.parseInt(lines[i].replaceAll("PlayerColorG: ", ""));
			}
			if (lines[i].startsWith("PlayerColorB: "))
			{
				b = Integer.parseInt(lines[i].replaceAll("PlayerColorB: ", ""));
			}
			if (lines[i].startsWith("PlayerColorA: "))
			{
				a = Integer.parseInt(lines[i].replaceAll("PlayerColorA: ", ""));
			}

			Vars.playerColor = new Color(r, g, b, a);
		}
	}

	private void write(String stat, Object stuff)
	{
		FileHelper.writeToFile(file.toString(), stat + stuff, true);
	}

	private void writeValues()
	{
		write("FilePassword: ", Vars.saveFilePassword);
		write("PlayerName: ", Vars.playerName);
		write("DifficultyLevel: ", Vars.difficultyLevel);
		write("SIMaxRound: ", Vars.siMaxRound);
		write("SNMaxScore: ", Vars.snMaxScore);
		write("TNMaxScore: ", Vars.tnMaxScore);
		write("PPMaxScore: ", Vars.ppMaxScore);
		write("TSCoins: ", Vars.tsCoins);
		write("TSAttackXP: ", Vars.tsAttackXP);
		write("TSDefenceXP: ", Vars.tsDefenceXP);
		write("TSX: ", Vars.tsX);
		write("TSY: ", Vars.tsY);
		write("TSHealth: ", Vars.tsHealth);

		int[] ids = new int[Vars.tsPlayerItems.length];

		for (int i = 0; i < Vars.tsPlayerItems.length; i++)
		{
			if (Vars.tsPlayerItems[i] != null)
			{
				ids[i] = Vars.tsPlayerItems[i].itemID;
			}
		}

		write("TSPlayerItems: ", ids[0] + " " + ids[1] + " " + ids[2] + " " + ids[3] + " " + ids[4] + " " + ids[5] + " " + ids[6] + " " + ids[7]);

		write("PlayerColorR: ", Vars.playerColor.getRed());
		write("PlayerColorG: ", Vars.playerColor.getGreen());
		write("PlayerColorB: ", Vars.playerColor.getBlue());
		write("PlayerColorA: ", Vars.playerColor.getAlpha());
	}
}