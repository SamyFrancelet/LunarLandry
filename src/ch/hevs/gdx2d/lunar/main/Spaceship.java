package ch.hevs.gdx2d.lunar.main;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ch.hevs.gdx2d.components.audio.MusicPlayer;
import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicalObject;

public class Spaceship extends PhysicalObject implements DrawableObject {

	public boolean thrustUp;
	public boolean thrustLeft;
	public boolean thrustRight;
	private int fuel;
	private boolean kaputt;
	private static final int MAX_THRUST = 1500;
	private static final int BASE_MASS = 100;
	private static final double MAX_FUEL = 300;
	private static final Vector2 POSITION_BAR_FUEL = new Vector2(650, 750);
//	private final Texture shipSkin = new Texture("data/images/SpaceShip_2.png");

	// Particle related
	World world = PhysicsWorld.getInstance();
	static final Random rand = new Random();
	public final int MAX_AGE = 20;
	public int CREATION_RATE = 3;
	public int ctnExplosion = 0;
	public int ctnFire = 0;

	public Spaceship(Vector2 p) {
		super(p, new Vector2(0, 0), BASE_MASS, 50, 50);
		thrustUp = false;
		thrustLeft = false;
		thrustRight = false;
		kaputt = false;
		fuel = (int) MAX_FUEL;
	}
	
	public void changePosition(Vector2 p) {
		this.position = p;
	}

	@Override
	public void draw(GdxGraphics arg0) {
		// arg0.drawFilledRectangle(position.x, position.y+8, 10, 16, 0, Color.BLUE);

		drawFuelBar(arg0);

		if (kaputt) {
			// arg0.drawAlphaPicture(position.x, position.y, -90, 0.3f, 0.3f, new
			// BitmapImage("data/images/flame.png"));
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);

			Iterator<Body> it = bodies.iterator();

			while (it.hasNext()) {
				Body p = it.next();

				if (p.getUserData() instanceof Explosion) {
					Explosion explosion = (Explosion) p.getUserData();
					explosion.step();
					explosion.render(arg0);

					if (explosion.shouldbeDestroyed()) {
						explosion.destroy();
					}
				}
			}
			PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
			if (ctnExplosion < 10) {
				createExplosion();
				ctnExplosion++;
			} else if (ctnFire < 50) {
				createFire();
				ctnFire++;
			} else {
				arg0.draw(new Texture("data/images/Rip.png"), position.x - 25, position.y - 25, 50, 50);
			}

		} else {
			arg0.draw(new Texture("data/images/SpaceShip_2.png"), position.x - 25, position.y - 30, 50, 50);

			if ( !Constants.won) {
				Array<Body> bodies = new Array<Body>();
				world.getBodies(bodies);

				Iterator<Body> it = bodies.iterator();

				while (it.hasNext()) {
					Body p = it.next();

					if (p.getUserData() instanceof Particle) {
						Particle particle = (Particle) p.getUserData();
						particle.step();
						particle.render(arg0);

						if (particle.shouldbeDestroyed()) {
							particle.destroy();
						}
					}
				}
				PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

				if ((thrustUp || thrustLeft || thrustRight) && fuel>0)
					createParticles();
			}
		}
	}

	void drawFuelBar(GdxGraphics arg0) {
		// Print fuel on screen
		arg0.drawRectangle(POSITION_BAR_FUEL.x, POSITION_BAR_FUEL.y, 200, 50, 0);
		arg0.drawFilledRectangle(POSITION_BAR_FUEL.x - (float) (fuel / MAX_FUEL) * 100 + 100, POSITION_BAR_FUEL.y,
				(float) (fuel / MAX_FUEL) * 200, 50, 0, Color.RED);
		arg0.drawString(POSITION_BAR_FUEL.x - 90, POSITION_BAR_FUEL.y, "Fuel :" + fuel + "/" + (int) MAX_FUEL);

	}

	void createExplosion() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Explosion e = new Explosion(position, 1, MAX_AGE * 2 + rand.nextInt(MAX_AGE / 2));
			// Vector2 randForce = new Vector2(1f * rand.nextFloat(), 1f*rand.nextFloat());
			// Apply a vertical force with some random horizontal component
			force.x = rand.nextFloat() * 0.0000705f * (rand.nextBoolean() == true ? -1 : 1);
			force.y = rand.nextFloat() * 0.000105f;
			e.applyBodyLinearImpulse(force, position, true);
		}
	}

	void createFire() {
		Explosion f = new Explosion(position, 1, MAX_AGE + rand.nextInt(MAX_AGE / 2));
		f.setBodyActive(false);
	}

	void createParticles() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Particle c = new Particle(position, 10, MAX_AGE + rand.nextInt(MAX_AGE / 2));

			// Apply a vertical force with some random horizontal component
			force.x = rand.nextFloat() * 0.00095f * (rand.nextBoolean() == true ? -1 : 1);
			force.y = rand.nextFloat() * 0.000095f * (rand.nextBoolean() == true ? -1 : 1);
			c.applyBodyLinearImpulse(force, position, true);
		}
	}
	
	public void shoot(GdxGraphics arg0, Vector2 ss, Vector2 click) {
			arg0.drawLine(click.x, click.y, ss.x, ss.y, Color.RED);		
	}



	@Override
	public void step() {
		// this.force.y = thrustUp ? MAX_THRUST : 0;
		// this.force.x = thrustLeft ? -MAX_THRUST : (thrustRight ? MAX_THRUST : 0);
		if (!Constants.won) {
			if (thrustUp && fuel > 0) {
				force.y = MAX_THRUST;
				fuel--;
			} else {
				force.y = 0;
			}

			if (thrustLeft && !thrustRight && fuel > 0) {
				force.x = -MAX_THRUST;
				fuel--;
			} else if (!thrustLeft && thrustRight && fuel > 0) {
				force.x = MAX_THRUST;
				fuel--;
			} else {
				force.x = 0;
			}	
		}
	}

	@Override
	public void removedFromSim() {
		kaputt = true;
	}

	@Override
	public boolean notifyCollision(int energy) {
		System.out.println("Hit with energy : " + energy);
		return (energy >= Constants.DESTRUCTION_ENERGY);
		//return false;
	}

	public boolean getFuel() {
		if (fuel == 0)
			return true;
		else
			return false;
	}

	public boolean getKaputt() {
		if (kaputt)
			return true;
		else
			return false;
	}

}
