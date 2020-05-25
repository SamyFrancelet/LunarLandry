package ch.hevs.gdx2d.lunar.main;

import com.badlogic.gdx.Gdx;
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
	
	public LunarLander_Main() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}
	
	@Override
	public void onInit() {
		setTitle("LunarLandry (Team PLS)");		
		//physics.addSimulatableObject(b1);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		physics.simulate_step();
		g.drawFPS();
		g.drawSchoolLogo();		
	}

	public static void main(String[] args) {
		new LunarLander_Main();
	}
}
