package customcode;

import java.awt.Graphics2D;
import main.games.GamePanel;
import main.utils.Rand;

public class MyGame extends GamePanel
{
	private static final long serialVersionUID = 1L;
	private final int rand;

	public MyGame()
	{
		super(25, 400, 200);
		rand = Rand.nextInt();
	}

	@Override
	public void init()
	{

	}

	@Override
	public void updateGame(int ticks)
	{

	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		g2d.drawString("Ticks: " + getTicks(), 200, 100);
		g2d.drawString("My Random: " + rand, 200, 130);
	}

	@Override
	public String getGameName()
	{
		return "My Game";
	}

}
