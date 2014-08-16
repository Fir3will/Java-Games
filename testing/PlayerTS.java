package testing;

import java.awt.event.KeyEvent;
import main.Save;

public class PlayerTS extends LivingSpriteTS
{
	private static final long serialVersionUID = 1L;
	private int homeDelay = 0;

	public PlayerTS()
	{
		super(400, 300, 20, 20, 50, false);
		toggleCanLevel();

		setSpriteName(Save.PLAYER_NAME);
		addXPToSkill(0, Save.TS_ATTACK_XP);
		addXPToSkill(1, Save.TS_DEFENCE_XP);
		setHealth(Save.TS_HEALTH);
		setCanPass(true);

		for (int i = 0; i < Save.TS_ITEMS.length; i++)
		{
			addItemToInventory(Save.TS_ITEMS[i]);
		}
	}

	public int getCoins()
	{
		return Save.TS_COINS;
	}

	public SkillTS getLevel(int skillID)
	{
		return getSkill(skillID);
	}

	@Override
	public void init()
	{
		Save.TS_ATTACK_XP = getXPFromSkill(0);
		Save.TS_DEFENCE_XP = getXPFromSkill(1);
		Save.TS_X = getX();
		Save.TS_Y = getY();
		Save.TS_HEALTH = getHealth();
		Save.TS_ITEMS = getInv();

		homeDelay++;
		x += xV;
		y += yV;
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_1)
		{
			RPG.chat.append("You are now traning " + getSkill(0) + '\n');
			setActivatedSkill(0);
		}

		if (key == KeyEvent.VK_2)
		{
			RPG.chat.append("You are now traning " + getSkill(1) + '\n');
			setActivatedSkill(1);
		}

		if (key == KeyEvent.VK_W)
		{
			yV = -1;
		}

		if (key == KeyEvent.VK_S)
		{
			yV = 1;
		}

		if (key == KeyEvent.VK_A)
		{
			xV = -1;
		}

		if (key == KeyEvent.VK_D)
		{
			xV = 1;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
		{
			yV = 0;
		}

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D)
		{
			xV = 0;
		}
	}

	@Override
	public LivingSpriteTS newClone()
	{
		return new PlayerTS();
	}

	public void processCommand(String line)
	{
		System.out.println("Processing Command: \"" + line + "\"");

		if (line.equals("::home"))
		{
			if (homeDelay >= 1000)
			{
				homeDelay = 0;
				x = 400;
				y = 300;
			}
			else
			{
				RPG.chat.append("You have to wait " + (10 - homeDelay / 100) + " more seconds!\n");
			}
		}
	}

	public void setCoins(int c)
	{
		Save.TS_COINS = c;
	}
}
