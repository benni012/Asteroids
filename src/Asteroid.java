// TODO offset base asteroid model generation
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

class Asteroid extends Entity implements Collidable
{
	private Vector2f velDir;

	public Asteroid(float x, float y)
	{
		super(x, y);

		velDir = new Vector2f(0f, 0f);
		init();
	}

	public Asteroid(float x, float y, Vector2f velDir)
	{
		super(x, y);

		this.velDir = velDir;
		init();
	}

	private void init()
	{
		model = Asteroid.generateModel();
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

	public static Polygon generateModel()
	{
		float[] pts = new float[20];
		for(int i = 0; i < 10; i++) {
			float x = 0.5f - Math.abs(i/10f - 0.5f);
			float y = 0.5f + (i-5 > 0 ? 1 : -1) * (float) Math.random() * 0.5f;
			//System.out.printf("(%.3f|%.3f)\n", x, y);
			pts[2*i] = x;
			pts[2*i + 1] = y;
		}
		return (Polygon)new Polygon(pts).transform(Transform.createScaleTransform(100f, 100f));
	}
}
