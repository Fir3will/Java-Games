package testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import main.Save;
import main.utils.Rand;
import main.utils.helper.Sound;
import testing.SkillTS.SkillsTS;

public abstract class LivingSpriteTS extends SpriteTS
{
	private static final long serialVersionUID = 1L;

	private ItemTS[] items = new ItemTS[18], inventory = new ItemTS[8];
	private SkillTS[] allSkills;
	private boolean[] activatedSkill, walkingCycle = new boolean[4];
	private String name = "[NO-NAME]";
	private int walkDelay, spawnDelay, health, maxHealth, forgetDelay, isFirst, hit, attackRate = 150, attackDelay = 150;
	private LivingSpriteTS attacker;
	private boolean isAggressive, isAttacked, canSpawn, canLevel, dropsItems;

	public LivingSpriteTS(int X, int Y, int WIDTH, int HEIGHT, int maxHealth, boolean aggressive)
	{
		super(X, Y, WIDTH, HEIGHT);
		startUp(aggressive, maxHealth);
	}

	public LivingSpriteTS(int X, int Y, String path, int maxHealth, boolean aggressive)
	{
		super(X, Y, path);
		startUp(aggressive, maxHealth);
	}

	public boolean addItemsToDrop(ItemTS itemToAdd)
	{
		for (int i = 0; i < items.length; i++)
		{
			if (items[i] == null)
			{
				items[i] = itemToAdd;
				return true;
			}
		}
		return false;
	}

