package main.options;

import java.awt.event.ActionEvent;
import java.nio.file.Paths;
import main.Vars;
import main.utils.WarningFrame;
import main.utils.WarningFrame.ButtonClicked;
import main.utils.WarningFrame.WarningAction;
import main.utils.file.FileHelper;

public class OpsResetGame extends OptionBar
{
	@Override
	public void initBar()
	{
		new WarningFrame(new WarningAction()
		{
			@Override
			public void buttonClicked(ButtonClicked type)
			{
				if (type == ButtonClicked.YES_CLICKED)
				{
					FileHelper.deleteDirectory(Paths.get(Vars.extension.substring(0, Vars.extension.lastIndexOf("/"))).toFile());
					System.exit(0);
				}
				else
				{
					System.out.println("The Cats have been SAVED! By: " + Vars.playerName);
				}
			}

			@Override
			public String getWarningMessage()
			{
				return "This will delete all the characters and the cache folder!";
			}

			@Override
			public boolean removeFrame()
			{
				return true;
			}
		});
	}

	@Override
	public void performEvent(ActionEvent e)
	{

	}
}
