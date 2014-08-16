package main.options;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import main.Save;
import main.Vars;

public class OpsGeneral extends OptionBar
{
	private JFrame frame;
	private JPanel difPanel, soundPanel;

	@Override
	public void initBar()
	{
		frame = new JFrame();
		Vars.frames.add(frame);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("General Settings");
		difPanel = new JPanel(new BorderLayout());
		difPanel.setBorder(BorderFactory.createTitledBorder("Difficulty"));
		soundPanel = new JPanel(new BorderLayout());
		soundPanel.setBorder(BorderFactory.createTitledBorder("Sound Options"));

		ButtonGroup difButtons = new ButtonGroup();
		JRadioButton normal = new JRadioButton("Normal", Save.DIFFICULTY == 1);
		normal.addActionListener(this);
		normal.setActionCommand("Normal");
		JRadioButton easy = new JRadioButton("Easy", Save.DIFFICULTY == 0);
		easy.addActionListener(this);
		easy.setActionCommand("Easy");
		JRadioButton hard = new JRadioButton("Hard", Save.DIFFICULTY == 2);
		hard.addActionListener(this);
		hard.setActionCommand("Hard");
		difButtons.add(easy);
		difButtons.add(normal);
		difButtons.add(hard);

		ButtonGroup soundButtons = new ButtonGroup();
		JRadioButton on = new JRadioButton("On", Save.SOUND_ON);
		on.addActionListener(this);
		on.setActionCommand("On");
		JRadioButton off = new JRadioButton("Off", !Save.SOUND_ON);
		off.addActionListener(this);
		off.setActionCommand("Off");
		soundButtons.add(on);
		soundButtons.add(off);

		difPanel.add(easy, BorderLayout.WEST);
		difPanel.add(normal, BorderLayout.CENTER);
		difPanel.add(hard, BorderLayout.EAST);
		soundPanel.add(on, BorderLayout.WEST);
		soundPanel.add(off, BorderLayout.EAST);

		frame.add(difPanel, BorderLayout.NORTH);
		frame.add(soundPanel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		if ("Normal".equals(e.getActionCommand()))
		{
			Save.DIFFICULTY = 1;
		}

		if ("Hard".equals(e.getActionCommand()))
		{
			Save.DIFFICULTY = 2;
		}

		if ("Easy".equals(e.getActionCommand()))
		{
			Save.DIFFICULTY = 0;
		}

		if ("On".equals(e.getActionCommand()))
		{
			Save.SOUND_ON = true;
		}

		if ("Off".equals(e.getActionCommand()))
		{
			Save.SOUND_ON = false;
		}
	}
}
