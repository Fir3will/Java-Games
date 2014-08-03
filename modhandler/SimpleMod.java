package modhandler;

import main.Login;
import main.utils.SaveManager;
import main.utils.SaveType;
import main.utils.helper.EnumHelper;

public abstract class SimpleMod
{
	private ModContainer mc;
	public SaveType CUSTOM = EnumHelper.addSaveType(getModid().toUpperCase());

	protected SimpleMod()
	{}

	protected final void addEventHandler(Class<? extends IEventHandler> clazz)
	{
		try
		{
			mc.addModProxy(clazz.newInstance());
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			System.err.println("Error Registering/Loading Event Handler: " + clazz.getSimpleName());
			e.printStackTrace();
		}
	}

	/**
	 * Really useless at the moment...
	 * 
	 * @param clazz The Important Class
	 */
	protected final void addImportantClass(Class<?> clazz)
	{
		mc.addClass(clazz);
	}

	public final String getModid()
	{
		return this.getClass().getAnnotation(Mod.class).modid();
	}

	public final SaveManager getSave()
	{
		SaveManager obj = null;

		try
		{
			if (this.getClass().getAnnotation(Mod.class).save() != SaveManager.class)
			{
				obj = this.getClass().getAnnotation(Mod.class).save().newInstance();
			}
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			System.err.println("Error loading ISaveManager For: " + this.getClass().getName());
			e.printStackTrace();
		}

		return obj;
	}

	public final String getVersion()
	{
		return this.getClass().getAnnotation(Mod.class).version();
	}

	/**
	 * Anything you want to initiate in the mod 
	 * should happen here. This is called right 
	 * after startup!
	 */
	public abstract void init();

	public final boolean isDisabled()
	{
		return mc.disabled();
	}

	/**
	 * On every default game start, this method
	 *  will be called with the respective 
	 *  {@link EnumGame}
	 */
	public void onGameStartUp(EnumGame game)
	{

	}

	/**
	 * Anything you want to post-initiate in the
	 *  mod should happen here. This is called 
	 *  right after the player logs in!
	 */
	public abstract void postInit();

	/**
	 * Anything you want to pre-initiate in the
	 *  mod should happen here. This is called
	 *   right after your mod is discovered!
	 */
	public abstract void preInit();

	public final void switchModes()
	{
		mc.switchModes();
	}

	@Override
	public String toString()
	{
		return getModid();
	}

	public final void addContainer(ModContainer mc)
	{
		this.mc = mc;
	}

	public void startSaving()
	{
		if (Login.loggedIn)
		{
			mc.startSaving(CUSTOM);
		}
	}

	public void startLoading()
	{
		if (Login.loggedIn)
		{
			mc.startLoading(CUSTOM);
		}
	}
}
