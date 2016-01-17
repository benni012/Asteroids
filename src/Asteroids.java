import org.newdawn.slick.*;
import java.io.File;

class Asteroids extends BasicGame implements KeyListener{
	public static boolean debug = false;
	Player p;

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
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		p.render(container, g);
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


		p.update(container, delta);
	}

	public void keyPressed(int key, char c)
	{
		switch(c) {
			case 'd': Asteroids.debug = !Asteroids.debug; break;
			default:
		}
	}
}
