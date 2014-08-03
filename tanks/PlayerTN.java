package tanks;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import main.Vars;

public class PlayerTN extends SpriteTN
{
	private ArrayList<MissileTN> missiles;
	private int specialStored = 1;
	public int x1, x2, y1, y2;
	private EnumFace playerFace;
	private int health = 10;

	public PlayerTN(int x, int y)
	{
		super("/Images/Tanks/player.png");
		this.x = x;
		this.y = y;
		missiles = new ArrayList<MissileTN>();
	}

	public void addHealth(int health)
	{
		this.health += 0;// health;
	}

	/**
	 * @param specialStored
	 *            the specialStored to add
	 */
	public void addSpecialStored(int specialStored)
	{
		this.specialStored += 10;// += specialStored;
	}

	public void fire(EnumFace face, int missileChosen)
	{
		missiles.add(new MissileTN(this, face, missileChosen));
	}

	public EnumFace getFace()
	{
		if (x1 <= x && y1 >= y)
		{
			playerFace = EnumFace.SW;
		}
		if (x1 >= x && y1 >= y)
		{
			playerFace = EnumFace.SE;
		}
		if (x1 <= x && y1 <= y)
		{
			playerFace = EnumFace.NW;
		}
		if (x1 >= x && y1 <= y)
		{
			playerFace = EnumFace.NE;
		}

		if (x1 == x)
		{
			if (y1 > y)
			{
				playerFace = EnumFace.S;
			}
			if (y1 < y)
			{
				playerFace = EnumFace.N;
			}
		}
		if (y1 == y)
		{
			if (x1 > x)
			{
				playerFace = EnumFace.E;
			}
			if (x1 < x)
			{
				playerFace = EnumFace.W;
			}
		}

		if (y1 == y && x1 == x)
		{
			playerFace = EnumFace.N;
		}

		return playerFace;
	}

	public int getHealth()
	{
		return health;
	}

	public ArrayList<MissileTN> getMissiles()
	{
		return missiles;
	}

	/**
	 * @return the specialStored
	 */
	public int getSpecialStored()
	{
		return specialStored;
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE)
		{
			if (Vars.missileChosen != 1 && specialStored >= 1)
			{
				if (specialStored >= 1)
				{
					specialStored--;
				}

				if (Vars.missileChosen == 4)
				{
					EnumFace[] face = EnumFace.values();

					for (int i = 0; i < EnumFace.values().length; i++)
					{
						fire(face[i], 1);
					}
				}

				if (Vars.missileChosen == 2)
				{
					fire(EnumFace.N, 2);
				}

				if (Vars.missileChosen == 3)
				{
					fire(EnumFace.N, 3);
				}

				if (Vars.missileChosen == 5)
				{
					fire(getFace(), 5);
				}
			}

			if (Vars.missileChosen == 1)
			{
				fire(getFace(), 1);
			}
		}

		if (key == KeyEvent.VK_LEFT)
		{
			xV = -1;
		}

		if (key == KeyEvent.VK_RIGHT)
		{
			xV = 1;
		}

		if (key == KeyEvent.VK_UP)
		{
			yV = -1;
		}

		if (key == KeyEvent.VK_DOWN)
		{
			yV = 1;
		}

		if (key == KeyEvent.VK_A)
		{
			x2++;
		}

		if (key == KeyEvent.VK_D)
		{
			x2--;
		}

		if (key == KeyEvent.VK_W)
		{
			y2++;
		}

		if (key == KeyEvent.VK_S)
		{
			y2--;
		}

		if (key == KeyEvent.VK_Q)
		{
			if (Vars.missileChosen < 5)
			{
				Vars.missileChosen++;
			}
			else if (Vars.missileChosen >= 5)
			{
				Vars.missileChosen = 1;
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
		{
			xV = 0;
		}

		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN)
		{
			yV = 0;
		}

		if (key == KeyEvent.VK_A || key == KeyEvent.VK_D)
		{
			x2 = 0;
		}

		if (key == KeyEvent.VK_W || key == KeyEvent.VK_S)
		{
			y2 = 0;
		}
	}

	@Override
	public void spriteInit()
	{
		super.spriteInit();
		getFace();

		if (getHealth() <= 0)
		{
			setDestroyed(true);
			StartTN.inGame = false;
		}

		x1 -= x2;
		y1 -= y2;

		x1 -= x2;
		y1 -= y2;
	}
}
