import java.io.*;
import javax.swing.*;

public class FileLoader{
	private int exitXCord=0;
	private int exitYCord=0;;
	private String[][] GameMatrix;
	private int column;
	private int row;
	
	public void loadFile(String fileName){  
		try{
			BufferedReader in = new BufferedReader(new FileReader(fileName));            
            String x;
            int lineNum=0;
            while (( x = in.readLine()) != null){ 
            	MatrixLoader(x,lineNum);//pass the Matrix loader method the line and the line number for parsing.
                lineNum++;//we will use the line number later in this class
            }
         } catch (IOException e){  
            JFrame frame = new JFrame("Alert");
            JOptionPane.showMessageDialog(frame, "Ooops IOException error, i did it again!" + e.getMessage());
        }
     }
     
	public void MatrixLoader(String fileTextLine, int lineNum)throws gameFileError{
        int sum=0;
        char textVar;
      //se checa si estamos en la primera linea del maze, de ser asi se crea la Matriz con base a esa linea
        if(lineNum == 0){
        	for(int i=0; i<fileTextLine.length();i++){
        		if(fileTextLine.charAt(i) ==' ') {//find blank area on first line number 
        			sum+=1;
        		}
             } 
             int locationOfSpace = fileTextLine.indexOf(" ");//still handling that possible blank space in the matrix size in the file
             String c1=fileTextLine.substring(0,locationOfSpace);
             String r1=fileTextLine.substring(locationOfSpace+sum,fileTextLine.length());
             column = Integer.parseInt(c1);
             row = Integer.parseInt(r1);
             GameMatrix=new String[row][column];//create new matrix based on the size from the file       
          }//end if 
          else
             for(int i=0; i< fileTextLine.length();i++){//it is not the first line of the maze file
                 textVar = fileTextLine.charAt(i); //grab the individual charaters from the string.
                 if(textVar == '.') {//change . to N, so we dont have any goofy file system problems
                	 textVar='N';
                 }
                 String textVar1= "" + textVar;
                 if(textVar == 'E'){//log the position of the exit for later use                  
                     exitXCord = lineNum-1;
                     exitYCord =i;
                     // textVar='W';
                     textVar1= "" + textVar;//turn the exit into a wall
                 }
                 GameMatrix[lineNum-1][i]=textVar1; //load the matrix with values, aka N,W, D, H, etc
             }//end for loop
     }
     
     public String[][] getGameMatrix(){ 
    	 int exitCount=0;
         int i1=0;
         int j1=0;
          //  playerCount=0;//we must reset our variables to zero for the next level.
          //before we will return the matrix we will quick do some error checking
         int playerCount=0;
         for (int i = 0; i < GameMatrix.length; i++) {
        	 for (int j = 0; j < GameMatrix[i].length; j++) {
        		 if(GameMatrix[i][j].equals("P")){
        			 playerCount+=1;
        		 } else if(GameMatrix[i][j].equals("E")){
        			 exitCount+=1;
        			 i1=i;
        			 j1=j;
        		 }
        		 System.out.println(playerCount + "playerCount");
        		 System.out.println(exitCount + "playerCount");
        	 }
         }//end double for loop
         if(playerCount >1 || exitCount>1){
                throw new gameFileError();
         } else {
        	 GameMatrix[i1][j1]="W";
         }
       
         return GameMatrix;
     }
     
     public int getMatrixSizeColumn(){
         return column;
     }
     
     public int getMatrixSizeRow(){
         return row;
         
     }
     
   public int ExitXCord(){
      return exitXCord;
   }
   
   public int ExitYCord(){
      return exitYCord; 
   }
   
   public int dimondCount(){
       int totalDimonds=0;
        for (int i = 0; i < GameMatrix.length; i++){
            for(int j = 0; j < GameMatrix[i].length; j++){
            	if(GameMatrix[i][j].equals("D") || GameMatrix[i][j].equals("H")) {
            		totalDimonds+=1;
            	}
            }
        }
     return totalDimonds;
    }
    
    private class gameFileError extends RuntimeException {//if a level is loaded with ether two players or two exits throw this
        
    	public gameFileError(){
    		JFrame frame = new JFrame("Alert");
            JOptionPane.showMessageDialog(frame, "Your maze file ether had more than one player, or more than one exit.");
         }
    }
    


}//end class