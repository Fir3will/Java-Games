package main.options;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Save;
import main.Vars;

public class OpsPlayerName extends OptionBar implements ActionListener
{
	private JFrame frame;
	private JTextArea inputUML;
	private JButton saveButton;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if ("playerName".equals(e.getActionCommand()))
		{
			inputUML.setCaretPosition(inputUML.getText().length());

			if (inputUML.getText().length() > 15)
			{
				inputUML.setText("Name Is Too Long! Must be less than 15 Letters!");
			}
			else
			{
				Save.PLAYER_NAME = inputUML.getText();
				System.out.println("Saving Player Name");
			}

			System.out.println("Player Name: " + Save.PLAYER_NAME);
		}
	}

	@Override
	public void initBar()
	{
		frame = new JFrame();
		Vars.frames.add(frame);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Change Name");

		inputUML = new JTextArea("", 4, 20);
		inputUML.setLineWrap(true);
		inputUML.setWrapStyleWord(true);
		inputUML.setForeground(Save.PLAYER_COLOR.darker());

		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		saveButton.setMnemonic(KeyEvent.VK_S);
		saveButton.setActionCommand("playerName");
		saveButton.setToolTipText("Name to be Displayed on the Games");
		saveButton.setVisible(true);

		frame.add(saveButton, BorderLayout.PAGE_END);
		frame.add(new JScrollPane(inputUML), BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
		inputUML.append(Save.PLAYER_NAME);
		inputUML.setCaretPosition(inputUML.getText().length());
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		actionPerformed(e);
	}
}