import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class GUI extends JFrame {
	
	public boolean resetter = false;
	
	int spacing = 5;
	int neights = 0;
	public int mx = -100;
	public int my = -100;
	
	Date startDate = new Date();
	Date endDate;
	
	
	
	public int smileyX = 605;
	public int smileyY = 5;
	
	public int smileyCenterX = smileyX  + 37;
	public int smileyCenterY = smileyY  + 53;
	
	public int timeX = 1130;
	public int timeY = 5;
	
	public int vicMesX = 740;
	public int vicMesY = -50;
	String vicMes = "Pending defeat ;)";
	
	public int sec = 0;
	
	
	
	Random rand=  new Random();
	
	public boolean happines = true;
	
	public boolean victory = false;
	
	public boolean defeat = false;
	
	int[][] mines = new int[16][9];
	int[][] neighbors = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];
	public GUI() {
		this.setTitle("Minesweeper");
		this.setSize(1286,829);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(rand.nextInt(100)< 20) {
					mines[i][j] = 1;
				}else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
			}
		}
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				neights = 0;
				for(int m = 0; m < 16; m++) {
					for(int n = 0; n < 9; n++) {
						if(!(m==i && n == j)) {
							if(isN(i, j, m, n) == true) 
								neights++;
							
						}
					}	
				}
					neighbors[i][j] = neights;
			}
		}
		
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		 Click click = new Click();
		 this.addMouseListener(click);
	}
	
	public class Board extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 1280, 800);
			g.setColor(Color.gray);
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 9; j++) {
					g.setColor(Color.gray);
					
				
					
					/*if(mines[i][j]==1) {
						g.setColor(Color.yellow);
					}*/
					
					if(revealed[i][j]==true) {
						g.setColor(Color.white);
						if(mines[i][j] == 1) {
							g.setColor(Color.red);
						}
							
					}
					
					
					//hovering over a box
					if(mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing);
					if(revealed[i][j]==true) {
						g.setColor(Color.black);
							if(mines[i][j] == 0 && neighbors[i][j] != 0)   {
								if(neighbors[i][j] ==1) {
									g.setColor(Color.blue);
								}else if(neighbors[i][j] ==2) {
									g.setColor(Color.GREEN);
								}else if(neighbors[i][j] ==3) {
									g.setColor(Color.RED);
								}else if(neighbors[i][j] ==4) {
									g.setColor(new Color(0,0,123));
								}else if(neighbors[i][j] ==5) {
									g.setColor(new Color(178,34,34));
								}else if(neighbors[i][j] ==6) {
									g.setColor(new Color(72,209,204));
								}else if(neighbors[i][j] ==8) {
									g.setColor(Color.DARK_GRAY);
								}
						
						
								g.setFont(new Font("Tahoma",Font.BOLD,40));
								g.drawString(Integer.toString(neighbors[i][j]),i*80+27, j*80+80+55);
							}else if(mines[i][j] == 1){
								g.fillRect(i*80+10+20, j*80+80+20, 20,40);
								g.fillRect(i*80+20, j*80+80+10+20, 40,20);
								g.fillRect(i*80+5+20, j*80+80+5+20, 30,30);
								g.fillRect(i*80+38, j*80+80+15, 4,50);
								g.fillRect(i*80+15, j*80+80+38, 50,4);
								happines = false;
						}
					}
					
					
				}
			}
			//smiley painting
			g.setColor(Color.YELLOW);
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileyX+15, smileyY+15, 10, 10);
			g.fillOval(smileyX+45, smileyY+15, 10, 10);
			
			if(happines == true) {
				g.fillRect(smileyX+20, smileyY+50, 30,5);
				g.fillRect(smileyX+17, smileyY+45, 5,5);
				g.fillRect(smileyX+48, smileyY+45, 5,5);
			}else {
				g.fillRect(smileyX+20, smileyY+50, 30,5);
				g.fillRect(smileyX+17, smileyY+55, 5,5);
				g.fillRect(smileyX+48, smileyY+55, 5,5);
			}
			
			//time counter paintin
			g.setColor(Color.BLACK);
			g.fillRect(timeX, timeY, 140, 70);
			if(defeat == false && victory == false) {
				sec = (int)((new Date().getTime()-startDate.getTime())/1000);
			}
			
			if(sec > 999) {
				sec=  999;
			}
			g.setColor(Color.white);
			if(victory == true) {
				g.setColor(Color.green);
			}else if(defeat == true) {
				g.setColor(Color.red);
				
			}
			g.setFont(new Font("Tahoma",Font.PLAIN,80));
			if(sec < 10) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+65);
			}else if(sec < 100) {
				g.drawString("0"+Integer.toString(sec), timeX, timeY+65);
			}else {
				g.drawString(Integer.toString(sec), timeX, timeY+65);
			}
			//Victory meessage
			if(victory == true) {
				g.setColor(Color.GREEN);
				vicMes = "You Win!";
				
			}else if(defeat == true) {
				g.setColor(Color.RED);
				vicMes = "DEFEAT";
			}
			//displaying victory or lose message
			if(victory == true || defeat == true) {
				vicMesY = (int) (-50 + (new Date().getTime() - endDate.getTime())) / 10;
				if(vicMesY > 70) {
					vicMesY = 70;
				}
				g.setFont(new Font("Tahome", Font.PLAIN,70));
				g.drawString(vicMes, vicMesX, vicMesY);
			}
			
		}

	}
	
	
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
			mx = e.getX();
			my = e.getY();
			
		}
		
	}
	
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
			if(inBoxX() != -1 && inBoxY() != -1) {
				revealed[inBoxX()][inBoxY()] = true;
			}
			if(inBoxX() != -1 && inBoxY() != -1) {
				System.out.println("Mouse is in the [" + inBoxX() + " " + inBoxY() +"], Number of mine neighs: " + neighbors[inBoxX()][inBoxY()]);
			}else {
				System.out.println("Mouse is not inside any box");
			}
			
			
			if(inSmiley() == true) {
				System.out.println("Is in smiley");
				resetAll();
			}else {
				System.out.println("Is not inside smiley");
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	/*public void revealAll() {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				revealed[i][j] = true;
					
					
				
			}
		}
		
	}*/
	public void checkVictoryStatus() {
		if(defeat == false) {
			for(int i = 0; i < 16; i++) {
				for(int j = 0; j < 9; j++) {
					if(revealed[i][j] == true && mines[i][j] == 1) {
						defeat = true;
						happines = false;
						endDate = new Date();
					}
				}
			}
		}
		if(totalBoxesRevealed() >= 144 - totalMines() && victory == false) {
			victory = true;
			endDate = new Date();
		}
		
	}
	public int totalMines() {
		int total = 0;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(mines[i][j] == 1) {
					total++;
				}
			}
		}
		return total;
	}
	public int totalBoxesRevealed() {
		int total = 0;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(revealed[i][j] == true) {
					total++;
				}
			}
		}
		return total;
	}
	
	public void resetAll() {
		resetter = true;
		vicMes = "Pending defeat";
		startDate = new Date();
		 vicMesY = -50;
		happines = true;
		victory = false;
		defeat = false;
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				if(rand.nextInt(100)< 20) {
					mines[i][j] = 1;
				}else {
					mines[i][j] = 0;
				}
				revealed[i][j] = false;
				flagged[i][j]= false;
			}
		}
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
				neights = 0;
				for(int m = 0; m < 16; m++) {
					for(int n = 0; n < 9; n++) {
						if(!(m==i && n == j)) {
							if(isN(i, j, m, n) == true) 
								neights++;
							
						}
					}	
				}
					neighbors[i][j] = neights;
			}
		}
		resetter = false;
	}
	
	
	public boolean inSmiley() {
		int diff = (int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
		System.out.println(diff);
		if(diff < 40) {
			
			return true;
		}
		return false;
	}
	
	public int inBoxX() {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {
			
				if(mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) {
					return i;
				}
				
			}
		}
		return -1;
	}
	public int inBoxY() {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 9; j++) {			
				if(mx >= spacing+i*80 && mx < spacing+i*80+80-2*spacing && my >= spacing+j*80+80+26 && my < spacing+j*80+26+80+80-2*spacing) {
					return j;
				}				
			}
		}
		return -1;
	}
	
	
	public boolean isN(int mX, int mY, int cX, int cY) {
		if(mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY -cY > -2 && mines[cX][cY] == 1 ) {
			return true;
		}
		return false;
		
	}

	
	
	
	
	
	
}


