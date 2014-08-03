package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.games.Games;
import main.utils.Main;
import main.utils.helper.Utils;

public class Login extends JPanel implements ActionListener, KeyListener
{
	public static boolean loggedIn;
	private static final long serialVersionUID = 1L;

	public static void login()
	{
		Login LOGIN = new Login();

		Vars.frames = new ArrayList<JFrame>();
		Vars.frames.add(Vars.login);

		Vars.login.setContentPane(LOGIN);
		Vars.login.setSize(800, 600);
		Vars.login.setLocationRelativeTo(null);
		Vars.login.setResizable(false);
		Vars.login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vars.login.pack();
		Vars.login.setVisible(true);
	}

	private JTextField userName, password;
	private JTextArea info;

	private JButton login;

	public Login()
	{
		super(new BorderLayout());

		for (int i = 0; i < Main.mods.length; i++)
		{
			if (!Main.mods[i].isDisabled())
			{
				Main.mods[i].init();
			}
		}

		userName = new JTextField("", 20);
		userName.setBorder(BorderFactory.createTitledBorder("Name"));
		userName.addKeyListener(this);

		password = new JTextField("", 20);
		password.setBorder(BorderFactory.createTitledBorder("Password"));
		password.addKeyListener(this);

		info = new JTextArea("", 5, 20);
		info.setEditable(false);
		info.setLineWrap(true);
		info.setWrapStyleWord(true);
		info.setBorder(BorderFactory.createTitledBorder("Details"));
		info.setText("Type in the name you want to be called. Remember, if you played this game before, you will need to use your old name to load your old stats. Remember to try out all the games and aspects and just have fun! Enjoy my game! Highly Recommended that you set up your options before starting playing.");
		Font small = new Font("Helvetica", Font.ITALIC, 14);
		info.setFont(small);

		login = new JButton("Login");
		login.addActionListener(this);
		login.setMnemonic(KeyEvent.VK_L);
		login.setActionCommand("login");
		login.setToolTipText("Login with the give username");
		login.setVisible(true);

		add(info, BorderLayout.SOUTH);
		add(login, BorderLayout.WEST);
		add(userName, BorderLayout.CENTER);
		add(password, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (!Utils.isEmptyString(userName.getText(), false) && !Utils.isEmptyString(password.getText(), false))
		{
			try
			{
				userName.setCaretPosition(userName.getText().length());

				if (userName.getText().length() > 15)
				{
					userName.setText("Name Is Too Long! Must be less than 15 Letters!");
				}
				else
				{
					Vars.playerName = userName.getText();
					Vars.saveFilePassword = password.getText();
					Vars.save = new SaveFile();

					if (Vars.save.fileCheck())
					{
						Vars.save.init();
						loggedIn = true;
						Games.openLauncher();
						Vars.login.dispose();

						for (int i = 0; i < Main.mods.length; i++)
						{
							if (!Main.mods[i].isDisabled())
							{
								Main.mods[i].startLoading();
							}
						}

						System.gc();
					}
					else
					{
						info.setText("Incorrect Password and Username Combination! \n Did you change the Password? You can use a different username to create a new account. But if you are trying to reset a save, login to the/any account, go to Options, and click Reset Save");
						userName.setBackground(Color.RED);
						password.setBackground(Color.RED);
						Vars.save = null;
					}
				}
			}
			catch (InvalidPathException f)
			{
				info.setText("Invalid Letters for the name or password! FIX IT!");
				userName.setBackground(Color.RED);
				password.setBackground(Color.RED);
			}
		}
		else
		{
			info.setText("Both fields have to be filled with something for you to enter an previously account or to create a new account.");
			userName.setBackground(Color.RED);
			password.setBackground(Color.RED);
		}

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (userName.isFocusOwner())
		{
			if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB)
			{
				password.requestFocus();
				return;
			}
		}
		if (password.isFocusOwner())
		{
			if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_TAB)
			{
				login.doClick();
				return;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		keyPressed(e);
	}
}