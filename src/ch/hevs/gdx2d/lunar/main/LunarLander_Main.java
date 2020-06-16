package ch.hevs.gdx2d.lunar.main;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.Ground;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;
import ch.hevs.gdx2d.components.audio.MusicPlayer;
import ch.hevs.gdx2d.desktop.PortableApplication;

public class LunarLander_Main extends PortableApplication {

	PhysicsSimulator physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	Spaceship ssLandry = new Spaceship(new Vector2(400, 700));
	static final Random rand = new Random();
	private final boolean drawBoxes = true;
	Ground sol = new Ground();
	
	//music
	MusicPlayer music;
	
	//BackGround
	public final int MAX_STAR_AGE = 80;
	public int CREATION_STAR_RATE = 1;

	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}

	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");
		physics.changeGround(sol.getPolygon());
		physics.addSimulatableObject(ssLandry);
		playMusic();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		physics.simulate_step();
		g.drawFPS();
		g.drawSchoolLogo();
		drawBackGround(g);
		g.drawFilledPolygon(sol.getPolygon(), Color.LIGHT_GRAY);
		//g.drawLine(0, Constants.GROUND_ALTITUDE, Constants.WIN_WIDTH, Constants.GROUND_ALTITUDE, Color.WHITE);
		ssLandry.draw(g);
		if (drawBoxes) {
			Rectangle box = ssLandry.getBoundingBox();
			g.drawRectangle(box.getX()+box.getWidth()/2, box.getY()+box.getHeight()/2, box.getWidth(), box.getHeight(), 0);
		}
	}
	
	void drawBackGround(GdxGraphics g) {
		Array<Body> bodies = new Array<Body>();
		ssLandry.world.getBodies(bodies);

		Iterator<Body> it = bodies.iterator();

		while (it.hasNext()) {
			Body p = it.next();

		
			if (p.getUserData() instanceof BackGround) {
				BackGround backGround = (BackGround) p.getUserData();
				backGround.step();
				backGround.render(g);

				if (backGround.shouldbeDestroyed()) {
					backGround.destroy();
				}
			}
		}
		for (int i = 0; i < CREATION_STAR_RATE; i++) {
			Vector2 random = new Vector2(rand.nextFloat() * Constants.WIN_WIDTH,
					(rand.nextFloat() * (Constants.WIN_HEIGHT - Constants.GROUND_ALTITUDE))
							+ Constants.GROUND_ALTITUDE);
			BackGround b = new BackGround(random, 10, MAX_STAR_AGE + rand.nextInt(MAX_STAR_AGE));
			b.setBodyActive(false);
			}
	}
	
	void playMusic() {
		// music = new SoundSample("data\\sons\\sound1.mp3");
		music = new MusicPlayer("data\\sons\\sound1.mp3");
		music.loop();
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
