package main.games.asteroid;

import main.games.asteroid.RocketPart.PartType;
import main.saving.DataTag;

public class RocketPremadeParts
{
	public final static DataTag ENGINE_EE_279;

	public static RocketEngine getEE_279Engine()
	{
		return new RocketEngine(ENGINE_EE_279);
	}

	static
	{
		ENGINE_EE_279 = new DataTag();
		ENGINE_EE_279.setString("Name", "EE-279");
		ENGINE_EE_279.setInteger("Part Type", PartType.ENGINE.ordinal());
		ENGINE_EE_279.setFloat("Drag", 0.272F);
		ENGINE_EE_279.setFloat("Mass", 54.8F);
		ENGINE_EE_279.setInteger("Width", 40);
		ENGINE_EE_279.setInteger("Height", 60);
		ENGINE_EE_279.setInteger("Points", 4);
		ENGINE_EE_279.setIntegerArray("Point-0", new int[] { -20, -30 });
		ENGINE_EE_279.setIntegerArray("Point-1", new int[] { -20, 30 });
		ENGINE_EE_279.setIntegerArray("Point-2", new int[] { 20, 30 });
		ENGINE_EE_279.setIntegerArray("Point-3", new int[] { 20, -30 });
		ENGINE_EE_279.setString("Info", "Developed by Jaguar(c) in 2017. Built to act as a balance between the center of mass and also thrust.");
		ENGINE_EE_279.setFloat("Thrust", 93.34F);
		ENGINE_EE_279.setBoolean("Controllable", true);
	}
}
