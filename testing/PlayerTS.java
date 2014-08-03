package testing;

import java.awt.event.KeyEvent;
import main.Vars;

public class PlayerTS extends LivingSpriteTS
{
	private int homeDelay = 0;

	public PlayerTS()
	{
		// super(Vars.tsX, Vars.tsY, "/Images/Testing/player.png", 50, false);
		super(Vars.tsX, Vars.tsY, 20, 20, 50, false);
		toggleCanLevel();

		setSpriteName(Vars.playerName);
		addXPToSkill(0, Vars.tsAttackXP);
		addXPToSkill(1, Vars.tsDefenceXP);
		setHealth(Vars.tsHealth);

		canPass = true;
		for (int i = 0; i < Vars.tsPlayerItems.length; i++)
		{
			this.addItemToInventory(Vars.tsPlayerItems[i]);
		}
	}

	public int getCoins()
	{
		return Vars.tsCoins;
	}

	public SkillTS getLevel(int skillID)
	{
		return getSkill(skillID);
	}

	@Override
	public void init()
	{
		Vars.tsAttackXP = getXPFromSkill(0);
		Vars.tsDefenceXP = getXPFromSkill(1);
		Vars.tsX = getX();
		Vars.tsY = getY();
		Vars.tsHealth = getHealth();
		Vars.tsPlayerItems = getInv();

		homeDelay++;
		x += xV;
		y += yV;
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_1)
		{
			Testing.chat.append("You are now traning " + getSkill(0) + '\n');
			setActivatedSkill(0);
		}

		if (key == KeyEvent.VK_2)
		{
			Testing.chat.append("You are now traning " + getSkill(1) + '\n');
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

		if (line.equals("/Home"))
		{
			if (homeDelay >= 1000)
			{
				homeDelay = 0;
				x = 400;
				y = 300;
			}
			else
			{
				Testing.chat.append("You have to wait " + (10 - homeDelay / 100) + " more seconds!\n");
			}
		}
	}

	public void setCoins(int c)
	{
		Vars.tsCoins = c;
	}
}
