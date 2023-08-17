import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;
/*
 * Name: QUAN TRAN
 * ITEC 4700 
 * Project - part 2
 */
/**
 * Tic-Tac-Toe: Two-player Graphics version with Simple-OO
 */
@SuppressWarnings("serial")
public class TTTGraphics2P extends JFrame {
	static class AI{

		private char map[][];
		/*
		 * e= Empty
		 * #= boarder
		 * p= occupied by player
		 * c= computer respond.
		 * */
		public AI(){
			map=new char[ROWS+2][COLS+2];
			resetMap();
			
		}
		public void resetMap(){
			int i,j;
			// IMPLEMENT EMPTY MAP WITH BOARDER
			EmptyCells=0;
			for (i=0;i<map.length;i++){
				for (j=0;j<map[i].length;j++){
					map[i][j]=( ((i==0)||(i==(map.length-1)))
							|| ((j==0)||(j==(map[i].length-1))))
							?'#':'e';
					if (map[i][j]=='e') EmptyCells++;
				}
			}
		}
		public boolean isTouched(int r,int c){
			int i,j;
			//  checking around the cell [i,j]
			for (i=-1;i<=1;i++)  for (j=-1;j<=1;j++){
				if ((map[r+i][c+j]=='p')||(map[r+i][c+j]=='c')) return true;
			}
			return false;
		}
		public double evaluatePositionforP(char player, final int row, final int col){
			if (map[row][col]!='e') 
				if (map[row][col]!=player)  return 0;
			int r,c,i,j;
			double TotalValue=0, subValue=0;
			//mainDiag
			for (i=(1-winningPieces);i<=0;i++){
				subValue=16;
				r=row+i; c=col+i;
				if (r<=0||c<=0||r>ROWS||c>COLS) {
					subValue=0;
				} else{
					for (j=0;j<winningPieces;j++){
						if (map[r+j][c+j]=='e'){
							subValue=subValue/64.0;
						}else{
							if (map[r+j][c+j]!=player){
								subValue=0;
								break;
							}
						}
					}
				}
				TotalValue=TotalValue+subValue;
			}
			
			//subDiag
			for (i=(1-winningPieces);i<=0;i++){
				subValue=16;
				r=row+i; c=col-i;
				if (r<=0||c<=0||r>ROWS||c>COLS) {
					subValue=0;
				} else{
					for (j=0;j<winningPieces;j++){
						if (map[r+j][c-j]=='e'){
							subValue=subValue/64.0;
						}else{
							if (map[r+j][c-j]!=player){
								subValue=0;
								break;
							}
						}
					}
				}
				TotalValue=TotalValue+subValue;
			}
			
			//column
			for (i=(1-winningPieces);i<=0;i++){
				subValue=16.0;
				r=row+i; c=col;
				if (r<0||c<0||r>ROWS||c>COLS) {
					subValue=0;
				} else{
					for (j=0;j<winningPieces;j++){
						if (map[r+j][c]=='e'){// EMPTY CELLS IN STREAK 
							subValue=subValue/64.0;
							// EMPTY CELL CAN BE OCCUPIED BY OPPONENT later
						}else{
							if (map[r+j][c]!=player){// the streak is broken 
								subValue=0;
								break;
							}
						}
					}
				}
				TotalValue=TotalValue+subValue;
			}
			//row
			for (i=(1-winningPieces);i<=0;i++){
				subValue=16.0;
				r=row; c=col+i;
				if (r<0||c<0||r>ROWS||c>COLS) {
					subValue=0;
				} else{
					for (j=0;j<winningPieces;j++){
						if (map[r][c+j]=='e'){
							subValue=subValue/64.0;
							// only need to dSrease EVERY EMPTY
						}else{
							if (map[r][c+j]!=player){
								/// WHEN OPPONENT BLOCKED THE POSIBILITY
								subValue=0;
								break;
							}
						}
						
					}
				}
				TotalValue=TotalValue+subValue;
			}
			return  TotalValue;			
		}
		public boolean isFinishMove(char player,int rowSelected,int colSelected){
			int r,c,i;
			if (map[rowSelected][colSelected]!='e') return false;
			map[rowSelected][colSelected]=player;
			  // CHECK MAIN DIAGNAL0
			  r=rowSelected; c=colSelected;
			  while ((r>0)	&&(c>0)){
				  if (map[r-1][c-1]==player){
					  r--; c--;
				  }
				  else 
					  break;
			  }
			  for (i=0;i<winningPieces;i++){
				  if ((r+i>=ROWS) || (c+i>=COLS)) break;
				  if (map[r+i][c+i]!=player) break;
			  }
			  if (i>=winningPieces) {
				  map[rowSelected][colSelected]='e';
				  return true;
			  }
			  
			// CHECK SUB DIAGNAL
			  r=rowSelected; c=colSelected;
			  while (true){
				  if (map[r-1][c+1]==player){
					  r--; c++;
				  }
				  else 
					  break;
			  }
			  for (i=0;i<winningPieces;i++){
				  if (map[r+i][c-i]!=player) break;
			  }
			  if (i>=winningPieces) {
				  map[rowSelected][colSelected]='e';
				  return true;
			  }
			// CHECK ROW
			  r=rowSelected; c=colSelected;
			  while (true){
				  if (map[r][c-1]==player){
					  c--;
				  }
				  else 
					  break;
			  }
			  for (i=0;i<winningPieces;i++){
				  if (map[r][c+i]!=player) break;
			  }
			  if (i>=winningPieces) {
				  map[rowSelected][colSelected]='e';
				  return true;
			  }
			// CHECK COLUMN
			  r=rowSelected; c=colSelected;
			  while (true){
				  if (map[r-1][c]==player){
					  r--;
				  }
				  else 
					  break;
			  }
			  for (i=0;i<winningPieces;i++){
				  if (map[r+i][c]!=player) break;
			  }
			  if (i>=winningPieces) {
				  map[rowSelected][colSelected]='e';
				  return true;
			  }
			  map[rowSelected][colSelected]='e';
			  return false;
		}
		
