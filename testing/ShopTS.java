package testing;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class ShopTS extends SpriteTS
{
	private static final long serialVersionUID = 1L;
	private static int counter = 1;

	private static boolean openShop(int shopID)
	{
		ShopTS.openShopDelay++;

		if (ShopTS.openShopDelay >= 500) return false;

		return true;
	}

	public static void processKeyEvent(int key, int shopID)
	{
		if (key == KeyEvent.VK_K)
		{
			System.out.println("Activated KeyListener for Shop: " + shopID);
		}
	}

	private int shopID = 0;

	private static int openShopDelay = 0;

	public static boolean displayShop(Graphics2D g2d, int shopID)
	{
		g2d.drawString("Shop Opened: " + shopID, 45, 360);
		return openShop(shopID);
	}

	public ShopTS(int X, int Y)
	{
		super(X, Y, "/Images/Testing/shop.png");
		shopID = ShopTS.counter++;
	}

	@Override
	public void drawSprite(Graphics2D g2d, ImageObserver obs)
	{
		g2d.drawImage(getImage().getImage(), x, y, obs);
	}

	public int getShopID()
	{
		return shopID;
	}

	@Override
	public void run()
	{

	}
}
