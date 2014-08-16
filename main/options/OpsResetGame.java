package main.options;

import java.awt.event.ActionEvent;
import java.nio.file.Paths;
import main.Save;
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
					FileHelper.deleteDirectory(Paths.get(Vars.HOME_DIR.substring(0, Vars.HOME_DIR.lastIndexOf("/"))).toFile());
					System.exit(0);
				}
				else
				{
					System.out.println("The Cats have been SAVED! By: " + Save.PLAYER_NAME);
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
