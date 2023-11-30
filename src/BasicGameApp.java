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
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {
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

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
	private Astronaut astro2;
	public boolean createExplosion;
	public int ExplosionXpos;
	public int ExplosionYpos;

	public int explosionSize;
	public int explosionFrame = 0;
	public int explosiontime = 100;
	public int maxexplosionsize = 150;


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
		background = Toolkit.getDefaultToolkit().getImage("space.png");
		explosion = Toolkit.getDefaultToolkit().getImage("Explosion.png");
		astro = new Astronaut(Astronaut.RandInt(0,940),Astronaut.RandInt(0,640));
		astro2 = new Astronaut(Astronaut.RandInt(0,940),Astronaut.RandInt(0,640));
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
		if(astro.rectangle.intersects(astro2.rectangle)){
			astro.staticbounce();
			astro2.staticbounce();
			System.out.println("Crash!");
			createExplosion = true;
			ExplosionXpos = (astro.xpos+astro.width/2+astro2.xpos+astro2.width/2)/2;
			ExplosionYpos = (astro.ypos+astro.height/2+astro2.ypos+astro2.height/2)/2;
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
		g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
		g.drawImage(astroPic,astro2.xpos,astro2.ypos,astro2.width,astro2.height,null);

		g.dispose();

		bufferStrategy.show();
	}
}