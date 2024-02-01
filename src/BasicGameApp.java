//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {
	//Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too

   //Sets the width and height of the program window

	final int WIDTH = 1000;
	final int HEIGHT = 700;
   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
	public BufferStrategy bufferStrategy;
	public Image astroPic;
	public Image background;
	public Image explosion;
	public Image purpleAstroPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
	private Astronaut astro2;
	private Astronaut astro3;
	private Astronaut player;
	public boolean createExplosion;
	public int ExplosionXpos;
	public int ExplosionYpos;
	public Image greenAstroPic;

	public int explosionSize;
	public int explosionFrame = 0;
	public int explosiontime = 100;
	public int maxexplosionsize = 150;

	public boolean isCrashing = false;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
		purpleAstroPic = Toolkit.getDefaultToolkit().getImage("PurpleAstronaut.png");
		background = Toolkit.getDefaultToolkit().getImage("space.png");
		explosion = Toolkit.getDefaultToolkit().getImage("Explosion.png");
		greenAstroPic = Toolkit.getDefaultToolkit().getImage("Green Astronaut.png");
		astro = new Astronaut(Astronaut.RandInt(0,900),Astronaut.RandInt(0,600));
		astro2 = new Astronaut(Astronaut.RandInt(0,900),Astronaut.RandInt(0,600));
		astro3 = new Astronaut(Astronaut.RandInt(0,900),Astronaut.RandInt(0,600));
		player = new Astronaut(Astronaut.RandInt(0,900),Astronaut.RandInt(0,600));
	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {
      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
		 if(createExplosion){
			 explosionFrame++;
			 explosionSize = (int)(-maxexplosionsize/Math.pow((explosiontime/2),2)*Math.pow(explosionFrame-explosiontime/2,2)+maxexplosionsize);
			 if(explosionFrame == explosiontime){
				 createExplosion = false;
				 explosionFrame = 0;
			 }
		 }
         render();  // paint the graphics
         pause(10); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		astro.bounce();
		astro2.wrap();
		astro3.bounce();
		if(spaceIsPressed){
			astro.bounce();
			astro2.wrap();
			astro3.bounce();
		}
		if(leftIsPressed&&!upIsPressed&&!downIsPressed){
			player.xpos-=5;
		}
		if(rightIsPressed&&!upIsPressed&&!downIsPressed){
			player.xpos+=5;
		}
		if(upIsPressed&&!rightIsPressed&&!leftIsPressed){
			player.ypos-=5;
		}
		if(downIsPressed&&!rightIsPressed&&!leftIsPressed){
			player.ypos+=5;
		}
		if(leftIsPressed&&upIsPressed&&!rightIsPressed&&!downIsPressed){
			player.xpos-=Math.sqrt(12.5);
			player.ypos-=Math.sqrt(12.5);
		}
		if(leftIsPressed&&downIsPressed&&!rightIsPressed&&!upIsPressed){
			player.xpos-=Math.sqrt(12.5);
			player.ypos+=Math.sqrt(12.5);
		}
		if(rightIsPressed&&downIsPressed&&!leftIsPressed&&!upIsPressed){
			player.xpos+=Math.sqrt(12.5);
			player.ypos+=Math.sqrt(12.5);
		}
		if(rightIsPressed&&upIsPressed&&!leftIsPressed&&!downIsPressed){
			player.xpos+=Math.sqrt(12.5);
			player.ypos-=Math.sqrt(12.5);
		}
		if(upIsPressed&&leftIsPressed&&rightIsPressed){
			player.ypos-=5;
		}
		if(downIsPressed&&leftIsPressed&&rightIsPressed){
			player.ypos+=5;
		}
		if(rightIsPressed&&upIsPressed&&downIsPressed){
			player.xpos+=5;
		}
		if(leftIsPressed&&upIsPressed&&downIsPressed){
			player.xpos-=5;
		}
		if(astro3.rectangle.intersects(astro.rectangle)){
			astro3.randomteleport();
			astro.randomteleport();
			System.out.println("Crash!");
		}
		if(astro3.rectangle.intersects(astro2.rectangle)){
			astro3.staticbounce();
			astro2.staticbounce();
			astro3.speedup();
			astro2.speedup();
			System.out.println("Crash!");
		}
		if(astro.rectangle.intersects(astro2.rectangle)&&!isCrashing){
			isCrashing = true;
			astro.staticbounce();
			astro2.staticbounce();
 			astro.expand();
			astro2.expand();
			System.out.println("Crash!");
			createExplosion = true;
			ExplosionXpos = ((int)(astro.xpos)+astro.width/2+(int)(astro2.xpos)+astro2.width/2)/2;
			ExplosionYpos = ((int)(astro.ypos)+astro.height/2+(int)(astro2.ypos)+astro2.height/2)/2;
		}
		if(!astro.rectangle.intersects(astro2.rectangle)){
			isCrashing = false;
		}

	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addKeyListener(this );
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

		g.drawImage(background,0,0,WIDTH,HEIGHT,null);
		if(createExplosion){
			g.drawImage(explosion,ExplosionXpos-explosionSize/2,ExplosionYpos-explosionSize/2,explosionSize,explosionSize,null);
		}
      //draw the image of the astronaut
		g.drawImage(astroPic, (int)astro.xpos, (int)astro.ypos, astro.width, astro.height, null);
		g.drawImage(astroPic,(int)astro2.xpos,(int)astro2.ypos,astro2.width,astro2.height,null);
		g.drawImage(purpleAstroPic,(int)astro3.xpos,(int)astro3.ypos,astro3.width,astro3.height,null);
		g.drawImage(greenAstroPic,(int)player.xpos,(int)player.ypos,astro3.width,astro3.height,null);

		g.dispose();

		bufferStrategy.show();
	}
	boolean spaceIsPressed;
	boolean leftIsPressed;
	boolean rightIsPressed;
	boolean upIsPressed;
	boolean downIsPressed;
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			spaceIsPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			leftIsPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			rightIsPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			upIsPressed = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			downIsPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			spaceIsPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			leftIsPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			rightIsPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP){
			upIsPressed = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN){
			downIsPressed = false;
		}
	}
}