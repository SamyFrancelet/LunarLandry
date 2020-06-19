package ch.hevs.gdx2d.lunar.main;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
	ArrayList<Gegner> meteors;
	private int gameNb;

	// music
	MusicPlayer music;
	SoundSample noFuel;
	SoundSample bruitExplosion;
	SoundSample winSound;
	SoundSample pew;
	private boolean doSoundFuel = true;
	private boolean doExplosion = true;
	private boolean doWinSound = true;

	// Shooting related
	private ArrayList<Particles> laserExplo;
	boolean mouseActive = false;
	int waitLaser = 0;
	Vector2 positionClick;

	// Stars particles
	private ArrayList<Particles> stars;
	static final Random rand = new Random();
	static int waitStar;

	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}

	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");
		gameNb = 1;
		waitStar = 0;
		playMusic();
		ssLandry = new Spaceship(new Vector2(100, 700), gameNb);
		sol = new Ground();
		lz = new LandZone(sol.getPolyPoint(Constants.FLAT_ZONE));
		physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
		physics.changePlayground(sol.getPolygon(), lz);
		physics.addSimulatableObject(ssLandry);
		stars = new ArrayList<Particles>();
		laserExplo = new ArrayList<Particles>();
		meteors = new ArrayList<Gegner>();
		meteors.add(new Gegner(new Vector2(400, 700)));
		physics.addSimulatableObject(meteors.get(0));
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		// Simulate every object
		physics.simulate_step();

		// Draw basic layout
		// g.drawFPS();
		// g.drawSchoolLogo();

		// Draw the stars on the background
		drawStars(g, 200);

		// Spaceship
		ssLandry.draw(g);

		// Meteors
		if (meteors.size() != 0) {
			for (int i = 0; i < meteors.size(); i++) {
				meteors.get(i).draw(g);
			}
		}
		drawBoundingBoxes(g);

		if (ssLandry.isFinished() && ssLandry.isKaputt()) {
			g.drawStringCentered(660, "Appuiez sur 'R' pour recommencer");
		}
		if (ssLandry.isFinished() && ssLandry.isLanded()) {
			g.drawStringCentered(660, "Appuiez sur 'R' pour continuer");
		}
		playSound();
		g.drawFilledPolygon(sol.getPolygon(), Color.LIGHT_GRAY);
		drawLandZone(g);
		// g.drawLine(0, Constants.GROUND_ALTITUDE, Constants.WIN_WIDTH,
		// Constants.GROUND_ALTITUDE, Color.WHITE);
		drawLaser(g);
		drawLaserExplo(g, 80);

	}

	void drawBoundingBoxes(GdxGraphics arg0) {
		if (Constants.DRAW_BOUNDINGBOXES) { // Hitboxes
			Rectangle box = ssLandry.getBoundingBox();
			arg0.drawRectangle(box.getX() + box.getWidth() / 2, box.getY() + box.getHeight() / 2, box.getWidth(),
					box.getHeight(), 0);
			if (meteors.size() != 0) {
				for (int i = 0; i < meteors.size(); i++) {
					box = meteors.get(i).getBoundingBox();
					arg0.drawRectangle(box.getX() + box.getWidth() / 2, box.getY() + box.getHeight() / 2,
							box.getWidth(), box.getHeight(), 0);
				}
			}
		}
	}

	void drawLaser(GdxGraphics arg0) {
		if ((mouseActive || waitLaser > 0) && !ssLandry.isFinished())
			arg0.drawLine(positionClick.x, positionClick.y, ssLandry.position.x, ssLandry.position.y, Color.RED);
		waitLaser--;
	}

	void drawLaserExplo(GdxGraphics arg0, int age) {
		// Laser logik
		if (mouseActive && !ssLandry.isFinished()) {
			pew = new SoundSample("data/sons/BruitLaser_low.mp3");
			pew.play();
			pew.setVolume(0.1f);
			mouseActive = false;
			waitLaser = 4;
			if (meteors.size() != 0) {
				for (int i = 0; i < meteors.size(); i++) {
					if (meteors.get(i).getBoundingBox().contains(positionClick)) {
						physics.removeObjectFromSim(meteors.get(i));
					}
				}
			}

			Vector2 vec;
			for (int i = 0; i < 100; i++) {
				vec = new Vector2(1, 1).setToRandomDirection();
				laserExplo.add(new Particles(new Vector2(positionClick.x, positionClick.y), vec.scl(0.2f),
						rand.nextInt(age),
						rand.nextBoolean() ? "data/images/fire_particle.png" : "data/images/reactor_particle.png"));
			}
		}
		// Explosion laser animation
		if (laserExplo.size() != 0) {
			for (int j = 0; j < laserExplo.size(); j++) {
				Particles p = laserExplo.get(j);
				p.update();
				p.draw(arg0);
				if (p.shouldBeDestroyed()) {
					laserExplo.remove(p);
				}
			}
		}
	}

	void drawLandZone(GdxGraphics arg0) {
		Color color = Color.RED;
		if (ssLandry.isLanded()) {
			color = Color.GREEN;
		}
		arg0.drawFilledRectangle(lz.landBox.getX() + Constants.Z_WIDTH / 2, lz.landBox.getY() + Constants.Z_HEIGHT / 2,
				Constants.Z_WIDTH, Constants.Z_HEIGHT, 0, color);
	}

	void drawStars(GdxGraphics arg0, int age) {

		waitStar++;

		// Adds a star every n frames
		if (waitStar == 5) {
			final String img = "data/images/star.png";
			final String img2 = "data/images/star2.png";
			final String img3 = "data/images/star4big.png";
			String imgRand;

			int value = (int) (Math.random() * 20);
			switch (value) {
			case 3:
				imgRand = img2;
				age = age / 3;
				break;
			case 6:
				imgRand = img3;
				age = age / 3;
				break;
			default:
				imgRand = img;
				break;
			}
			stars.add(new Particles(new Vector2(rand.nextFloat() * Constants.WIN_WIDTH,
					(rand.nextFloat() * (Constants.WIN_WIDTH - Constants.GROUND_ALTITUDE)) + Constants.GROUND_ALTITUDE),
					new Vector2(0.1f, 0), age, imgRand));

			waitStar = 0;
		}

		// Draw the stars
		if (stars.size() != 0) {
			for (int i = 0; i < stars.size(); i++) {
				Particles p = stars.get(i);
				p.update();
				p.draw(arg0);
				if (p.shouldBeDestroyed()) {
					stars.remove(p);
				}
			}
		}
	}

	public int getNbGame() {
		return gameNb;
	}

	void playMusic() {
		music = new MusicPlayer("data/sons/sound1_low.mp3");
		music.loop();
	}

	void playSound() {
		if (ssLandry.isDry() && doSoundFuel) {
			final String dry1 = "data/sons/NoFuel.mp3";
			final String dry2 = "data/sons/Ecolo.mp3";
			final String dry3 = "data/sons/Sub.mp3";
			String dry;
			int value = (int) (Math.random() * 4);
			switch (value) {
			case 2:
				dry = dry2;
				break;
			case 3:
				dry = dry3;
				break;
			default:
				dry = dry1;
				break;
			}
			noFuel = new SoundSample(dry);
			noFuel.play();
			doSoundFuel = false;
		}
		if (ssLandry.isKaputt() && doExplosion) {
			gameNb = 1;
			final String kaputt1 = "data/sons/bruitExplo.mp3";
			final String kaputt2 = "data/sons/doucement.mp3";
			String kaputt;
			int value = (int) (Math.random() * 3);
			switch (value) {
			case 2:
				kaputt = kaputt2;
				break;
			default:
				kaputt = kaputt1;
				break;
			}
			bruitExplosion = new SoundSample(kaputt);
			bruitExplosion.play();
			doExplosion = false;
		}
		if (ssLandry.isLanded() && doWinSound) {
			if (gameNb == 11) {
				winSound = new SoundSample("data/sons/OneSmallStep.mp3");
			} else if (gameNb < 11) {
				winSound = new SoundSample(rand.nextBoolean() ? "data/sons/Sympa.mp3" : "data/sons/bof_low.mp3");
			} else {
				winSound = new SoundSample("data/sons/VSS.mp3");
			}
			winSound.play();
			winSound.mofidyPlayingVolument(0.1f, 1);
			doWinSound = false;
			gameNb++;
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

//	public void onDrag(int x, int y) {
//		super.onDrag(x, y);
//		positionClick.x = x;
//		positionClick.y = y;
//	}

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
		case Input.Keys.W:
			ssLandry.thrustUp = false;
			break;
		case Input.Keys.A:
			ssLandry.thrustLeft = false;
			break;
		case Input.Keys.D:
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
		case Input.Keys.W:
			ssLandry.thrustUp = true;
			break;
		case Input.Keys.A:
			ssLandry.thrustLeft = true;
			break;
		case Input.Keys.D:
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
		if (ssLandry.isLanded()) {
			winSound.stop();
		}

		physics.removeAllObjectsfromSim();
		ssLandry = new Spaceship(new Vector2(100, 700), gameNb);
		sol = new Ground();
		lz = new LandZone(sol.getPolyPoint(Constants.FLAT_ZONE));
		physics.changePlayground(sol.getPolygon(), lz);
		physics.addSimulatableObject(ssLandry);

		meteors.clear();
		for (int i = 0; i < gameNb; i++) {
			meteors.add(new Gegner(new Vector2(rand.nextInt(300) + 400, 700)));
			physics.addSimulatableObject(meteors.get(i));
		}

		doSoundFuel = true;
		doExplosion = true;
		doWinSound = true;
	}

	public static void main(String[] args) {
		new LunarLander_Main();
	}
}
