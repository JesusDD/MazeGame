import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class ScoreGui extends JDialog implements ActionListener {

    public ScoreGui(){
        super();
    }
    
    public void ScoreGui(){
    	Container cp = getContentPane();
        JButton ok = new JButton("OK");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
        cp.add(ok,BorderLayout.SOUTH);
        try{
        	String line = "";
        	String[] myScoreArray = new String[100];
        	for(int i = 0; i < myScoreArray.length; i++) {
        		myScoreArray[i] = " ";
        	}
        	BufferedReader brScore = new BufferedReader(new InputStreamReader(new FileInputStream("scores.txt")));
        	int recordsCount = 0;
        	while((line = brScore.readLine()) != null) {
        		line = brScore.readLine();
        		if(line != ""){
        			recordsCount += 1;
        			int tempPOS = line.indexOf("*");
        			String pos = line.substring(tempPOS+1);
        			int index = Integer.parseInt(pos);
        			if(myScoreArray[index] == " ") {
        				myScoreArray[index] = line;
        			} else {
        				buscarSigBco(line, myScoreArray, index);
        			}
                        JPanel scorePanel = new JPanel();
                        scorePanel.setLayout(new GridLayout(recordsCount,recordsCount));
                        mostrarScoreScreen(myScoreArray, scorePanel);
                        cp.add(scorePanel); 
                     }
                }            
        } catch(IOException ex) {
        	JFrame frame = new JFrame("Alert");
        	JOptionPane.showMessageDialog(frame, "Problem with scores.txt file.  Cant load high Scores");
        }
        pack();
        setVisible (true);
    }

	private void mostrarScoreScreen(String[] myScoreArray, JPanel scorePanel) {
		for(int i = 0; i < myScoreArray.length;i++) {
			if(myScoreArray[i] != " ") {
				mainLabel = new JLabel(myScoreArray[i], JLabel.LEFT);//display the score on the screen
		        scorePanel.add(mainLabel);
			}
		}
	}

	private void buscarSigBco(String line, String[] myScoreArray, int index) {
		for(int i = 0; i < myScoreArray.length;i++) {
			if(index + i < myScoreArray.length) {//prevent array out of bounds errors.
				if(myScoreArray[index + i].equals(" ")) {
					myScoreArray[index + 1] = line;//add in a score to the next available area of the array
				}
			}
		}
	}
    
    public void actionPerformed(ActionEvent e){
    	dispose();
    }
    
private JLabel mainLabel;
}
