/* 
COP 3503C Assignment 5 poly 
This program is written by: Kyle Anthony Franklin 
using Arughp guhas code intmult code as a reference
*/ 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class poly {
	private int length;
	private long[] coeff;

	public static void main(String[] args) throws IOException{
		//buffered reader and string tokenizer to speed up input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String degree = br.readLine();
		int power = ((int)Math.pow(2, Integer.parseInt(degree)));
		String poly = br.readLine();
		StringTokenizer st = new StringTokenizer(poly," ");
		
		//creating the first polynomial
		long temp[] = new long[power];
		int counter=0;
		while(st.hasMoreTokens()) {
			temp[counter]= Long.parseLong(st.nextToken());
			counter++;
		}
		poly firstPoly = new poly(temp);

		//creating the second polynomial 
		poly = br.readLine();
		st = new StringTokenizer(poly," "); 
		counter=0;
		while(st.hasMoreTokens()) {
			temp[counter]= Long.parseLong(st.nextToken());
			counter++;
		}
		poly secondPoly = new poly(temp);

		//multiplying the polynomials, and performing Karatsuba’s algorithm
		poly output = firstPoly.mult(secondPoly);

		//string builder for printing out the answer
		StringBuilder str = new StringBuilder();
		for(int i=0;i<output.length;i++) {
			str.append(output.coeff[i]+"\n");
		}
		System.out.print(str);
	}

	//constructor for polynomials
	public poly(long[] vals) {
		length = vals.length;
		coeff = Arrays.copyOf(vals, length);
	}

	//adding the polynomials together
	public poly add(poly other) {
		//create a new temp array for holding all values (using the math.max shouldn't matter but its safe to not assume for out of bounds)
		long[] res = new long[Math.max(length,other.length)];

		//fill temp array with added numbers
		for (int i=0; i<res.length; i++) {
			res[i] = coeff[i] + other.coeff[i];
		}

		//creating and returning the new polynomial 
		poly temp = new poly(res);
		return temp;
	}

	//subtracting the polynomials together
	public poly sub(poly other) {
		//create a copy of the existing polynomial to return
		poly temp = new poly(coeff);

		//subtract from the new polynomial
		for (int i=0; i<length; i++) {
			temp.coeff[i] -= other.coeff[i];	
		}

		//return new polynomial
		return temp;
	}

	//base case for multiplying
	public poly multSlow(poly other) {
		//create a new temp array for holding all values
		long[] res = new long[length+other.length-1];

		//multiply values and store them into the temp array
		for (int i=0; i<other.length; i++) {
			for (int j=0; j<length; j++) {
				res[i+j] += (coeff[i]*other.coeff[j]);
			}
		}

		//create and return new polynomial
		poly temp = new poly(res);
		return temp;
	}

	//Karatsuba’s algorithm
	public poly mult(poly other) {
		//base case of 32
		if (other.length <= 32) {
			return multSlow(other);
		}

		//split the polynomial in 4 pieces
		poly A = getLeft();
		poly B = getRight();
		poly C = other.getLeft();
		poly D = other.getRight();

		//recurse while multiplying left half's, and the right half's
		poly AC = A.mult(C);
		poly BD = B.mult(D);

		//Foil (A+B)(C+D)
		poly leftSum = A.add(B);
		poly rightSum = C.add(D);
		poly mid = leftSum.mult(rightSum);

		//(A+B)(C+D)-(AC+BD)
		mid = mid.sub(AC.add(BD));

		//combine polynomials together
		long[] res = new long[Math.max(AC.length, Math.max(BD.length, mid.length))+length];
		addIn(res, AC.coeff, 0);
		addIn(res, BD.coeff,length);
		addIn(res, mid.coeff, length/2);

		//create a final polynomial and return it
		poly out = new poly(res);
		return out;
	}

	// Returns the left half of this poly in its own poly.
	private poly getLeft() {
		poly temp = new poly(coeff);
		temp.length = length/2;
		temp.coeff = Arrays.copyOf(coeff, length/2);
		return temp;
	}

	// Returns the right half of this poly in its own poly.
	private poly getRight() {
		poly temp = new poly(coeff);
		temp.length = length/2;
		temp.coeff = Arrays.copyOfRange(coeff, length/2, length);
		return temp;
	}

	//shifts the final array to a specified spot and combines the entered values with existing ones
	public static void addIn(long[] total, long[] vals, int shift) {
		for (int i=shift; i<shift+vals.length; i++) {
			total[i] += vals[i-shift];
		}
	}
}