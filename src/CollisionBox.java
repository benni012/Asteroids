class CollisionBox
{
	private float x, y;
	private float width, height;

	public CollisionBox(float x, float y, float width, float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public static boolean collides(CollisionBox cb, float x, float y)
	{
		return x >= cb.x && x <= cb.x+cb.width && y >= cb.y && y <= cb.y+cb.height;
	}

	public static boolean collides(CollisionBox cb1, CollisionBox cb2)
	{
		return CollisionBox.collides(cb1, cb2.x, cb2.y) || CollisionBox.collides(cb1, cb2.x+cb2.width, cb2.y)
			|| CollisionBox.collides(cb1, cb2.x, cb2.y+cb2.height) || CollisionBox.collides(cb1, cb2.x+cb2.width, cb2.y+cb2.height);
	}
}
