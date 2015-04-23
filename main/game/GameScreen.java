package main.game;

import java.awt.Rectangle;

public final class GameScreen
{
	public final Rectangle bounds;
	public final int width, height;
	public final Game game;

	public GameScreen(Game game)
	{
		this.game = game;
		width = game.getWidth();
		height = game.getHeight();
		bounds = new Rectangle(0, 0, width, height);
	}
}
