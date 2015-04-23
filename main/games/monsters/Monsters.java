package main.games.monsters;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import main.game.Game;
import main.game.NewGame;
import main.game.sprites.SpriteList;
import main.saving.DataTag;
import main.utils.math.Vector2F;

@NewGame(name = "Monsters")
public class Monsters extends Game
{
	private static final long serialVersionUID = 1L;
	public PlayerMN player;
	public SpriteList sprites;

	public Monsters()
	{
		super(400, 600);
	}

	@Override
	public void init()
	{
		sprites = new SpriteList(this);
		sprites.add(player = new PlayerMN());
	}

	@Override
	public void updateGame(int ticks)
	{
		sprites.updateSprites();
		sprites.runSpriteAIs();
		sprites.handleCollisions();
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		sprites.drawSprites(g2d);
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		player.getRotation().set(new Vector2F(e.getX(), e.getY()).subtract(player.getLocation()));
		player.getVelocity().set(player.getRotation().toVector2F().mult(2));
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		player.fireMissile();
	}

	@Override
	public void loadGameFromTag(DataTag tag)
	{
		sprites.complexLoadFromTag(tag);
		player = sprites.getAllOf(PlayerMN.class)[0];
	}

	@Override
	public void saveGameToTag(DataTag tag)
	{
		sprites.complexSaveToTag(tag);
	}
}
