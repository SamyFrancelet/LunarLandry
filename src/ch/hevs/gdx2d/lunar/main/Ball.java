package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Collisionnable;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

public class Ball extends PhysicalObject implements DrawableObject {
	
	public Ball(Vector2 p, Vector2 s, int m) {
		super(p,s,m);
	}

	@Override
	public void draw(GdxGraphics arg0) {
		
		arg0.drawCircle(position.x, position.y, 10);
	}

	@Override
	public void removedFromSim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean notifyCollision(int energy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
