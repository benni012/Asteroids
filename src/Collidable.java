interface Collidable
{
	public <U extends Entity & Collidable> boolean collides(U e);
}
