package spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Vars;

public class StartSI extends JPanel implements ActionListener
{
	/**
	 * Private Class that is used for the Key Adapter
	 */
	private class TAdapter extends KeyAdapter
	{
		/**
		 * Java Default
		 */
		@Override
		public void keyPressed(KeyEvent e)
		{
			craft.keyPressed(e);
		}

		/**
		 * Java Default
		 */
		@Override
		public void keyReleased(KeyEvent e)
		{
			craft.keyReleased(e);
		}
	}

	/**
	 * Default by Java
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Alien Boss that will Spawn
	 */
	private BossAlienSI alienBoss;

	/**
	 * The array of aliens on the Board at the Moment, Decreases with each Alien
	 * killed.
	 */
	private ArrayList<AlienSI> aliens;

	/**
	 * Board Height
	 */
	private int B_HEIGHT;

	/**
	 * Board Width
	 */
	private int B_WIDTH;

	/**
	 * Is the Boss defeated. If so, this will be true Else this will be false
	 */
	private boolean bossDefeated;

	/**
	 * How many missile hits it will take to kill the Boss
	 */
	private int bossHealth = 10;

	/**
	 * The Player that will Spawn
	 */
	private PlayerSI craft;

	/**
	 * The Chance that the Fast Alien will spawn
	 */
	private int fastAlienSpawnChance;

	/**
	 * Has the Game been completed yet (Not legitimately used yet, just keeps
	 * looping)
	 */
	private boolean gameWon = false;

	/**
	 * Has the boss spawned in yet
	 */
	private boolean hasBoss = false;

	/**
	 * The Player's health
	 */
	private int health = 10;

	/**
	 * Is the player playing the Game. Has the game Ended?
	 */
	private boolean ingame;

	/**
	 * The Pickup that will Spawn
	 */
	private PickupSI pickup;

	/**
	 * The chance that the pickup will spawn
	 */
	private int pickUpRandomnessChance = 0;

	/**
	 * will be true if the pickup has been spawned and not taken yet. If it has
	 * been spawned and taken will be false again
	 */
	private boolean pickUpSpawned = false;

	/**
	 * Positions for the Aliens, the Aliens will spawn at these Positions.
	 */
	private int[][] pos = { {2380, 60}, {2500, 120}, {1380, 180}, {780, 220}, {580, 280}, {680, 480}, {790, 520}, {760, 100}, {790, 300}, {980, 420}, {560, 90}, {510, 140}, {930, 320}, {590, 90}, {530, 120}, {940, 120}, {990, 60}, {920, 400}, {900, 420}, {660, 100}, {540, 180}, {810, 440}, {860, 40}, {740, 360}, {820, 260}, {490, 340}, {700, 60}, {810, 250}, {610, 310}, {710, 510}, {820, 450}, {790, 130}, {820, 330}, {990, 450}, {590, 120}, {540, 170}, {960, 350}, {620, 190}, {560, 150}, {940 + 30, 59 * 2 + 30}, {990 + 30, 30 * 2 + 30}, {920 + 30, 200 * 2 + 30}, {900 + 30, 259 * 2 + 30}, {660 + 30, 50 * 2 + 30}, {540 + 30, 90 * 2 + 30}, {810 + 30, 220 * 2 + 30}, {860 + 30, 20 * 2 + 30}, {740 + 30, 180 * 2 + 30}, {820 + 30, 128 * 2 + 30}, {490 + 30, 170 * 2 + 30}, {700 + 30, 30 * 2 + 30}, {780 - 30, 109 * 2 - 30}, {580 - 30, 139 * 2 - 30}, {680 - 30, 239 * 2 - 30}, {790 - 30, 259 * 2 - 30}, {760 - 30, 50 * 2 - 30}, {790 - 30, 150 * 2 - 30}, {980 - 30, 209 * 2 - 30}, {560 - 30, 45 * 2 - 30}, {510 - 30, 70 * 2 - 30}, {930 - 30, 159 * 2 - 30}, {590 - 30, 80 * 2 - 30}, {530 - 30, 60 * 2 - 30}, {940 - 30, 59 * 2 - 30}, {990 - 30, 30 * 2 - 30}, {920 - 30, 200 * 2 - 30}, {900 - 30, 259 * 2 - 30}, {660 - 30, 50 * 2 - 30}, {540 - 30, 90 * 2 - 30}, {810 - 30, 220 * 2 - 30}, {860 - 30, 20 * 2 - 30}, {740 - 30, 180 * 2 - 30}, {820 - 30, 128 * 2 - 30}, {490 - 30, 170 * 2 - 30}, {700 - 30, 30 * 2 - 30}};

