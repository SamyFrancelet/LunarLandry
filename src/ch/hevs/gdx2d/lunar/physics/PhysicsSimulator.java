package ch.hevs.gdx2d.lunar.physics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A simple p simulator for the inf1 project.
 */
public class PhysicsSimulator {

	/**
	 * This represents the borders of the simulated area (for collisions)
	 */
	double width;
	double height;

	private final boolean VERBOSE_PHYSICS = false;

	/**
	 * The objects that require p simulation (objects that move)
	 */
	private ArrayList<Simulatable> sim_objects;

	/**
	 * @param width  The width of the space for the p simulation
	 * @param height The height of the space for the p simulation
	 */
	public PhysicsSimulator(double width, double height) {
		sim_objects = new ArrayList<Simulatable>();
		this.width = width;
		this.height = height;
	}

	/**
	 * Adds a new object to the simulation framework
	 * 
	 * @param o The object to be added
	 */
	public void addSimulatableObject(Simulatable o) {
		sim_objects.add(o);
	}

	/**
	 * Remove an object from simulation
	 */
	public void removeObjectFromSim(Collisionnable o) {
		o.removedFromSim();
		sim_objects.remove(o);
	}

	/**
	 * Simulates all the objects that ought to be simulated
	 * 
	 * @return
	 */
	public void simulate_step() {
		if (sim_objects.size() == 0)
			return;
		
		for (int i = 0; i < sim_objects.size(); i++) {
			Simulatable s = sim_objects.get(i);

			s.step();

			if (s instanceof PhysicalObject) {
				PhysicalObject p = (PhysicalObject) s;
				
				Vector2 oldAcc = p.acceleration;
				Vector2 oldSpeed = p.speed;
				//Vector2 oldPos = p.position;

				/**
				 * General Physics equations
				 */
				// 1 - Newton's first law
				//Vector2 forceSum = oldAcc.scl(p.mass);
				// 2 - Atmospheric friction => -kv
				Vector2 forceFrix = new Vector2(-p.speed.x*Constants.AIR_FRICTION, -p.speed.y*Constants.AIR_FRICTION);
				// 3 - Gravity => mg
				//Vector2 forceGrav = new Vector2(0, p.mass * Constants.GRAVITY);
				Vector2 accGravity = new Vector2(0, Constants.GRAVITY);
				/*
				 * forceFrix + forceGrave = forceSum
				 * -> acceleration = GRAVITY - (AIR_FRICTION/mass)
				 * -> speed(t + DELTA_TIME) = speed(t) + acceleration(DELTA_TIME)
				 * -> position(t + DELTA_TIME) = position(t) + speed(t)*DELTA_TIME
				 */
				
				// acceleration = GRAVITY + (forceFrix/mass)
				p.acceleration = accGravity.mulAdd(forceFrix, 1.0f/(p.mass));
				// speed = oldSpeed + acceleration*DELA_TIME
				p.speed = p.speed.mulAdd(p.acceleration, Constants.DELTA_TIME);
				// position = oldPos + oldSpeed*DELTA_TIME
				p.position = p.position.mulAdd(oldSpeed, Constants.DELTA_TIME);
			

				if (VERBOSE_PHYSICS) {
					System.out.println("Position :" + p.position);
					System.out.println("Speed :" + p.speed);
					System.out.println("Acceleration :" + p.acceleration);
				}

				/**
				 * Elastic collisions with borders
				 */
				if (p.position.y <= Constants.GROUND_ALTITUDE) {
					p.speed.y = - p.speed.y * Constants.DAMPING_FACTOR;
					p.acceleration.y = - p.acceleration.y * Constants.DAMPING_FACTOR;
				}
			}
		}

	}

	public void removeAllObjectsfromSim() {
		sim_objects.clear();
	}
}
