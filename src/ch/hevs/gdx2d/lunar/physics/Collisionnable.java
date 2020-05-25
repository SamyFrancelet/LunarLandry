package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Interface for objects that represent obstacles for the {@link PhysicsSimulator}
 * @author P.-A. Mudry
 */
public interface Collisionnable {
	/**
	 * Callback used to notify the object that he was removed form the simulation
	 */
	void removedFromSim();

	/**
	 * When the {@link PhysicalObject} that implements this
	 * interfaces collides another object, this callback method
	 * is called
	 * 
	 * @param energy Energy of the collision
	 * @return true if the object has to be destroyed
	 */
	public boolean notifyCollision(int energy);
	
	/**
	 * Gives the bounding box of the object which is used for detecting 
	 * collisions. 
	 * 
	 * @return the bounding box
	 */
	public Rectangle getBoundingBox();	
}
