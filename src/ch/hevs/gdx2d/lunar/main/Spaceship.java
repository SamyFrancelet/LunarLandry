package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Collisionnable;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

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
		arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);
		//arg0.draw(new Texture(), position.x, position.y);
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
