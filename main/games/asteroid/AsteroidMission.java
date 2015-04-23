package main.games.asteroid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import main.game.Game;
import main.game.GameFrame;
import main.game.NewGame;
import main.game.sprites.SpriteList;
import main.saving.DataTag;
import main.utils.Keys;
import main.utils.helper.JavaHelp;
import main.utils.math.Vector2F;

@NewGame(name = "Asteroid Mission")
public class AsteroidMission extends Game
{
	private boolean debug;
	public boolean shiftPressed, buildingMode, redraw;
	public int speed = 0;
	private int mouseX, mouseY, mX, mY;
	private Point start;
	private double moveX, moveY;
	private double zoom = 1.0D;
	private Vector2F min, max;
	public SpriteList sprites;
	private List<SpaceOverlay> overlays;
	private SpriteSpaceObject megrez, arche;
	private SpriteRocket rocket;

	public AsteroidMission()
	{
		super(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 130);
	}

	@Override
	public void init()
	{
		setBackground(Color.WHITE);
		overlays = JavaHelp.newArrayList();
		addOverlays();
		sprites = new SpriteList(this);

		rocket = new SpriteRocket(this);
		rocket.setVelocity(0F, 9F);
		sprites.add(rocket);

		megrez = new SpriteSpaceObject("Megrez", 0F, 0F, 1000F, 2000F);
		sprites.add(megrez);

		arche = new SpriteSpaceObject("Arche", 20000F, 0F, 10F, 100F);
		arche.setVelocity(0F, 2.1F);
		sprites.add(arche);

		arche.influences.add(megrez);
		rocket.influences.addAll(Arrays.asList(sprites.getAllOf(SpriteSpaceObject.class)));
	}

	@Override
	public void updateGame(int ticks)
	{
		this.ticks--;
		for (int k = 0; k < speed; k++)
		{
			this.ticks++;
			sprites.updateSprites();
			sprites.runSpriteAIs();
		}
		for (SpaceOverlay overlay : overlays)
		{
			overlay.updateOverlay(speed);
		}
	}

	public Vector2F[] getAccelerationBetween(Vector2F pos1, Vector2F pos2, float mass1, float mass2)
	{
		Vector2F sub = pos2.subtract(pos1);
		float force = 100 * mass1 * mass2 / sub.lengthSquared();
		Vector2F direction = sub.normalize();
		Vector2F v1 = new Vector2F(direction.x * (force / mass1), direction.y * (force / mass1));
		Vector2F v2 = new Vector2F(direction.x * (force / mass2), direction.y * (force / mass2));
		return new Vector2F[] { v1, v2.negate() };
	}

