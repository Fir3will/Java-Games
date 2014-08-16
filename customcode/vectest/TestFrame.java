package customcode.vectest;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import main.Save;
import main.games.GamePanel;
import main.games.NewGame;
import main.utils.Keys;

@NewGame(name = "Zombies")
public class TestFrame extends GamePanel
{
	private static final long serialVersionUID = 1L;
	public ArrayList<SpriteVT> sprites;
	public PlayerVT player;
	public ArrayList<ZombieVT> entity = new ArrayList<ZombieVT>();
	public boolean isPaused;
	public Level levelObj;

	public TestFrame()
	{
		super(2, 400, 600);
	}

	public void checkCollisions()
	{
		for (SpriteVT spriteA : sprites)
		{
			for (SpriteVT spriteB : sprites)
			{
				if (!spriteA.equals(spriteB) && spriteA.getBounds().intersects(spriteB.getBounds()))
				{
					spriteA.collidedWith(spriteB);
				}
			}
		}
	}

	@Override
	public void keyPressed(Keys e)
	{
		player.keyPressed(e);
	}

	@Override
	public void keyReleased(Keys e)
	{
		player.keyReleased(e);
	}

	@Override
	public void init()
	{
		sprites = new ArrayList<SpriteVT>();
		setBackground(Save.PLAYER_COLOR.brighter());

		player = new PlayerVT(this);

		if (levelObj == null)
		{
			levelObj = new Level(this);
		}
	}

	@Override
	public void updateGame(int ticks)
	{
		levelObj.run();

		ArrayList<SpriteVT> removes = new ArrayList<SpriteVT>();

		for (SpriteVT sprite : sprites)
		{
			if (!sprite.isDestroyed())
			{
				sprite.init();
			}
			else
			{
				removes.add(sprite);
			}
		}

		for (SpriteVT sprite : removes)
		{
			sprites.remove(sprite);
		}

		checkCollisions();
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		if (player.health > 0)
		{
			if (isPaused)
			{
				Font defaultFont = g2d.getFont();
				Font monaco = new Font("Monaco", Font.PLAIN, 24);
				g2d.setFont(monaco);
				int toAdd = g2d.getFontMetrics().stringWidth("Paused, P to Unpause") / 2;
				g2d.drawString("Paused, P to Unpause", 200 - toAdd, 300);
				g2d.setFont(defaultFont);
			}

			for (SpriteVT sprite : sprites)
			{
				sprite.drawSprite(g2d, this);
			}

			levelObj.drawGame(g2d);
		}
		else
		{
			Font defaultFont = g2d.getFont();
			Font monaco = new Font("Monaco", Font.PLAIN, 24);
			g2d.setFont(monaco);
			int toAdd = g2d.getFontMetrics().stringWidth("Game Over") / 2;
			int toAdd2 = g2d.getFontMetrics().stringWidth("Press E to Restart") / 2;
			g2d.drawString("Game Over", 200 - toAdd, 250);
			g2d.drawString("Press E to Restart", 200 - toAdd2, 300);
			g2d.setFont(defaultFont);
			stop();
		}
	}
}
