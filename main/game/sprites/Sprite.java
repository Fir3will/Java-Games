package main.game.sprites;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Map;
import java.util.UUID;
import javax.swing.ImageIcon;
import main.events.Event;
import main.events.SpriteEvent;
import main.events.SpriteEvent.KillSpriteEvent;
import main.events.SpriteEvent.LoadSpriteEvent;
import main.events.SpriteEvent.SaveSpriteEvent;
import main.events.SpriteEvent.UpdateSpriteEvent;
import main.game.Game;
import main.game.sprites.tasks.TaskMap;
import main.saving.DataTag;
import main.saving.Savable;
import main.utils.helper.JavaHelp;
import main.utils.math.Rotation;
import main.utils.math.Vector2F;
import modhandler.Loader;

public abstract class Sprite implements Savable
{
	private boolean isDead;
	protected UUID uuid;
	protected final PathHelper pathHelper;
	protected final TaskMap tasks;
	protected final long entityID;
	protected int terminalVelocity;
	private float health, maxHealth;
	protected Game game;
	public final Control control;
	public DataTag spriteTag;
	protected SpriteBounds bounds;

	public Sprite()
	{
		pathHelper = new PathHelper(this);
		entityID = ID++;
		tasks = new TaskMap(this);
		control = new Control();
		uuid = UUID.randomUUID();
		setTasks();
		Sprite.sprites.put(uuid, this);
		sendEvent(new SpriteEvent(this));
		terminalVelocity = 65;
		spriteTag = new DataTag();
	}

	public abstract void drawSprite(Graphics2D g2d);

