/* COP 3503C Assignment 4 Maze 
This program is written by: Kyle Anthony Franklin 
*/ 
import java.util.*;

public class maze {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int row = scan.nextInt();
		int col = scan.nextInt();
		//hashmap to store teleporter points 
		HashMap<Character, List<coordinate>> tele = new HashMap<>();
		scan.nextLine();
		int startX=-1, startY=-1;
		//scanned in board
		char[][] board = new char[row][col];
		//board for marking visited
		boolean [][] visited = new boolean[row][col];
		//filling in the board
		for(int i=0;i<row;i++) {
			String temp = scan.nextLine();
			board[i]=temp.toCharArray();
		}
		//finding the start position, and filling teleporters
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				//finding the start position 
				if(board[i][j]=='*'){
					startX = j;
					startY = i;
				}
				// finding the positions of all the teleporters 
				else if(Character.isAlphabetic(board[i][j])) {
					coordinate temp = new coordinate(j,i);
					//if it has already been added add to it
					if(tele.containsKey(board[i][j])) {
						tele.get(board[i][j]).add(temp);
					}
					//else create a new entry to the hash map
					else {
						tele.put(board[i][j], new ArrayList<coordinate>());
						tele.get(board[i][j]).add(temp);
					}
				}
			}
		}
		//printing the output with a function call
		System.out.print(solve(board, visited, startX, startY, row, col, tele));
	}

	static int solve(char board[][], boolean visited[][], int x,int y, int row, int col, HashMap<Character, List<coordinate>> tele) {
		//holds the distance from the start
		int [][] output = new int[row][col];
		//starting position
		coordinate start= new coordinate(x,y);
		//bfs queue
		LinkedList<coordinate> queue = new LinkedList<coordinate>();
		queue.add(start);
		//bfs
		while(!queue.isEmpty()) {
			//creates the current node
			coordinate curPoint  = queue.poll();
			int curX = curPoint.x;
			int curY = curPoint.y;
			//modified dx dy matrix for the cardinal directions
			for(int dy = -1; dy <= 1; dy++) {
				for(int dx = -1; dx <= 1; dx++) {
					//if it goes out of bounds skip it
					if((dx == 0 && dy == 0)||(curX + dx < 0 ||curY + dy < 0)|| (curX + dx >= col || curY + dy >= row)){
						continue;
					}
					//if it is a directional skip it
					if(Math.abs(dx)==1 && Math.abs(dy)==1) {
						continue;
					}
					//if it has been visited already skip it
					if(visited[curY+dy][curX+dx]) {
						continue;
					}
					//if it is the end return the shortest path
					if(board[curY+dy][curX+dx]=='$') {
						return output[curY][curX]+1;
					}
					//add it if it is a dot
					else if(board[curY+dy][curX+dx]=='.') {
						visited[curY+dy][curX+dx] = true;
						coordinate newPoint  = new coordinate(curX+dx,curY+dy);
						output[curY+dy][curX+dx] = output[curY][curX]+1;
						queue.add(newPoint);
					}
					//if its a letter teleport to all letters 
					else if(Character.isAlphabetic(board[curY+dy][curX+dx])){
						visited[curY+dy][curX+dx] = true;
						coordinate boardPoint  = new coordinate(curX+dx,curY+dy);
						output[curY+dy][curX+dx] = output[curY][curX]+1;
						queue.add(boardPoint);
						//goes through each teleporter of a given letter, and adds them to the queue
						for(coordinate c : tele.get(board[curY+dy][curX+dx])) {
							
							//if it has been visited skip it
							if(visited[c.y][c.x]) {
								continue;
							}
							
							visited[c.y][c.x] = true;
							output[c.y][c.x] = output[curY+dy][curX+dx]+1;
							queue.add(c);
						}
						//deletes the letter so i would not have to check all of the same letter again
						tele.get(board[curY+dy][curX+dx]).clear();
					}
				}
			}
		}
		//if it reaches here it is unsolvable
		return -1;
	}

	// a class for x and y coordinates
	static public class coordinate {
		public int x;
		public int y;

		public coordinate(int x,int y) {
			this.x = x;
			this.y = y;
		}
	}	

}