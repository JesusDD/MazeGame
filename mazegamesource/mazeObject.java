import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class mazeObject extends JLabel {
	/**
	 * 
	 */
	private final GameGui mazeObject;
	private static final long serialVersionUID = 1L;
	        
	public mazeObject(GameGui gameGui, String nombreImg){
        mazeObject = gameGui;
		nombreImg += ".png";
        JLabel fancyLabel;
        fancyLabel = new JLabel("",new ImageIcon(nombreImg),JLabel.LEFT);
        mazeObject.newPanel.add(fancyLabel);
    }
}