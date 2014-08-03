package tanks;

import java.util.ArrayList;

public class EnemyTN extends SpriteTN
{
	private EnumFace face;
	private ArrayList<MissileTN> missiles;
	private int tankState;
	private int health = 3;
	private SpriteTN target;
	private EnumFace enemyFace;
	private int shotCounter = 0;

	public EnemyTN(int x, int y, EnumFace face, int tankState, SpriteTN target)
	{
		super("/Images/Tanks/tank1.png");
		this.x = x;
		this.y = y;
		missiles = new ArrayList<MissileTN>();
		setTankState(tankState);
		this.face = face;
		this.target = target;
	}

	public void addHealth(int health)
	{
		this.health += health;
	}

	public void fire(EnumFace face, int missileState)
	{
		missiles.add(new MissileTN(this, face, missileState));
	}

	public EnumFace getFace()
	{
		if (target.x <= x && target.y >= y)
		{
			enemyFace = EnumFace.SW;
		}
		if (target.x >= x && target.y >= y)
		{
			enemyFace = EnumFace.SE;
		}
		if (target.x <= x && target.y <= y)
		{
			enemyFace = EnumFace.NW;
		}
		if (target.x >= x && target.y <= y)
		{
			enemyFace = EnumFace.NE;
		}

		if (target.x == x)
		{
			if (target.y > y)
			{
				enemyFace = EnumFace.S;
			}
			if (target.y < y)
			{
				enemyFace = EnumFace.N;
			}
		}
		if (target.y == y)
		{
			if (target.x > x)
			{
				enemyFace = EnumFace.E;
			}
			if (target.x < x)
			{
				enemyFace = EnumFace.W;
			}
		}

		if (target.y == y && target.x == x)
		{
			enemyFace = EnumFace.N;
		}

		return enemyFace;
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
	 * @return the tankState
	 */
	public int getTankState()
	{
		return tankState;
	}

	/**
	 * @param tankState
	 *            the tankState to set
	 */
	public void setTankState(int tankState)
	{
		this.tankState = tankState;
	}

	@Override
	public void spriteInit()
	{
		if (shotCounter < 100)
		{
			shotCounter++;
		}

		if (shotCounter >= 100)
		{
			fire(getFace(), 1);
			shotCounter = 0;
		}

		if (health <= 0)
		{
			setDestroyed(true);
		}

		x += xV;
		y += yV;

		if (face == EnumFace.N)
		{
			yV = -1;
		}
		if (face == EnumFace.S)
		{
			yV = 1;
		}
		if (face == EnumFace.W)
		{
			xV = -2;
		}
		if (face == EnumFace.E)
		{
			xV = 2;
		}
		if (face == EnumFace.NE)
		{
			yV = -1;
			xV = 1;
		}
		if (face == EnumFace.NW)
		{
			yV = -1;
			xV = -1;
		}
		if (face == EnumFace.SE)
		{
			yV = 1;
			xV = 1;
		}
		if (face == EnumFace.SW)
		{
			yV = 1;
			xV = -1;
		}

		if (x < 0)
		{
			x = 360;
		}
		if (x > 360)
		{
			x = 0;
		}
		if (y < 0)
		{
			y = 360;
		}
		if (y > 360)
		{
			y = 0;
		}
	}
}
