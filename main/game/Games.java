package main.game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.GameButton;
import main.Save;
import main.Vars;
import main.events.GameEvent;
import main.games.asteroid.AsteroidMission;
import main.games.monsters.Monsters;
import main.games.testing.Testing;
import main.games.zombies.Zombies;
import main.options.Options;
import modhandler.Initialization.State;
import modhandler.Loader;
import modhandler.ModContainer;

public class Games extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public static void openLauncher()
	{
		final Games contentPane = new Games();
		Vars.frame = new JFrame("Games! |" + Save.PLAYER_NAME.toUpperCase() + "|");
		Vars.frame.setContentPane(contentPane);
		Vars.frame.setSize(1200, 1200);
		Vars.frame.setLocationRelativeTo(null);
		Vars.frame.setResizable(false);
		Vars.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vars.frame.pack();
		Vars.frame.setVisible(true);
	}

	private final GameButton options;
	private static ArrayList<GameButton> customTopButtons = new ArrayList<GameButton>();
	private static ArrayList<GameButton> customBottomButtons = new ArrayList<GameButton>();
	private static ArrayList<GameButton> thirdRow = new ArrayList<GameButton>();
	private final JLabel title;
	private static boolean isPlaying;

	private static boolean isEven;

	public Games()
	{
		super(new BorderLayout());
		addGame(Zombies.class);
		addGame(Testing.class);
		addGame(Monsters.class);
		addGame(AsteroidMission.class);
		addGame(PolyCreator.class);

		setBackground(Save.PLAYER_COLOR);

		final JPanel topRow = new JPanel();
		topRow.setBackground(Save.PLAYER_COLOR);
		topRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Save.PLAYER_COLOR.darker().darker()));
		final JPanel bottomRow = new JPanel();
		bottomRow.setBackground(Save.PLAYER_COLOR);
		bottomRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Save.PLAYER_COLOR.darker().darker()));

		for (ModContainer mc : Loader.getContainerList())
		{
			mc.initMethod(State.POST_INIT);
		}
		Loader.addEventToAll(new GameEvent(this));

		options = new GameButton("Options", this);
		options.setToolTipText("Options, Adjust Gameplay");
		options.setVisible(true);

		final Font small = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);
		title = new JLabel("Games", SwingConstants.CENTER);
		title.setVisible(true);
		title.setFont(small);
		title.setForeground(Save.PLAYER_COLOR.darker().darker());
		title.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Save.PLAYER_COLOR.darker().darker()));

		add(title, BorderLayout.NORTH);
		add(topRow, BorderLayout.CENTER);
		add(bottomRow, BorderLayout.SOUTH);

		topRow.add(options);

		for (int i = 0; i < Games.customTopButtons.size(); i++)
		{
			topRow.add(Games.customTopButtons.get(i));
		}
		for (int i = 0; i < Games.customBottomButtons.size(); i++)
		{
			bottomRow.add(Games.customBottomButtons.get(i), BorderLayout.NORTH);
		}
		if (Games.thirdRow.size() > 0)
		{
			for (int i = 0; i < Games.thirdRow.size(); i++)
			{
				bottomRow.add(Games.thirdRow.get(i), BorderLayout.SOUTH);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("Launching Options");

		final Options options = new Options();
		options.initGui();
	}

	public void addGame(final Class<? extends Game> clazz)
	{
		if (!clazz.isAnnotationPresent(NewGame.class)) throw new IllegalArgumentException(clazz.getSimpleName() + " does not have @NewGame annotation! Skipping Registration");

		final String gameName = clazz.getAnnotation(NewGame.class).name();

		for (final Class<? extends Game> game : Games.games)
		{
			if (game.equals(clazz))
			{
				System.err.println("Game already added: " + gameName + ", Skipping...");
				return;
			}
		}

		isEven = !isEven;
		games.add(clazz);
		GameButton button = new GameButton(gameName, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isPlaying) return;
				else
				{
					isPlaying = true;
					System.out.println("Launching " + gameName);
					try
					{
						new GameFrame(clazz.newInstance());
					}
					catch (InstantiationException | IllegalAccessException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});

		if (Games.customTopButtons.size() < 10 && Games.customBottomButtons.size() < 10)
		{
			if (Games.isEven)
			{
				Games.customBottomButtons.add(button);
			}
			else
			{
				Games.customTopButtons.add(button);
			}
		}
		else
		{
			Games.thirdRow.add(button);
		}
		Games.buttons.add(button);
	}

	public static boolean isPlaying()
	{
		return Games.isPlaying;
	}

	public static void setPlaying(boolean playing)
	{
		Games.isPlaying = playing;
	}

	private static final ArrayList<Class<? extends Game>> games = new ArrayList<Class<? extends Game>>();
	private static final ArrayList<GameButton> buttons = new ArrayList<GameButton>();
}
