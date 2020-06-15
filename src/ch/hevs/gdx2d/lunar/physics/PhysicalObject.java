package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class PhysicalObject implements Simulatable, Collisionnable{
		
	public Vector2 position; //position
	public Vector2 speed; //speed
	public Vector2 acceleration; //acceleration
	public int mass;	  //mass
	public Vector2 force; //force applied on the object
	protected Rectangle boundingBox;
	
	public PhysicalObject(Vector2 p, Vector2 s, int m, int width, int height){
		
		this.position = p;
		this.speed = s;
		this.acceleration = new Vector2(0,0);
		this.force = new Vector2(0,0);
		this.mass = m;
		
		this.boundingBox = new Rectangle(p.x, p.y, width, height);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Rectangle getBoundingBox() {
		boundingBox.setPosition(position);
		return boundingBox;
	}
}
