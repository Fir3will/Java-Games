package main.games.monsters;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import main.game.sprites.Sprite;
import main.game.sprites.SpriteList;
import main.saving.DataTag;
import main.utils.math.Vector2F;

public class PlayerMN extends Sprite
{
	private final ImageIcon image;
	private final SpriteList missiles;

	public PlayerMN()
	{
		super();
		image = getIcon("Snake/player.png");
		setScale(new Vector2F(image.getIconWidth(), image.getIconHeight()));
		setLocation(new Vector2F(200, 300));
		missiles = new SpriteList(game);
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		g2d.drawImage(image.getImage(), 0, 0, null);
	}

	public void fireMissile()
	{

	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
		missiles.complexLoadFromTag(tag);
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
		missiles.complexSaveToTag(tag);
	}
}
