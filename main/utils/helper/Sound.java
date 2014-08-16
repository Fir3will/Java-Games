package main.utils.helper;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound
{
	public static final String EXPLOSION = "/sounds/explosion.wav";
	public static final String ZOMBIE_MOAN = "/sounds/zombie_moan.wav";
	public static final String GUNSHOT = "/sounds/gunshot.wav";
	public static final String FLESH_SHOT = "/sounds/flesh_shot.wav";
	public static final String SWORD_SWIPE = "/sounds/sword_swipe.wav";
	public static final String SWORD_CLASH = "/sounds/sword_clash.wav";
	public static final String GUN_RELOAD = "/sounds/gun_reload.wav";
	public static final String LEVEL_UP = "/sounds/level_up.wav";
	public static final String BUTTON_CLICK = "/sounds/button_click.wav";

	public static synchronized void playSound(final String fileName, final boolean loop, final float volumeModifier)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/sounds/" + fileName));
					clip.open(inputStream);
					if (loop)
					{
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					}
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(volumeModifier);
					clip.start();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
