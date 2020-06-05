package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Collisionnable;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;

public class Spaceship extends PhysicalObject implements DrawableObject {
	
	public boolean thrustUp;
	public boolean thrustLeft;
	public boolean thrustRight;
	private static final int MAX_THRUST = 1500;
	private static final int BASE_MASS = 100;
	
	public Spaceship(Vector2 p) {
		super(p, new Vector2(0, 0), BASE_MASS);
		thrustUp = false;
		thrustLeft = false;
		thrustRight = false;
	}

	@Override
	public void draw(GdxGraphics arg0) {
		//arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);
		arg0.draw(new Texture("data/images/SpaceShip_2.png"), position.x - 30, position.y,60,60);
	}
	
	
	public void shoot(GdxGraphics arg0, Vector2 ss, Vector2 click) {
		float diffX = click.x - ss.x;
		float diffY = click.y - ss.y;
		//click.setLength(1);
		//Vector2 directionShoot = new Vector2(10*diffX, 10*diffY);
		//float vectUnit = click / Math.sqrt(Math.pow(diffX, 2)+Math.pow(diffY, 2));
		arg0.drawLine(click.x, click.y, ss.x, ss.y, Color.RED);
//		System.out.println("X"+click.x);
//		System.out.println("Y"+click.y);
		
		
//		Ball bullet = new Ball(test, directionBullet, 10);
//		bullet.draw(arg0);

		
//		PhysicsSimulator physicsB = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
//		Vector2 directionB = new Vector2(valueX,valueY);
//		Vector2 positionB = new Vector2(position.x, position.y);
//		Ball bullet = new Ball(positionB, directionB, 10);
//		physicsB.addSimulatableObject(bullet);
//		bullet.draw(arg0);
//		physicsB.simulate_step();
	}


	@Override
	public void step() {
		// TODO Auto-generated method stub
		this.force.y = thrustUp ? MAX_THRUST : 0;
		this.force.x = thrustLeft ? -MAX_THRUST : (thrustRight ? MAX_THRUST : 0);
	}

	@Override
	public void removedFromSim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean notifyCollision(int energy) {
		// TODO Auto-generated method stub
		System.out.println("Hit with energy : " + energy);
		return false;
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
