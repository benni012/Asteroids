import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

class Player extends Entity implements Collidable
{
	private CollisionBox cb;
	private Vector2f velDir;
	private Vector2f accDir;

	private float rotation = 0.0f;
	private final float ACCELERATION = 150f;

	private Polygon model;

	public Player(float x, float y)
	{
		super(x, y);

		model = new Polygon(new float[]
		 					 /*Upper part*/
							{0.00f, 0.00f, 
							 0.14f, 0.25f, 
							 0.28f, 0.25f, 
							 0.50f, 0.00f, 
							 0.72f, 0.25f,
							 0.86f, 0.25f, 
							 1.00f, 0.00f, 
							 /*Lower part*/
							 1.00f, 1.00f,
							 0.86f, 0.66f,
							 0.50f, 0.75f,
							 0.14f, 0.66f,
							 0.00f, 1.00f,
							 0.00f, 0.00f});
		model.setCenterX(x);
		model.setCenterY(y);
		model = (Polygon) model.transform(Transform.createScaleTransform(20f, 20f));
		model = (Polygon) model.transform(Transform.createRotateTransform((float)Math.PI*0.5f, model.getCenterX(), model.getCenterY()));

		velDir = new Vector2f(0f,0f);
		accDir = new Vector2f(0f, ACCELERATION);

		cb = new CollisionBox(model.getMinX(), model.getMinY(), model.getWidth(), model.getHeight());
	}

	public <U extends Entity & Collidable> boolean collides(U e)
	{
		return CollisionBox.collides(e.cb, this.cb);
	}

	public void accelerate(int delta) 
	{
		float time = delta / 1000f;
		velDir.add(new Vector2f(accDir).scale(time));
	}

	public void rotate(float theta)
	{
		accDir.add(toDegrees(theta));
	}

	public void render(GameContainer container, Graphics g) throws SlickException
	{
		model.setCenterX(x);
		model.setCenterY(y);

		//System.out.println("X: " + x);
		//System.out.println("Y: " + y);

		// Transform and draw the polygon
		g.draw(model.transform(Transform.createRotateTransform(toRadians((float)accDir.getTheta()), model.getCenterX(), model.getCenterY())));
		
		if(Asteroids.debug) {
			// Draw the center of the polygon
			g.drawRect(x, y, 1, 1);
			// Draw a line for the direction of the player
			g.drawLine(x, y, x+accDir.x, y+accDir.y);
			// Draw a line for the vel vector
			g.drawLine(x, y, x+velDir.x, y+velDir.y);
		}
	}

	public void update(GameContainer container, int delta) throws SlickException
	{
		float time = delta / 1000f;
		x += velDir.x * time;
		y += velDir.y * time;
	}

	private float toDegrees(float radians)
	{
		return radians * (180/(float)Math.PI);
	}

	private float toRadians(float degrees)
	{
		return degrees * ((float)Math.PI/180);
	}
}
