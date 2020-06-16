package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.Ground;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;
import ch.hevs.gdx2d.desktop.PortableApplication;

public class LunarLander_Main extends PortableApplication {

	PhysicsSimulator physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	Spaceship ssLandry = new Spaceship(new Vector2(400, 700));
	private final boolean drawBoxes = true;
	Ground sol = new Ground();

	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}

	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");
		physics.changeGround(sol.getPolygon());
		physics.addSimulatableObject(ssLandry);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		physics.simulate_step();
		g.drawFPS();
		g.drawSchoolLogo();
		g.drawFilledPolygon(sol.getPolygon(), Color.LIGHT_GRAY);
		//g.drawLine(0, Constants.GROUND_ALTITUDE, Constants.WIN_WIDTH, Constants.GROUND_ALTITUDE, Color.WHITE);
		ssLandry.draw(g);
		if (drawBoxes) {
			Rectangle box = ssLandry.getBoundingBox();
			g.drawRectangle(box.getX()+box.getWidth()/2, box.getY()+box.getHeight()/2, box.getWidth(), box.getHeight(), 0);
		}
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
