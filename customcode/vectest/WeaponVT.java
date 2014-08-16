package customcode.vectest;

import static main.utils.Keys.KEY_1;
import static main.utils.Keys.KEY_2;
import static main.utils.Keys.KEY_3;
import static main.utils.Keys.KEY_4;
import static main.utils.Keys.KEY_5;
import static main.utils.Keys.KEY_6;
import static main.utils.Keys.KEY_7;
import static main.utils.Keys.KEY_8;
import java.io.Serializable;
import main.utils.Keys;
import main.utils.Rand;
import main.utils.math.Vector2F;

public class WeaponVT implements Serializable
{
	public static final WeaponVT m1911 = new WeaponVT(KEY_1, "M1911", 7, 1, false);
	public static final WeaponVT spas_12 = new WeaponVT(KEY_2, "Spas-12", 25, 5, false);
	public static final WeaponVT wa2000 = new WeaponVT(KEY_3, "WA2000", 6, 1, false);
	public static final WeaponVT mp7 = new WeaponVT(KEY_4, "MP7", 30, 2, true);
	public static final WeaponVT m4a1 = new WeaponVT(KEY_5, "M4A1", 45, 1, true);
	public static final WeaponVT famas = new WeaponVT(KEY_6, "Famas", 30, 3, false);
	public static final WeaponVT mk14 = new WeaponVT(KEY_7, "MK14", 15, 1, false);
	public static final WeaponVT desert_eagle = new WeaponVT(KEY_8, "Desert Eagle", 7, 1, false);
	public static final WeaponVT[] weapons = {m1911, spas_12, wa2000, mp7, m4a1, famas, mk14, desert_eagle};

	public final String name;
	public final Keys key;
	public final int id, maxAmmo, bulletsUsed;
	public final boolean isAutomatic;

	public WeaponVT(Keys key, String name, int maxAmmo, int bulletsUsed, boolean isAutomatic)
	{
		this.key = key;
		this.name = name;
		this.maxAmmo = maxAmmo;
		this.bulletsUsed = bulletsUsed;
		this.isAutomatic = isAutomatic;
		this.id = ID++;
	}

	public void fireAt(TestFrame main, Vector2F pos, Vector2F destination)
	{
		switch (id)
		{
			case 0:
			{
				new BulletVT(main, pos, destination, 4.0F, 2);
				break;
			}
			case 1:
			{
				for (int i = 0; i < 5; i++)
				{
					Vector2F vec = destination.clone();
					vec.x += Rand.nextFloat() * 50 * (Rand.nextBoolean() ? 1.0F : -1.0F);
					vec.y += Rand.nextFloat() * 50 * (Rand.nextBoolean() ? 1.0F : -1.0F);
					new BulletVT(main, pos, vec, 1.0F, 1);
				}
				break;
			}
			case 2:
			{
				new BulletVT(main, pos, destination, 12.0F, 3);
				break;
			}
			case 3:
			{
				for (int i = 0; i < 2; i++)
				{
					Vector2F vec = destination.clone();
					vec.x += Rand.nextFloat() * 10 * (Rand.nextBoolean() ? 1.5F : -1.5F);
					vec.y += Rand.nextFloat() * 10 * (Rand.nextBoolean() ? 1.5F : -1.5F);
					new BulletVT(main, pos, vec, 2.0F, 1);
				}
				break;
			}
			case 4:
			{
				Vector2F vec = destination.clone();
				vec.x += Rand.nextFloat() * 10 * (Rand.nextBoolean() ? 1.0F : -1.0F);
				vec.y += Rand.nextFloat() * 10 * (Rand.nextBoolean() ? 1.0F : -1.0F);
				new BulletVT(main, pos, vec, 3.0F, 2);
				break;
			}
			case 5:
			{
				for (int i = 0; i < 3; i++)
				{
					new BulletVT(main, pos, destination, 4.0F - i, 1);
				}
				break;
			}
			case 6:
			{
				new BulletVT(main, pos, destination, 4.0F, 2);
				break;
			}
			case 7:
			{
				new BulletVT(main, pos, destination, 4.0F, 4);
				break;
			}
			default:
			{
				break;
			}
		}
	}

	public static int getWeapons()
	{
		return ID;
	}

	private static int ID;
	private static final long serialVersionUID = 1L;

	public static void keyPressed(PlayerVT player, Keys key)
	{
		for (WeaponVT wep : weapons)
		{
			if (key == wep.key)
			{
				player.wep = wep;
			}
		}
	}
}
