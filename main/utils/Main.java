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
				System.out.println("Launching...");
				FileHelper.createFile(Vars.HOME_DIR, true);
				FileHelper.createFile(Vars.HOME_DIR + "mods", true);
				FileHelper.createFile(Vars.HOME_DIR + "natives", true);
				FileHelper.createFile(Vars.HOME_DIR + "saves", true);

				try
				{
					Files.walkFileTree(new File(Vars.HOME_DIR + "mods").toPath(), new FVisitor());
				}
				catch (IOException e)
				{
					System.err.println("Error Loading Files in " + new File(Vars.HOME_DIR + "mods").toString());
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
		Main.mainFile = Vars.HOME_DIR + "Main.txt";
		FileHelper.createFile(Vars.HOME_DIR, true);
		FileHelper.createFile(Vars.HOME_DIR + "Documents", true);
		FileHelper.resetFile(Main.mainFile);

		mainFile("Accounts: ");

		int counter = 0;
		File[] files = Paths.get(Vars.HOME_DIR).toFile().listFiles();

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