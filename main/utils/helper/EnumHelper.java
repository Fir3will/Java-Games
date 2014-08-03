package main.utils.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.utils.SaveType;
import modhandler.EnumGame;

public class EnumHelper
{
	private static Object reflectionFactory = null;
	private static Method newConstructorAccessor = null;
	private static Method newInstance = null;
	private static Method newFieldAccessor = null;
	private static Method fieldAccessorSet = null;
	private static boolean isSetup = false;

	private static Class<?>[][] commonTypes = {{SaveType.class}};

	public static EnumGame addGame(String gameName)
	{
		return addEnum(EnumGame.class, gameName);
	}

	public static SaveType addSaveType(String enumName)
	{
		return addEnum(SaveType.class, enumName);
	}

	private static void setup()
	{
		if (isSetup) return;

		try
		{
			Method getReflectionFactory = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory");
			reflectionFactory = getReflectionFactory.invoke(null);
			newConstructorAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newConstructorAccessor", Constructor.class);
			newInstance = Class.forName("sun.reflect.ConstructorAccessor").getDeclaredMethod("newInstance", Object[].class);
			newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", Field.class, boolean.class);
			fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", Object.class, Object.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		isSetup = true;
	}

	private static Object getConstructorAccessor(Class<?> enumClass, Class<?>[] additionalParameterTypes) throws Exception
	{
		Class<?>[] parameterTypes = new Class[additionalParameterTypes.length + 2];
		parameterTypes[0] = String.class;
		parameterTypes[1] = int.class;
		System.arraycopy(additionalParameterTypes, 0, parameterTypes, 2, additionalParameterTypes.length);
		return newConstructorAccessor.invoke(reflectionFactory, enumClass.getDeclaredConstructor(parameterTypes));
	}

	private static <T extends Enum<?>> T makeEnum(Class<T> enumClass, String value, int ordinal, Class<?>[] additionalTypes, Object[] additionalValues) throws Exception
	{
		Object[] parms = new Object[additionalValues.length + 2];
		parms[0] = value;
		parms[1] = Integer.valueOf(ordinal);
		System.arraycopy(additionalValues, 0, parms, 2, additionalValues.length);
		return enumClass.cast(newInstance.invoke(getConstructorAccessor(enumClass, additionalTypes), new Object[] {parms}));
	}

	private static void setFailsafeFieldValue(Field field, Object target, Object value) throws Exception
	{
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		Object fieldAccessor = newFieldAccessor.invoke(reflectionFactory, field, false);
		fieldAccessorSet.invoke(fieldAccessor, target, value);
	}

	private static void blankField(Class<?> enumClass, String fieldName) throws Exception
	{
		for (Field field : Class.class.getDeclaredFields())
		{
			if (field.getName().contains(fieldName))
			{
				field.setAccessible(true);
				setFailsafeFieldValue(field, enumClass, null);
				break;
			}
		}
	}

	private static void cleanEnumCache(Class<?> enumClass) throws Exception
	{
		blankField(enumClass, "enumConstantDirectory");
		blankField(enumClass, "enumConstants");
	}

	private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Object... paramValues)
	{
		setup();
		return addEnum(commonTypes, enumType, enumName, paramValues);
	}

	@SuppressWarnings("rawtypes")
	private static <T extends Enum<?>> T addEnum(Class[][] map, Class<T> enumType, String enumName, Object... paramValues)
	{
		for (Class[] lookup : map)
		{
			if (lookup[0] == enumType)
			{
				Class<?>[] paramTypes = new Class<?>[lookup.length - 1];
				if (paramTypes.length > 0)
				{
					System.arraycopy(lookup, 1, paramTypes, 0, paramTypes.length);
				}
				return addEnum(enumType, enumName, paramTypes, paramValues);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Enum<?>> T addEnum(Class<T> enumType, String enumName, Class<?>[] paramTypes, Object[] paramValues)
	{
		if (!isSetup)
		{
			setup();
		}

		Field valuesField = null;
		Field[] fields = enumType.getDeclaredFields();

		for (Field field : fields)
		{
			String name = field.getName();
			if (name.equals("$VALUES") || name.equals("ENUM$VALUES"))
			{
				valuesField = field;
				break;
			}
		}

		int flags = Modifier.PUBLIC | Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL | 0x1000;
		if (valuesField == null)
		{
			String valueType = String.format("[L%s;", enumType.getName().replace('.', '/'));

			for (Field field : fields)
			{
				if ((field.getModifiers() & flags) == flags && field.getType().getName().replace('.', '/').equals(valueType))
				{
					valuesField = field;
					break;
				}
			}
		}

		if (valuesField == null)
		{
			System.err.println("Could not find $VALUES field for enum: " + enumType.getName());
			System.err.println("Flags: " + String.format("%16s", Integer.toBinaryString(flags)).replace(' ', '0'));
			System.err.print("Fields:");

			for (Field field : fields)
			{
				String mods = String.format("%16s", Integer.toBinaryString(field.getModifiers())).replace(' ', '0');
				System.err.print("       " + mods + " " + field.getName() + ": " + field.getType().getName());
			}
			System.err.println();
			return null;
		}

		valuesField.setAccessible(true);

		try
		{
			T[] previousValues = (T[]) valuesField.get(enumType);
			List<T> values = new ArrayList<T>(Arrays.asList(previousValues));
			T newValue = makeEnum(enumType, enumName, values.size(), paramTypes, paramValues);
			values.add(newValue);
			setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(enumType, 0)));
			cleanEnumCache(enumType);

			return newValue;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	static
	{
		if (!isSetup)
		{
			setup();
		}
	}
}