package ch.hevs.gdx2d.lunar.main;

import ch.hevs.gdx2d.components.bitmaps.BitmapImage;
import ch.hevs.gdx2d.components.physics.primitives.PhysicsBox;
import ch.hevs.gdx2d.lib.GdxGraphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Demonstrates how to render particles
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.2
 */
public class Explosion extends PhysicsBox {
	protected final int maxAge;
	// Resources MUST not be static
	protected BitmapImage img = new BitmapImage("data/images/explosion2.png");
	protected BitmapImage img1 = new BitmapImage("data/images/explosion2-1.png");
	protected BitmapImage img2 = new BitmapImage("data/images/explosion2-2.png");
	protected BitmapImage img3 = new BitmapImage("data/images/fire2.png");
	protected BitmapImage img4 = new BitmapImage("data/images/fire3.png");
	protected BitmapImage img5 = new BitmapImage("data/images/fire5.png");
	protected BitmapImage img6 = new BitmapImage("data/images/fire5-1.png");
	protected BitmapImage img7 = new BitmapImage("data/images/fire5-2.png");
	protected BitmapImage img8 = new BitmapImage("data/images/fire5-3.png");
	protected int age = 0;
	private boolean init = false;

	public Explosion(Vector2 position, int radius, int maxAge) {
		super(null, position, radius, radius, 0.12f, 1f, 1f);
		this.maxAge = maxAge;

		// Particles should not collide together
		Filter filter = new Filter();
		filter.groupIndex = -1;
		f.setFilterData(filter);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		img.dispose();
	}

	public boolean shouldbeDestroyed() {
		return age > maxAge ? true : false;
	}

	public void step() {
		age++;
	}

	public void render(GdxGraphics g) {
		final Color col = g.sbGetColor();
		final Vector2 pos = getBodyPosition();

		if (!init) {
			g.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
			init = true;
		}

		// Make the particle disappear with time
		g.sbSetColor(.5f, 0.7f, 0.9f, 1.0f - age / (float) (maxAge + 5));

		// Draw the particle
		int value = (int) (Math.random() * 12);
		switch (value) {
		case 1:
			g.draw(img.getRegion(), pos.x - img.getImage().getWidth() / 2,
					pos.y + 0 - img.getImage().getHeight() / 2);
			break;
		case 2:
			g.draw(img1.getRegion(), pos.x - img1.getImage().getWidth() / 2,
					pos.y + 0 - img1.getImage().getHeight() / 2);
			break;
		case 3:
			g.draw(img2.getRegion(), pos.x - img2.getImage().getWidth() / 2,
					pos.y + 0 - img2.getImage().getHeight() / 2);
			break;
		case 4:
			g.draw(img3.getRegion(), pos.x - img3.getImage().getWidth() / 2,
					pos.y + 0 - img3.getImage().getHeight() / 2);
			break;
		case 5:
			g.draw(img4.getRegion(), pos.x - img4.getImage().getWidth() / 2,
					pos.y + 0 - img4.getImage().getHeight() / 2);
			break;
		case 6:
			g.draw(img6.getRegion(), pos.x - img6.getImage().getWidth() / 2,
					pos.y + 0 - img6.getImage().getHeight() / 2);
			break;
		case 7:
			g.draw(img7.getRegion(), pos.x - img7.getImage().getWidth() / 2,
					pos.y + 0 - img7.getImage().getHeight() / 2);
			break;
		case 8:
			g.draw(img8.getRegion(), pos.x - img8.getImage().getWidth() / 2,
					pos.y + 0 - img8.getImage().getHeight() / 2);
			break;
		default:
			g.draw(img5.getRegion(), pos.x - img5.getImage().getWidth() / 2, pos.y + 0 - img5.getImage().getHeight() / 2);
			break;
		}
		g.sbSetColor(col);
	}
}
