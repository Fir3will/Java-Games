package main.options;

import java.awt.event.ActionEvent;
import main.Save;
import main.Vars;
import main.utils.WarningFrame;
import main.utils.WarningFrame.ButtonClicked;
import main.utils.WarningFrame.WarningAction;

public class OpsLogout extends OptionBar
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
					for (int i = 0; i < Vars.frames.size(); i++)
					{
						Vars.frames.get(i).dispose();
					}
					Vars.save.save();
					Vars.login.setVisible(true);

					System.out.println("\"" + Save.PLAYER_NAME + "\" Logged Out!");
				}
			}

			@Override
			public String getWarningMessage()
			{
				return "Logout of " + Save.PLAYER_NAME + "?";
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
