package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

public class Ground {
	
	private Vector2[] polyPoints = new Vector2[6];

	public Ground() {
		
		for (int i = 0; i < polyPoints.length; i++) {
			if (i == 0) {
				polyPoints[i] = new Vector2(0, 100);
			} else if (i == polyPoints.length - 1) {
				polyPoints[i] = new Vector2(800, 100);
			} else {
				polyPoints[i] = new Vector2(800 / polyPoints.length*i, (float) (100 + Math.random() * 700));
			}
		}
	}
	public Vector2[] getPolygon () {
		return polyPoints;
	}

}