	public boolean addItemToInventory(ItemTS item)
	{
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] == null)
			{
				inventory[i] = item;
				return true;
			}
		}
		return false;
	}

	public boolean addItemToInventory(ItemTS item, int slot)
	{
		if (inventory[slot] == null)
		{
			inventory[slot] = item;
			return true;
		}
		return false;
	}

	public void addSpawnDelay(int delay)
	{
		spawnDelay += delay;
	}

	public void addXPToSkill(int index, int xpToAdd)
	{
		if (canLevel)
		{
			allSkills[index].addXP(xpToAdd);
		}
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		if (getImage() == null)
		{
			g2d.draw3DRect(x, y, width, height, false);
		}
		else
		{
			g2d.drawImage(getImage().getImage(), x, y, obs);
		}
		if (isAttacked())
		{
			if (isAggressive())
			{
				g2d.setColor(Color.RED);
			}
			else
			{
				g2d.setColor(Color.BLUE);
			}
			if (this instanceof PlayerTS)
			{
				g2d.setColor(Color.GREEN);
			}
			if (isFirst == 1)
			{
				g2d.drawString("|" + getHealth() + "|" + hit + "|", x, y);
				g2d.setColor(Color.BLACK);
				g2d.drawString("|" + getHealth() + "|" + hit + "|", x + 1, y + 1);
			}
			else if (isFirst == 2)
			{
				g2d.drawString("|" + getHealth() + "|" + hit + "|", x, y + 10);
				g2d.setColor(Color.BLACK);
				g2d.drawString("|" + getHealth() + "|" + hit + "|", x + 1, y + 11);
			}
		}
	}

	public void dropItems()
	{
		if (getAttacker() == null) return;

		for (int i = 0; i < items.length; i++)
		{
			if (items[i] != null)
			{
				getAttacker().addItemToInventory(items[i]);
			}
		}
	}

	public int getActivatedSkill()
	{
		for (int i = 0; i < allSkills.length; i++)
		{
			if (activatedSkill[i]) return i;
		}
		return 0;
	}

	public LivingSpriteTS getAttacker()
	{
		return attacker;
	}

	public boolean getCanLevel()
	{
		return canLevel;
	}

	public boolean getCanSpawn()
	{
		return canSpawn;
	}

	public int getHealth()
	{
		return health;
	}

	public ItemTS[] getInv()
	{
		return inventory;
	}

	public ItemTS[] getItems()
	{
		return items;
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public SkillTS getSkill(int index)
	{
		return allSkills[index];
	}

	public int getSpawnDelay()
	{
		return spawnDelay;
	}

	public String getSpriteName()
	{
		return name;
	}

	public int getXPFromSkill(int index)
	{
		return allSkills[index].getXP();
	}

	public abstract void init();

	public boolean isAggressive()
	{
		return isAggressive;
	}

	public boolean isAttacked()
	{
		return isAttacked;
	}

	public void isAttacked(LivingSpriteTS attacker, int isFirst)
	{
		int lvlDiff = attacker.getSkill(SkillsTS.ATTACK.getSkillID() - 1).getLevel() - getSkill(SkillsTS.DEFENCE.getSkillID() - 1).getLevel();
		int rand0 = 100 - attacker.getSkill(1).getLevel();
		int rand1 = Rand.isPercent(rand0) ? Rand.nextInt(1) : 0;
		int mult = Save.DIFFICULTY == 2 ? 6 : Save.DIFFICULTY == 1 ? 8 : 12;
		int hit = lvlDiff <= 0 ? rand1 : Rand.nextInt(lvlDiff);

		if (attackDelay >= attackRate)
		{
			if (attacker.getCanLevel())
			{
				attacker.addXPToSkill(attacker.getActivatedSkill(), hit * mult);
			}

			if (hit == 0)
			{
				Sound.playSound(Rand.nextBoolean() ? Sound.SWORD_CLASH : Sound.SWORD_SWIPE, false, 0.0F);
			}

			this.hit = hit;
			this.isFirst = isFirst;
			setHealth(getHealth() - hit);
			setAttacked(true);
			setAttacker(attacker);
			attackDelay = 0;
			forgetDelay = 0;
		}

		if (attacker.getHealth() <= 0)
		{
			setAttacker(null);
		}
	}

	public abstract LivingSpriteTS newClone();

	@Override
	public void run()
	{
		attackDelay++;

		if (isAttacked() && getAttacker() != null)
		{
			forgetDelay++;

			if (forgetDelay >= 500)
			{
				getAttacker().setAttacked(false);
				getAttacker().setAttacker(null);
				getAttacker().setIsFirst(0);

				setAttacker(null);
				setAttacked(false);
				isFirst = 0;
			}
		}

		if (getHealth() <= 0)
		{
			if (isAttacked() && getAttacker() != null)
			{
				if (getAttacker() instanceof PlayerTS)
				{
					((PlayerTS) getAttacker()).setCoins(((PlayerTS) getAttacker()).getCoins() + Rand.nextInt(200));
				}

				if (dropsItems)
				{
					dropItems();
				}

				getAttacker().setAttacked(false);
				getAttacker().setAttacker(null);
				getAttacker().setIsFirst(0);

				setAttacker(null);
				setAttacked(false);
				isFirst = 0;
				setHealth(getMaxHealth());

				if (!(this instanceof PlayerTS))
				{
					setDestroyed(true);
				}
			}
		}

		init();
	}

	public void setActivatedSkill(int index)
	{
		for (int i = 0; i < allSkills.length; i++)
		{
			activatedSkill[i] = false;
		}

		activatedSkill[index] = true;
	}

	public void setAggressive(boolean aggressive)
	{
		isAggressive = aggressive;
	}

	public void setAttacked(boolean a)
	{
		isAttacked = a;
	}

	public void setAttacker(LivingSpriteTS a)
	{
		attacker = a;
	}

	public void setAttackRate(int rate)
	{
		// attackRate = rate;
	}

	public void setCanSpawn(boolean canSpawn)
	{
		this.canSpawn = canSpawn;
	}

	public void setDropsItems()
	{
		dropsItems = true;
	}

	public void setHealth(int h)
	{
		health = h;
	}

	public void setIsFirst(int isFirst)
	{
		this.isFirst = isFirst;
	}

	public void setSkills(SkillTS[] skills)
	{
		allSkills = skills;
	}

	public void setSpawnDelay(int delay)
	{
		spawnDelay = delay;
	}

	public void setSpriteName(String name)
	{
		this.name = name;
	}

	protected void startUp(boolean aggressive, int maxHealth)
	{
		isAggressive = aggressive;
		health = this.maxHealth = maxHealth;

		allSkills = new SkillTS[] {new SkillTS(SkillsTS.ATTACK), new SkillTS(SkillsTS.DEFENCE)};
		activatedSkill = new boolean[allSkills.length];

		for (int i = 0; i < allSkills.length; i++)
		{
			activatedSkill[i] = false;
		}

		activatedSkill[0] = true;
	}

	public void toggleCanLevel()
	{
		canLevel = !canLevel;
	}

	@Override
	public String toString()
	{
		return getSpriteName();
	}

	public void walkRandomly()
	{
		walkDelay++;

		if (Rand.isPercent(25) && walkDelay >= 100)
		{
			for (int i = 0; i < walkingCycle.length; i++)
			{
				walkingCycle[i] = false;
			}
			walkDelay = 0;
			walkingCycle[Rand.nextInt(4) - 1] = true;
		}
		if (!isAttacked())
		{
			if (walkingCycle[0])
			{
				x += 1;
			}
			if (walkingCycle[1])
			{
				x -= 1;
			}
			if (walkingCycle[2])
			{
				y += 1;
			}
			if (walkingCycle[3])
			{
				y -= 1;
			}
		}
		if (isAttacked() && getAttacker() != null)
		{
			if (isAttacked() && getAttacker().getHealth() != 0)
			{
				if (getAttacker().getX() > x)
				{
					x++;
				}
				if (getAttacker().getX() < x)
				{
					x--;
				}
				if (getAttacker().getY() > y)
				{
					y++;
				}
				if (getAttacker().getY() < y)
				{
					y--;
				}
			}
		}

	}
}
