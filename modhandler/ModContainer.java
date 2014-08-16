package modhandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import main.utils.CompoundTag;
import main.utils.SaveType;

public class ModContainer
{
	public static ModContainer addMod(SimpleMod mod, String modid)
	{
		return new ModContainer(mod, mod.getClass(), modid);
	}

	private ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
	private Class<?> baseClass;
	private SimpleMod mod;
	private IEventHandler proxy;
	private String modid;
	private InfoFile infoFile;
	private boolean disabled;

	public ModContainer(SimpleMod mod, Class<? extends SimpleMod> baseClass, String modid)
	{
		System.out.println("[Added new Mod] Class: " + baseClass.getName() + ", Modid: " + modid);
		Importer.addMod(this, mod);
		this.mod = mod;
		this.baseClass = baseClass;
		this.modid = modid;
		addClass(this.baseClass);
	}

	public void addClass(Class<?> clazz)
	{
		for (int i = 0; i < classes.size(); i++)
		{
			if (!classes.get(i).getName().equals(clazz.getName()))
			{
				classes.add(clazz);
			}
		}
	}

	public void addModInfo(InfoFile infoFile)
	{
		this.infoFile = infoFile;
	}

	public void addModProxy(IEventHandler clazz)
	{
		proxy = clazz;
		initiateProxyMethod("init");
	}

	public boolean disabled()
	{
		return disabled;
	}

	public InfoFile getInfoFile()
	{
		return infoFile;
	}

	public String getModid()
	{
		return modid;
	}

	public void initiateProxyMethod(String methodName, Object... parameters)
	{
		if (disabled()) return;

		try
		{
			Method[] methods = proxy.getClass().getMethods();

			for (int i = 0; i < methods.length; i++)
			{
				if (methodName == methods[i].getName())
				{
					methods[i].invoke(proxy, parameters);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("Error Loading Proxy Method: " + methodName);
			e.printStackTrace();
		}
	}

	public void switchModes()
	{
		disabled = !disabled;
	}

	@Override
	public String toString()
	{
		return mod.toString();
	}

	public void startSaving(SaveType type)
	{
		if (mod.getSave() != null)
		{
			CompoundTag tag = new CompoundTag(mod);
			mod.getSave().writeToTag(tag);
		}
	}

	public void startLoading(SaveType type)
	{
		if (mod.getSave() != null)
		{
			CompoundTag tag = new CompoundTag(mod);
			mod.getSave().readFromTag(tag);
		}
	}
}
