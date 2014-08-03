package testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import main.Vars;
import main.games.GamePanel;
import testing.SkillTS.SkillsTS;

public class Testing extends GamePanel implements KeyListener
{
	private class AC implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String c = e.getActionCommand();

			if ("1".equals(c))
			{
				if (player.getInv()[0] != null && player.getInv()[0].activatedItem(player, player.getInv()[0].itemID))
				{
					player.getInv()[0] = null;
				}
			}

			if ("2".equals(c))
			{
				if (player.getInv()[1] != null && player.getInv()[1].activatedItem(player, player.getInv()[1].itemID))
				{
					player.getInv()[1] = null;
				}
			}

			if ("3".equals(c))
			{
				if (player.getInv()[2] != null && player.getInv()[2].activatedItem(player, player.getInv()[2].itemID))
				{
					player.getInv()[2] = null;
				}
			}

			if ("4".equals(c))
			{
				if (player.getInv()[3] != null && player.getInv()[3].activatedItem(player, player.getInv()[3].itemID))
				{
					player.getInv()[3] = null;
				}
			}

			if ("5".equals(c))
			{
				if (player.getInv()[4] != null && player.getInv()[4].activatedItem(player, player.getInv()[4].itemID))
				{
					player.getInv()[4] = null;
				}
			}

			if ("6".equals(c))
			{
				if (player.getInv()[5] != null && player.getInv()[5].activatedItem(player, player.getInv()[5].itemID))
				{
					player.getInv()[5] = null;
				}
			}

			if ("7".equals(c))
			{
				if (player.getInv()[6] != null && player.getInv()[6].activatedItem(player, player.getInv()[6].itemID))
				{
					player.getInv()[6] = null;
				}
			}

