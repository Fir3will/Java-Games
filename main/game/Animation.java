package main.game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import main.utils.helper.JavaHelp;

public class Animation
{
	private final List<ImageIcon> icons;
	private int ticks, frame;
	private final int delay, maxFrame;
	private final String fileName;
	private boolean looping, reverse, restart;
	private Updater updater;

	public Animation(String fileName, int delay, int maxFrame)
	{
		this.fileName = fileName.replaceAll(".png", "");
		this.delay = delay;
		this.maxFrame = maxFrame - 1;
		icons = JavaHelp.newArrayList();
		for (int i = 0; i < maxFrame; i++)
		{
			icons.add(new ImageIcon(Animation.class.getResource(fileName + "_" + i + ".png")));
		}
	}

	public Animation setLooping(boolean looping)
	{
		this.looping = looping;
		reverse = !looping;
		return this;
	}

	public Animation setReverse(boolean reverse)
	{
		this.reverse = reverse;
		looping = !reverse;
		return this;
	}

	public Animation setUpdater(Updater updater)
	{
		this.updater = updater;
		return this;
	}

	public Animation setFrame(int frame)
	{
		this.frame = frame;
		return this;
	}

	public Animation setTicks(int ticks)
	{
		this.ticks = ticks;
		return this;
	}

	public Animation setRestart(boolean restart)
	{
		this.restart = restart;
		return this;
	}

	public void drawAnim(Graphics g, int x, int y, boolean update)
	{
		if (!update)
		{
			ticks--;
		}
		g.drawImage(nextImage(), x, y, null);
	}

	private Image nextImage()
	{
		ticks++;
		if (delay == ticks)
		{
			ticks = 0;
			frame += amountToChange();
		}
		return icons.get(frame).getImage();
	}

	private int amountToChange()
	{
		if (updater != null) return updater.getAmount(frame);
		else
		{
			if (!reverse && !looping && frame < maxFrame) return 1;
			if (looping)
			{
				if (frame >= maxFrame) return -frame;
				else return 1;
			}
			if (reverse)
			{
				if (frame >= maxFrame)
				{
					restart = true;
				}
				else if (frame <= 0)
				{
					restart = false;
				}
				return restart ? -1 : 1;
			}
		}
		return 0;
	}

	public Animation setupClone()
	{
		return new Animation(fileName, delay, maxFrame + 1).setLooping(looping).setReverse(reverse);
	}

	public Animation deepClone()
	{
		return new Animation(fileName, delay, maxFrame + 1).setFrame(frame).setLooping(looping).setRestart(restart).setReverse(reverse).setTicks(ticks).setUpdater(updater);
	}

	public boolean deepEquals(Object obj)
	{
		if (obj instanceof Animation) return ((Animation) obj).deepHashCode() == deepHashCode();
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Animation) return ((Animation) obj).hashCode() == hashCode();
		return false;
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(new int[] { delay, maxFrame }) + Arrays.hashCode(new boolean[] { looping, reverse }) + fileName.hashCode();
	}

	public int deepHashCode()
	{
		return Arrays.hashCode(new int[] { delay, maxFrame, ticks, frame }) + Arrays.hashCode(new boolean[] { looping, reverse, restart }) + fileName.hashCode();
	}

	public static interface Updater
	{
		public int getAmount(int count);
	}
}
