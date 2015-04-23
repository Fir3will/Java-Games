package main.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class WarningFrame extends JPanel implements ActionListener
{
	public static enum ButtonClicked
	{
		YES_CLICKED,
		NO_CLICKED;
	}

	public static interface WarningAction
	{
		public void buttonClicked(ButtonClicked type);

		public String getWarningMessage();

		public boolean removeFrame();
	}

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private final JButton yes, no;
	private final JLabel warning;

	private final WarningAction action;

	private final JPanel bottom;

	public WarningFrame(WarningAction a)
	{
		super(new BorderLayout());

		bottom = new JPanel();
		action = a;

		warning = new JLabel(action.getWarningMessage());
		warning.setFont(new Font("SansSerif", Font.BOLD, 14));
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		warning.setPreferredSize(new Dimension(action.getWarningMessage().length() * 7, 65));

		yes = new JButton("Yes");
		yes.addActionListener(this);
		yes.setMnemonic(KeyEvent.VK_Y);
		yes.setActionCommand("yes");
		yes.setToolTipText("Reset the File");
		yes.setVisible(true);

		no = new JButton("No");
		no.addActionListener(this);
		no.setMnemonic(KeyEvent.VK_N);
		no.setActionCommand("no");
		no.setToolTipText("Do not Reset the File");
		no.setVisible(true);

		add(warning, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		bottom.add(yes);
		bottom.add(no);

		init();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if ("yes".equals(e.getActionCommand()))
		{
			action.buttonClicked(ButtonClicked.YES_CLICKED);
			if (action.removeFrame())
			{
				frame.dispose();
			}
		}
		if ("no".equals(e.getActionCommand()))
		{
			action.buttonClicked(ButtonClicked.NO_CLICKED);
			if (action.removeFrame())
			{
				frame.dispose();
			}
		}
	}

	public void init()
	{
		frame = new JFrame("WARNING");
		frame.add(this);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}
}
