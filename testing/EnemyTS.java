package testing;

import main.utils.Rand;
import testing.ItemTS.ItemsTS;

public class EnemyTS extends LivingSpriteTS
{
	public EnemyTS(int X, int Y, int maxHealth)
	{
		super(X, Y, "/Images/Testing/enemy1.png", 30, false);
		setSpriteName("Enemy- " + Rand.nextInt(99));
		setDropsItems();
		toggleCanLevel();

		for (int i = 0; i < ItemsTS.allItems.length; i++)
		{
			addItemsToDrop(ItemsTS.allItems[i]);
		}
	}

	@Override
	public void init()
	{
		setCanSpawn(true);
		/*
		 * walkDelay++; if (Rand.isPercent(25) && walkDelay >= 100) { for (int i
		 * = 0; i < walkingCycle.length; i++) { walkingCycle[i] = false; }
		 * walkDelay = 0; walkingCycle[Rand.nextInt(4) - 1] = true; } if
		 * (!isAttacked()) { if (walkingCycle[0]) { x += 1; } if
		 * (walkingCycle[1]) { x -= 1; } if (walkingCycle[2]) { y += 1; } if
		 * (walkingCycle[3]) { y -= 1; } } if (isAttacked() && getAttacker() !=
		 * null) { if (isAttacked() && getAttacker().getHealth() != 0) { if
		 * (getAttacker().x > x) { x++; } if (getAttacker().x < x) { x--; } if
		 * (getAttacker().y > y) { y++; } if (getAttacker().y < y) { y--; } } }
		 */
	}

	@Override
	public LivingSpriteTS newClone()
	{
		return new EnemyTS(getX(), getY(), getMaxHealth());
	}
}
