package modhandler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import main.events.Event;
import main.events.EventException;
import main.events.EventHandler;
import main.events.LoadEvent;
import main.saving.CompoundTag;
import main.saving.DataTag;
import main.saving.Savable;
import modhandler.Initialization.State;

public class ModContainer
{
	private final Object mod;
	private final Savable saver;
	private final Object eventHandler;
	private final String modid;
	private final String version;
	private final String name;
	private boolean disabled;

	public ModContainer(Object mod)
	{
		this.mod = mod;
		modid = mod.getClass().getAnnotation(Mod.class).modid();
		version = mod.getClass().getAnnotation(Mod.class).version();
		name = mod.getClass().getAnnotation(Mod.class).name();
		saver = getSaver(mod);
		eventHandler = getEventHandler(mod);
		System.out.println("[Added new Mod] Class: " + mod.getClass().getName() + ", Name: " + name + ", Modid: " + modid + ", Version: " + version);
		Loader.addMod(this, mod);
		sendEvent(new LoadEvent(this));
	}

	private Object getEventHandler(Object mod)
	{
		try
		{
			String save = mod.getClass().getAnnotation(Mod.class).events();
			return save.equals("null") ? null : Class.forName(save).newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private Savable getSaver(Object mod)
	{
		try
		{
			String save = mod.getClass().getAnnotation(Mod.class).save();
			return (Savable) (save.equals("null") ? null : Class.forName(save).newInstance());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void initMethod(State state)
	{
		try
		{
			Method[] mets = mod.getClass().getMethods();
			for (Method met : mets)
			{
				if (met.isAnnotationPresent(Initialization.class) && met.getAnnotation(Initialization.class).value() == state)
				{
					met.invoke(mod);
				}
			}
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	public boolean disabled()
	{
		return disabled;
	}

	public String getModid()
	{
		return modid;
	}

	public String getVersion()
	{
		return version;
	}

	public String getName()
	{
		return name;
	}

	public boolean isDisabled()
	{
		return disabled;
	}

	public void sendEvent(Event event)
	{
		if (disabled()) return;

		try
		{
			if (eventHandler != null)
			{
				final Method[] methods = eventHandler.getClass().getMethods();

				for (final Method method : methods)
				{
					if (method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes()[0] == event.getClass())
					{
						method.invoke(eventHandler, event);
					}
				}
			}
		}
		catch (final IllegalArgumentException e)
		{
			new EventException("Method must have one argument and a " + event.getClass().getSimpleName() + " parameter", e).printStackTrace();
		}
		catch (final IllegalAccessException e)
		{
			System.err.println("Caught Exception: " + e.getMessage());
			System.err.println("Error Send Event: " + event.getClass().getName() + ", Modid: " + getModid());
			new EventException(e).printStackTrace();
		}
		catch (final InvocationTargetException e)
		{
			System.err.println("Caught Exception: " + e.getMessage());
			System.err.println("Error Send Event: " + event.getClass().getName() + ", Modid: " + getModid());
			new EventException(e).printStackTrace();
		}
	}

	public void switchModes()
	{
		disabled = !disabled;
	}

	@Override
	public String toString()
	{
		return getName() + "|" + getVersion();
	}

	public void startSaving()
	{
		if (saver != null)
		{
			final DataTag tag = new DataTag();
			saver.saveToTag(tag);
			DataTag.saveTo(new File(CompoundTag.PLAYER_FOLDER, getModid() + ".dat"), tag);
		}
	}

	public void startLoading()
	{
		if (saver != null)
		{
			final DataTag tag = DataTag.loadFrom(new File(CompoundTag.PLAYER_FOLDER, getModid() + ".dat"));
			saver.loadFromTag(tag);
		}
	}
}
