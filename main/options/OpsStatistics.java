package main.options;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import main.Vars;

public class OpsStatistics extends OptionBar
{
	private JTextArea credits;

	@Override
	public void initBar()
	{
		JFrame frame = new JFrame("Credits");
		Vars.frames.add(frame);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		credits = new JTextArea(5, 50);
		credits.setEditable(false);
		credits.setForeground(Color.BLUE);
		credits.setBorder(BorderFactory.createTitledBorder("Statistics for \"" + Vars.playerName + "\""));
		credits.append("Space Invaders Max Round: " + Vars.siMaxRound + "\n");
		credits.append("Snake Max Score: " + Vars.snMaxScore + "\n");
		credits.append("Tanks Max Score: " + Vars.tnMaxScore + "\n");
		credits.append("Popous Pelican Max Score: " + Vars.ppMaxScore + "\n");
		credits.append("* Coins: " + Vars.tsCoins + "\n");

		frame.add(new JScrollPane(credits));

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void performEvent(ActionEvent e)
	{

	}
}
