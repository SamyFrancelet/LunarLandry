package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lunar.main.PolygonWorking;

public class Ground {

	private Vector2[] polyPoints = new Vector2[Constants.SCALE];
	private PolygonWorking groundPoly;

	public Ground() {
		for (int i = 0; i < polyPoints.length; i++) {
			if (i == 0) {
				polyPoints[i] = new Vector2(0, 100);
			} else if (i == polyPoints.length - 1) {
				polyPoints[i] = new Vector2(800, 100);
			} else if (i == Constants.FLAT_ZONE + 1) {
				polyPoints[i] = new Vector2(800 / polyPoints.length * i, polyPoints[i - 1].y);
			} else if (polyPoints[i - 1].y <= Constants.MIN_ALTITUDE) {
				polyPoints[i] = new Vector2(800 / polyPoints.length * i,
						(float) (polyPoints[i - 1].y + Math.random() * Constants.MAX_INCLINE * 2));
			} else {
				polyPoints[i] = new Vector2(800 / polyPoints.length * i, (float) (polyPoints[i - 1].y
						+ Math.random() * Constants.MAX_INCLINE * 2 - Constants.MAX_INCLINE));
			}
		}

		groundPoly = new PolygonWorking(polyPoints);
	}

	public PolygonWorking getPolygon() {
		return groundPoly;
	}

	public Vector2 getPolyPoint(int i) {
		if (i >= polyPoints.length) {
			i = 0;
		}
		return polyPoints[i];
	}
}
