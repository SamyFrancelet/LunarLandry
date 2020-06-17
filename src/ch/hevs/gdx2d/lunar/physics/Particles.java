package ch.hevs.gdx2d.lunar.physics;

import com.badlogic.gdx.math.Vector2;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.lib.GdxGraphics;
import ch.hevs.gdx2d.lib.interfaces.DrawableObject;

public class Particles implements DrawableObject{
	
	private Vector2 position;
	private Vector2 speed;
	
	private float alpha;
	private BitmapImage img;
	
	private int lifetime;
	
	public Particles(Vector2 p, Vector2 s, int lifetime, String imgPath) {
		this.position = p;
		this.speed = s;
		this.lifetime = lifetime;
		
		this.alpha = 1f;
		this.img = new BitmapImage(imgPath);
	}
	
	public void update() {
		this.position.add(this.speed);
		alpha -= 1.0f/lifetime;
	}
	
	public void changePosition(float x, float y) {
		this.position = new Vector2(x,y);
	}
	
	public boolean shouldBeDestroyed() {
		return (alpha <= 0.01f);
	}

	@Override
	public void draw(GdxGraphics arg0) {
		arg0.drawAlphaPicture(this.position, this.alpha, this.img);
	}
}
