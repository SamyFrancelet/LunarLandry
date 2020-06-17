package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lunar.physics.Constants;

public class LandZone {

	public Rectangle landBox;
	
	public LandZone(Vector2 position) {
		landBox = new Rectangle(position.x, position.y, Constants.Z_WIDTH, Constants.Z_HEIGHT);
		landBox.setCenter(position.x + (800/Constants.SCALE/2), position.y);
	}
	
	public void newLandZone(Vector2 position) {
		landBox = new Rectangle(position.x, position.y, Constants.Z_WIDTH, Constants.Z_HEIGHT);
		landBox.setCenter(position.x + (800/Constants.SCALE/2), position.y);
	}
}
