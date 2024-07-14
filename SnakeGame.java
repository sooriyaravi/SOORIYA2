import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import com.sun.glass.events.KeyEvent;

public class SnakeGame extends JPanel implements ActionListener,KeyListener{
private class Tile{
int x;
int y;

Tile(int x,int y){
        this.x=x;
        this.y=y;

}
}

int boardWidth;
int boardHeight;
int tilesize=25;


//snake
Tile snakeHead = new Tile(5,5);
ArrayList<Tile> snakebody=new ArrayList<Tile>(); 

//food
Tile food=new Tile(10,10);
Random random =new Random();
//game  logic
Timer gameLoop= new Timer(100,this);
int velocityX=1;
int velocityY=0;
boolean gameOVer= false;





SnakeGame(int boardWidth,int boardHeight){
this.boardWidth=boardWidth;
this.boardHeight=boardHeight;
setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
setBackground(Color.black);
placeFood();
gameLoop.start();
addKeyListener(this);
setFocusable(true);
}




public void paintComponent(Graphics g){
super.paintComponent(g);
draw(g);
}
public void draw(Graphics g){

//grid
  for(int i=0;i<boardWidth/tilesize;i++){
    g.drawLine(i*tilesize,0,i*tilesize,boardHeight);//vertical
    g.drawLine(0,i*tilesize,boardWidth,i*tilesize);//horizontal
}
//snake head
g.setColor(Color.green);
//g.fillRect(snakeHead.x*tilesize,snakeHead.y*tilesize,tilesize,tilesize);
g.fill3DRect(snakeHead.x*tilesize,snakeHead.y*tilesize,tilesize,tilesize,true);

//snake body
    
for(int i=0;i<snakebody.size();i++){
    Tile snakepart= snakebody.get(i);
    // g.fillRect(snakepart.x*tilesize,snakepart.y*tilesize,tilesize,tilesize);
    g.fill3DRect(snakepart.x*tilesize,snakepart.y*tilesize,tilesize,tilesize,true);
    
}
                                          //score

g.setFont(new Font("Arial",Font.PLAIN,16));
if(gameOVer){
    g.setColor(Color.red);
    g.drawString("Game Over :"+String.valueOf(snakebody.size()*2),tilesize-16,tilesize);

}
else{

    g.drawString("Score:"+String.valueOf(snakebody.size()*2),tilesize-16,tilesize);
}


                                          //food
g.setColor(Color.RED);
//g.fillRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize);
g.fill3DRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize,true);



}
public void placeFood(){
food.x= random.nextInt(boardWidth/tilesize);//600/25=24
food.y=random.nextInt(boardHeight/tilesize);
}


public boolean collision(Tile tile1,Tile tile2){
return tile1.x ==tile2.x&& tile1.y ==tile2.y;
}

public void move(){
//eat
if(collision(snakeHead, food)){
    snakebody.add(new Tile(food.x, food.y));
    placeFood();
}
//snake head
snakeHead.x+= velocityX;
snakeHead.y +=velocityY;

//snakebody
for(int i= snakebody.size()-1;i>=0;i--){
        Tile snakepart =snakebody.get(i);
        if(i==0){
        snakepart.x=snakeHead.x;
        snakepart.y=snakeHead.y;
        }
        else{
        Tile prevSnakepart=snakebody.get(i-1);
        snakepart.x=prevSnakepart.x;
        snakepart.y=prevSnakepart.y;
        }

}
// game over conditions
for(int i=1;i<snakebody.size();i++){
Tile snakepart =snakebody.get(i);
//collision with the snake head
if(collision(snakeHead, snakepart)){
    gameOVer=true;
}
}

if(snakeHead.x*tilesize <0||snakeHead.x*tilesize>boardWidth|| snakeHead.y*tilesize <0||snakeHead.y*tilesize>boardHeight){
    gameOVer=true;
}
}



@Override
public void actionPerformed(ActionEvent e) {
    move();
    repaint();
    if(gameOVer){
    gameLoop.stop();
    }
}




@Override
public void keyTyped(java.awt.event.KeyEvent e) {

}




@Override
public void keyPressed(java.awt.event.KeyEvent e) {
if(e.getKeyCode()==KeyEvent.VK_UP && velocityY != 1){
    velocityX=0;
    velocityY=-1;
}
else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
    velocityX=0;
    velocityY=1;
}
else if(e.getKeyCode()==KeyEvent.VK_LEFT&& velocityX!=1){
    velocityX=-1;
    velocityY=0;
}
else if(e.getKeyCode()==KeyEvent.VK_RIGHT&& velocityX!=-1){
    velocityX=1;
    velocityY=0;
}
}




@Override
public void keyReleased(java.awt.event.KeyEvent e) {

}



}
