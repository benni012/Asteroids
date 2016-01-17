// TODO offset base asteroid model generation
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

class Asteroid extends Entity implements Collidable
{
	public static final int GEN_RANDOM = 0;
	public static final int GEN_LINEAR = 1;
	public static final int GEN_SINUS  = 2;
	private int mode;

	private float scale;

	private Vector2f velDir;

	public Asteroid(float x, float y)
	{
		super(x, y);

		velDir = new Vector2f(0f, 0f);
		init();
	}

	public Asteroid(float x, float y, Vector2f velDir, int mode, float scale)
	{
		super(x, y);

		this.velDir = velDir;
		this.mode = mode;
		this.scale = scale;
		init();
	}

	private void init()
	{
		model = Asteroid.generateModel(20, mode, 0.1f);
		cb = new CollisionBox(model.getMinX(), model.getMinY(), model.getWidth(), model.getHeight());
	}

	public <U extends Entity & Collidable> boolean collides(U e)
	{
		return CollisionBox.collides(e.cb, this.cb);
	}

	public void setVelDir(Vector2f velDir)
	{
		this.velDir = velDir;
	}

	public void update(GameContainer container, int delta) throws SlickException
	{
		float time = delta / 1000f;
		x += velDir.x * time;
		y += velDir.y * time;
	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{
		model.setCenterX(x);
		model.setCenterY(y);

		// Draw the polygon
		g.draw(model);
		
		if(Asteroids.debug) {
			// Draw the center of the polygon
			g.drawRect(x, y, 1, 1);
			// Draw a line for the vel vector
			g.drawLine(x, y, x+velDir.x, y+velDir.y);
		}
	}

	public static Polygon generateModel(int steps, int mode, float randomness)
	{
		if(randomness < 0f) randomness = 0f;
		else if(randomness > 1f) randomness = 1f;
		if(steps < 3) steps = 3;
		if(mode < 0 || mode > 3) mode = GEN_RANDOM;

		final int STEPS = steps;
		float[] pts = new float[STEPS*2];

		for(int i = 0; i < STEPS; i++) {
			float p = i/(STEPS - 1f);

			float x = 0f, y = 0f;
			float roffX = (float)(2 * Math.random() - 1f) * randomness;
			float roffY = (float)(2 * Math.random() - 1f) * randomness;
			switch(mode) {
				case GEN_RANDOM:
					x = 1f - 2f * Math.abs(p - 0.5f);
					y = 0.5f + (p > 0.5f ? 1 : -1) * (float)Math.random() * 0.5f;
					break;
				case GEN_LINEAR:
					x = 1f - 2f * Math.abs(p - 0.5f) + roffX;
					y = Math.abs(1f - 2f * (float)Math.abs(p - 0.25f)) + roffY;
					break;
				case GEN_SINUS:
					x = (float)Math.cos(p*2*Math.PI)/2 + 0.5f + roffX;
					y = (float)Math.sin(p*2*Math.PI)/2 + 0.5f + roffY;
					break;
			}

			//System.out.printf("(%.3f|%.3f)\n", x, y);
			pts[2*i] = x;
			pts[2*i + 1] = y;
		}
		float scale = 50f + (float)Math.random() * 50f;
		return (Polygon)new Polygon(pts).transform(Transform.createScaleTransform(scale, scale));
	}
}
