package main.options;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Vars;

public class OpsCredits extends OptionBar
{
	private JTextArea credits;
	private String[] creds;

	@Override
	public void initBar()
	{
		JFrame frame = new JFrame("Credits");
		Vars.frames.add(frame);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		creds = new String[] {"Created By:  Hashim Kayani", "Programmer: Hashim Kayani", "Graphics Manager: Hashim Kayani", "SHOUT OUTS: TRCS(For their Botball Program)"};

		credits = new JTextArea(5, 50);
		credits.setEditable(false);
		credits.setForeground(Color.BLUE);
		credits.setBorder(BorderFactory.createTitledBorder("Credits"));

		for (int i = 0; i < creds.length; i++)
		{
			credits.append(creds[i] + "\n");
		}

		frame.add(new JScrollPane(credits));

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void performEvent(ActionEvent e)
	{

	}
}
