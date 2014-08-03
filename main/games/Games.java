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
import main.Vars;
import main.options.Options;
import modhandler.Importer;
import popouspelican.PopousPelicanPP;
import puzzle.PuzzleP;
import snake.SnakeSN;
import spaceinvaders.LaunchSI;
import tanks.TanksTN;
import testing.Testing;
import tetris.Tetris;
import breakout.BreakOutBO;

public class Games extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public static void openLauncher()
	{
		Games contentPane = new Games();
		Vars.frame = new JFrame("Games! |" + Vars.playerName.toUpperCase() + "|");

		Vars.frames.add(Vars.frame);

		Vars.frame.setContentPane(contentPane);
		Vars.frame.setSize(800, 600);
		Vars.frame.setLocationRelativeTo(null);
		Vars.frame.setResizable(false);
		Vars.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vars.frame.pack();
		Vars.frame.setVisible(true);
	}

	private JButton options, spaceInvaders, puzzle, snake, breakOut, tanks, popousPelican;
	private static ArrayList<JButton> customTopButtons = new ArrayList<JButton>();
	private static ArrayList<JButton> customBottomButtons = new ArrayList<JButton>();
	private static ArrayList<JButton> thirdRow = new ArrayList<JButton>();
	private JLabel title;

	private static boolean isEven;

	public Games()
	{
		super(new BorderLayout());

		addGame(Testing.class, 800, 600);
		addGame(Tetris.class, 100, 220);

		setBackground(Vars.playerColor);

		JPanel topRow = new JPanel();
		topRow.setBackground(Vars.playerColor);
		topRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
		JPanel bottomRow = new JPanel();
		bottomRow.setBackground(Vars.playerColor);
		bottomRow.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));

		for (int i = 0; i < Importer.modContainers.size(); i++)
		{
			Importer.modContainers.get(i).initiateProxyMethod("onGamesMenuStartup", this);
		}

		spaceInvaders = new JButton("Space Invaders");
		spaceInvaders.addActionListener(this);
		spaceInvaders.setMnemonic(KeyEvent.VK_I);
		spaceInvaders.setActionCommand("spaceInvaders");
		spaceInvaders.setToolTipText("2D Alien Shooter");
		spaceInvaders.setVisible(true);

		puzzle = new JButton("Puzzle");
		puzzle.addActionListener(this);
		puzzle.setMnemonic(KeyEvent.VK_P);
		puzzle.setActionCommand("puzzle");
		puzzle.setToolTipText("2D Picture Slide Puzzle");
		puzzle.setVisible(true);

		snake = new JButton("Snake");
		snake.addActionListener(this);
		snake.setMnemonic(KeyEvent.VK_S);
		snake.setActionCommand("snake");
		snake.setToolTipText("2D Collection/Point Rack up");
		snake.setVisible(true);

		breakOut = new JButton("Break Out");
		breakOut.addActionListener(this);
		breakOut.setMnemonic(KeyEvent.VK_B);
		breakOut.setActionCommand("breakOut");
		breakOut.setToolTipText("2D Wall Remover");
		breakOut.setVisible(true);

		tanks = new JButton("Tanks");
		tanks.addActionListener(this);
		tanks.setMnemonic(KeyEvent.VK_T);
		tanks.setActionCommand("tanks");
		tanks.setToolTipText("2D Bird's Eye Shooter");
		tanks.setVisible(true);

		popousPelican = new JButton("Popous Pelican");
		popousPelican.addActionListener(this);
		popousPelican.setMnemonic(KeyEvent.VK_P);
		popousPelican.setActionCommand("popousPelican");
		popousPelican.setToolTipText("2D Coordination Shifter");
		popousPelican.setVisible(true);

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
		topRow.add(tanks);
		topRow.add(breakOut);
		topRow.add(snake);
		bottomRow.add(puzzle, BorderLayout.NORTH);
		bottomRow.add(spaceInvaders, BorderLayout.NORTH);
		bottomRow.add(popousPelican, BorderLayout.NORTH);

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
		if ("spaceInvaders".equals(e.getActionCommand()))
		{
			System.out.println("Launching Space Invaders");
			Vars.frames.add(new LaunchSI());
		}
		if ("puzzle".equals(e.getActionCommand()))
		{
			System.out.println("Launching Puzzle");
			Vars.frames.add(new PuzzleP());
		}
		if ("snake".equals(e.getActionCommand()))
		{
			System.out.println("Launching Snake");
			Vars.frames.add(new SnakeSN());
		}
		if ("popousPelican".equals(e.getActionCommand()))
		{
			System.out.println("Launching Popous Pelican");
			Vars.frames.add(new PopousPelicanPP());
		}
		if ("breakOut".equals(e.getActionCommand()))
		{
			System.out.println("Launching Break Out");
			Vars.frames.add(new BreakOutBO());
		}
		if ("tanks".equals(e.getActionCommand()))
		{
			System.out.println("Launching Tanks");
			Vars.frames.add(new TanksTN());
		}
		if ("options".equals(e.getActionCommand()))
		{
			System.out.println("Launching Options");

			Options options = new Options();
			options.initGui();
		}
	}

	public void addGame(final Class<? extends GamePanel> clazz, final int width, final int height)
	{
		for (Class<? extends GamePanel> game : games)
		{
			if (game.equals(clazz))
			{
				System.err.println("Game already added: " + clazz.getSimpleName() + ", Skipping...");
				return;
			}
		}

		isEven = !isEven;
		games.add(clazz);
		JButton button = new JButton(clazz.getSimpleName());
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Launching " + clazz.getSimpleName());
				try
				{
					Vars.frames.add(new GameFrame(clazz.newInstance(), width, height));
				}
				catch (InstantiationException | IllegalAccessException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		if (customTopButtons.size() > 10 && customBottomButtons.size() > 10)
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
	}

	private static final ArrayList<Class<? extends GamePanel>> games = new ArrayList<Class<? extends GamePanel>>();
}
