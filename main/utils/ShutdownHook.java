package main.utils;

import java.io.File;
import java.nio.file.Files;
import main.Login;
import main.Vars;
import main.events.ShutdownEvent;
import main.utils.file.FileHelper;
import modhandler.Loader;
import modhandler.ModContainer;

public class ShutdownHook extends Thread
{
	public ShutdownHook()
	{
		super(new Runnable()
		{
			@Override
			public void run()
			{
				final File natives = new File(Vars.NATIVES_DIR);
				if (Files.exists(natives.toPath()))
				{
					System.out.println("Deleting Native: " + natives.getPath());
					FileHelper.deleteDirectory(natives);
				}

				Loader.addEventToAll(new ShutdownEvent());
				for (int i = 0; i < Loader.getContainerList().size(); i++)
				{
					ModContainer mc = Loader.getContainerList().get(i);
					if (Login.loggedIn)
					{
						mc.startSaving();
					}
					System.out.println("Mod[" + i + "]: " + mc.getModid());
				}

				if (Vars.save != null)
				{
					Vars.save.save();
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