	public void draw(Graphics2D g2d)
	{
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(getRotation().getDegrees()), getLocation().x + getWidth() / 2, getLocation().y + getHeight() / 2);
		drawSprite(g2d);
		g2d.setTransform(old);
	}

	public void updateSprite()
	{}

	public void setTasks()
	{}

	public PathHelper getPathHelper()
	{
		return pathHelper;
	}

	protected boolean allowEvent(Class<? extends Event> eventClass)
	{
		return true;
	}

	public void setLocation(Vector2F vec)
	{
		getLocation().set(vec);
	}

	public void setScale(Vector2F vec)
	{
		getScale().set(vec);
	}

	public void setVelocity(Vector2F vec)
	{
		getVelocity().set(vec);
	}

	public void setAcceleration(Vector2F vec)
	{
		getAcceleration().set(vec);
	}

	public void setLocation(float x, float y)
	{
		getLocation().set(x, y);
	}

	public void setScale(float x, float y)
	{
		getScale().set(x, y);
	}

	public void setVelocity(float x, float y)
	{
		getVelocity().set(x, y);
	}

	public void setAcceleration(float x, float y)
	{
		getAcceleration().set(x, y);
	}

	public void setRotation(Rotation rot)
	{
		getRotation().set(rot);
	}

	public void setRotation(int rotation)
	{
		getRotation().set(new Rotation(rotation));
	}

	public Vector2F getLocation()
	{
		return control.getLocation();
	}

	public Vector2F getScale()
	{
		return control.getScale();
	}

	public Vector2F getVelocity()
	{
		return control.getVelocity();
	}

	public Vector2F getAcceleration()
	{
		return control.getAcceleration();
	}

	public Rotation getRotation()
	{
		return control.getRotation();
	}

	public SpriteBounds getBounds()
	{
		return bounds;
	}

	public boolean intersects(Sprite s)
	{
		return getBounds().intersects(s.getBounds());
	}

	public void runSpriteAI()
	{
		if (getVelocity().y < terminalVelocity)
		{
			getVelocity().addLocal(getAcceleration());
		}
		getBounds().x = getX();
		getBounds().y = getY();
		getLocation().addLocal(control.getVelocity());
		pathHelper.updatePath();
		tasks.updateAITasks();
		sendEvent(new UpdateSpriteEvent(this));
	}

	public void setDead(boolean isDead)
	{
		KillSpriteEvent e = new KillSpriteEvent(this, isDead);
		sendEvent(e);
		this.isDead = e.isApproved() ? isDead : e.isDead;
	}

	public boolean isDead()
	{
		return isDead;
	}

	public long getID()
	{
		return entityID;
	}

	public float getX()
	{
		return getLocation().x;
	}

	public float getY()
	{
		return getLocation().y;
	}

	public float getWidth()
	{
		return getScale().x;
	}

	public float getHeight()
	{
		return getScale().y;
	}

	public float getXVelocity()
	{
		return getVelocity().x;
	}

	public float getYVelocity()
	{
		return getVelocity().y;
	}

	public float getDistanceTo(Vector2F vec)
	{
		return vec.distance(getLocation());
	}

	public float getDistanceTo(Sprite s)
	{
		return getDistanceTo(s.getLocation());
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public float getHealth()
	{
		return health;
	}

	public float getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth)
	{
		this.maxHealth = maxHealth;
		setHealth(health);
	}

	public void setHealth(float health)
	{
		this.health = Math.min(health, maxHealth);
	}

	public void collidedWith(Sprite sprite)
	{}

	public <T> T getClosestSprite(Class<? extends T> clazz, Sprite... sprites)
	{
		T sprite = null;
		float distance = Float.MAX_VALUE;

		for (final Sprite s : sprites)
		{
			if (s.getClass().isAssignableFrom(clazz) && getDistanceTo(s.getLocation()) < distance)
			{
				distance = getDistanceTo(s.getLocation());
				sprite = clazz.cast(s);
			}
		}

		return sprite;
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
		uuid = new UUID(tag.getLong("Most Sig Bits", 0L), tag.getLong("Least Sig Bits", 0L));
		isDead = tag.getBoolean("Is Dead", false);
		setHealth(tag.getFloat("Health", getMaxHealth()));
		setVelocity(new Vector2F(tag.getFloat("Velocity X", 0.0F), tag.getFloat("Velocity Y", 0.0F)));
		setLocation(new Vector2F(tag.getFloat("Pos X", 0.0F), tag.getFloat("Pos Y", 0.0F)));
		setRotation(new Rotation(tag.getFloat("Rotation", 0.0F)));
		spriteTag = tag.getTag("Sprite Tag");
		pathHelper.loadFromTag(tag);
		tasks.loadTasks(tag);

		sendEvent(new LoadSpriteEvent(this, tag));
	}

	@Override
	public void saveToTag(DataTag tag)
	{
		tag.setLong("Most Sig Bits", uuid.getMostSignificantBits());
		tag.setLong("Least Sig Bits", uuid.getLeastSignificantBits());
		tag.setBoolean("Is Dead", isDead);
		tag.setFloat("Health", getHealth());
		tag.setFloat("Velocity X", getXVelocity());
		tag.setFloat("Velocity Y", getYVelocity());
		tag.setFloat("Pos X", getX());
		tag.setFloat("Pos Y", getY());
		tag.setFloat("Rotation", getRotation().getDegrees());
		tag.setTag("Sprite Tag", spriteTag);
		pathHelper.saveToTag(tag);
		tasks.saveTasks(tag);

		sendEvent(new SaveSpriteEvent(this, tag));
	}

	public void sendEvent(Event event)
	{
		if (allowEvent(event.getClass()))
		{
			Loader.addEventToAll(event);
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		return this == obj;
	}

	@Override
	public int hashCode()
	{
		return uuid.hashCode();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "|" + getLocation();
	}

	public static Sprite getSpriteFromUUID(UUID uuid)
	{
		return sprites.get(uuid);
	}

	public static ImageIcon getIcon(String fileName)
	{
		return new ImageIcon(Sprite.class.getResource("/images/" + fileName));
	}

	private static final Map<UUID, Sprite> sprites = JavaHelp.newHashMap();
	private static long ID;
}
