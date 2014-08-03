package tanks;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.Vars;
import main.utils.Rand;
import modhandler.EnumGame;
import modhandler.Importer;

public class StartTN extends JPanel implements ActionListener
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
			player.keyPressed(e);
		}

		/**
		 * Java Default
		 */
		@Override
		public void keyReleased(KeyEvent e)
		{
			player.keyReleased(e);
		}
	}

	private static final long serialVersionUID = 1L;
	public int HEIGHT, WIDTH;
	public static boolean inGame = false;
	private PlayerTN player;
	private int specialWeapon = 0;

	private ArrayList<EnemyTN> enemies;

	private Timer timer;
	private int score;
	private boolean scoredHigher = false;
	private boolean enemiesSpawned = false;

	public StartTN()
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Vars.playerColor.brighter().brighter());
		setDoubleBuffered(true);
		StartTN.inGame = true;
		setSize(400, 600);

		player = new PlayerTN(200, 200);
		enemies = new ArrayList<EnemyTN>();

		Importer.throwGameEvent(EnumGame.Tanks);

		timer = new Timer(25, this);
		timer.start();
	}

	/**
	 * Implementation Java Default
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.gc();

		if (!StartTN.inGame)
		{
			timer.stop();
		}

		specialWeapon += 4;

		if (specialWeapon >= 1000)
		{
			if (player.getSpecialStored() < 5)
			{
				player.addSpecialStored(1);
			}

			specialWeapon = 0;
		}

		if (enemies.size() == 0 && !enemiesSpawned)
		{
			enemies.add(new EnemyTN(0, Rand.nextInt(360), EnumFace.E, 1, player));
			enemiesSpawned = true;
		}

		if (enemies.size() == 10)
		{
			enemiesSpawned = false;
		}

		if (enemies.size() < 10 && enemiesSpawned)
		{
			if (Rand.nextInt(300) == 0)
			{
				enemies.add(new EnemyTN(0, Rand.nextInt(360), EnumFace.E, 1, player));
			}
			else if (Rand.nextInt(300) == 1)
			{
				enemies.add(new EnemyTN(400, Rand.nextInt(360), EnumFace.W, 1, player));
			}
			else if (Rand.nextInt(300) == 2)
			{
				enemies.add(new EnemyTN(Rand.nextInt(360), 0, EnumFace.S, 1, player));
			}
			else if (Rand.nextInt(300) == 3)
			{
				enemies.add(new EnemyTN(Rand.nextInt(360), 400, EnumFace.N, 1, player));
			}
			else if (Rand.nextInt(300) == 4)
			{
				enemies.add(new EnemyTN(0, 360, EnumFace.NE, 1, player));
			}
			else if (Rand.nextInt(300) == 5)
			{
				enemies.add(new EnemyTN(360, 360, EnumFace.NW, 1, player));
			}
			else if (Rand.nextInt(300) == 6)
			{
				enemies.add(new EnemyTN(0, 0, EnumFace.SE, 1, player));
			}
			else if (Rand.nextInt(300) == 7)
			{
				enemies.add(new EnemyTN(360, 360, EnumFace.SW, 1, player));
			}
		}

		ArrayList<?> ms = player.getMissiles();

		for (int i = 0; i < ms.size(); i++)
		{
			SpriteTN m = (SpriteTN) ms.get(i);

			if (!m.isDestroyed())
			{
				m.spriteInit();
			}
			else
			{
				player.getMissiles().remove(i);
			}
		}

		for (int q = 0; q < enemies.size(); q++)
		{
			EnemyTN en = enemies.get(q);

			ArrayList<MissileTN> msd = en.getMissiles();

			if (!en.isDestroyed())
			{
				en.spriteInit();
			}
			else
			{
				enemies.remove(q);
			}

			for (int j = 0; j < msd.size(); j++)
			{
				MissileTN m = msd.get(j);

				if (!m.isDestroyed())
				{
					m.spriteInit();
				}
				else
				{
					msd.remove(j);
				}
			}
		}

		player.spriteInit();
		repaint();
		checkCollisions();
	}

	/**
	 * Java Default
	 */
	@Override
	public void addNotify()
	{
		super.addNotify();
		WIDTH = getWidth();
		HEIGHT = getHeight();
	}

	/**
	 * Checks when any of the Game's Elements collide, and If they have an
	 * effect, they occur here
	 */
	public void checkCollisions()
	{
		for (int i = 0; i < enemies.size(); i++)
		{
			if (!enemies.get(i).isDestroyed())
			{
				if (enemies.get(i).getBounds().intersects(player.getBounds()))
				{
					if (score == Vars.tnMaxScore)
					{
						System.out.println("Scored Higher!");
						scoredHigher = true;
					}

					if (scoredHigher)
					{
						Vars.tnMaxScore = score;
					}

					score++;
					enemies.get(i).addHealth(-1);
				}

				for (int d = 0; d < player.getMissiles().size(); d++)
				{
					if (player.getMissiles().get(d).getBounds().intersects(enemies.get(i).getBounds()) && !player.getMissiles().get(d).isDestroyed())
					{
						if (score == Vars.tnMaxScore)
						{
							System.out.println("Scored Higher!");
							scoredHigher = true;
						}

						if (scoredHigher)
						{
							Vars.tnMaxScore = score;
						}

						score++;
						if (player.getMissiles().get(d).getMissileState() != 5)
						{
							player.getMissiles().get(d).setDestroyed(true);
							enemies.get(i).addHealth(-1);
						}

						if (player.getMissiles().get(d).getMissileState() == 5)
						{
							player.getMissiles().get(d).setSplitUp(true);
						}
					}
				}

				for (int d = 0; d < enemies.get(i).getMissiles().size(); d++)
				{
					if (enemies.get(i).getMissiles().get(d).getBounds().intersects(player.getBounds()))
					{
						if (!enemies.get(i).getMissiles().get(d).isDestroyed())
						{
							enemies.get(i).getMissiles().get(d).setDestroyed(true);
							player.addHealth(-1);
						}
					}
				}
			}
		}
	}

	/**
	 * Java Default
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		if (StartTN.inGame)
		{
			ArrayList<?> ms = player.getMissiles();

			for (int i = 0; i < ms.size(); i++)
			{
				MissileTN m = (MissileTN) ms.get(i);

				if (!m.isDestroyed())
				{
					g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
				}
			}

			g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
			g2d.drawString("PLAYER- X: " + player.getX() + ", Y: " + player.getY(), 100, 415);
			g2d.drawString("Special Weapon Charge: " + specialWeapon, 100, 430);
			g2d.drawString("X: " + player.x1 + ", Y:" + player.y1, 100, 445);
			g2d.drawString("Enemies Left: " + enemies.size(), 100, 460);

			for (int q = 0; q < enemies.size(); q++)
			{
				EnemyTN e = enemies.get(q);

				ArrayList<MissileTN> msd = e.getMissiles();

				if (!e.isDestroyed())
				{
					g2d.drawImage(e.getImage(), e.getX(), e.getY(), this);

					if (e.getHealth() == 3)
					{
						g2d.setColor(Color.GREEN);
					}
					if (e.getHealth() == 2)
					{
						g2d.setColor(Color.ORANGE);
					}
					if (e.getHealth() <= 1)
					{
						g2d.setColor(Color.RED);
					}
					g2d.drawString("Health: " + e.getHealth(), e.getX(), e.getY() + 40);
					g2d.setColor(Color.BLACK);
					g2d.drawString("Health: " + e.getHealth(), e.getX() - 1, e.getY() + 39);
				}
				for (int j = 0; j < msd.size(); j++)
				{
					MissileTN m = msd.get(j);

					if (!m.isDestroyed())
					{
						g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
					}
				}
			}

			g2d.setColor(Vars.playerColor.darker().darker());
			g2d.drawString("(X)", player.x1, player.y1);
			g2d.drawLine(player.x1, player.y1, player.x, player.y);

			g2d.setColor(Color.BLACK);
			g2d.drawString(Vars.playerName, player.x, player.y - 5);
			g2d.drawString("Health: " + player.getHealth(), player.x - 10, player.y + 40);

			if (Vars.missileChosen == 1)
			{
				g2d.drawString("The Crasher", 220, 550);
			}
			if (Vars.missileChosen == 2)
			{
				g2d.drawString("The Chaser", 220, 550);
			}
			if (Vars.missileChosen == 3)
			{
				g2d.drawString("The Director", 220, 550);
			}
			if (Vars.missileChosen == 4)
			{
				g2d.drawString("The Spreader", 220, 550);
			}
			if (Vars.missileChosen == 5)
			{
				g2d.drawString("The Violater", 220, 550);
			}
			else if (Vars.missileChosen > 5 || Vars.missileChosen < 1) throw new IllegalArgumentException("Invalid missileChosen Number! Can Not be " + Vars.missileChosen + ", Has to be 1-5");

			g2d.drawString("Missile Chosen: " + Vars.missileChosen, 220, 565);

			g2d.setColor(Color.WHITE);
			g2d.draw3DRect(Vars.missileChosen * 50 + 20, 480, 60, 40, false);
			g2d.draw3DRect(Vars.missileChosen * 50 + 15, 475, 70, 50, true);

			g2d.setColor(Color.BLUE);

			for (int d = 0; d < player.getSpecialStored(); d++)
			{
				ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/Tanks/missile.png"));
				Image image = ii.getImage();

				g2d.drawImage(image, 10 + d * 20, 560, this);
			}

			for (int k = 0; k < 5; k++)
			{
				String[] paths = new String[] {"/Images/Tanks/ONE.png", "/Images/Tanks/TWO.png", "/Images/Tanks/THREE.png", "/Images/Tanks/FOUR.png", "/Images/Tanks/FIVE.png"};

				ImageIcon ii = new ImageIcon(this.getClass().getResource(paths[k]));
				Image image = ii.getImage();

				if (k == 0)
				{
					g2d.drawImage(image, 50 + 20, 480, 60, 40, this);
				}
				if (k == 1)
				{
					g2d.drawImage(image, 100 + 20, 480, 60, 40, this);
				}
				if (k == 2)
				{
					g2d.drawImage(image, 150 + 20, 480, 60, 40, this);
				}
				if (k == 3)
				{
					g2d.drawImage(image, 200 + 20, 480, 60, 40, this);
				}
				if (k == 4)
				{
					g2d.drawImage(image, 250 + 20, 480, 60, 40, this);
				}
				if (k == 5)
				{
					g2d.drawImage(image, 300 + 20, 480, 60, 40, this);
				}
			}

			g2d.drawString("Special Weapon Shots:", 10, 550);
			g2d.setColor(Color.RED);
			if (player.getSpecialStored() == 5)
			{
				g2d.drawString("Maximum Shots Stored!", 10, 535);
			}

			g2d.setColor(Color.BLUE);
			g2d.draw3DRect(0, 390, 400, 10, true);

			String msg = "Tanks!";
			Font small = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);

			g2d.setColor(Color.YELLOW);
			g2d.setFont(small);
			g2d.drawString(msg, 1, 425);
		}
		else
		{
			String msg = "Game Over! Your Score is " + score;
			Font small = new Font("Helvetica", Font.BOLD | Font.ITALIC, 24);

			g2d.setColor(Color.WHITE);
			g2d.setFont(small);
			g2d.drawString(msg, 50, 300);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
}