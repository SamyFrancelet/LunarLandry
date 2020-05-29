package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lunar.physics.Constants;
import ch.hevs.gdx2d.lunar.physics.PhysicsSimulator;
import ch.hevs.gdx2d.lunar.physics.Simulatable;
import ch.hevs.gdx2d.desktop.PortableApplication;

public class LunarLander_Main extends PortableApplication{
	
	PhysicsSimulator physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	Spaceship ssLandry = new Spaceship(new Vector2(400, 400));
	
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
