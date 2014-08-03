package main.utils;

import java.io.File;
import java.nio.file.Files;
import main.Login;
import main.Vars;
import main.utils.file.FileHelper;
import modhandler.Importer;

public class ShutdownHook extends Thread
{
	public ShutdownHook()
	{
		super(new Runnable()
		{
			@Override
			public void run()
			{
				File natives = new File(Vars.extension + "natives/");
				if (Files.exists(natives.toPath()))
				{
					System.out.println("Deleting Native: " + natives.getPath());
					FileHelper.deleteDirectory(natives);
				}

				for (int i = 0; i < Importer.modContainers.size(); i++)
				{
					Importer.modContainers.get(i).initiateProxyMethod("onShutdown");
					if (Login.loggedIn)
					{
						Importer.mods.get(i).getSave().writeToTag(new WriterTag(Importer.mods.get(i).CUSTOM));
					}
					System.out.println("Mod[" + i + "]: " + Importer.mods.get(i));
				}

				if (Vars.save != null && Files.exists(Vars.save.file))
				{
					Vars.save.saveGame();
					System.out.println("Shutdown With No Secondary Problems Detected!");
				}
				else
				{
					System.out.println("The Save is Null! Must be logging in... Or Recently Reset");
				}
			}
		});
	}
}
