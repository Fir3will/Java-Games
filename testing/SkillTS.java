package testing;

import java.io.Serializable;
import main.utils.helper.Sound;
import main.utils.helper.Utils;

public class SkillTS implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static enum SkillsTS
	{
		ATTACK(1, "Attack", true), DEFENCE(2, "Defence", true);

		private boolean isCombatSkill;
		private String skillName = "[NO-NAME]";
		private int skillID = 0;

		SkillsTS(int skillID, String skillName, boolean isCombatSkill)
		{
			this.isCombatSkill = isCombatSkill;
			this.skillName = skillName;
			this.skillID = skillID;
		}

		public int getSkillID()
		{
			return skillID;
		}

		public String getSkillName()
		{
			return skillName;
		}

		public boolean isCombatSkill()
		{
			return isCombatSkill;
		}
	}

	public static int getXPForLevel(int level)
	{
		return Utils.toExponent(level, 4);
	}

	private String skillName;
	private int skillID, xp = 1, level = 1;
	private boolean isCombatSkill;

	public SkillTS(SkillsTS skill)
	{
		skillName = skill.getSkillName();
		skillID = skill.getSkillID();
		isCombatSkill = skill.isCombatSkill();
	}

	public void addXP(int xp)
	{
		if (getXPForLevel(level + 1) > this.xp + xp)
		{
			this.xp += xp;
		}
		else
		{
			Sound.playSound(Sound.LEVEL_UP, false, 0.0F);
			level += 1;
			addXP(xp);
		}
	}

	public int getID()
	{
		return skillID;
	}

	public int getLevel()
	{
		return level;
	}

	public String getName()
	{
		return skillName;
	}

	public int getXP()
	{
		return xp;
	}

	public boolean isCombatSkill()
	{
		return isCombatSkill;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	@Override
	public String toString()
	{
		return getName();
	}
}
