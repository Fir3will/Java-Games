package main.games.monsters;

import java.awt.Color;
import java.awt.Graphics2D;
import main.game.sprites.Sprite;
import main.utils.math.Vector2F;

public class MissileMN extends Sprite
{
	public MissileMN()
	{
		setScale(new Vector2F(2, 2));
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		getBounds().drawShape(g2d);
	}
}
