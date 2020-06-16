package ch.hevs.gdx2d.lunar.physics;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;

/**
 * A simple physics simulator for the inf1 project.
 */
public class PhysicsSimulator {

	/**
	 * This represents the borders of the simulated area (for collisions)
	 */
	double width;
	double height;
	Moon moon;

	private final boolean VERBOSE_PHYSICS = false;

	/**
	 * The objects that require physics simulation (objects that move)
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
		moon = new Moon();
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
			boolean destroyed = false;
			Simulatable s = sim_objects.get(i);
			s.step();

			if (s instanceof PhysicalObject) {
				PhysicalObject p = (PhysicalObject) s;
				/**
				 * General Physics equations
				 */
				// 1 - Newton's first law
				// Vector2 forceSum = oldAcc.scl(p.mass);
				// 2 - Atmospheric friction => -kv
				Vector2 forceFrix = new Vector2(-p.speed.x * Constants.AIR_FRICTION,
						-p.speed.y * Constants.AIR_FRICTION);
				// 3 - Gravity => mg
				// Vector2 forceGrav = new Vector2(0, p.mass * Constants.GRAVITY);
				Vector2 accGravity = new Vector2(0, Constants.GRAVITY);
				/*
				 * forceFrix + forceGrave = forceSum -> acceleration = GRAVITY -
				 * (AIR_FRICTION/mass) -> speed(t + DELTA_TIME) = speed(t) +
				 * acceleration(DELTA_TIME) -> position(t + DELTA_TIME) = position(t) +
				 * speed(t)*DELTA_TIME
				 */

				// acceleration = GRAVITY + ((forceFrix + forceObj)/mass)
				p.acceleration = accGravity.mulAdd(forceFrix.add(p.force), 1.0f / (p.mass));
				// p.acceleration = accGravity.mulAdd(forceFrix, 1.0f/(p.mass));
				// speed = oldSpeed + acceleration*DELA_TIME
				p.speed = p.speed.mulAdd(p.acceleration, Constants.DELTA_TIME);

				if (VERBOSE_PHYSICS) {
					System.out.println("Position :" + p.position);
					System.out.println("Speed :" + p.speed);
					System.out.println("Acceleration :" + p.acceleration);
				}

				/**
				 * Elastic collisions with borders
				 */
//				// Calculate collision energy Ecin = 1/2 * mv²
//				destroyed = p.notifyCollision((int) (p.mass * p.speed.len() * p.speed.len()) / 2);
				Rectangle box = p.getBoundingBox();
				Vector2[] boxPoints = new Vector2[4];
				boxPoints[0] = new Vector2(box.getX(), box.getY());
				boxPoints[1] = new Vector2(box.getX() + box.getWidth(), box.getY());
				boxPoints[2] = new Vector2(box.getX(), box.getY() + box.getHeight());
				boxPoints[3] = new Vector2(box.getX() + box.getWidth(), box.getY() + box.getHeight());
				
				// Ground corner into object
				for (int j = 0; j < moon.polyPoints.length; j++) {
					System.out.println(boxPoints[j]);
					if (box.contains(moon.polyPoints[j]) || destroyed) {
						destroyed = true;
						break;
					}
				}
				
				// Object corner into ground
				for (int j = 0; j < 4; j++) {
					if (moon.ground.contains(boxPoints[j]) || destroyed) {
						destroyed = true;
						break;
					}
				}

				// position = oldPos + oldSpeed*DELTA_TIME
				p.position = p.position.mulAdd(p.speed, Constants.DELTA_TIME);
				if (destroyed) {
					removeObjectFromSim(p);
				}
			}
		}

	}

	public void removeAllObjectsfromSim() {
		sim_objects.clear();
	}
}
