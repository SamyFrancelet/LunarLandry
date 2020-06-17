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
import ch.hevs.gdx2d.lunar.physics.Particles;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;
import ch.hevs.gdx2d.components.audio.MusicPlayer;
import ch.hevs.gdx2d.components.audio.SoundSample;
import ch.hevs.gdx2d.desktop.PortableApplication;

public class LunarLander_Main extends PortableApplication {

	// Game components
	PhysicsSimulator physics;
	Spaceship ssLandry;
	Ground sol;
	LandZone lz;

	// music
	MusicPlayer music;
	SoundSample noFuel;
	SoundSample bruitExplosion;
	private boolean doSoundFuel = true;
	private boolean doExplosion = true;
	
	Particles p;
	
	// Shooting related
	boolean mouseActive = false;
	Vector2 positionClick;

	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}

	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");
		ssLandry = new Spaceship(new Vector2(400, 700));
		sol = new Ground();
		lz = new LandZone(sol.getPolyPoint(Constants.FLAT_ZONE));
		physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
		physics.changePlayground(sol.getPolygon(), lz);
		//physics.addSimulatableObject(ssLandry);
		p = new Particles(new Vector2(400,400), new Vector2(1,1), "data/images/fire_particle.png");
		//playMusic();
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		// Simulate every object
		physics.simulate_step();
		
		p.update();
		p.draw(g);
		
		// Draw basic layout
		g.drawFPS();
		g.drawSchoolLogo();
		
		// Draw the stars on the background
		// drawBackGround(g);
		
		// Spaceship
		ssLandry.draw(g);
		if (Constants.DRAW_BOUNDINGBOXES) { // Hitboxes
			Rectangle box = ssLandry.getBoundingBox();
			g.drawRectangle(box.getX() + box.getWidth() / 2, box.getY() + box.getHeight() / 2, box.getWidth(),
					box.getHeight(), 0);
		}
		if (ssLandry.isFinished()) {
			g.drawStringCentered(70, "Appuie sur 'R' pour recommencer");
		}
		playSound();
		g.drawFilledPolygon(sol.getPolygon(), Color.LIGHT_GRAY);
		g.drawFilledRectangle(lz.landBox.getX() + Constants.Z_WIDTH/2, lz.landBox.getY() + Constants.Z_HEIGHT/2, Constants.Z_WIDTH, Constants.Z_HEIGHT, 0, Color.RED);
		//g.drawLine(0, Constants.GROUND_ALTITUDE, Constants.WIN_WIDTH, Constants.GROUND_ALTITUDE, Color.WHITE);
		if (mouseActive)
			ssLandry.shoot(g, ssLandry.position, positionClick);
	}

	void playMusic() {
		// music = new SoundSample("data\\sons\\sound1.mp3");
		music = new MusicPlayer("data/sons/sound1.mp3");
		music.loop();
	}

	void playSound() {
		if (ssLandry.isDry() && doSoundFuel) {
			noFuel = new SoundSample("data/sons/NoFuel.mp3");
			noFuel.play();
			doSoundFuel = false;
		}
		if (ssLandry.isKaputt() && doExplosion) {
			bruitExplosion = new SoundSample("data/sons/bruitExplo.mp3");
			bruitExplosion.play();
			doExplosion = false;
		}
	}

	@Override
	public void onClick(int x, int y, int button) {
		super.onClick(x, y, button);
		mouseActive = true;
		positionClick = new Vector2(x, y);
	}
	
	@Override
	public void onRelease(int x, int y, int button) {
		super.onRelease(x, y, button);
		positionClick.x = x;
		positionClick.y = y;
		mouseActive = false;
	}
	
	public void onDrag(int x, int y) {
		super.onDrag(x, y);
		positionClick.x = x;
		positionClick.y = y;
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
		case Input.Keys.R:
			if (ssLandry.isFinished()) {
				replay();
			}
		default:
			break;
		}
	}
	
	public void replay() {
		ssLandry = new Spaceship(new Vector2(400, 700));
		sol = new Ground();
		lz = new LandZone(sol.getPolyPoint(Constants.FLAT_ZONE));
		physics.changePlayground(sol.getPolygon(), lz);
		physics.addSimulatableObject(ssLandry);
		doSoundFuel = true;
		doExplosion = true;
	}

	public static void main(String[] args) {
		new LunarLander_Main();
	}
}
