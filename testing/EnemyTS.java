package testing;

import main.utils.Rand;
import testing.ItemTS.ItemsTS;

public class EnemyTS extends LivingSpriteTS
{
	private static final long serialVersionUID = 1L;

	public EnemyTS(int X, int Y)
	{
		super(X, Y, "/Images/Testing/enemy1.png", 30, false);
		setSpriteName("Enemy- " + Rand.nextInt(99));
		setDropsItems();
		setCanSpawn(true);
		toggleCanLevel();

		for (int i = 0; i < ItemsTS.allItems.length; i++)
		{
			addItemsToDrop(ItemsTS.allItems[i]);
		}
	}

	@Override
	public void init()
	{
		this.walkRandomly();
	}

	@Override
	public LivingSpriteTS newClone()
	{
		return new EnemyTS(getX(), getY());
	}
}