		static class cell{
			public int r,c;			public double value;
			public cell(int row, int col, double val){
				this.r=row; this.c=col; this.value=val;
			}
			public cell betterVCell(cell c1, cell c2){
				if (c1.value==c2.value){
					double x=ROWS/2.0, y=COLS/2.0;
					double d1=sqr(c1.r-x) + sqr(c1.c-y);
					double d2=sqr(c2.r-x) + sqr(c2.c-y);
					if (d1<d2) return c1;
					else return c2;
				}
				if (c1.value>c2.value) return c1;
				else return c2;
				
			}
			private double sqr(double s){ return s*s;}
		}
	
		public char opponent(char p){
			if (p=='p') return 'c';
			return 'p';
		}
		public int EmptyCells;

		// explore upto [Maxlevel] of moves after [player] make move. 
		public cell bestMove(int MaxLevel, char player){
			// phase 1:CHECK  no more cell avalable. -> GAME DRAW
			if (EmptyCells==0) return new cell(0,0,0);
			
			
			int i,j;
			//  phase 2: CHECK IF the player can finish game. 
			//            if so obviously bestmove,
			for (i=1;i<=ROWS;i++){
				for (j=1;j<=COLS;j++) {
					if (map[i][j]=='e')
						if(isFinishMove(player, i,j)) 
							return new cell(i,j,
									Integer.MAX_VALUE);
				}
			}
			
			
			cell ret=new cell(0,0,(double)Integer.MIN_VALUE);
			//phase 3:  MAXLEVEL REACHED. 
			//			Need a decision immediately. 
			//			Don't care opponent's next move any more. 
			if (MaxLevel<=0){ // NO MORE EXPLORING
				for (i=1;i<=ROWS;i++){
					for (j=1;j<=COLS;j++) if ((isTouched(i,j)==true)&&(map[i][j]=='e')){
						ret=ret.betterVCell(ret, 
								new cell(i,j, evaluatePositionforP(player, i, j)));						
					}
				}
				return ret;
			}
			
			
			
			ret=new cell(0,0,(double)Integer.MAX_VALUE);

			// PHASE 4: Explore opponent's moves. 		// Mini-max algorithm:
	// The value of return is the minimum best response from opponent
			for (i=1;i<=ROWS;i++){ 
				for (j=1;j<=COLS;j++)  
					if ((isTouched(i,j)==true) && (map[i][j]=='e')){
						
						//SUPPOSE
						map[i][j]=player;
						EmptyCells--;
			
						cell bestReact = bestMove(MaxLevel-1, 
								opponent(player));
						// react from  opponent
						if ((ret.r==0)&&(ret.c==0)) 
							ret=new cell (i,j,-bestReact.value);
						else
							ret=ret.betterVCell(ret, new cell (
								i,j, - bestReact.value));
						/// ==> reverse the value of opponent
						// ROLLBACK
						map[i][j]='e';
						EmptyCells++;
					}
			}
			return ret;
		}
		
		public void playerMakeMove(int row, int col){
			// SET PERManent// no roll back
			if (map[row + 1][col + 1]=='e'){ 
					map[row+1][col+1]='p';
					EmptyCells--;
					doRespond();
			}
		}
		public void doRespond(){
			// SET PERManent// no roll back

			cell bestReact = bestMove(((ROWS*COLS)<=16)?6:3,'c');
			map[bestReact.r][bestReact.c]='c';
			EmptyCells--;
			computerReact(bestReact.r-1,bestReact.c-1);
			
		}
		
 	}
   // Named-constants for the game board
   public static int ROWS = 3;  // ROWS by COLS cells
   public static int COLS = 3;
   
