package modhandler;

import java.util.ArrayList;
import main.utils.Main;

public class Importer
{
	public static ArrayList<SimpleMod> mods = new ArrayList<SimpleMod>();

	public static ArrayList<String> modids = new ArrayList<String>();

	public static ArrayList<ModContainer> modContainers = new ArrayList<ModContainer>();

	public static void addMod(ModContainer mc, SimpleMod mod)
	{
		Importer.mods.add(mod);
		Importer.modids.add(mod.getModid());
		Importer.modContainers.add(mc);
	}

	public static void throwGameEvent(EnumGame game)
	{
		for (int i = 0; i < Main.mods.length; i++)
		{
			Main.mods[i].onGameStartUp(game);
		}
	}
}
