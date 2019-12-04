import java.util.*;
import java.io.*;
import java.beans.*; 

public class HighScore{
	public void addHighScore(String nombre, int min, int seg, int nivel){
		try{
			String outData = "Nombre del Jugador: "+nombre+" Tiempo total: "+min+":"+seg+"(Minutos:Segundos)"+ "Nivel Alcanzado:*" + nivel;
            PrintWriter out = new PrintWriter(new FileOutputStream("scores.txt", true));
            out.println("");
            out.println(outData);
            out.close();
		} catch(Exception ex){
			System.out.println(ex);
		}
	    
    }   
}