			if ("8".equals(c))
			{
				if (player.getInv()[7] != null && player.getInv()[7].activatedItem(player, player.getInv()[7].itemID))
				{
					player.getInv()[7] = null;
				}
			}
		}
	}

	private static final long serialVersionUID = 1L;
	private boolean inGame = true;
	public static ArrayList<SpriteTS> sprites;
	private PlayerTS player;
	private ShopTS[] shops;
	private EnemyTS[] enemies;
	private ObstacleTS[] obstacles;
	private boolean[] enemyRespawn;
	private boolean[] shopOpened;
	private int[] respawnDelay;
	public static boolean activated;
	private int openedShopID;
	public static JTextArea chat;
	private JScrollPane pane;
	private boolean attackCollidedSprite;
	public static JTextField chatBox;
	private JButton[] slots;

	public Testing()
	{
		super(10, 800, 600);
	}

	private void checkCollisions()
	{
		for (int i = 0; i < sprites.size(); i++)
		{
			for (int j = 0; j < sprites.size(); j++)
			{
				SpriteTS a = sprites.get(i);
				SpriteTS b = sprites.get(j);

				if (!a.isDestroyed() && !b.isDestroyed() && i != j && a.getBounds().intersects(b.getBounds()))
				{
					if (b instanceof PlayerTS)
					{
						if (a instanceof CrateTS)
						{
							ObstacleTS.cannotCollide(b, a);
						}
						for (int k = 0; k < shops.length; k++)
						{
							if (a == shops[k])
							{
								activated = true;
								shopOpened[k] = true;
								openedShopID = shops[k].getShopID();
							}
						}
					}
					if (a instanceof LivingSpriteTS && b instanceof LivingSpriteTS)
					{
						if (a instanceof PlayerTS || b instanceof PlayerTS)
						{
							if (((LivingSpriteTS) a).getAttacker() == null && ((LivingSpriteTS) b).getAttacker() == null || ((LivingSpriteTS) a).getAttacker() == (LivingSpriteTS) b || ((LivingSpriteTS) b).getAttacker() == (LivingSpriteTS) a)
							{
								if (attackCollidedSprite)
								{
									((LivingSpriteTS) a).isAttacked((LivingSpriteTS) b, 1);
									((LivingSpriteTS) b).isAttacked((LivingSpriteTS) a, 2);
								}
								else if (!((LivingSpriteTS) b).isAggressive())
								{
									((LivingSpriteTS) b).setAttacked(false);
									((LivingSpriteTS) b).setAttacker(null);
									((LivingSpriteTS) b).setIsFirst(0);

									player.setAttacked(false);
									player.setAttacker(null);
									player.setIsFirst(0);
								}
							}
						}

						if (((LivingSpriteTS) a).isAggressive())
						{
							if (((LivingSpriteTS) a).getAttacker() == null && ((LivingSpriteTS) b).getAttacker() == null || ((LivingSpriteTS) a).getAttacker() == (LivingSpriteTS) b || ((LivingSpriteTS) b).getAttacker() == (LivingSpriteTS) a)
							{
								((LivingSpriteTS) a).isAttacked((LivingSpriteTS) b, 1);
								((LivingSpriteTS) b).isAttacked((LivingSpriteTS) a, 2);
							}
						}
					}
					if (b instanceof ObstacleTS || b instanceof ShopTS)
					{
						ObstacleTS.cannotCollide(b, a);
					}
					if (a instanceof ObstacleTS || a instanceof ShopTS)
					{
						ObstacleTS.cannotCollide(a, b);
					}
				}
			}
		}
	}

	public void initSprites()
	{
		for (int i = 0; i < obstacles.length; i++)
		{
			sprites.add(obstacles[i]);
		}
		for (int i = 0; i < shops.length; i++)
		{
			sprites.add(shops[i]);
		}
		for (int i = 0; i < enemies.length; i++)
		{
			sprites.add(enemies[i]);
		}
		sprites.add(new CrateTS());
		sprites.add(player);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (chatBox.isFocusOwner())
		{
			if (key == KeyEvent.VK_ENTER)
			{
				chat.append(Vars.playerName + ": " + chatBox.getText() + "\n");
				player.processCommand(chatBox.getText());
				chatBox.setText("");
			}
			else if (key == KeyEvent.VK_TAB)
			{
				chatBox.setText("-Press Tab to Type-");
				requestFocus();
			}
		}
		else
		{
			if (key == KeyEvent.VK_ENTER)
			{
				requestFocus();
			}
			if (key == KeyEvent.VK_TAB)
			{
				chatBox.requestFocus();
			}

			if (key == KeyEvent.VK_SPACE)
			{
				attackCollidedSprite = !attackCollidedSprite;
				chat.append("You are " + (attackCollidedSprite ? "" : "NOT ") + "Attacking the enemy on contact" + '\n');
			}

			if (activated)
			{
				if (key == KeyEvent.VK_Q)
				{
					activated = false;
					openedShopID = -1;
				}
				ShopTS.processKeyEvent(key, openedShopID);
			}
			else
			{
				player.keyPressed(e);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		player.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		keyPressed(e);
	}

	@Override
	public void init()
	{
		addKeyListener(this);
		chat = new JTextArea("Welcome to * \n", 4, 30);
		chat.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		chat.setEditable(false);

		chatBox = new JTextField("-Press Tab Twice to Type-", 30);
		chatBox.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		chatBox.addKeyListener(this);

		slots = new JButton[8];
		pane = new JScrollPane(chat, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		for (int i = 0; i < slots.length; i++)
		{
			slots[i] = new JButton("Slot " + (i + 1));
			slots[i].setActionCommand("" + (i + 1));
			slots[i].addActionListener(new AC());

			slots[i].addKeyListener(this);
			add(slots[i]);
		}
		add(pane);
		add(chatBox);

		sprites = new ArrayList<SpriteTS>();
		player = new PlayerTS();

		enemies = new EnemyTS[] {new EnemyTS(100, 100, 3), new EnemyTS(200, 100, 3), new EnemyTS(300, 100, 3), new EnemyTS(410, 100, 3), new EnemyTS(100, 200, 3), new EnemyTS(100, 300, 3), new EnemyTS(100, 410, 3)};
		shops = new ShopTS[] {new ShopTS(600, 600)};
		obstacles = new ObstacleTS[] {new ObstacleTS(100, 100), new ObstacleTS(404, 201)};
		shopOpened = new boolean[shops.length];

		enemyRespawn = new boolean[enemies.length];
		respawnDelay = new int[enemies.length];

		for (int i = 0; i < enemies.length; i++)
		{
			enemyRespawn[i] = false;
			respawnDelay[i] = 0;
		}
		for (int i = 0; i < shops.length; i++)
		{
			shopOpened[i] = false;
		}

		initSprites();
	}

	@Override
	public void updateGame(int ticks)
	{
		for (int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i) != null && sprites.get(i).isDestroyed() && sprites.get(i) instanceof LivingSpriteTS)
			{
				if (((LivingSpriteTS) sprites.get(i)).getCanSpawn())
				{
					((LivingSpriteTS) sprites.get(i)).addSpawnDelay(1);

					if (((LivingSpriteTS) sprites.get(i)).getSpawnDelay() >= 500)
					{
						SpriteTS spawn = ((LivingSpriteTS) sprites.get(i)).newClone();
						sprites.add(spawn);
					}
				}
			}
		}

		pane.setLocation(11, 486);
		chatBox.setLocation(11, 552);

		for (int i = 0; i < slots.length; i++)
		{
			int xTracker = 1;
			int yTracker = 1;

			if (i == 1)
			{
				xTracker = 125;
			}
			if (i == 2)
			{
				xTracker = 125 * 2;
			}
			if (i == 3)
			{
				yTracker = 55;
			}
			if (i == 4)
			{
				yTracker = 55;
				xTracker = 125;
			}
			if (i == 5)
			{
				yTracker = 55;
				xTracker = 125 * 2;
			}
			if (i == 6)
			{
				yTracker = 110;
			}
			if (i == 7)
			{
				yTracker = 110;
				xTracker = 125;
			}
			slots[i].setLocation(430 + xTracker, 430 + yTracker);

			if (player.getInv()[i] != null)
			{
				slots[i].setText(player.getInv()[i].itemName);
			}
			else
			{
				slots[i].setText("Slot " + (i + 1));
			}
		}

		checkCollisions();
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		if (inGame)
		{
			Image image = new ImageIcon(this.getClass().getResource("/Images/Testing/backgrond1.png")).getImage();
			boolean isTraning = false;

			g2d.setColor(Color.GREEN);
			g2d.drawLine(0, 410, 800, 410);

			if (player.getActivatedSkill() == SkillsTS.ATTACK.getSkillID() - 1)
			{
				isTraning = true;
			}

			g2d.setColor(isTraning ? Color.GREEN : Color.RED);
			g2d.drawString("Attack Level: " + player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getLevel(), 10, 450);
			g2d.drawString("Attack XP: " + player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getXP(), 10, 465);
			g2d.drawString("Attack XP Till Next Level: " + SkillTS.getXPForLevel(player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getLevel() + 1), 10, 480);

			g2d.setColor(isTraning ? Color.RED : Color.GREEN);
			g2d.drawString("Defence Level: " + player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getLevel(), 230, 450);
			g2d.drawString("Defence XP: " + player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getXP(), 230, 465);
			g2d.drawString("Defence XP Till Next Level: " + SkillTS.getXPForLevel(player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getLevel() + 1), 230, 480);

			g2d.setColor(attackCollidedSprite ? Color.RED : Color.GREEN);
			g2d.drawString("You are " + (attackCollidedSprite ? "" : "NOT ") + "Attacking on Contact", 160, 435);

			g2d.setColor(Color.BLACK);
			g2d.drawString("You are " + (attackCollidedSprite ? "" : "NOT ") + "Attacking on Contact", 161, 436);
			g2d.drawString("Attack Level: " + player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getLevel(), 11, 451);
			g2d.drawString("Attack XP Till Next Level: " + SkillTS.getXPForLevel(player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getLevel() + 1), 11, 481);
			g2d.drawString("Defence Level: " + player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getLevel(), 231, 451);
			g2d.drawString("Defence XP Till Next Level: " + SkillTS.getXPForLevel(player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getLevel() + 1), 231, 481);
			g2d.drawString("Defence XP: " + player.getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getXP(), 231, 466);
			g2d.drawString("Attack XP: " + player.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getXP(), 11, 466);
			g2d.setColor(Color.GREEN);

			if (!activated)
			{
				openedShopID = -1;

				g2d.drawImage(image, 0, 0, this);
				for (int i = 0; i < sprites.size(); i++)
				{
					if (!(sprites.get(i) instanceof PlayerTS) && !sprites.get(i).isDestroyed())
					{
						sprites.get(i).drawSprite(g2d, this);
						sprites.get(i).run();
						sprites.get(i).init(getParent().getWidth(), 410);
					}
				}
				player.drawSprite(g2d, this);
				player.run();
				player.init(getParent().getWidth(), 410);
			}
			else if (activated)
			{
				g2d.setColor(Color.BLACK);
				g2d.drawRect(20, 20, 750, 380);

				for (int i = 0; i < shops.length; i++)
				{
					g2d.setColor(Color.BLUE);
					g2d.drawRect(45 + 70 * i, 60, 40, 40);

					if (shopOpened[i])
					{
						activated = shopOpened[i] = ShopTS.displayShop(g2d, i);
					}
				}
			}
		}
		else
		{
			String msg = "You Lost!";
			Font small = new Font("Helvetica", Font.ITALIC, 24);
			FontMetrics metr = getFontMetrics(small);

			g2d.setColor(Color.BLUE);
			g2d.setFont(small);
			g2d.drawString(msg, (getWidth() - metr.stringWidth(msg)) / 2, getHeight() / 2);
		}
	}

	@Override
	public String getGameName()
	{
		return "*";
	}
}