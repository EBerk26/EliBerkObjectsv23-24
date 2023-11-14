import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Astronaut {
   public static int RandInt(int LowerBound, int UpperBound){
        return(int)(Math.random()*(UpperBound-LowerBound+1)+LowerBound);
    }

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Astronaut(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =RandInt(-5,5);
        dy =RandInt(-5,5);
        while(dx==0){
            dx =RandInt(-5,5);
        }
        while(dy==0){
            dy =RandInt(-5,5);
        }
        width = 60;
        height = 60;
        isAlive = true;
 
    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        if(xpos>940||xpos<0){
            dx=-dx;
        }
        if(ypos>640||ypos<0){
            dy=-dy;
        }
        xpos=xpos+dx;
        ypos=ypos+dy;
    }
}






