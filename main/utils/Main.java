package main.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.SwingUtilities;
import main.Login;
import main.Vars;
import main.utils.file.FVisitor;
import main.utils.file.FileHelper;
import main.utils.helper.Coder;
import main.utils.helper.Logger;
import modhandler.Importer;
import modhandler.SimpleMod;

public class Main
{
	public static String mainFile;

	public static SimpleMod[] mods;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				System.setOut(new Logger(System.out));
				System.setErr(new Logger(System.err));

				System.out.println("Home Directory: " + System.getProperty("user.home"));
				System.out.println("Current Directory: " + System.getProperty("user.dir"));

				Runtime.getRuntime().addShutdownHook(new ShutdownHook());
				System.out.println("Launching Game");
				FileHelper.createFile(Vars.extension, true);
				FileHelper.createFile(Vars.extension + "mods", true);
				FileHelper.createFile(Vars.extension + "natives", true);
				FileHelper.createFile(Vars.extension + "saves", true);

				try
				{
					System.out.println("Loading All Mods");
					Files.walkFileTree(new File(Vars.extension + "mods").toPath(), new FVisitor());
				}
				catch (IOException e)
				{
					System.err.println("Error Loading Files in " + new File(Vars.extension + "mods").toPath());
					e.printStackTrace();
				}

				setupMainFile();

				Main.mods = Importer.mods.toArray(new SimpleMod[0]);

				Login.login();
			}
		});
	}

	public static void mainFile(String string)
	{
		FileHelper.writeToFile(Main.mainFile, Coder.encode(string));
	}

	private static void setupMainFile()
	{
		Main.mainFile = Vars.extension + "Main.txt";
		FileHelper.createFile(Vars.extension, true);
		FileHelper.createFile(Vars.extension + "Documents", true);
		FileHelper.resetFile(Main.mainFile);

		mainFile("Accounts: ");

		int counter = 0;
		File[] files = Paths.get(Vars.extension).toFile().listFiles();

		for (int i = 0; i < files.length; i++)
		{
			if (files[i].getName().endsWith(".class"))
			{
				mainFile("\"" + files[i].getName() + "\"");
				counter++;
			}
		}
		String f1 = counter == 1 ? "1 Account" : "Number of Accounts: " + counter;

		mainFile(counter > 0 ? f1 : "No Accounts");
	}
}