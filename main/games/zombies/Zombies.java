package main.games.zombies;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import main.game.Game;
import main.game.NewGame;
import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.utils.Keys;
import main.utils.helper.JavaHelp;
import main.utils.math.Vector2F;

@NewGame(name = "Zombies")
public class Zombies extends Game
{
	public SpritePlayer player;
	public List<Sprite> sprites;

	public Zombies()
	{
		super(40, 400, 600);
	}

	@Override
	public void init()
	{
		sprites = JavaHelp.newArrayList();
		sprites.add(new SpriteZombie(this, 20, 20));
		sprites.add(new SpriteZombie(this, 40, 40));
		sprites.add(new SpriteZombie(this, 20, 40));
		sprites.add(new SpriteZombie(this, 40, 20));
		sprites.add(player = new SpritePlayer(this));
	}

	@Override
	public void updateGame(int ticks)
	{
		final Iterator<Sprite> iterator = sprites.iterator();

		while (iterator.hasNext())
		{
			final Sprite s = iterator.next();
			if (s.isDead())
			{
				iterator.remove();
			}
		}

		for (final Sprite sprite : sprites)
		{
			sprite.runSpriteAI();
			sprite.updateSprite();
			for (final Sprite sprite1 : sprites)
			{
				if (sprite1 != sprite && sprite.intersects(sprite1))
				{
					sprite1.collidedWith(sprite);
				}
			}
		}
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		for (final Sprite sprite : sprites)
		{
			sprite.draw(g2d);
		}
	}

	@Override
	public void loadGameFromTag(DataTag tag)
	{
		for (int i = 0; i < sprites.size(); i++)
		{
			final DataTag tag1 = tag.getTag("" + i);
			if (tag1 != null)
			{
				sprites.get(i).loadFromTag(tag1);
			}
		}
	}

	@Override
	public void saveGameToTag(DataTag tag)
	{
		tag.setInteger("Size", sprites.size());
		for (int i = 0; i < sprites.size(); i++)
		{
			final DataTag tag1 = new DataTag();
			final Sprite sprite = sprites.get(i);
			sprite.saveToTag(tag1);
			tag.setTag("" + i, tag1);
		}
	}

	@Override
	public void keyPressed(Keys key)
	{
		switch (key)
		{
			case KEY_LEFT:
				player.getVelocity().x = -1;
				break;
			case KEY_RIGHT:
				player.getVelocity().x = 1;
				break;
			case KEY_UP:
				player.getVelocity().y = -1;
				break;
			case KEY_DOWN:
				player.getVelocity().y = 1;
				break;
			case KEY_SPACE:
				player.shootBullet(new Vector2F(player.lookX, player.lookY));
				break;
			case KEY_W:
				player.yV = -3;
				break;
			case KEY_S:
				player.yV = 3;
				break;
			case KEY_A:
				player.xV = -3;
				break;
			case KEY_D:
				player.xV = 3;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(Keys key)
	{
		switch (key)
		{
			case KEY_LEFT:
			case KEY_RIGHT:
				player.getVelocity().x = 0;
				break;
			case KEY_UP:
			case KEY_DOWN:
				player.getVelocity().y = 0;
				break;
			case KEY_W:
			case KEY_S:
				player.yV = 0;
				break;
			case KEY_A:
			case KEY_D:
				player.xV = 0;
				break;
			default:
				break;
		}
	}

	private static final long serialVersionUID = 1L;
}
