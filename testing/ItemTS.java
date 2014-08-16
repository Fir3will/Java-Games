package testing;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class ItemTS implements Serializable
{
	private static final long serialVersionUID = 1L;

	public enum EnumRarity
	{
		FREQUENT, REGULAR, COMMON, UNCOMMON, SCARCE, RARE, IMPOSSIBLE, UNFRIGGINBELIEVABLE;
	}

	public static class ItemsTS
	{
		public static ItemTS[] allItems = new ItemTS[2056];

		public static final ItemTS ironSword = new ItemTS("Iron Sword");
		public static final ItemTS ironHelmet = new ItemTS("Iron Helmet");
		public static final ItemTS ironChestplate = new ItemTS("Iron Chestplate");
		public static final ItemTS ironLeggings = new ItemTS("Iron Leggings");
		public static final ItemTS ironBoots = new ItemTS("Iron Boots");
		public static final ItemTS ironGauntlets = new ItemTS("Iron Gauntlets");
		public static final ItemTS ironShield = new ItemTS("Iron Shield");
		public static final ItemTS healthPack = new ItemTS("Health Pack");
	}

	private static int counter = 1;

	public static ItemTS getIDItem(int itemId)
	{
		return ItemsTS.allItems[itemId];
	}

	public static ItemTS getNameItem(String itemname)
	{
		for (int i = 0; i < ItemsTS.allItems.length; i++)
		{
			if (ItemsTS.allItems[i].itemName == itemname) return ItemsTS.allItems[i];
		}
		return null;
	}

	public int itemID = 0;
	public String itemName;
	public transient Image icon;

	public ItemTS(String itemname)
	{
		itemID = ItemTS.counter++;
		itemName = itemname;

		ItemsTS.allItems[itemID] = this;
		ImageIcon ii = new ImageIcon(this.getClass().getResource("/Images/Testing/shop.png"));
		icon = ii.getImage();
	}

	public boolean activatedItem(PlayerTS player, int itemId)
	{
		System.out.println("Item Activated: " + ItemTS.getIDItem(itemID).itemName);

		if (itemId == ItemsTS.healthPack.itemID)
		{
			player.setHealth(player.getHealth() + 3 > player.getMaxHealth() ? player.getMaxHealth() : player.getHealth() + 3);
			return true;
		}
		return false;
	}
}
