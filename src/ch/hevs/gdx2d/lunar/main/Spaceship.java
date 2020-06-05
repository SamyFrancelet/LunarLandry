package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Collisionnable;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

public class Spaceship extends PhysicalObject implements DrawableObject {
	
	public boolean thrustUp;
	public boolean thrustLeft;
	public boolean thrustRight;
	private boolean kaputt;
	private static final int MAX_THRUST = 1500;
	private static final int BASE_MASS = 100;
	//private final Texture shipSkin = new Texture("data/images/SpaceShip_2.png");
	
	public Spaceship(Vector2 p) {
		super(p, new Vector2(0, 0), BASE_MASS);
		thrustUp = false;
		thrustLeft = false;
		thrustRight = false;
		kaputt = false;
	}

	@Override
	public void draw(GdxGraphics arg0) {
		//arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);
		if (kaputt) {
			//arg0.draw(shipSkin = new Texture("data/images/SpaceShip_2.png"), position.x, position.y,50,50);
			arg0.drawAlphaPicture(position.x, position.y+30, -90, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));
		} else {
			arg0.draw(new Texture("data/images/SpaceShip_2.png"), position.x, position.y,50,50);
			

			if(thrustUp && !(thrustLeft || thrustRight))
				arg0.drawAlphaPicture(position.x + 25,
				position.y - 20, 90, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));
			
			if(!thrustUp && thrustLeft && !thrustRight)
				arg0.drawAlphaPicture(position.x + 50,
				position.y + 10, 180, 0.2f, 0.2f, new BitmapImage("data/images/flame.png"));
			
			if(!thrustUp && !thrustLeft && thrustRight)
				arg0.drawAlphaPicture(position.x + 0,
				position.y + 10, 0, 0.2f, 0.2f, new BitmapImage("data/images/flame.png"));

			if(thrustUp && thrustLeft)
				arg0.drawAlphaPicture(position.x + 40,
				position.y - 20, 120, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));

			if(thrustUp && thrustRight)
				arg0.drawAlphaPicture(position.x + 10,
				position.y - 20, 60, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));
		}
	}
	
	@Override
	public void step() {
		this.force.y = thrustUp ? MAX_THRUST : 0;
		this.force.x = thrustLeft ? -MAX_THRUST : (thrustRight ? MAX_THRUST : 0);
	}

	@Override
	public void removedFromSim() {
		kaputt = true;
	}

	@Override
	public boolean notifyCollision(int energy) {
		System.out.println("Hit with energy : " + energy);
		return (energy >= Constants.DESTRUCTION_ENERGY);
	}

	@Override
	public Rectangle getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}

}
