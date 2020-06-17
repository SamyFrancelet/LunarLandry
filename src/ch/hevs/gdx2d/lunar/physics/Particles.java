package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

public class Particles {
	
	private Vector2 position;
	private Vector2 speed;
	private Vector2 acceleration; // Probably useless
	
	public Particles(Vector2 p, Vector2 s) {
		this.position = p;
		this.speed = s;
	}
	
	public void update() {
		
	}
}
