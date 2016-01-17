// TODO fix ast overflow
import org.newdawn.slick.*;
import java.io.File;
import java.util.ArrayList;
import org.newdawn.slick.geom.*;

class Asteroids extends BasicGame implements KeyListener{
	private long ms = 0;
	public static boolean debug = false;
	private Player p;
	private ArrayList<Asteroid> ast;

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
		ast = new ArrayList<Asteroid>();
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
			ast.add(new Asteroid(0f, 100f, new Vector2f(100f, 0f)));



		p.update(container, delta);
		for(Asteroid a : ast)
			a.update(container, delta);
		ms += delta;
	}

	public void keyPressed(int key, char c)
	{
		switch(c) {
			case 'd': Asteroids.debug = !Asteroids.debug; break;
			default:
		}
	}
}
