package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

public class Ground {

	private Vector2[] polyPoints = new Vector2[Constants.scale];


	public Ground() {
		
		for (int i = 0; i < polyPoints.length; i++) {
			if (i == 0) {
				polyPoints[i] = new Vector2(0, 100);
			} else if (i == polyPoints.length - 1) {
				polyPoints[i] = new Vector2(800, 100);
			} else if (polyPoints[i-1].y <= Constants.minGround){
				polyPoints[i] = new Vector2(800 / polyPoints.length*i, (float) (polyPoints[i-1].y + Math.random() * Constants.maxIncline * 2));
			} else {
				polyPoints[i] = new Vector2(800 / polyPoints.length*i, (float) (polyPoints[i-1].y + Math.random() * Constants.maxIncline * 2 
						-Constants.maxIncline));
			}
		}
	}
	public Vector2[] getPolygon () {
		return polyPoints;
	}

}
