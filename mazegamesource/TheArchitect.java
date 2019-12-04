import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class TheArchitect extends JFrame {
	
   public void setExit(int x, int y){
       WallXCord=x;
       WallYCord=y;  
   } 
   public void showWall(){
       updatedMatrix[WallXCord][WallYCord]="E";  
   }

    public void playerMove(int xScale, int yScale, String[][] currentMatrix,int totalDimonds)throws MovimientoNoValido {
    	int [] playerPos = {0,0};
    	globalTotalDimonds = totalDimonds; 
    	nextLevel(false); 
        
    	playerPos = locatePlayerPos(currentMatrix);
        
        if(itsADimond(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            collected+=1;
        } else if(itsAMovableWall(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            currentMatrix[playerPos[0]+(xScale*2)][playerPos[1]+(yScale*2)]="M";
        } else if (itsANormalMove(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
        } else if (itsAExit(currentMatrix, playerPos, xScale, yScale)){
        	changeValues(currentMatrix, playerPos, xScale, yScale);
            nextLevel(true);
        } else{
            throw new MovimientoNoValido("Ass Hole hit wall!");
        }
      //Si el jugador recogio todos los diamantes se le muestra la salida
        if(collected == totalDimonds){ 
            showWall();
        }
               
        updatedMatrix = currentMatrix;                       
    }

	public void nextLevel(boolean tOrF) {
    	level=tOrF;
    }
    
    public boolean getLevel() {
        return level;
    }
        
    public int getDimondsLeft() {
        return globalTotalDimonds - collected;
    }
    
    public String[][] getUpdatedMatrix(){
        return updatedMatrix;    
    }
    
    private int [] locatePlayerPos(String[][] currentMatrix) {
    	int [] pos = {0,0};
    	for (int i = 0; i < currentMatrix.length; i++){ 
            for (int j = 0; j < currentMatrix[i].length; j++){
                if(currentMatrix[i][j].equals("P")){
                    pos[0]=i;
                    pos[1]=j;
                    break;
                }
            }
        }
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
    
    private class MovimientoNoValido extends RuntimeException{
         public MovimientoNoValido(String event){
             JFrame frame = new JFrame("Warning");
             JOptionPane.showMessageDialog(frame, "Chocaste contra una pared");
         }
    }
    
//int foundPlayer=0;
String[][] updatedMatrix;
int WallXCord;
int WallYCord;
int collected=0;
boolean level;
int globalTotalDimonds=0;
}
