import org.newdawn.slick.*;
import java.io.File;
import java.util.LinkedList;
import org.newdawn.slick.geom.*;

class Asteroids extends BasicGame implements KeyListener{
	private long ms = 0;
	public static boolean debug = false;
	private Player p;
	private LinkedList<Asteroid> ast;

	public Asteroids()
	{
		super("Asteroids");
	}

	public static void main(String[] args) 
	{
//		System.setProperty("org.lwjgl.librarypath", new File("../library/natives").getAbsolutePath());
		try{
			AppGameContainer app = new AppGameContainer(new Asteroids());
			app.setDisplayMode(1024, 768, false);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		p = new Player(100, 100);
		ast = new LinkedList<Asteroid>();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		p.render(container, g);
		for(Asteroid a : ast)
			a.render(container, g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();

		if(input.isKeyDown(Input.KEY_LEFT))
			p.rotate(-0.05f);
		if(input.isKeyDown(Input.KEY_RIGHT))
			p.rotate(0.05f);
		if(input.isKeyDown(Input.KEY_UP))
			p.accelerate(delta);

		if((ms+delta)/1000 > ms/1000)
			ast.add(new Asteroid(0f, 100f, new Vector2f(100f, 0f), (int) (Math.random() * 3), 50f + (float)Math.random() * 50f));

		p.update(container, delta);
		for(int i = 0; i < ast.size(); i++) {
			if(inView(ast.get(i), container))
				ast.get(i).update(container, delta);
			else
				ast.remove(i);
		}
		ms += delta;
	}

	public void keyPressed(int key, char c)
	{
		switch(c) {
			case 'd': Asteroids.debug = !Asteroids.debug; break;
			default:
		}
	}

	private <U extends Entity> boolean inView(U e, GameContainer container)
	{
		Polygon model = e.getModel();
		return model.getMaxX() >= 0f && model.getMinX() <= container.getWidth() 
			&& model.getMaxY() >= 0f && model.getMinY() <= container.getHeight();
	}
}
