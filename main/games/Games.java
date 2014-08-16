package main.games;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.Save;
import main.Vars;
import main.options.Options;
import main.utils.helper.Sound;
import modhandler.Importer;
import tanks.Tanks;
import testing.RPG;

public class Games extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public static void openLauncher()
	{
		Games contentPane = new Games();
		Vars.frame = new JFrame("Games! |" + Save.PLAYER_NAME.toUpperCase() + "|");

		Vars.frames.add(Vars.frame);

		Vars.frame.setContentPane(contentPane);
		Vars.frame.setSize(800, 600);
		Vars.frame.setLocationRelativeTo(null);
		Vars.frame.setResizable(false);
		Vars.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vars.frame.pack();
		Vars.frame.setVisible(true);
	}

	private JButton options;
	private static ArrayList<JButton> customTopButtons = new ArrayList<JButton>();
	private static ArrayList<JButton> customBottomButtons = new ArrayList<JButton>();
	private static ArrayList<JButton> thirdRow = new ArrayList<JButton>();
	private JLabel title;

	private static boolean isEven;

	public Games()
	{
		super(new BorderLayout());

		addGame(RPG.class, 800, 600);
		addGame(Tanks.class, 400, 600);

		setBackground(Save.PLAYER_COLOR);

		JPanel topRow = new JPanel();
		topRow.setBackground(Save.PLAYER_COLOR);
		topRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
		JPanel bottomRow = new JPanel();
		bottomRow.setBackground(Save.PLAYER_COLOR);
		bottomRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));

		for (int i = 0; i < Importer.modContainers.size(); i++)
		{
			Importer.modContainers.get(i).initiateProxyMethod("onGamesMenuStartup", this);
		}

		options = new JButton("Options");
		options.addActionListener(this);
		options.setMnemonic(KeyEvent.VK_O);
		options.setActionCommand("options");
		options.setToolTipText("Options, Adjust Gameplay");
		options.setVisible(true);

		Font small = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);
		title = new JLabel("Simple Java Games", SwingConstants.CENTER);
		title.setVisible(true);
		title.setFont(small);
		title.setForeground(Color.RED);
		title.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));

		add(topRow, BorderLayout.CENTER);
		add(bottomRow, BorderLayout.SOUTH);
		add(title, BorderLayout.NORTH);

		topRow.add(options);

		for (int i = 0; i < customTopButtons.size(); i++)
		{
			topRow.add(customTopButtons.get(i));
		}
		for (int i = 0; i < customBottomButtons.size(); i++)
		{
			bottomRow.add(customBottomButtons.get(i), BorderLayout.NORTH);
		}
		if (thirdRow.size() <= 0) return;

		for (int i = 0; i < thirdRow.size(); i++)
		{
			bottomRow.add(thirdRow.get(i), BorderLayout.SOUTH);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if ("options".equals(e.getActionCommand()))
		{
			System.out.println("Launching Options");

			Options options = new Options();
			options.initGui();
		}
	}

	public void addGame(final Class<? extends GamePanel> clazz, final int width, final int height)
	{
		if (!clazz.isAnnotationPresent(NewGame.class))
		{
			System.err.println(clazz.getSimpleName() + " does not have @Props annotation! Skipping Game");
			return;
		}

		final String gameName = clazz.getAnnotation(NewGame.class).name();

		for (Class<? extends GamePanel> game : games)
		{
			if (game.equals(clazz))
			{
				System.err.println("Game already added: " + gameName + ", Skipping...");
				return;
			}
		}

		isEven = !isEven;
		games.add(clazz);
		JButton button = new JButton(gameName);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Launching " + gameName);
				try
				{
					Sound.playSound(Sound.BUTTON_CLICK, false, 0.0F);
					Vars.frames.add(new GameFrame(clazz.newInstance(), width, height));
				}
				catch (InstantiationException | IllegalAccessException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		if (customTopButtons.size() < 10 && customBottomButtons.size() < 10)
		{
			if (isEven)
			{
				customBottomButtons.add(button);
			}
			else
			{
				customTopButtons.add(button);
			}
		}
		else
		{
			thirdRow.add(button);
		}
		buttons.add(button);
	}

	public static void startGame(String buttonName)
	{
		for (JButton b : buttons)
		{
			if (b.getText().equals(buttonName))
			{
				b.doClick();
				return;
			}
		}
	}

	private static final ArrayList<Class<? extends GamePanel>> games = new ArrayList<Class<? extends GamePanel>>();
	private static final ArrayList<JButton> buttons = new ArrayList<JButton>();
}
