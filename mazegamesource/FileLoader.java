import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class FileLoader{
	public void loadFile(String fileName){  
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));            
            String linea;
            int numLinea=0;
            while (( linea = in.readLine()) != null) { 
            	MatrixLoader(linea,numLinea);
                numLinea++;
            }
         } catch (IOException e) {  
        	 JFrame frame = new JFrame("Alert");
        	 JOptionPane.showMessageDialog(frame, "Ooops IOException error, i did it again!" + e.getMessage());
        }
     }
     
     public void MatrixLoader(String fileTextLine, int lineNum)throws gameFileError {        
         int espaciosBcos = 0;
         char textVar;
         //Si es la primera fila del archivo maze, se crea la matriz en base a ella.
         if(lineNum == 0){ 
             espaciosBcos = countBlanks(fileTextLine, espaciosBcos); 
             int ubicacionBco = fileTextLine.indexOf(" ");
             String columna1 = fileTextLine.substring(0,ubicacionBco);
             String fila1 = fileTextLine.substring(ubicacionBco + espaciosBcos, fileTextLine.length());
             columna = Integer.parseInt(columna1);
             fila = Integer.parseInt(fila1);
             GameMatrix = new String[fila][columna];       
          } else {
        	  for(int i = 0;i < fileTextLine.length();i++){
        		  textVar = fileTextLine.charAt(i); 
        		  if(textVar == '.') {
        			  textVar = 'N';
        		  }
        		  String textVar1 = "" + textVar;
        		  if(textVar == 'E') {
        			  exitXCord = lineNum-1;
        			  exitYCord = i;
        			  textVar1= "" + textVar;
        		  }
        		  GameMatrix[lineNum-1][i] = textVar1; 
        	  }
          }
     }

	private int countBlanks(String fileTextLine, int espaciosBcos) {
		for(int i = 0;i < fileTextLine.length();i++){
		     if(fileTextLine.charAt(i) ==' ')
		     espaciosBcos += 1;
		 }
		return espaciosBcos;
	}
     
     public String[][] getGameMatrix(){ 
    	 int exitCount = 0;
         int i1 = 0;
         int j1 = 0;
         int playerCount = 0;
         
         for (int i = 0; i < GameMatrix.length; i++) {
        	 for (int j = 0; j < GameMatrix[i].length; j++) {
        		 if(itsAPlayer(i, j)){
        			 playerCount += 1;
        		 } 
        		 if(itsAExit(i, j)) {
        			 exitCount += 1;   
        			 i1 = i;
        			 j1 = j;
        		 }
        		 System.out.println(playerCount + "playerCount");
        		 System.out.println(exitCount + "playerCount");
        	 }
         }
         
         checkPlayersAndExits(exitCount, i1, j1, playerCount);
         
         return GameMatrix;
     }

	private void checkPlayersAndExits(int exitCount, int i1, int j1, int playerCount) {
		if(playerCount >1 || exitCount>1) {
        	 throw new gameFileError();
         } else {
        	 GameMatrix[i1][j1]="W";
         }
	}

	private boolean itsAExit(int i, int j) {
		return GameMatrix[i][j].equals("E");
	}

	private boolean itsAPlayer(int i, int j) {
		return GameMatrix[i][j].equals("P");
	}
     
     public int getMatrixSizeColumn(){
         return columna;
     }
     
     public int getMatrixSizeRow(){
         return fila;
         
     }
     
   public int ExitXCord(){
      return exitXCord;
   }
   
   public int ExitYCord(){
      return exitYCord; 
   }
   
   public int dimondCount(){
	   int diamantesTotales=0;
       for (int i = 0; i < GameMatrix.length; i++){
    	   for(int j = 0; j < GameMatrix[i].length; j++){
    		   if(GameMatrix[i][j].equals("D") || GameMatrix[i][j].equals("H"))
    			   diamantesTotales += 1;
    	   }
       }
       return diamantesTotales;
    }
    //Si un nivel tiene mas de una salida o mas de un jugador, se genera esta excepcion
    private class gameFileError extends RuntimeException {
        public gameFileError() {
            JFrame frame = new JFrame("Alert");
            JOptionPane.showMessageDialog(frame, "Tu archivo de Maze tiene mas de un jugador o mas de una salida.");
         }
    }
    
private int exitXCord=0;
private int exitYCord=0;;
private String[][] GameMatrix;
private int columna;
private int fila;

}