	@Override
	public void drawGameScreen(Graphics2D g2d)
	{
		g2d.drawString("Speed: " + speed, 5, 15);
		g2d.drawString("Zoom: " + zoom, 5, 30);
		g2d.drawString("(" + mX + ", " + mY + ")", 5, 45);
		g2d.drawString("(" + (width / 2 + moveX) + ", " + (height / 2 + moveY) + ")", 5, 60);
		g2d.drawString("(" + (width / 2 + moveX) / 20 + ", " + (height / 2 + moveY) / 20 + ")", 5, 75);
		if (buildingMode)
		{
			AffineTransform b4 = g2d.getTransform();
			g2d.translate(width / 2, height / 2);
			for (int i = -width / 20; i < width / 20; i++)
			{
				g2d.drawLine(i * 20, -height, i * 20, height);
			}
			for (int i = -height / 20; i < height / 20; i++)
			{
				g2d.drawLine(-width, i * 20, width, i * 20);
			}
			g2d.clearRect(-60, -120, 120, 240);
			g2d.setColor(Color.RED);
			g2d.drawRect(-60, -120, 120, 240);
			g2d.drawRect(-59, -119, 118, 238);
			g2d.setColor(Color.DARK_GRAY);
			g2d.fillOval(-60, -60, 120, 120);
			g2d.setColor(Color.GREEN);
			g2d.drawRect(mX * 20, mY * 20, 20, 20);
			g2d.setColor(Color.BLACK);
			g2d.setTransform(b4);
			g2d.clearRect(0, height / 2 - height / 3, width / 5, 500);
			g2d.drawRect(0, height / 2 - height / 3, width / 5, 500);
			g2d.drawRect(-1, height / 2 - height / 3 - 1, width / 5, 502);
		}
		else
		{
			if (debug)
			{
				g2d.setColor(Color.RED);
				g2d.drawRect(0, 0, width - 1, getSize().height - 1);
				g2d.drawRect(1, 1, width - 3, getSize().height - 3);
				g2d.setColor(Color.BLACK);
			}
			AffineTransform b4 = g2d.getTransform();
			g2d.translate(width / 2 + moveX, height / 2 + moveY);
			g2d.scale(zoom / 20, zoom / 20);
			sprites.drawSprites(g2d);
			g2d.setTransform(b4);
			g2d.setColor(Color.BLACK);
			//g2d.clearRect(3, height - 200, width - 6, 90);
			//g2d.drawRect(3, height - 200, width - 6, 90);
		}
		for (SpaceOverlay overlay : overlays)
		{
			Color c = g2d.getColor();
			overlay.drawOverlay(g2d, mouseX, mouseY, overlay.inBounds(new Point(mouseX, mouseY)));
			g2d.setColor(c);
		}
	}

	@Override
	public void keyPressed(Keys key)
	{
		if (buildingMode) return;
		if (key == Keys.KEY_SHIFT)
		{
			shiftPressed = true;
		}
		if (key == Keys.KEY_F2)
		{
			debug = !debug;
		}
		rocket.keyPressed(key);
	}

	@Override
	public void keyReleased(Keys key)
	{
		if (buildingMode) return;
		if (debug)
		{
			if (key == Keys.KEY_RIGHT)
			{
				speed += shiftPressed ? 10 : 1;
			}
			if (key == Keys.KEY_LEFT)
			{
				speed -= shiftPressed ? 10 : 1;
			}
		}
		if (key == Keys.KEY_SHIFT)
		{
			shiftPressed = false;
		}
		rocket.keyReleased(key);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (buildingMode) return;
		boolean less = e.getWheelRotation() < 0;
		zoom += less ? -zoom / 10 : zoom / 10;
		zoom = zoom <= 0 ? 0.01D : zoom;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		start = e.getPoint();
		for (SpaceOverlay overlay : overlays)
		{
			overlay.buttonClicked(start.x, start.y, overlay.inBounds(new Point(mouseX, mouseY)));
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getPoint().x;
		mouseY = e.getPoint().y;
		mX = e.getX() / 20 - width / 40;
		mY = e.getY() / 20 - height / 40;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		moveX -= start.getX() - e.getPoint().getX();
		moveY -= start.getY() - e.getPoint().getY();
		start = e.getPoint();
	}

	public void addOverlays()
	{
		overlays.add(new BuildRocketOverlay(this, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 190));
	}

	@Override
	public void loadGameFromTag(DataTag tag)
	{
		//moveX = tag.getDouble("Move X", moveX);
		//moveY = tag.getDouble("Move Y", moveY);
		//zoom = tag.getDouble("Zoom", 1.0D);
	}

	@Override
	public void saveGameToTag(DataTag tag)
	{
		//tag.setDouble("Move X", moveX);
		//tag.setDouble("Move Y", moveY);
		//tag.setDouble("Zoom", zoom);
	}

	@Override
	public void setupFrame(final GameFrame gameFrame)
	{
		gameFrame.setResizable(true);
		gameFrame.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
		//gameFrame.setExtendedState(gameFrame.getExtendedState() | Frame.MAXIMIZED_BOTH);
		gameFrame.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				width = gameFrame.getWidth();
				height = gameFrame.getHeight();
			}
		});
	}

	private static final long serialVersionUID = 1L;
}