	/**
	 * The round of Aliens the Player is on
	 */
	private int round = 1;

	/**
	 * How many aliens have been killed by the Player's Missiles
	 */
	private int score = 0;

	/**
	 * The Timer that is run every 10 milliseconds to create a loop for the Game
	 */
	private Timer timer;

	private boolean scoredHigher = false;

	/**
	 * StartSI Constructor. Here, most null variables are defined and
	 * initialized
	 */
	public StartSI()
	{

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Vars.playerColor.darker().darker());
		setDoubleBuffered(true);
		ingame = true;

		setSize(800, 600);

		craft = new PlayerSI();

		alienBoss = new BossAlienSI(680, 400);

		pickup = new PickupSI("Health", (int) (Math.random() * 500), (int) (Math.random() * 500));

		initAliens();

		timer = new Timer(10, this);
		timer.start();
	}

	/**
	 * Implementation Java Default
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// fastAlienSpawnChance++;

		if (fastAlienSpawnChance == 1000)
		{
			aliens.add(new AlienSI(650, (int) (Math.random() * 500), 2));
			if (round > 10)
			{
				aliens.add(new AlienSI(650, (int) (Math.random() * 500), 2));
			}
			if (round > 10)
			{
				aliens.add(new AlienSI(650, (int) (Math.random() * 500), 1));
			}

			fastAlienSpawnChance = 0;
		}

		pickUpRandomnessChance++;

		if (pickUpRandomnessChance == 1000 && !pickUpSpawned)
		{
			pickUpSpawned = true;
			pickup.setVisible(true);
		}

		if ((aliens.size() == 0 || hasBoss) && !bossDefeated)
		{
			hasBoss = true;

			alienBoss.setVisible(true);

			alienBoss.move();
		}

		if (aliens.size() == 0 && bossDefeated)
		{
			round++;

			if (round == Vars.siMaxRound)
			{
				System.out.println("New SI Highscore Set!");
				scoredHigher = true;
			}

			if (scoredHigher)
			{
				Vars.siMaxRound = round;
			}

			initAliens();
		}

		ArrayList<?> ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++)
		{
			MissleSI m = (MissleSI) ms.get(i);

			if (m.isVisible())
			{
				m.move();
			}
			else
			{
				ms.remove(i);
			}
		}

		ArrayList<?> abms = alienBoss.getMissiles();

		for (int i = 0; i < abms.size(); i++)
		{
			MissleSI m = (MissleSI) abms.get(i);

			if (m.isVisible())
			{
				m.move();
			}
			else
			{
				abms.remove(i);
			}
		}

		for (int i = 0; i < aliens.size(); i++)
		{
			AlienSI a = aliens.get(i);

			if (a.isVisible())
			{
				a.move();
			}
			else
			{
				aliens.remove(i);
			}
		}

		craft.move();
		checkCollisions();
		repaint();
	}

	/**
	 * Java Default
	 */
	@Override
	public void addNotify()
	{
		super.addNotify();
		B_WIDTH = getWidth();
		B_HEIGHT = getHeight();
	}

	/**
	 * Checks when any of the Game's Elements collide, and If they have an
	 * effect, they occur here
	 */
	public void checkCollisions()
	{
		Rectangle r3 = craft.getBounds();
		Rectangle rB = alienBoss.getBounds();
		Rectangle rP = pickup.getBounds();

		ArrayList<?> abms = alienBoss.getMissiles();

		if (rP.intersects(r3))
		{
			if (health < 10 && pickUpSpawned)
			{
				pickUpRandomnessChance = 0;
				pickUpSpawned = false;
				pickup.setVisible(false);
				if (health == 9)
				{
					health++;
				}
				if (health < 9)
				{
					health += 2;
				}
			}
		}

		for (int i = 0; i < abms.size(); i++)
		{
			MissleSI m = (MissleSI) abms.get(i);
			Rectangle r1 = m.getBounds();

			if (r1.intersects(r3))
			{
				if (health > 1)
				{
					health--;
					m.setVisible(false);
				}
				else
				{
					craft.setVisible(false);
					m.setVisible(false);
					ingame = false;
				}
			}
		}

		for (int j = 0; j < aliens.size(); j++)
		{
			AlienSI a = aliens.get(j);
			Rectangle r2 = a.getBounds();

			if (r3.intersects(r2))
			{
				if (health > 1)
				{
					if (a.getAlienState() != 0)
					{
						health--;
						if (a.getAlienState() == 2)
						{
							health -= 3;
						}
					}

					health--;
					a.setVisible(false);
				}
				else
				{
					craft.setVisible(false);
					a.setVisible(false);
					ingame = false;
				}
			}
		}

		ArrayList<?> ms = craft.getMissiles();

		for (int i = 0; i < ms.size(); i++)
		{
			MissleSI m = (MissleSI) ms.get(i);

			Rectangle r1 = m.getBounds();

			if (rB.intersects(r1))
			{
				if (bossHealth > 1)
				{
					bossHealth--;
					m.setVisible(false);
				}
				else
				{
					score += 5;
					bossDefeated = true;
					alienBoss.setVisible(false);
					m.setVisible(false);
				}
			}

			for (int j = 0; j < aliens.size(); j++)
			{
				AlienSI a = aliens.get(j);
				Rectangle r2 = a.getBounds();

				if (r1.intersects(r2))
				{
					m.setVisible(false);
					a.setVisible(false);
					score++;
				}
			}
		}
	}

	/**
	 * Initialization Event for all the Aliens. Recalled every round to create a
	 * never ending loop.
	 */
	public void initAliens()
	{
		aliens = new ArrayList<AlienSI>();

		for (int i = 0; i < pos.length; i++)
		{
			aliens.add(new AlienSI(pos[i][0], pos[i][1]));
		}
	}

	/**
	 * Java Default
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);

		if (ingame)
		{
			if (health <= 0)
			{
				craft.setVisible(false);
				ingame = false;
			}

			Graphics2D g2d = (Graphics2D) g;

			if (craft.isVisible())
			{
				g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);
			}

			if (hasBoss && !bossDefeated)
			{
				g2d.drawImage(alienBoss.getImage(), alienBoss.getX(), alienBoss.getY(), this);
			}

			if (pickUpSpawned)
			{
				g2d.drawImage(pickup.getImage(), pickup.getX(), pickup.getY(), this);
			}

			ArrayList<?> ms = craft.getMissiles();

			for (int i = 0; i < ms.size(); i++)
			{
				MissleSI m = (MissleSI) ms.get(i);

				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			ArrayList<?> abms = alienBoss.getMissiles();

			for (int i = 0; i < abms.size(); i++)
			{
				MissleSI m = (MissleSI) abms.get(i);

				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}

			for (int i = 0; i < aliens.size(); i++)
			{
				AlienSI a = aliens.get(i);

				if (a.isVisible())
				{
					g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
				}
			}

			g2d.setColor(Color.BLACK);
			g2d.drawString("SPACE- Shoot a Missle", 25, 15);
			g2d.drawString("Arrow Keys- Move Around", 200, 15);
			g2d.drawString("Aliens left: " + aliens.size(), 25, 30);
			g2d.drawString("Pickup Chance: " + pickUpRandomnessChance, 325, 30);
			g2d.drawString("Fast Alien Chance: " + fastAlienSpawnChance, 325, 45);

			g2d.setColor(Color.BLUE);
			g2d.drawString("Round:" + round, 25, 45);

			if (health >= 5)
			{
				g2d.setColor(Color.BLUE);
			}
			if (health < 5)
			{
				g2d.setColor(Color.RED);
			}
			g2d.drawString("Health:" + health, craft.getX() - 15, craft.getY() + 45);

			g2d.setColor(Vars.playerColor);
			g2d.drawString(Vars.playerName, craft.getX() - 7, craft.getY() - 10);

			if (bossHealth >= 5)
			{
				g2d.setColor(Color.GREEN);
			}
			if (bossHealth < 5)
			{
				g2d.setColor(Color.RED);
			}
			if (hasBoss && !bossDefeated)
			{
				g2d.drawString("Boss Health:" + bossHealth, 350, 545);
			}

			g2d.setColor(Color.YELLOW);
			if (hasBoss && bossDefeated)
			{
				g2d.drawString("Free Play, try to survive!", 350, 545);
			}
		}
		else
		{
			if (!gameWon)
			{
				String msg = "Game Over, You have been Caught! You're Score was " + score + "!";
				Font small = new Font("Helvetica", Font.BOLD, 24);
				FontMetrics metr = getFontMetrics(small);

				if (scoredHigher)
				{
					Vars.siMaxRound = round;
					scoredHigher = false;
				}

				g.setColor(Color.white);
				g.setFont(small);
				g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
				g.drawString("Your Round Highscore is " + Vars.siMaxRound, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2 + 30);
			}
			if (gameWon)
			{
				String msg = "You Won! You're Score was " + score + "!";
				Font small = new Font("Helvetica", Font.ITALIC, 24);
				FontMetrics metr = getFontMetrics(small);

				g.setColor(Color.white);
				g.setFont(small);
				g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
			}
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}