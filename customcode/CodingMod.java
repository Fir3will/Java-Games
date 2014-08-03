package customcode;

import modhandler.Mod;
import modhandler.SimpleMod;
import customcode.elements.Saver;

@Mod(modid = "Custom Language", version = "1.2", save = Saver.class)
public class CodingMod extends SimpleMod
{
	public static CodingMod instance;

	public CodingMod()
	{
		instance = this;
	}

	@Override
	public void preInit()
	{
		addEventHandler(CEventHandler.class);
	}

	@Override
	public void init()
	{}

	@Override
	public void postInit()
	{}
}
