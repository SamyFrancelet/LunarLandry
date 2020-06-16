package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lunar.main.PolygonWorking;

public class Moon {
	public PolygonWorking ground;
	public Vector2[] polyPoints;
	
	public Moon(){
		polyPoints = new Vector2[3];
		polyPoints[0] = new Vector2(100,100);
		polyPoints[1] = new Vector2(300,100);
		polyPoints[2] = new Vector2(500,500);
		ground = new PolygonWorking(polyPoints);
	}
}
