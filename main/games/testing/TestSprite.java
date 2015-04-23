package main.games.testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import main.game.sprites.Sprite;
import main.saving.DataTag;
import main.utils.math.Vector2F;

public class TestSprite extends Sprite
{
	public String name = "";

	public TestSprite()
	{
		super();
		setScale(new Vector2F(20, 20));
	}

	@Override
	public void drawSprite(Graphics2D g2d)
	{
		Color color = g2d.getColor();
		int width = g2d.getFontMetrics().stringWidth(name);

		g2d.setColor(Color.BLACK);
		g2d.draw(new Rectangle(0, 0, (int) getWidth(), (int) getHeight()));
		g2d.setColor(Color.RED);
		g2d.drawString(name, -width / 2, 6);
		g2d.setColor(Color.BLACK);
		g2d.drawString(name, -width / 2 + 1, 7);
		g2d.setColor(color);
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		super.loadFromTag(tag);
		name = tag.getString("Test Name", "");
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		super.saveToTag(tag);
		tag.setString("Test Name", name);
	}
}
