package customcode.vectest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;
import main.utils.helper.Utils;

public class Level implements Serializable
{
	private static final long serialVersionUID = 1L;

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
		spawnIn(getEnemiesPerRound());
	}

	private void spawnIn(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			new ZombieVT(main);
		}
	}

	private int getEnemiesPerRound()
	{
		return (wave == 1 ? 0 : 3) + Utils.toExponent(2, wave - 1);
	}

	public void drawGame(Graphics2D g2d)
	{
		g2d.setColor(Color.BLACK);
		Font defaultFont = g2d.getFont();
		if (waveBreak)
		{
			Font monaco = new Font("Monaco", Font.PLAIN, 12);
			g2d.setFont(monaco);
			int toAdd = g2d.getFontMetrics().stringWidth("Stock Up") / 2;
			g2d.drawString("Stock Up", 200 - toAdd, 530);
		}

		int toAdd = g2d.getFontMetrics().stringWidth("Round " + wave) / 2;
		g2d.drawString("Round " + wave, 200 - toAdd, 550);
		g2d.setFont(defaultFont);

		g2d.drawString("Wave Break Delay: " + waveBreakDelay + "/150", 5, 45);
		g2d.drawString("Enemies Killed: " + enemiesKilled + "/" + getEnemiesPerRound(), 5, 60);
	}
}
