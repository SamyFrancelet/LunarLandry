package ch.hevs.gdx2d.lunar.main;

import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.physics.PhysicsWorld;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;
import ch.hevs.gdx2d.lunar.physics.Simulatable;
import ch.hevs.gdx2d.desktop.PortableApplication;

public class LunarLander_Main extends PortableApplication{
	
	PhysicsSimulator physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	Spaceship ssLandry = new Spaceship(new Vector2(400, 400));
	// Particle related
	World world = PhysicsWorld.getInstance();
	static final Random rand = new Random();
	public final int MAX_AGE = 35;
	public int CREATION_RATE = 3;
	// Shooting related
	boolean mouseActive = false;
	Vector2 positionClick;
	
	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}
	
	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");
		physics.addSimulatableObject(ssLandry);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		physics.simulate_step();
		g.drawFPS();
		g.drawSchoolLogo();
		g.drawLine(0, Constants.GROUND_ALTITUDE, Constants.WIN_WIDTH, Constants.GROUND_ALTITUDE, Color.WHITE);
		ssLandry.draw(g);
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);

		Iterator<Body> it = bodies.iterator();


		while (it.hasNext()) {
			Body p = it.next();

			if (p.getUserData() instanceof Particle) {
				Particle particle = (Particle) p.getUserData();
				particle.step();
				particle.render(g);

				if (particle.shouldbeDestroyed()) {
					particle.destroy();
				}
			}
		}
		

		if(ssLandry.thrustUp && !(ssLandry.thrustLeft || ssLandry.thrustRight))
			g.drawAlphaPicture(ssLandry.position.x + 25,
			ssLandry.position.y - 20, 90, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));
		
		if(!ssLandry.thrustUp && ssLandry.thrustLeft && !ssLandry.thrustRight)
			g.drawAlphaPicture(ssLandry.position.x + 50,
			ssLandry.position.y + 10, 180, 0.2f, 0.2f, new BitmapImage("data/images/flame.png"));
		
		if(!ssLandry.thrustUp && !ssLandry.thrustLeft && ssLandry.thrustRight)
			g.drawAlphaPicture(ssLandry.position.x + 0,
			ssLandry.position.y + 10, 0, 0.2f, 0.2f, new BitmapImage("data/images/flame.png"));

		if(ssLandry.thrustUp && ssLandry.thrustLeft)
			g.drawAlphaPicture(ssLandry.position.x + 40,
			ssLandry.position.y - 20, 120, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));

		if(ssLandry.thrustUp && ssLandry.thrustRight)
			g.drawAlphaPicture(ssLandry.position.x + 10,
			ssLandry.position.y - 20, 60, 0.3f, 0.3f, new BitmapImage("data/images/flame.png"));
		
		if(mouseActive) {
			ssLandry.shoot(g, ssLandry.position, positionClick);
		}
		PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());

		if (ssLandry.thrustUp || ssLandry.thrustLeft || ssLandry.thrustRight)
			createParticles();
	
	
	}
	
	void createParticles() {
		for (int i = 0; i < CREATION_RATE; i++) {
			Particle c = new Particle(ssLandry.position, 10, MAX_AGE + rand.nextInt(MAX_AGE / 2));

			// Apply a vertical force with some random horizontal component
			ssLandry.force.x = rand.nextFloat() * 0.00095f * (rand.nextBoolean() == true ? -1 : 1);
			ssLandry.force.y = rand.nextFloat() * 0.00095f * (rand.nextBoolean() == true ? -1 : 1);
			c.applyBodyLinearImpulse(ssLandry.force, ssLandry.position, true);
		}
	}
	
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		positionClick.x = x;
		positionClick.y = y;
	}
	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouseActive = true;
		positionClick = new Vector2(x, y);
		System.out.println("X"+x);
		System.out.println("Y"+y);
	}
	
	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		positionClick.x = x;
		positionClick.y = y;
		mouseActive = false;
	}
	
	@Override
	public void onKeyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.UP:
				ssLandry.thrustUp = false;
				break;
			case Input.Keys.LEFT:
				ssLandry.thrustLeft = false;
				break;
			case Input.Keys.RIGHT:
				ssLandry.thrustRight = false;
				break;
			default:
				break;
		}
	}

	@Override
	public void onKeyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.UP:
				ssLandry.thrustUp = true;
				break;
			case Input.Keys.LEFT:
				ssLandry.thrustLeft = true;
				break;
			case Input.Keys.RIGHT:
				ssLandry.thrustRight = true;
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {
		new LunarLander_Main();
	}
}
