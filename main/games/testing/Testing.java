package main.games.testing;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import main.game.Game;
import main.game.NewGame;
import main.game.sprites.Sprite;
import main.game.sprites.SpriteList;
import main.utils.Keys;
import main.utils.Rand;
import main.utils.math.Rotation;
import main.utils.math.Vector2F;

@NewGame(name = "Testing")
public class Testing extends Game
{
	private static final long serialVersionUID = 1L;
	private SpriteList sprites;

	public Testing()
	{
		super(5, 1200, 600);
	}

	@Override
	public void init()
	{
		if (sprites == null)
		{
			sprites = new SpriteList(this);
		}
		addSprite(new TestSprite());
	}

	private void addSprite(TestSprite s)
	{
		s.setLocation(new Vector2F(Rand.nextInt(getWidth()), Rand.nextInt(getHeight())));
		sprites.add(s);
	}

	@Override
	public void updateGame(int ticks)
	{
		sprites.updateSprites();
		sprites.runSpriteAIs();
		for (int i = 0; i < sprites.size(); i++)
		{
			Vector2F v = sprites.get(i).getLocation();
			if (v.x > 1200 || v.y > 600 || v.x < 0 || v.y < 0)
			{
				sprites.remove(i);
				break;
			}
		}
	}

	@Override
	public void keyPressed(Keys key)
	{
		keyReleased(key);
	}

	@Override
	public void keyReleased(Keys key)
	{
		if (key == Keys.KEY_SPACE)
		{
			addSprite(new TestSprite());
		}
		if (key == Keys.KEY_R)
		{
			for (int i = 0; i < sprites.size(); i++)
			{
				sprites.get(i).setRotation(sprites.get(i).getRotation().set(sprites.get(i).getRotation().getDegrees() + 1));
			}
		}
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		sprites.drawSprites(g2d);
		for (int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			g2d.drawString(i + "|Rotation: " + (int) s.getRotation().getDegrees(), 5, 15 + i * 15);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Vector2F v = new Vector2F(e.getX(), e.getY());
		for (Sprite s : sprites)
		{
			Rotation r = s.getRotation();
			r.set(v.subtract(s.getLocation()));
		}
	}
}
