/* COP 3503C Assignment 2 Tentaizu 
This program is written by: Kyle Anthony Franklin */ 
import java.util.Scanner;

public class tentaizu {
	static int[][] board = new int [7][7];//given board
	static int[][] placed = new int [7][7];//board for bombs
	static int stars;//number of bombs
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numCases = scan.nextInt();
		for (int i=0; i<numCases; i++) {
			//filling in the board, and used arrays
			fillboard(scan);
			stars=0;
			System.out.println("Tentaizu Board #"+ (i+1)+":");
			//solving the board
			solve(0, 0);
			//print the board
			print();
		}
		scan.close();
	}
	static boolean solve(int x, int y) {
		//iterate to the next row,
		if(y == 7) {
			y = 0;
			x++;
		}
		if(x==7) {
			if(stars != 10) {//if not enough/to many stars return false
				return false;
			}
			for(int i =0;i<7;i++) {//final check for the whole board
				for(int j=0;j<7;j++) {
					if(board[i][j]>-1 && !check(i,j)) {
							return false;
					}
				}
			}
			return true;
		}

		if(x > 0 && y > 1) { //if there is a valid square for finalized spaces
			if(board[x - 1][y - 2]>-1 && !check(x-1,y-2))
				return false;
		}
		if(x > 1 && y == 0) {//if you just placed in the last column, check if the last placed bombs are valid
			if(board[x - 2][5]>-1 && !check(x-2,5)) {
				return false;
			}
			if(board[x - 2][6]>-1 && !check(x-2,6)) {
				return false;
			}
		}

		if(board[x][y]>-1){//if its a number skip it
			return solve(x, y + 1);
		}
		//place a star
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				if((dx == 0 && dy == 0)||(x + dx < 0 || y + dy < 0)||(x + dx >= 7 || y + dy >= 7)){
					continue;
				}
				placed[x + dx][y + dy]++;//if it doesn't go out of bounds, or is not x and y update placed
			}
		}
		board[x][y]=-2;// place a star at x,y
		stars++;//increase stars placed
		if(solve(x, y+1)) {//recurse
			return true;
		}
		stars--;//decrement stars placed
		board[x][y]=-1;//remove the star and place a dot at x,
		//remove a star
		for(int dx = -1; dx <= 1; dx++) {
			for(int dy = -1; dy <= 1; dy++) {
				if((dx == 0 && dy == 0)||(x + dx < 0 || y + dy < 0)||(x + dx >= 7 || y + dy >= 7)){
					continue;
				}
				placed[x + dx][y + dy]--;//if it doesn't go out of bounds, or is not x and y update placed
			}
		}
		return solve(x,y+1);//recurse 
	}
	static void fillboard(Scanner scan) {//self explanatory fill in the board
		for(int x=0; x<7;x++) {
			char[] row = scan.next().toCharArray();
			for(int y = 0; y < 7; y++) {
				placed[x][y] = 0;
				board[x][y] = -1;//-1 means a dot
				//if the current index is a number set used to the number
				if(Character.isDigit(row[y])) {
					board[x][y] = Integer.parseInt(String.valueOf(row[y]));
				}
			}
		}	
	}
	static void print() {
		for(int j =0; j<7;j++) {
			for(int k=0; k<7;k++) {
				if(board[j][k]==-1) {//print a dot
					System.out.print(".");
				}
				else if(board[j][k]==-2) {//print a star
					System.out.print("*");
				}
				else {//else print the number
					System.out.print(board[j][k]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	static boolean check(int x,int y) {//simple check statement, made because i used it a lot
		if(board[x][y] != placed[x][y]) {
			return false;
		}
		return true;
	}
}