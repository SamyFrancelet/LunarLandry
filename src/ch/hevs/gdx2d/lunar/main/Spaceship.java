package ch.hevs.gdx2d.lunar.main;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.Particles;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

public class Spaceship extends PhysicalObject implements DrawableObject {

	private int fuel;
	private int gameNb;

	public boolean thrustUp;
	public boolean thrustLeft;
	public boolean thrustRight;

	private boolean landed;
	private boolean kaputt;
	private boolean firstExplo;

	// Particles stuff
	private ArrayList<Particles> reactor;
	private ArrayList<Particles> explosion;

	static final Random rand = new Random();

	// Textures
	private Texture spaceship;
	private Texture ded;

	public Spaceship(Vector2 p, int gameNb) {
		super(p, new Vector2(0, 0), Constants.BASE_MASS, 50, 50);

		fuel = (int) Constants.MAX_FUEL;
		this.gameNb = gameNb;

		thrustUp = false;
		thrustLeft = false;
		thrustRight = false;

		kaputt = false;
		landed = false;
		firstExplo = true;

		reactor = new ArrayList<Particles>();
		explosion = new ArrayList<Particles>();

		spaceship = new Texture("data/images/ssLandry.png");
		ded = new Texture("data/images/Rip.png");
	}

	@Override
	public void draw(GdxGraphics arg0) {
		// arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);
		if (kaputt) {
			Vector2 vec;
			if (firstExplo) {
				for (int i = 0; i < 500; i++) {
					vec = new Vector2(1, 1).setToRandomDirection();
					explosion.add(new Particles(new Vector2(position.x, position.y), vec.scl(rand.nextFloat() * 2),
							rand.nextInt(80),
							rand.nextBoolean() ? "data/images/fire_particle.png" : "data/images/reactor_particle.png"));
				}
				firstExplo = false;
			}
			// Explosion animation
			arg0.draw(ded, position.x - 25, position.y - 25, 50, 50);

			if (explosion.size() != 0) {
				for (int i = 0; i < explosion.size(); i++) {
					Particles p = explosion.get(i);
					p.update();
					p.draw(arg0);
					if (p.shouldBeDestroyed()) {
						explosion.remove(p);
					}
				}
			}

		} else {
			arg0.draw(spaceship, position.x - 25, position.y - 30, 50, 50);

			if (!landed && fuel > 0 && (thrustUp || thrustLeft || thrustRight)) {
				// Thrust animation
				reactor.add(new Particles(new Vector2(position.x, position.y - 25),
						new Vector2(rand.nextFloat() / 2 * (rand.nextBoolean() ? 1 : -1), -2).mulAdd(speed, 0.1f),
						rand.nextInt(80),
						rand.nextBoolean() ? "data/images/fire_particle.png" : "data/images/reactor_particle.png"));
			}
			if (reactor.size() != 0) {
				for (int i = 0; i < reactor.size(); i++) {
					Particles p = reactor.get(i);
					p.update();
					p.draw(arg0);
					if (p.shouldBeDestroyed()) {
						reactor.remove(p);
					}
				}
			}
		}

		drawHUD(arg0);
	}

	void drawHUD(GdxGraphics arg0) {
		// Print fond noir
		arg0.drawFilledRectangle(400, 50, 800, 100, 0, Color.DARK_GRAY);
		// Print fuel
		Vector2 POSITION_BAR_FUEL = new Vector2(650, 50);
		Vector2 POSITION_SPEED = new Vector2(100, 50);
		Vector2 POSITION_NB_GAME = new Vector2(350, 50);

		arg0.drawRectangle(POSITION_BAR_FUEL.x, POSITION_BAR_FUEL.y, 200, 50, 0);
		arg0.drawFilledRectangle(POSITION_BAR_FUEL.x - (float) (fuel / Constants.MAX_FUEL) * 100 + 100,
				POSITION_BAR_FUEL.y, (float) (fuel / Constants.MAX_FUEL) * 200, 50, 0, Color.RED);
		arg0.drawString(POSITION_BAR_FUEL.x - 90, POSITION_BAR_FUEL.y,
				"Fuel :" + fuel + "/" + (int) Constants.MAX_FUEL);

		// Print speed
		BitmapFont bfSpeed = new BitmapFont();
		bfSpeed.setColor(Color.RED);
		if (speed.len() < Constants.CRASH_SPEED) {
			bfSpeed.setColor(Color.GREEN);
		}
		arg0.drawString(POSITION_SPEED.x, POSITION_SPEED.y, "Speed :" + (int) speed.len() + " m/s", bfSpeed);

		arg0.drawString(POSITION_NB_GAME.x, POSITION_NB_GAME.y, "Apollo " + gameNb);

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
		System.out.println("Max hit energy : " + Constants.DESTRUCTION_ENERGY);
		landed = energy < Constants.DESTRUCTION_ENERGY;
		return (!landed);
	}

	public boolean isLanded() {
		return landed;
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
