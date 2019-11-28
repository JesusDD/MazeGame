public class TimeKeeper {
	
    public void TimeKeeper(int min, int seg) {
    	if(seg + segundos <=60) {
    		minutos += min;
	        segundos = seg + segundos;
    	} else {
	       minutos += min;
	       minutos += 1 * ((seg + segundos) / 60);
	       segundos = (seg + segundos) % 60;
	   }
	}
	
	public int getMinutos() {
	    return minutos;
	}
	
	public int getSegundos() {
	    return segundos;
	}
	
int minutos = 0; 
int segundos = 0;
}
