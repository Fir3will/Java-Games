package main;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import testing.ItemTS;

public class Vars
{
	public static int missileChosen = 1;
	public static String[] cheats = new String[] {"ToTheMax", "b", "c", "D", "e", "f"};
	public static String[] cheatsEnabled = new String[Vars.cheats.length];
	public static SaveFile save;
	public static JFrame login = new JFrame("Login");
	public static JFrame frame;
	public static ArrayList<JFrame> frames;
	public static String extension = System.getProperty("user.home") + "/JavaGamesCache/";
	public static String debugSign = "";
	public static int saveAmount;
	public static boolean edited = false;

	/*
	 * SAVE VARIABLES- START
	 */
	public static String playerName;
	public static int siMaxRound = 2;
	public static String difficultyLevel = "Normal";
	public static String saveFilePassword;
	public static Color playerColor = Color.LIGHT_GRAY;
	public static int snMaxScore = 3;
	public static int tnMaxScore = 1;
	public static int ppMaxScore = 1;
	public static int tsCoins;
	public static int tsX = 400;
	public static int tsY = 400;
	public static int tsHealth = 50;
	public static int tsAttackXP = 1;
	public static int tsDefenceXP = 1;
	public static ItemTS[] tsPlayerItems = new ItemTS[8];
	/*
	 * SAVE VARIABLES- END
	 */
}
