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

public class BallApplication extends PortableApplication{
	
	PhysicsSimulator physics = new PhysicsSimulator(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	Ball b1 = new Ball(new Vector2(400, 400),new Vector2(200,40), 100);
	
	public BallApplication() {
		super(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
	}
	
	@Override
	public void onInit() {
		setTitle("Ball physic test");		
		physics.addSimulatableObject(b1);
	}

	@Override
	public void onGraphicRender(GdxGraphics g) {
		// Clears the screen
		g.clear();

		physics.simulate_step();
		g.drawFPS();
		g.drawSchoolLogo();
		b1.draw(g);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BallApplication();
	}

}
