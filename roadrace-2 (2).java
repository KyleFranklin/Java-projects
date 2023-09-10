/* COP 3503C Assignment 6 Road race part 1 
This program is written by: Kyle Anthony Franklin 
*/ 
import java.text.DecimalFormat;
import java.util.Scanner;

public class roadrace {

	public static int maxNum = Integer.MAX_VALUE;
	static final DecimalFormat df = new DecimalFormat("0.00");

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int l = scan.nextInt();
		int distance [] = new int[n];
		double info [][]=new double[n][l];
		double min [][] = new double[n][l];

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
			min[0][i] = info[0][i];
		}
		
		//dp output
		double dp[] = new double[l];
		dp=Dp(l,n,info,min);
		
		//finding the smallest value for the output
		double output=maxNum;
		for(int i=0;i<l;i++) {
			if(output>dp[i]){
				output = dp[i];
			}
		}
		
		//printing out the output rounded to 2 decimal places
		System.out.println(df.format(output));
	}
	
	static double[] Dp(int l,int n,double info[][],double min[][]){
		//go through every i
		for(int i=1;i<n;i++) {

			// go through every j
			for(int j=0;j<l;j++) {

				//looking at a previous rows j
				double temp = min[i-1][j];

				//look through every node in each row
				for(int k=0;k<l;k++) {
					
					//calculate
					double low = temp + (Math.pow (Math.abs(j-k),2)+5) + info[i][k];
					
					//update the lowest if needed
					if(min[i][k]>low) {
						min[i][k]=low;
					}
				}
			}
		}
		//return the last row of the smallest nums
		return min[n-1];
	}
}