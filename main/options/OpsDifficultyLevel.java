package main.options;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import main.Vars;

public class OpsDifficultyLevel extends OptionBar
{
	private JFrame frame;
	private JButton NormalDL, EasyDL, HardDL;

	@Override
	public void initBar()
	{
		frame = new JFrame();
		Vars.frames.add(frame);
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setTitle("Change Difficulty");

		NormalDL = new JButton("Normal");
		NormalDL.addActionListener(this);
		NormalDL.setMnemonic(KeyEvent.VK_N);
		NormalDL.setActionCommand("Normal");
		NormalDL.setToolTipText("Normal Difficulty (Not Recommended)");
		NormalDL.setVisible(true);

		EasyDL = new JButton("Easy");
		EasyDL.addActionListener(this);
		EasyDL.setMnemonic(KeyEvent.VK_E);
		EasyDL.setActionCommand("Easy");
		EasyDL.setToolTipText("Easy Difficulty (Not Recommended)");
		EasyDL.setVisible(true);

		HardDL = new JButton("Hard");
		HardDL.addActionListener(this);
		HardDL.setMnemonic(KeyEvent.VK_H);
		HardDL.setActionCommand("Hard");
		HardDL.setToolTipText("Hard Difficulty (Not Recommended)");
		HardDL.setVisible(true);

		frame.add(EasyDL, BorderLayout.PAGE_START);
		frame.add(NormalDL, BorderLayout.CENTER);
		frame.add(HardDL, BorderLayout.PAGE_END);

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		if ("Normal".equals(e.getActionCommand()))
		{
			Vars.difficultyLevel = "Normal";
			frame.dispose();
		}

		if ("Hard".equals(e.getActionCommand()))
		{
			Vars.difficultyLevel = "Hard";
			frame.dispose();
		}

		if ("Easy".equals(e.getActionCommand()))
		{
			Vars.difficultyLevel = "Easy";
			frame.dispose();
		}

	}
}