   // Named-constants of the various dimensions used for graphics drawing
   public static final int CELL_SIZE = 100; // cell width and height (square)
   public static int CANVAS_WIDTH;  // the drawing canvas
   public static int CANVAS_HEIGHT;
   public static final int GRID_WIDTH = 8;                   // Grid-line's width
   public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
   // Symbols (cross/nought) are displayed inside a cell, with padding from border
   public static final int CELL_PADDING = CELL_SIZE / 6;
   public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
   public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
 
   // Use an enumeration (inner class) to represent the various states of the game
   public enum GameState {
      PLAYING, DRAW, CROSS_WON, NOUGHT_WON,PAUSE
   }
   static int winningPieces;
   private static GameState currentState;  // the current game state
 
   // Use an enumeration (inner class) to represent the seeds and cell contents
   public enum Seed {
      EMPTY, CROSS, NOUGHT
   }
   private static Seed currentPlayer;  // the current player
 
   private static Seed[][] board   ; // Game board of ROWS-by-COLS cells
   private static DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
   private static JLabel statusBar;  // Status Bar
   public static AI computerControl;
   public static void computerReact(int rowSelected, int colSelected){
       if (currentState == GameState.PLAYING) {
           if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                 && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
              board[rowSelected][colSelected] = currentPlayer; // Make a move
              updateGame(currentPlayer, rowSelected, colSelected); // update state
              // Switch player
              currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
           }
        } else {       // game over
           initGame(); // restart the game
        }
        // Refresh the drawing canvas
        canvas.repaint();  // Call-back paintComponent().
   }
   /** Constructor to setup the game and the GUI components */
   public TTTGraphics2P() {
	  Scanner reader=new Scanner(System.in);
	  System.out.print("Input the number of rows of the board: ");
	  ROWS=reader.nextInt();
	  
	  System.out.print("Input the number of columns of the board: ");
	  COLS=reader.nextInt();
	  
	  System.out.print("Input the number of winning pieces: ");
	  winningPieces=reader.nextInt();
	  if ((ROWS<=0)||(COLS<=0)||(winningPieces<=0)||(winningPieces>Math.max(ROWS, COLS))){
		  System.out.println();
		  System.out.println();
		  System.out.println("Invalid input. Unable to process. Program terminated!");
		  System.exit(0);
	  }
	  // Recalculating:
	  CANVAS_WIDTH = CELL_SIZE * COLS;  // the drawing canvas
	  CANVAS_HEIGHT = CELL_SIZE * ROWS;
	  
	  computerControl=new AI();
	  computerControl.resetMap();
      canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
     
      // The canvas (JPanel) fires a MouseEvent upon mouse-click
      canvas.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
        	int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int rowSelected = mouseY / CELL_SIZE;
            int colSelected = mouseX / CELL_SIZE;
            if (currentState == GameState.PLAYING) {
               if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                     && colSelected < COLS && board[rowSelected][colSelected] == Seed.EMPTY) {
                  board[rowSelected][colSelected] = currentPlayer; // Make a move
                  
                  updateGame(currentPlayer, rowSelected, colSelected); // update state
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                  if (currentState == GameState.PLAYING)
                	  computerControl.playerMakeMove(rowSelected, colSelected);
               }
            } else {       // game over
               initGame(); // restart the game
            }
            // Refresh the drawing canvas
            repaint();  // Call-back paintComponent().
         }
      });
 
      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel("  ");
      statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
      statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
 
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      cp.add(canvas, BorderLayout.CENTER);
      cp.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();  // pack all the components in this JFrame
      setTitle("Tic Tac Toe");
      setVisible(true);  // show this JFrame
 
      board = new Seed[ROWS][COLS]; // allocate array
      initGame(); // initialize the game board contents and game variables
   }
 
   /** Initialize the game-board contents and the status */
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = Seed.EMPTY; // all cells empty
         }
      }
      computerControl.resetMap();
      currentState = GameState.PLAYING; // ready to play
      currentPlayer = Seed.CROSS;       // cross plays first
   }
 
   /** Update the currentState after the player with "theSeed" has placed on
       (rowSelected, colSelected). */
   public static void updateGame(Seed theSeed, int rowSelected, int colSelected) {
      if (hasWon(theSeed, rowSelected, colSelected)) {  // check for win
         currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (isDraw()) {  // check for draw
         currentState = GameState.DRAW;
      }
      // Otherwise, no change to current state (still GameState.PLAYING).
   }
 
   /** Return true if it is a draw (i.e., no more empty cell) */
   public static boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (board[row][col] == Seed.EMPTY) {
               return false; // an empty cell found, not draw, exit
            }
         }
      }
      return true;  // no more empty cell, it's a draw
   }
 
   /** Return true if the player with "theSeed" has won after placing at
     0  (rowSelected, colSelected) */
   public static boolean hasWon(Seed theSeed, int rowSelected, int colSelected) {
	  int r,c,i;
	  // CHECK MAIN DIAGNAL
	  r=rowSelected; c=colSelected;
	  while ((r>0)&&(c>0)){
		  if (board[r-1][c-1]==theSeed){
			  r--; c--;
		  }
		  else 
			  break;
	  }
	  // r,c now denote the top left
	  for (i=0;i<winningPieces;i++){
		  if ((r+i>=ROWS) || (c+i>=COLS)) break;
		  if (board[r+i][c+i]!=theSeed)break;
	  }
	  if (i>=winningPieces) return true;
	  
	// CHECK SUB DIAGNAL
	  r=rowSelected; c=colSelected;
	  while ((r>0)&&(c+1<COLS)){
		  if (board[r-1][c+1]==theSeed){
			  r--; c++;
		  }
		  else 
			  break;
	  }
	  for (i=0;i<winningPieces;i++){
		  if ((r+i>=ROWS) || (c-i<0)) break;
		  if (board[r+i][c-i]!=theSeed) break;
	  }
	  if (i>=winningPieces) return true;
	// CHECK ROW
	  r=rowSelected; c=colSelected;
	  while ((c>0)){
		  if (board[r][c-1]==theSeed){
			  c--;
		  }
		  else 
			  break;
	  }
	  for (i=0;i<winningPieces;i++){
		  if ((c+i>=COLS)) break;
		  if (board[r][c+i]!=theSeed) break;
	  }
	  if (i>=winningPieces) return true;
	  	  
	// CHECK COLUMN
	  r=rowSelected; c=colSelected;
	  while ((r>0)){
		  if (board[r-1][c]==theSeed){
			  r--;
		  }
		  else 
			  break;
	  }
	  for (i=0;i<winningPieces;i++){
		  if ((r+i>=ROWS)) break;
		  if (board[r+i][c]!=theSeed) break;
	  }
	  if (i>=winningPieces) return true;
		  	      g
	  return false;
	  /*
      return (board[rowSelected][0] == theSeed  // 3-in-the-row
            && board[rowSelected][1] == theSeed
            && board[rowSelected][2] == theSeed
       || board[0][colSelected] == theSeed      // 3-in-the-column
            && board[1][colSelected] == theSeed
            && board[2][colSelected] == theSeed
       || rowSelected == colSelected            // 3-in-the-diagonal
            && board[0][0] == theSeed
            && board[1][1] == theSeed
            && board[2][2] == theSeed
       || rowSelected + colSelected == 2  // 3-in-the-opposite-diagonal
            && board[0][2] == theSeed
            && board[1][1] == theSeed
            && board[2][0] == theSeed);
*/ 
   }
 
   /**
    *  Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
    */
   class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {  // invoke via repaint()
         super.paintComponent(g);    // fill background
         setBackground(Color.WHITE); // set its background color
 
         // Draw the grid-lines
         g.setColor(Color.LIGHT_GRAY);
         for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                  CANVAS_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
         }
         for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                  GRID_WIDTH, CANVAS_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
         }
 
         // Draw the Seeds of all the cells if they are not empty
         // Use Graphics2D which allows us to set the pen's stroke
         Graphics2D g2d = (Graphics2D)g;
         g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
               BasicStroke.JOIN_ROUND));  // Graphics2D only
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               int x1 = col * CELL_SIZE + CELL_PADDING;
               int y1 = row * CELL_SIZE + CELL_PADDING;
               if (board[row][col] == Seed.CROSS) {
                  g2d.setColor(Color.RED);
                  int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                  int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                  g2d.drawLine(x1, y1, x2, y2);
                  g2d.drawLine(x2, y1, x1, y2);
               } else if (board[row][col] == Seed.NOUGHT) {
                  g2d.setColor(Color.BLUE);
                  g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
               }
            }
         }
 
         // Print status-bar message
         if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Seed.CROSS) {
               statusBar.setText("X's Turn");
            } else {
               statusBar.setText("O's Turn");
            }
         } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
         } else if (currentState == GameState.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
         } else if (currentState == GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
         }
      }
   }
 
   /** The entry main() method */
   public static void main(String[] args) {
      // Run GUI codes in the Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new TTTGraphics2P(); // Let the constructor do the job
         }
      });
   }
}