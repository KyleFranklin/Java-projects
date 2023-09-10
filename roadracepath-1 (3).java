/* COP 3503C Assignment 6 Road race part 2 
This program is written by: Kyle Anthony Franklin 
*/ 
import java.text.DecimalFormat;
import java.util.Scanner;

public class roadracepath {

	public static int maxNum = Integer.MAX_VALUE;
	static final DecimalFormat df = new DecimalFormat("0.00");
	static int path[][];

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int l = scan.nextInt();
		int distance [] = new int[n];
		double info [][]=new double[n][l];
		double min [][] = new double[n][l];
		path = new int[n][l];

		//fill min with max num
		for(int i=0;i<n;i++) {
			for(int j =0;j<l;j++) {
				min[i][j] = maxNum;
			}
		}

		//scanning in the distacnes of each check
		for(int i=0;i<n;i++) {
			distance[i]=scan.nextInt();
		}

		//changes each input from km/hr to seconds
		for(int i=0;i<n;i++) {
			for(int j=0;j<l;j++) {
				info[i][j] = distance[i]/(scan.nextDouble()*(double)5/18);
			}
		}

		//Base case 
		for(int i=0;i<l;i++) {
			min[n-1][i] = info[n-1][i];
			
		}
		
		//finding a backwards path
		double dp[] = new double[l];
		dp = Dp(l,n,info,min);
		
		//find the smallest num in the last row to find the fastest path
		int index =0;
		for(int i=1;i<l;i++) {
			if(min[0][index]>min[0][i]) {
				index = i;
			}
		}
		
		//print out the array of paths
		System.out.print(index+1+" ");
		for(int i=0; i<n-1;i++ ) {
			System.out.print(path[i][index]+" ");	
		}
	}

	static double[] Dp(int l,int n,double info[][],double min[][]){
		//go through every i
		for(int i=n-2;i>=0;i--) {
			// go through every j
			for(int j=0;j<l;j++) {
				//look through every node in each row
				for(int k=0;k<l;k++) {
					double temp = min[i+1][k];
					//calculate
					double low = temp + (Math.pow (Math.abs(j-k),2)+5) + info[i][j];
					//update the lowest if needed
					if(min[i][j]>low) {
						min[i][j]=low;
						path[i][j] = k+1;
					}
				}
			}
		}
		//return the last row to find the fastest starting path
		return min[0];
	}
}
