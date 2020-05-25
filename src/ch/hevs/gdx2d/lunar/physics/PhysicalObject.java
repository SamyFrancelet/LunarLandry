package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

public abstract class PhysicalObject implements Simulatable{
		
	public Vector2 position; //position
	public Vector2 speed; //speed
	public Vector2 acceleration; //acceleration
	public int mass;	  //mass
	
	public PhysicalObject(Vector2 p, Vector2 s, int m){
		
		this.position = p;
		this.speed = s;
		this.acceleration = new Vector2(0,0);
		this.mass = m;
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}
}
