import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

abstract class Entity
{
	protected float x, y;
	protected CollisionBox cb;
	protected Polygon model;

	public Entity(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void render(GameContainer container, Graphics g) throws SlickException {}
	public void update(GameContainer container, int delta) throws SlickException {}

	public Polygon getModel()
	{
		return model;
	}
}
