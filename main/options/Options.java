package main.options;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Vars;
import main.utils.helper.Utils;
import modhandler.Importer;

public class Options extends JPanel
{
	private static final long serialVersionUID = 1L;
	public static JFrame frame = new JFrame("Options");

	public static final ArrayList<JButton> buttons = new ArrayList<JButton>();

	private JTextArea info;

	public Options()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		info = new JTextArea("Play around with these options. You could change them anytime you want! Have Fun!", 5, 20);
		info.setBackground(Color.LIGHT_GRAY.brighter());
		info.setLineWrap(true);
		info.setEditable(false);
		info.setWrapStyleWord(true);
		info.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		addOption(OpsMods.class, "Mods", "All Installed Mods");
		addOption(OpsPlayerName.class, "Change Player Name", "What do you want the Player's name to be");
		addOption(OpsGeneral.class, "General Settings", "General settings...");
		addOption(OpsResetGame.class, "Reset Game", "Reset the Game so that all you do is delete the jar file to get rid of everything!");
		addOption(OpsCheatCodes.class, "Cheat Codes", "Know any Cheat Codes?, Check here!");
		addOption(OpsPlayerColor.class, "Change Player Color", "In game Player Color to Be!");
		addOption(OpsLogout.class, "Logout", "Logout of the current account");
		addOption(OpsChangePassword.class, "Change Password", "Change the password of your account");

		for (int i = 0; i < Importer.modContainers.size(); i++)
		{
			Importer.modContainers.get(i).initiateProxyMethod("onOptionStartup", this);
		}

		addOption(OpsStatistics.class, "Statistics", "All the Max points, etc.");
		addOption(OpsCredits.class, "Credits", "Makers and Helpers to create this project!");

		add(info);
	}

	public void addOption(Class<? extends OptionBar> option, String buttonName, String buttonToolTip)
	{
		Utils.isNull(option, true);
		final Class<? extends OptionBar> option1 = option;

		JButton button = new JButton(buttonName);
		button.setToolTipText(buttonToolTip);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					option1.newInstance().actionPerformed(e);
				}
				catch (InstantiationException | IllegalAccessException e1)
				{
					System.err.println("Error Adding option!");
					e1.printStackTrace();
				}
			}
		});
		add(button);
		add(Box.createRigidArea(new Dimension(0, 4)));
	}

	public void initGui()
	{
		Options contentPane = new Options();

		Vars.frames.add(Options.frame);

		Options.frame.setContentPane(contentPane);
		Options.frame.setSize(800, 600);
		Options.frame.setLocationRelativeTo(null);
		Options.frame.setResizable(false);
		Options.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Options.frame.pack();
		Options.frame.setVisible(true);
	}
}