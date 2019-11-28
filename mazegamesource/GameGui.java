import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GameGui extends JFrame implements ActionListener {

    public static void main(String[] args){ new GameGui(); }

    public GameGui(){
    	//Se inicializa el titulo en la G.U.I.
    	super("Maze, a game of wondering"); 
        cp=getContentPane();
        //Fondo de la GUI para la carga Inicial
        fondoInicial = new JLabel("",new ImageIcon("yeababyyea.jpg"),JLabel.LEFT);
        cp.add(fondoInicial);
        //Add Exit & New Game Menu Items
        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, KeyEvent.CTRL_MASK));//press CTRL+X to exit if you want
        itemSaveScore = new JMenuItem("Save High Score");
        itemSaveScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, KeyEvent.CTRL_MASK));//press CTRL+S to save high score if you want
        itemHighScore=new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, KeyEvent.CTRL_MASK));//press CTRL+H to view high score if you want
        itemEnterName = new JMenuItem("Enter Player Name");
        itemEnterName.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, KeyEvent.CTRL_MASK));//press CTRL+N to enter your name if you want
        newGameItem = new JMenuItem("New Game");
        openFileItem = new JMenuItem("Open Maze File.");
        openFileItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_O, KeyEvent.CTRL_MASK));//press CTRL+O to open a level if you want
        newGameItem.setActionCommand("New Game");
        newGameItem.addActionListener(this);
        itemEnterName.setActionCommand("EnterName");
        itemEnterName.addActionListener(this);
        itemSaveScore.setActionCommand("SaveScore");
        itemSaveScore.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        openFileItem.setActionCommand("Open");
        openFileItem.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(newGameItem);
        newMenu.add(itemEnterName);
        newMenu.add(openFileItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemSaveScore);
        newMenu.add(itemExit);
        //Add Menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);    
        newPanel = new JPanel();
        hs = new HighScore();
        tk=new TimeKeeper();
        pack();
        setVisible (true);
    }
     
    private class MyKeyHandler extends KeyAdapter {
    	
    	public void keyPressed (KeyEvent theEvent){         
           switch (theEvent.getKeyCode()){
           		case KeyEvent.VK_UP:{
           			theArc.playerMove(-1,0,scrapMatrix,fl.dimondCount());
           			loadMatrixGui("updateLoad");
           			if (theArc.getLevel()==true){
           				cargarSigNivel();
           			}
           			break;
           		}
           		
           		case KeyEvent.VK_DOWN:{
           			theArc.playerMove(1,0,scrapMatrix,fl.dimondCount());
           			loadMatrixGui("updateLoad");
           			if (theArc.getLevel()==true){
           				cargarSigNivel();
           			}
           			break;
           		}
           		
           		case KeyEvent.VK_LEFT: {
           			theArc.playerMove(0,-1,scrapMatrix,fl.dimondCount());
           			loadMatrixGui("updateLoad");
           			if (theArc.getLevel()==true){
           				cargarSigNivel();
           			}
           			break;
           		}
           		
           		case KeyEvent.VK_RIGHT: { 
           			theArc.playerMove(0,1,scrapMatrix,fl.dimondCount());
           			loadMatrixGui("updateLoad");
           			if (theArc.getLevel()==true){
           				cargarSigNivel();
           			}
           			break;   
           		}
           }
           JLabel mainLabel=new JLabel("Total Dimonds Left to Collect"+theArc.getDimondsLeft()+"", JLabel.CENTER);
           JPanel dimondsPanel = new JPanel();
           dimondsPanel.add(mainLabel);
           cp.add(dimondsPanel,BorderLayout.SOUTH);
       }
   }
    
    public void actionPerformed(ActionEvent e){
    	if (e.getActionCommand().equals("Exit")){
    		new Timer(1000, updateCursorAction).stop();
            System.exit(0);   
        } else if (e.getActionCommand().equals("New Game")){
            return; 
        } else if(e.getActionCommand().equals("EnterName")){
            playerName=JOptionPane.showInputDialog("Please Enter your Earth Name");      
        } else if(e.getActionCommand().equals("HighScore")){
            ScoreGui sg = new ScoreGui();
            sg.ScoreGui();   
        } else if(e.getActionCommand().equals("SaveScore")){
            hs.addHighScore(playerName,tk.getMinutos(),tk.getSegundos(),levelNum);
        } else if(e.getActionCommand().equals("Open")){
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
            	fl.loadFile(chooser.getSelectedFile().getName());
                theArc.setExit(fl.ExitXCord(),fl.ExitYCord());
                loadMatrixGui("newLoad"); 
            }
         }
     }
     
     public void loadMatrixGui(String event){
    	 if (event == "newLoad"){       
    		 remove(newPanel);
             if(progBarPanel !=null) {
            	 remove(progBarPanel);
             }
             scrapMatrix = fl.getGameMatrix();
             /*
             String[][] temp = fl.getGameMatrix();
             scrapMatrix = new String[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];   
             for (int i = 0; i < scrapMatrix.length; i++){
            	 for (int j = 0; j < scrapMatrix[i].length; j++){
            		 scrapMatrix[i][j]= temp[i][j];
            	 }
             }*/
             crearTimer();
             crearBarraDProgreso();
             crearMaze();
        } else if(event =="updateLoad"){
        	scrapMatrix = theArc.getUpdatedMatrix();
            remove(newPanel);
            crearMaze();        
        }
    	cargarMazeImg();
        cp.add(newPanel);
        remove(fondoInicial);
        System.gc();//force java to clean up memory use.
        pack();
        setVisible (true);
        newPanel.grabFocus();  
     }

	private void cargarMazeImg() {
		for (int i = 0; i < labelMatrix.length; i++){
    		for (int j = 0; j < labelMatrix[i].length; j++){
    			labelMatrix[i][j]=  maze=new mazeObject(scrapMatrix[i][j]);//add our maze images into the gui
            }
    	}
	}

	private void crearBarraDProgreso() {
		progBarPanel = new JPanel();
		 progressBar = new JProgressBar(0, timeCalc.getMinutes()*100);
		 progressBar.setStringPainted(true);
		 progBarPanel.add(progressBar);
		 cp.add(progBarPanel,BorderLayout.NORTH);
	}

	private void crearTimer() {
		timeCalc = new TimeCalculator();
		 timeCalc.calcTimeforMaze(fl.dimondCount(),fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()); 
		 tiempoRestante = timeCalc.getMinutes();
		 tiempoMax = timeCalc.getSeconds();
		 tiempoTotal=0;
		 tiempoTranscurrido = new Timer(1000,updateCursorAction);
		 tiempoTranscurrido.start();
	}

	private void crearMaze() {
		newPanel = new JPanel();
		newPanel.setLayout(new GridLayout(fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()));      
		labelMatrix=new JLabel[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];
		newPanel.addKeyListener( new MyKeyHandler() );
	}
 
    public class mazeObject extends JLabel {
		private static final long serialVersionUID = 1L;
		private JLabel imageLabel;
        
    	public mazeObject(String fileName){
            fileName+=".png";
            JLabel fancyLabel;
            fancyLabel = new JLabel("",new ImageIcon(fileName),JLabel.LEFT);
            newPanel.add(fancyLabel);
        }
    }
        
    public void cargarSigNivel(){
        levelNum+=1;
        tk.TimeKeeper(tiempoRestante,tiempoMax);//The TimeKeeper object keeps a running tab of the total time the player has used.(for high score)
        tiempoTranscurrido.stop();
        theArc = new TheArchitect();
        catFileName+=01;
        String fileName="level"+catFileName+".maz";
        System.gc();
        fl.loadFile(fileName);
        scrapMatrix=fl.getGameMatrix();
        theArc.setExit(fl.ExitXCord(),fl.ExitYCord());
        loadMatrixGui("newLoad");         
    }
 
    Action updateCursorAction = new AbstractAction() {
    	
    public void actionPerformed(ActionEvent e)throws DemaciadoLento{
        tiempoMax-=1;
        tiempoTotal+=1;
        if(tiempoMax<0){
            tiempoMax = 60;
            tiempoRestante -= 1;
        }
    if(tiempoRestante == 0 && tiempoMax == 0){
        tiempoTranscurrido.stop();
        JLabel perdisteLabel = new JLabel("",new ImageIcon("yousuck.jpg"),JLabel.LEFT);
        cp.add(perdisteLabel);
        remove(newPanel);
        remove(progBarPanel);
        pack();
        setVisible (true);
        tiempoTranscurrido.stop();
        catFileName-=01;
    if(catFileName<01) {
    	throw new DemaciadoLento("Tardaste demaciado");
    } else
    	loadMatrixGui("newLoad");
    }
    progressBar.setValue(tiempoTotal);
    progressBar.setString(tiempoRestante+":"+tiempoMax);
    }
};

    private class DemaciadoLento extends RuntimeException{
    	
        public DemaciadoLento(String event){
            //the game is over, here we must tell our high score method to recond the details.
            hs.addHighScore(playerName,tk.getMinutos(),tk.getSegundos(),levelNum);
            JFrame frame = new JFrame("Warning");
            JOptionPane.showMessageDialog(frame, "You Stupid Ass, Did you eat to much for dinner?  Move Faster!");//the entire game has ended.
        }
    }
    
private HighScore hs;  
private int catFileName=01;
private Container cp;
private FileLoader fl = new FileLoader();
//create menu items
private JMenuBar menuBar;
private JMenu newMenu;
private JMenuItem itemExit;
private JMenuItem newGameItem;
private JMenuItem openFileItem;
private JMenuItem itemEnterName;
private JMenuItem itemHighScore;
private JMenuItem itemSaveScore;
//end create menu items
private JLabel fondoInicial;
private int tiempoMax;
private int tiempoTotal;
private int tiempoRestante;
private JPanel progBarPanel;
private JLabel[][] labelMatrix;
private TimeCalculator timeCalc;
private  JProgressBar progressBar;
private mazeObject maze;
private JPanel newPanel;
private TheArchitect theArc = new TheArchitect();
private String[][] scrapMatrix; 
private  Timer tiempoTranscurrido; 
private TimeKeeper tk;
private  String playerName;
private int levelNum=1;
}//end class   
