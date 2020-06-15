package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ch.hevs.gdx2d.components.graphics.Polygon;

public class PolygonWorking extends Polygon {

	public PolygonWorking(Vector2[] points) {
		super(points);
	}

	@Override
	public boolean contains(Vector2 p) {
		Vector2[] v = Polygon.float2vec2(this.getVertices());
		Array<Vector2> a = Array.with(v);
		return Intersector.isPointInPolygon(a, p);
	}
}
