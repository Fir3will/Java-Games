package main;

import javax.swing.JFrame;

public class Vars
{
	public static int missileChosen = 1;
	public static String[] cheats = new String[] { "ToTheMax", "b", "c", "D", "e", "f" };
	public static String[] cheatsEnabled = new String[Vars.cheats.length];
	// public static SaveFile save;
	public static Save save;
	public static JFrame login = new JFrame("Login");
	public static JFrame frame;
	public static String HOME_DIR = System.getProperty("user.home") + "/JavaGamesCache/";
	public static String SAVE_DIR = Vars.HOME_DIR + "saves/";
	public static String NATIVES_DIR = Vars.HOME_DIR + "natives/";
	public static String debugSign = "";
	public static int saveAmount;
	public static boolean edited = false;
}
