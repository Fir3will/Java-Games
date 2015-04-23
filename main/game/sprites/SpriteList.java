package main.game.sprites;

import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import main.events.HandlerListEvent;
import main.events.SpriteCollisionEvent;
import main.game.Game;
import main.game.sprites.Collision.CollisionHandler;
import main.saving.DataTag;
import main.utils.helper.JavaHelp;
import modhandler.Loader;

public class SpriteList extends ArrayList<Sprite>
{
	private final Game game;
	public final List<Class<?>> exclusions;
	public final List<CollisionHandler> collisionHandlers;

	public SpriteList(Game game, CollisionHandler... handlers)
	{
		this.game = game;
		exclusions = JavaHelp.newArrayList();
		collisionHandlers = new ArrayList<CollisionHandler>(Arrays.asList(handlers));
		Loader.addEventToAll(new HandlerListEvent(collisionHandlers));
	}

	@Override
	public boolean add(Sprite e)
	{
		e.game = game;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends Sprite> c)
	{
		for (Sprite s : c)
		{
			s.game = game;
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Sprite> c)
	{
		for (Sprite s : c)
		{
			s.game = game;
		}
		return super.addAll(index, c);
	}

	@Override
	public void add(int index, Sprite element)
	{
		element.game = game;
		super.add(index, element);
	}

	public void handleCollisions(CollisionHandler... handlers)
	{
		for (int i = 0; i < size(); i++)
		{
			for (int j = 0; j < size(); j++)
			{
				if (i != j)
				{
					Sprite s1 = get(i);
					Sprite s2 = get(j);
					if (s1.intersects(s2))
					{
						Collision c = new Collision(s1, s2);
						Loader.addEventToAll(new SpriteCollisionEvent(c));
						for (CollisionHandler handler : handlers)
						{
							handler.collided(c);
						}
						s1.collidedWith(s2);
					}
				}
			}
		}
	}

	public void runSpriteAIs()
	{
		for (Sprite sprite : this)
		{
			if (!sprite.isDead())
			{
				sprite.runSpriteAI();
			}
		}
	}

	public void drawSprites(Graphics2D g2d)
	{
		for (Sprite sprite : this)
		{
			if (!sprite.isDead())
			{
				sprite.draw(g2d);
			}
		}
	}

	public void updateSprites()
	{
		for (Sprite sprite : this)
		{
			if (!sprite.isDead())
			{
				sprite.updateSprite();
			}
		}
	}

	@Override
	public Sprite[] toArray()
	{
		return super.toArray(new Sprite[0]);
	}

	@SuppressWarnings("unchecked")
	public <T extends Sprite> T[] getAllOf(Class<? extends T> clazz)
	{
		List<T> objs = JavaHelp.newArrayList();
		for (int i = 0; i < size(); i++)
		{
			Sprite s = get(i);
			if (s.getClass().isAssignableFrom(clazz))
			{
				objs.add(clazz.cast(s));
			}
		}
		return objs.toArray((T[]) Array.newInstance(clazz, 0));
	}

	public void complexLoadFromTag(DataTag tag)
	{
		try
		{
			int size = tag.getInteger("Size", 0);
			for (int i = 0; i < size; i++)
			{
				DataTag t = tag.getTag("" + i);
				Class<?> c = Class.forName(t.getString("Class Name", null));
				Sprite s = (Sprite) c.newInstance();
				set(i, s);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void complexSaveToTag(DataTag tag)
	{
		tag.setInteger("Size", size());
		for (int i = 0; i < size(); i++)
		{
			DataTag t = new DataTag();
			t.setString("Class Name", get(i).getClass().getName());
			tag.setTag("" + i, t);
		}
	}

	public void simpleLoadFromTag(DataTag tag)
	{
		for (int i = 0; i < size(); i++)
		{
			DataTag tag1 = tag.getTag("" + i);
			get(i).loadFromTag(tag1);
		}
	}

	public void simpleSaveToTag(DataTag tag)
	{
		for (int i = 0; i < size(); i++)
		{
			DataTag tag1 = new DataTag();
			get(i).saveToTag(tag1);
			tag.setTag("" + i, tag1);
		}
	}

	private static final long serialVersionUID = 1L;
}
