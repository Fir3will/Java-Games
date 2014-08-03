package testing;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ItemTS
{
	public enum EnumRarity
	{
		FREQUENT, REGULAR, COMMON, UNCOMMON, SCARCE, RARE, IMPOSSIBLE, UNFRIGGINBELIEVABLE;
	}

	public static class ItemsTS
	{
		public static ItemTS[] allItems = new ItemTS[2056];

		public static ItemTS ironSword = new ItemTS("Iron Sword");
		public static ItemTS ironHelmet = new ItemTS("Iron Helmet");
		public static ItemTS ironChestplate = new ItemTS("Iron Chestplate");
		public static ItemTS ironLeggings = new ItemTS("Iron Leggings");
		public static ItemTS ironBoots = new ItemTS("Iron Boots");
		public static ItemTS ironGauntlets = new ItemTS("Iron Gauntlets");
		public static ItemTS ironShield = new ItemTS("Iron Shield");
		public static ItemTS healthPack = new ItemTS("Health Pack");

		public static void addItems()
		{
			ItemsTS.ironSword = new ItemTS("Iron Sword");
			ItemsTS.ironHelmet = new ItemTS("Iron Helmet");
			ItemsTS.ironChestplate = new ItemTS("Iron Chestplate");
			ItemsTS.ironLeggings = new ItemTS("Iron Leggings");
			ItemsTS.ironBoots = new ItemTS("Iron Boots");
			ItemsTS.ironGauntlets = new ItemTS("Iron Gauntlets");
			ItemsTS.ironShield = new ItemTS("Iron Shield");
			ItemsTS.healthPack = new ItemTS("Health Pack");
		}
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

	public int itemID;

	public String itemName;

	public Image icon;

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
