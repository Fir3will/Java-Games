package main.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import main.Save;
import main.utils.WarningFrame;
import main.utils.WarningFrame.ButtonClicked;
import main.utils.WarningFrame.WarningAction;

public class OpsChangePassword extends OptionBar
{
	private JPanel panel, centerPanel;
	private JTextField oldPassword, password;
	private JButton ok;
	private JLabel title;
	private JFrame frame;

	@Override
	public void initBar()
	{
		panel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new BorderLayout());

		password = new JTextField(20);
		password.addActionListener(this);
		password.setBorder(BorderFactory.createTitledBorder("New Password"));

		oldPassword = new JTextField(20);
		oldPassword.addActionListener(this);
		oldPassword.setBorder(BorderFactory.createTitledBorder("Current Password"));

		title = new JLabel("Change Password");
		title.setForeground(Color.RED);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setOpaque(true);
		title.setFont(new Font("SansSerif", Font.BOLD, 18));
		title.setPreferredSize(new Dimension(100, 25));

		ok = new JButton("Save");
		ok.addActionListener(this);
		ok.setMnemonic(KeyEvent.VK_C);
		ok.setActionCommand("password");
		ok.setToolTipText("Confirm New Password");
		ok.setVisible(true);

		centerPanel.add(password, BorderLayout.SOUTH);
		centerPanel.add(oldPassword, BorderLayout.NORTH);

		panel.add(title, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(ok, BorderLayout.SOUTH);

		frame = new JFrame("Password");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void performEvent(ActionEvent e)
	{
		if ("password".equals(e.getActionCommand())) if (password.getText().replaceAll(" ", "").isEmpty())
		{
			password.setForeground(Color.RED);
			password.setText("Invalid New Password!");
		}
		else if (oldPassword.getText().equals(Save.FILE_PASSWORD))
		{
			new WarningFrame(new WarningAction()
			{

				@Override
				public void buttonClicked(ButtonClicked type)
				{
					if (type == ButtonClicked.YES_CLICKED)
					{
						System.out.println("New Password Set: " + password.getText());
						Save.FILE_PASSWORD = password.getText();
						frame.dispose();
					}
					else
					{
						password.setText("Didn't Change Password!");
					}
				}

				@Override
				public String getWarningMessage()
				{
					return "Change Password?";
				}

				@Override
				public boolean removeFrame()
				{
					return true;
				}

			});
		}
		else
		{
			oldPassword.setText("Invalid Old Password");
			oldPassword.setForeground(Color.RED);
		}
	}
}
