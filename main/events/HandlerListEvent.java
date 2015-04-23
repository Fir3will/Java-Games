package main.events;

import java.util.List;
import main.game.sprites.Collision.CollisionHandler;

public class HandlerListEvent extends Event
{
	public List<CollisionHandler> collisionHandlers;

	public HandlerListEvent(List<CollisionHandler> collisionHandlers)
	{
		this.collisionHandlers = collisionHandlers;
	}
}
