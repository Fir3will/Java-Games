package main.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import main.Save;

public class OpsPlayerColor extends OptionBar implements ChangeListener
{
	private JPanel newContentPane;
	private JColorChooser tcc;
	private JLabel banner;

	@Override
	public void initBar()
	{
		newContentPane = new JPanel(new BorderLayout());
		banner = new JLabel("Your Player's Color Is This, Cancel to save the color!", SwingConstants.CENTER);
		banner.setForeground(Save.PLAYER_COLOR.brighter());
		banner.setBackground(Color.blue);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif", Font.BOLD, 18));
		banner.setPreferredSize(new Dimension(100, 65));
		final JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner, BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
		tcc = new JColorChooser(banner.getForeground());
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Player Color"));
		newContentPane.add(bannerPanel, BorderLayout.CENTER);
		newContentPane.add(tcc, BorderLayout.PAGE_END);
		final JFrame frame = new JFrame("Change Player Color");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		final Color newColor = tcc.getColor();
		banner.setForeground(newColor);
		Save.PLAYER_COLOR = newColor;
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		performEvent(null);
	}
}