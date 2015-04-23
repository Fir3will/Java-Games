package modhandler;

import java.util.Collections;
import java.util.List;
import main.events.Event;
import main.utils.helper.JavaHelp;

public class Loader
{
	private static boolean isVanilla = true;
	private static final List<Object> mods;
	private static final List<String> modids;
	private static final List<ModContainer> modContainers;

	static
	{
		mods = JavaHelp.newArrayList();
		modids = JavaHelp.newArrayList();
		modContainers = JavaHelp.newArrayList();
	}

	public static List<Object> getModList()
	{
		return Collections.unmodifiableList(mods);
	}

	public static List<String> getModidList()
	{
		return Collections.unmodifiableList(Loader.modids);
	}

	public static List<ModContainer> getContainerList()
	{
		return Collections.unmodifiableList(Loader.modContainers);
	}

	public static boolean isModLoaded(String modid)
	{
		return modids.contains(modid);
	}

	public static void addEventToAll(Event event)
	{
		for (int i = 0; i < Loader.modContainers.size(); i++)
		{
			modContainers.get(i).sendEvent(event);
		}
	}

	public static void addMod(ModContainer mc, Object mod)
	{
		mods.add(mod);
		modids.add(mc.getModid());
		modContainers.add(mc);
		isVanilla = false;
	}

	public static ModContainer getContainerFor(Object mod)
	{
		int i = mods.indexOf(mod);
		return i < 0 ? null : modContainers.get(i);
	}

	public static boolean isVanilla()
	{
		return isVanilla;
	}
}
