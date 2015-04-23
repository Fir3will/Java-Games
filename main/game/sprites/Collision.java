package main.game.sprites;


public class Collision
{
	public final Sprite s1, s2;

	public Collision(Sprite s1, Sprite s2)
	{
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Collision)
		{
			Collision c = (Collision) o;
			if (c.s1 == s1 || c.s2 == s1 || c.s1 == s2 || c.s2 == s2) return true;
		}
		return false;
	}

	public interface CollisionHandler
	{
		public void collided(Collision c);
	}
}