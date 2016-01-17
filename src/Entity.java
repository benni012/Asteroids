import org.newdawn.slick.*;

abstract class Entity
{
	protected float x, y;
	protected CollisionBox cb;

	public Entity(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void render(GameContainer container, Graphics g) throws SlickException {}
	public void update(GameContainer container, int delta) throws SlickException {}
}
