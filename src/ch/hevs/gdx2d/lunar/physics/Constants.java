package ch.hevs.gdx2d.lunar.physics;

/**
 * Some useful physics constant that are used by the {@link PhysicsSimulator} class and others.
 * @author P.-A. Mudry 
 */
public final class Constants {
	/**
	 * Graphics related constants
	 */
	public static final int WIN_WIDTH  = 800;
	public static final int WIN_HEIGHT = 800;
	public static final int FPS = 100;
			
	/**
	 * Physics environment
	 */
	public static final float GRAVITY = -9.8f;
	public static final float DELTA_TIME = 0.1f;
	public static final float AIR_FRICTION = 0.3f;
	public static final float DAMPING_FACTOR = 0.9f;
	
	/**
	 * Game related constants
	 */
	// Maximal impact energy triggering a object destruction
	public static final int DESTRUCTION_ENERGY = 20000;	
	public static final int CLOUD_DENSITY = 5;
	public static final int GROUND_ALTITUDE = 100;
	public static final boolean DRAW_BOUNDINGBOXES = false;
	
	/**
	 * Maximal impact speed triggering a crash
	 */
	public static double crashSpeed = 1.4;
	
	/**
	 * Spaceship related
	 */
	public static float lateralPower = 0.05f;
	public static float verticalPower = 0.2f;
	
	/**
	 * fuel reserve
	 */
	public static final float maxFuel = 10.0f;
	
	/** 
	 * Ground parameters
	 */
	public static final float MAX_INCLINE = 100.0f;
	public static final int MIN_ALTITUDE = 200;
	public static final int SCALE = 10;
	public static final int FLAT_ZONE = 7;
	
	/** 
	 * Landing Zone
	 */
	public static final int Z_WIDTH = 100;
	public static final int Z_HEIGHT = 30;
	
	/** 
	 * Global variable (oopsiii)
	 */
	public static boolean won = false;
}
