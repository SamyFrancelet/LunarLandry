package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

public class Spaceship extends PhysicalObject implements DrawableObject {

	private int fuel;
	
	public boolean thrustUp;
	public boolean thrustLeft;
	public boolean thrustRight;
	
	private boolean landed;
	private boolean kaputt;

	private static final Vector2 POSITION_BAR_FUEL = new Vector2(650, 750);

	public Spaceship(Vector2 p) {
		super(p, new Vector2(0, 0), Constants.BASE_MASS, 50, 50);
		
		fuel = (int) Constants.MAX_FUEL;
		
		thrustUp = false;
		thrustLeft = false;
		thrustRight = false;
		
		kaputt = false;
		landed = false;
	}

	@Override
	public void draw(GdxGraphics arg0) {
		// arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);

		drawFuelBar(arg0);

		if (kaputt) {
			// Explosion animation
			arg0.draw(new Texture("data/images/Rip.png"), position.x - 25, position.y - 25, 50, 50);

		} else {
			arg0.draw(new Texture("data/images/SpaceShip_2.png"), position.x - 25, position.y - 30, 50, 50);

//			if (!landed) {
				// Thrust animation
//			}
		}
	}

	void drawFuelBar(GdxGraphics arg0) {
		// Print fuel on screen
		arg0.drawRectangle(POSITION_BAR_FUEL.x, POSITION_BAR_FUEL.y, 200, 50, 0);
		arg0.drawFilledRectangle(POSITION_BAR_FUEL.x - (float) (fuel / Constants.MAX_FUEL) * 100 + 100,
				POSITION_BAR_FUEL.y, (float) (fuel / Constants.MAX_FUEL) * 200, 50, 0, Color.RED);
		arg0.drawString(POSITION_BAR_FUEL.x - 90, POSITION_BAR_FUEL.y,
				"Fuel :" + fuel + "/" + (int) Constants.MAX_FUEL);

	}

	public void shoot(GdxGraphics arg0, Vector2 ss, Vector2 click) {
		arg0.drawLine(click.x, click.y, ss.x, ss.y, Color.RED);
	}

	@Override
	public void step() {
		// Simulate de thrust from the reactors
		if (!landed) {
			if (thrustUp && fuel > 0) {
				force.y = Constants.MAX_THRUST;
				fuel--;
			} else {
				force.y = 0;
			}

			if (thrustLeft && !thrustRight && fuel > 0) {
				force.x = -Constants.MAX_THRUST;
				fuel--;
			} else if (!thrustLeft && thrustRight && fuel > 0) {
				force.x = Constants.MAX_THRUST;
				fuel--;
			} else {
				force.x = 0;
			}
		}
	}

	@Override
	public void removedFromSim() {
		kaputt = !landed;
	}

	@Override
	public boolean notifyCollision(int energy) {
		System.out.println("Hit with energy : " + energy);
		landed = energy < Constants.DESTRUCTION_ENERGY;
		return (!landed);
	}

	public boolean isFinished() {
		return (kaputt || landed);
	}

	public boolean isDry() {
		return (fuel <= 0);
	}

	public boolean isKaputt() {
		return kaputt;
	}

}
