import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
//Your life is the sum of a remainder of an unbalanced equation inherent to the programming
//of the matrix

public class TheArchitect extends JFrame
{
   public void setExit(int x, int y)//records the location of the exit so we can show it when its time
   {
       WallXCord=x;
       WallYCord=y;  
   } 
   public void showWall()//used when its time to show the exit.  
   {
       updatedMatrix[WallXCord][WallYCord]="E";  
   }

    public void playerMove(int xScale, int yScale, String[][] currentMatrix,int totalDimonds)throws StupidAssMove
    {
    	int [] playerPos = {0,0};
    	globalTotalDimonds=totalDimonds; //use this later for the gui dimond count
    	nextLevel(false); //dont go to the next level yet.
        
    	playerPos = locatePlayerPos(currentMatrix);
        
        if(itsADimond(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            collected+=1;//we got a dimond!
        } else if(itsAMovableWall(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            currentMatrix[playerPos[0]+(xScale*2)][playerPos[1]+(yScale*2)]="M";
        } else if (itsANormalMove(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
        } else if (itsAExit(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            nextLevel(true);//allow the next level to be loaded.
        } else{
            throw new StupidAssMove("Ass Hole hit wall!");
        }
        
        if(collected==totalDimonds){ //if we have all the dimonds give the player the exit
            showWall();
        }
               
        updatedMatrix=currentMatrix;  //we will return updatedMatrix for the gui                     
    }//end method

    public void nextLevel(boolean tOrF)//true we go to next level, false we update current level's gui 
    {
        level=tOrF;
    }
    
    public boolean getLevel()//returs level true or false
    {
        return level;
    }
        
    public int getDimondsLeft()
    {
        return globalTotalDimonds-collected;//for GUI JLabel, show how many dimonds are left to be collected
    }
    
    public String[][] getUpdatedMatrix()//returns the updated matrix for the gui to display
    {
        return updatedMatrix;    
    }
    
    private int [] locatePlayerPos(String[][] currentMatrix) {
    	int [] pos = {0,0};
    	for (int i = 0; i < currentMatrix.length; i++){ //for loop will find were the player is now
            for (int j = 0; j < currentMatrix[i].length; j++){
                if(currentMatrix[i][j].equals("P")){//we found the player
                	//record the players position
                    pos[0]=i;
                    pos[1]=j;
                    break;
                }
            }
        }//end both for loops
    	return pos;
    }
    private String [][] changeValues(String[][] currentMatrix, int [] pos, int xScale, int yScale){
    	String [][] matrix = currentMatrix;
    	matrix[pos[0]][pos[1]]="N";
        matrix[pos[0]+xScale][pos[1]+yScale]="P";
        return matrix;
    }
    private boolean itsADimond(String[][] currentMatrix, int [] pos, int xScale, int yScale){
        return currentMatrix[pos[0]+xScale][pos[1]+yScale].equals("H")||currentMatrix[pos[0]+xScale][pos[1]+yScale].equals("D");
    }
    
    private boolean itsAMovableWall(String[][] currentMatrix, int [] pos, int xScale, int yScale){
        return currentMatrix[pos[0]+xScale][pos[1]+yScale].equals("M") && currentMatrix[pos[0]+(xScale*2)][pos[1]+(yScale*2)].equals("N");
    }
    
    private boolean itsANormalMove(String[][] currentMatrix, int [] pos, int xScale, int yScale){
        return currentMatrix[pos[0]+xScale][pos[1]+yScale].equals("N");
    }
    
    private boolean itsAExit(String[][] currentMatrix, int [] pos, int xScale, int yScale){ 
        return currentMatrix[pos[0]+xScale][pos[1]+yScale].equals("E");
    }
    
    private class StupidAssMove extends RuntimeException
    {
         public StupidAssMove(String event)
         {
             JFrame frame = new JFrame("Warning");
             JOptionPane.showMessageDialog(frame, "You Stupid Ass, Ran into something did you?");
         }
    }//end inner class
    
int foundPlayer=0;
String[][] updatedMatrix;
int WallXCord;
int WallYCord;
int collected=0;
boolean level;
int globalTotalDimonds=0;
}//end class
