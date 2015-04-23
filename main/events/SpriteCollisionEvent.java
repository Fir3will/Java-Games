package main.events;

import main.game.sprites.Collision;

public class SpriteCollisionEvent extends Event
{
	public Collision collision;

	public SpriteCollisionEvent(Collision collision)
	{
		this.collision = collision;
	}
}
