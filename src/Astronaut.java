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
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public Rectangle rectangle;
    public boolean isAlive;

    public boolean leftIsPressed;
    public boolean rightIsPressed;
    public boolean upIsPressed;
    public boolean downIsPressed;
    public boolean spaceIsPressed;





    //a boolean to denote if the hero is alive or dead.



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
        width = RandInt(60,100);
        height = RandInt(60,100);
        isAlive = true;
        rectangle = new Rectangle((int)xpos,(int)ypos,width,height);

 
    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos=xpos+dx;
        ypos=ypos+dy;
        rectangle = new Rectangle((int)xpos,(int)ypos,width,height);
    }
    public void bounce(){
        if(xpos>1000-width||xpos<0){
            dx=-dx;
        }
        if(ypos>700-height||ypos<0){
            dy=-dy;
        }
        move();
    }
    public void staticbounce(){
        dx=-dx;
        dy=-dy;
    }
    public void expand(){
        width = width+10;
        height = height+10;
    }
    public void staticwrap(){
        if(xpos>1000){
            xpos = -width;
        }
        if(xpos<-width){
            xpos=1000;
        }
        if(ypos>700){
            ypos = -height;
        }
        if(ypos<-height){
            ypos=700;
        }
    }
    public void wrap(){
        staticwrap();
        move();
    }
    public void randomteleport(){
        xpos = RandInt(5,1000-width);
        ypos = RandInt(5,700-width);
    }
    public void playerMovement(){
        if(leftIsPressed&&!upIsPressed&&!downIsPressed){
            xpos-=5;
        }
        if(rightIsPressed&&!upIsPressed&&!downIsPressed){
            xpos+=5;
        }
        if(upIsPressed&&!rightIsPressed&&!leftIsPressed){
            ypos-=5;
        }
        if(downIsPressed&&!rightIsPressed&&!leftIsPressed){
            ypos+=5;
        }
        if(leftIsPressed&&upIsPressed&&!rightIsPressed&&!downIsPressed){
            xpos-=Math.sqrt(12.5);
            ypos-=Math.sqrt(12.5);
        }
        if(leftIsPressed&&downIsPressed&&!rightIsPressed&&!upIsPressed){
            xpos-=Math.sqrt(12.5);
            ypos+=Math.sqrt(12.5);
        }
        if(rightIsPressed&&downIsPressed&&!leftIsPressed&&!upIsPressed){
            xpos+=Math.sqrt(12.5);
            ypos+=Math.sqrt(12.5);
        }
        if(rightIsPressed&&upIsPressed&&!leftIsPressed&&!downIsPressed){
            xpos+=Math.sqrt(12.5);
            ypos-=Math.sqrt(12.5);
        }
        if(upIsPressed&&leftIsPressed&&rightIsPressed){
            ypos-=5;
        }
        if(downIsPressed&&leftIsPressed&&rightIsPressed){
            ypos+=5;
        }
        if(rightIsPressed&&upIsPressed&&downIsPressed){
            xpos+=5;
        }
        if(leftIsPressed&&upIsPressed&&downIsPressed){
            xpos-=5;
        }
        staticwrap();
    }
    public void speedup(){
        if(dx<0){
            dx -=1;
        } else if (dx>0){
            dx +=1;
        }
        if (dy<0){
            dy-=1;
        } else if (dy>0){
            dy+=1;
        }
    }
}