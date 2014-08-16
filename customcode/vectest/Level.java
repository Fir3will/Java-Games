package customcode.vectest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Level
{
	private final TestFrame main;
	public boolean waveBreak = true;
	public int enemiesKilled, wave = 1, waveBreakDelay;

	public Level(TestFrame main)
	{
		this.main = main;
	}

	public void run()
	{
		if (waveBreak)
		{
			waveBreakDelay++;

			if (waveBreakDelay >= 150)
			{
				waveBreak = false;
				waveBreakDelay = 0;
				setupNextLevel();
			}
		}
		else
		{
			if (enemiesKilled >= getEnemiesPerRound())
			{
				enemiesKilled = 0;
				wave++;
				waveBreak = true;
			}
		}
	}

	private void setupNextLevel()
	{
		for (int i = 0; i < getEnemiesPerRound(); i++)
		{
			new ZombieVT(main);
		}
	}

	private int getEnemiesPerRound()
	{
		return wave * 2;
	}

	public void drawGame(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		Font defaultFont = g2d.getFont();
		Font monaco = new Font("Monaco", Font.PLAIN, 12);
		g2d.setFont(monaco);

		if (waveBreak)
		{
			int toAdd = g2d.getFontMetrics().stringWidth("Stock Up") / 2;
			g2d.drawString("Stock Up", 200 - toAdd, 530);
		}

		int toAdd = g2d.getFontMetrics().stringWidth("Round " + wave) / 2;
		g2d.drawString("Round " + wave, 200 - toAdd, 550);
		g2d.setFont(defaultFont);

		g2d.setColor(Color.BLUE);
		g2d.drawString("Zombies Left: " + (getEnemiesPerRound() - enemiesKilled), 5, 15);
	}
}
