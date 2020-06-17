package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Particles implements DrawableObject{
	
	private Vector2 position;
	private Vector2 speed;
	private Vector2 acceleration; // Probably useless
	
	private float alpha;
	private BitmapImage img;
	
	public Particles(Vector2 p, Vector2 s, String imgPath) {
		this.position = p;
		this.speed = s;
		
		alpha = 1f;
		img = new BitmapImage(imgPath);
	}
	
	public void update() {
		position.add(speed);
	}

	@Override
	public void draw(GdxGraphics arg0) {
		arg0.drawAlphaPicture(position, alpha, img);
	}
}
