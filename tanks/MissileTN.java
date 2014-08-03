package tanks;

public class MissileTN extends SpriteTN
{
	private EnumFace face;
	private int missileState;
	private SpriteTN sprite;
	@SuppressWarnings("unused")
	private int missileCounter = 0;
	private boolean splitUp = false;

	public MissileTN(SpriteTN sprite, EnumFace face, int missileState)
	{
		super("/Images/Tanks/missile.png");

		x = sprite.x + sprite.getWidth() / 2;
		y = sprite.y + sprite.getHeight() / 2;
		this.face = face;
		this.sprite = sprite;
		setMissileState(missileState);
	}

	public int getMissileState()
	{
		return missileState;
	}

	public boolean isSplitUp()
	{
		return splitUp;
	}

	public void setMissileState(int missileState)
	{
		this.missileState = missileState;
	}

	public void setSplitUp(boolean splitUp)
	{
		this.splitUp = splitUp;
	}

	@Override
	public void spriteInit()
	{
		if (missileState == 2)
		{
			try
			{
				if (x < ((PlayerTN) sprite).x1)
				{
					xV += 1;
					x += 1;
				}

				if (x > ((PlayerTN) sprite).x1)
				{
					xV -= 1;
					x -= 1;
				}

				if (y < ((PlayerTN) sprite).y1)
				{
					yV += 1;
					y += 1;
				}

				if (y > ((PlayerTN) sprite).y1)
				{
					yV -= 1;
					y -= 1;
				}

				if (y == ((PlayerTN) sprite).y1 && x == ((PlayerTN) sprite).x1)
				{
					setDestroyed(true);
				}
			}
			catch (Exception e)
			{
				System.err.println("Invalid Sprite! Shutting Down! Has to be the PLAYER for missileState 2, Not " + sprite);
				e.printStackTrace();
				System.exit(-1);
			}
			getLocation().add(sprite.getLocation());
		}

		if (missileState == 5)
		{
			if (face == EnumFace.N)
			{
				yV = -2;
			}
			if (face == EnumFace.S)
			{
				yV = 2;
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

			if (isSplitUp() && !isDestroyed())
			{
				for (int i = 0; i < EnumFace.values().length; i++)
				{
					EnumFace[] faces = EnumFace.values();
					((PlayerTN) sprite).getMissiles().add(new MissileTN(this, faces[i], 1));
				}

				setDestroyed(true);
			}
		}

		if (missileState == 3)
		{
			try
			{
				if (x < ((PlayerTN) sprite).x1)
				{
					xV += 1;
				}

				if (x > ((PlayerTN) sprite).x1)
				{
					xV -= 1;
				}

				if (y < ((PlayerTN) sprite).y1)
				{
					yV += 1;
				}

				if (y > ((PlayerTN) sprite).y1)
				{
					yV -= 1;
				}
			}
			catch (NullPointerException e)
			{
				System.err.println("Invalid Sprite! Shutting Down! Has to be PlayerTN for missileState 3, Not " + sprite);
				e.printStackTrace();
				System.exit(-1);
			}
		}

		if (missileState == 1)
		{
			if (face == EnumFace.N)
			{
				yV = -2;
			}
			if (face == EnumFace.S)
			{
				yV = 2;
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
		}
		System.out.println("Distance: " + getLocation().distance(sprite.getLocation()));

		x += xV;
		y += yV;

		if (x <= 0)
		{
			setDestroyed(true);
		}
		if (x >= 400 - width)
		{
			setDestroyed(true);
		}
		if (y <= 0)
		{
			setDestroyed(true);
		}
		if (y >= 400 - height)
		{
			setDestroyed(true);
		}
	}
}