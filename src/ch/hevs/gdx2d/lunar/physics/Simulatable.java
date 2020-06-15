package ch.hevs.gdx2d.lunar.physics;

/**
 * Interface for objects that can be simulated via the {@link PhysicsSimulator}.
 * 
 * @author P.-A. Mudry
 */
public interface Simulatable {

	/**
	 * Notify implementer that a simulation step has been performed
	 */
	void step();
}
