import java.awt.*;
public class Player {
    public int xpos;
    public int ypos;

    public int width;
    public int height;
    public Rectangle rectangle;

    public Player(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        width = 80;
        height = 80;
        rectangle = new Rectangle(xpos, ypos, width, height);
    }
    public void moveup(){
        ypos = ypos-7;
        if(ypos<0){
            ypos = 0;
        }
    }
    public void movedown(){
        ypos = ypos+7;
        if(ypos>700-height){
            ypos = 700-height;
        }
    }
    public void moveright(){
        xpos = xpos+7;
        if(xpos>1000-width){
            xpos = 1000-width;
        }
    }
    public void moveleft(){
        ypos = xpos-7;
        if(xpos<0){
            xpos = 0;
        }
    }
}