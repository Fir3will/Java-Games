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
import main.Vars;

public class OpsPlayerColor extends OptionBar implements ChangeListener
{
	private JPanel newContentPane;
	private JColorChooser tcc;
	private JLabel banner;

	@Override
	public void initBar()
	{
		newContentPane = new JPanel(new BorderLayout());

		// Set up the banner at the top of the window
		banner = new JLabel("Your Player's Color Is This, Cancel to save the color!", SwingConstants.CENTER);
		banner.setForeground(Vars.playerColor.brighter());
		banner.setBackground(Color.blue);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif", Font.BOLD, 18));
		banner.setPreferredSize(new Dimension(100, 65));

		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner, BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));

		// Set up color chooser for setting text color
		tcc = new JColorChooser(banner.getForeground());
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Player Color"));

		newContentPane.add(bannerPanel, BorderLayout.CENTER);
		newContentPane.add(tcc, BorderLayout.PAGE_END);

		// Create and set up the window.
		JFrame frame = new JFrame("Change Player Color");
		Vars.frames.add(frame);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// Create and set up the content pane.
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.setVisible(true);

		frame.pack();
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		Color newColor = tcc.getColor();
		banner.setForeground(newColor);
		Vars.playerColor = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), newColor.getAlpha());
		Vars.playerColor = newColor;
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		performEvent(null);
	}
}