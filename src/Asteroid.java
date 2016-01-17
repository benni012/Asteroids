import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

class Asteroid extends Entity implements Collidable
{
	private Polygon model;
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
		//model = Asteroid.generateModel();	
		model = new Polygon(new float[] {0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f});
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

		//System.out.println("X: " + x);
		//System.out.println("Y: " + y);

		// Transform and draw the polygon
		g.draw(model);
		
		if(Asteroids.debug) {
			// Draw the center of the polygon
			g.drawRect(x, y, 1, 1);
			// Draw a line for the vel vector
			g.drawLine(x, y, x+velDir.x, y+velDir.y);
		}
	}
}
