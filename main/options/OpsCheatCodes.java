package main.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Vars;

public class OpsCheatCodes extends OptionBar implements ActionListener
{
	private JFrame frame;
	private JTextArea inputUML;
	private JTextArea cheatConsole;
	private JButton checkButton;
	private final ArrayList<String> console = new ArrayList<String>();
	private int h = 0;

	@Override
	public void initBar()
	{
		for (int i = 0; i < Vars.cheats.length; i++)
		{
			Vars.cheatsEnabled[i] = "false";
		}

		frame = new JFrame();

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Change Name");

		inputUML = new JTextArea("", 6, 20);
		inputUML.setLineWrap(true);
		inputUML.setWrapStyleWord(true);

		cheatConsole = new JTextArea("Cheat Console", 6, 50);
		cheatConsole.setEditable(false);
		cheatConsole.setText("Enter a Cheat you think is a Cheat and click Enter!\n");

		checkButton = new JButton("Enter");
		checkButton.addActionListener(this);
		checkButton.setMnemonic(KeyEvent.VK_E);
		checkButton.setActionCommand("cheatCode");
		checkButton.setToolTipText("Enter the Cheat");
		checkButton.setVisible(true);

		frame.add(checkButton, BorderLayout.PAGE_END);
		frame.add(new JScrollPane(inputUML), BorderLayout.WEST);
		frame.add(new JScrollPane(cheatConsole), BorderLayout.EAST);

		frame.pack();
		frame.setVisible(true);
		inputUML.append("");
		inputUML.setCaretPosition(inputUML.getText().length());
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		if ("cheatCode".equals(e.getActionCommand()))
		{
			inputUML.setCaretPosition(inputUML.getText().length());

			for (int i = 0; i < Vars.cheats.length; i++)
				if (inputUML.getText().equals(Vars.cheats[i]))
				{
					if (Vars.cheatsEnabled[i].matches("true"))
					{
						Vars.cheatsEnabled[i] = "false";
						cheatConsole.setForeground(Color.GREEN);
						console.add(h, "Cheat " + Vars.cheats[i] + " Disabled! Rewrite to enable\n");
						break;
					}

					else if (Vars.cheatsEnabled[i].matches("false"))
					{
						Vars.cheatsEnabled[i] = "true";
						cheatConsole.setForeground(Color.RED);
						console.add(h, "Cheat " + Vars.cheats[i] + " Enabled! Rewrite to disable\n");
						break;
					}
				}
				else
				{
					cheatConsole.setForeground(Color.BLUE);
					console.add(h, "The word " + inputUML.getText() + " is Not a Cheat. Sorry, Try Again!\n");
				}
			cheatConsole.append("---------------------------------------------------\n");
			cheatConsole.append(console.get(h));
			h++;
		}
	}
}