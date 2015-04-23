package main;

import java.io.File;
import java.nio.file.Files;
import javax.swing.SwingUtilities;
import main.utils.ShutdownHook;
import main.utils.file.FVisitor;
import main.utils.file.FileHelper;
import main.utils.file.JavaFileHelper;
import main.utils.helper.Logger;
import modhandler.Loader;

public class Main
{
	private final File test;
	public static Object[] mods;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new Main();
			}
		});
	}

	private Main()
	{
		System.setOut(new Logger(System.out));
		System.setErr(new Logger(System.err));

		System.out.println("Home Directory: " + System.getProperty("user.home"));
		System.out.println("Current Directory: " + System.getProperty("user.dir"));

		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		System.out.println("Launching...");
		FileHelper.createFile(Vars.HOME_DIR, true);
		FileHelper.createFile(Vars.HOME_DIR + "Documents", true);
		FileHelper.createFile(Vars.HOME_DIR + "mods", true);
		FileHelper.createFile(Vars.HOME_DIR + "natives", true);
		FileHelper.createFile(Vars.HOME_DIR + "saves", true);

		File bin = new File(System.getProperty("user.dir") + "/bin");
		try
		{
			JavaFileHelper.getModsFrom(bin, bin);
			Files.walkFileTree(new File(Vars.HOME_DIR + "mods").toPath(), new FVisitor());
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		System.out.println("Is Vanilla Gameplay: " + Loader.isVanilla());
		test = new File(Vars.HOME_DIR + "test.txt");
		FileHelper.resetFile(test.getPath());

		try
		{
			if (tryStuff())
			{
				System.exit(0);
			}
		}
		catch (final Throwable e)
		{
			e.printStackTrace();
		}

		Main.mods = Loader.getModList().toArray(new Object[0]);

		Login.login();
	}

	private boolean tryStuff() throws Throwable
	{
		return false;
	}

	public static void p(Object... o)
	{
		String s = "";
		for (int i = 0; i < o.length; i++)
		{
			s += "{" + o[i] + (i == o.length - 1 ? "}" : "}, ");
		}
		System.out.println(s);
	}
}