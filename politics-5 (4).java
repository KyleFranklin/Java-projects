/* COP 3503C Assignment 1 Politics 
This program is written by: Kyle Anthony Franklin */ 

import java.util.*;

public class politics {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int numCan=0, numSup=0;

		numCan = scan.nextInt();
		numSup = scan.nextInt();

		while(numCan != 0 && numSup != 0) {
			//string arrays to represent the supporters and who they vote for
			String[] sNames = new String[numSup];
			String[] vNames = new String[numSup];

			// a 2d int array to keep track of the position, and the order of the position of the supporter
			Integer[][] position = new Integer[numSup][2];

			// a linkedHashMap that keeps track of all the candidates, linkedHashMaps keep insertion order
			LinkedHashMap<String, Integer> allCan = new LinkedHashMap<String, Integer>();

			//adds each candidate into the linkedHashMap at the end, giving them an index of their position
			for (int i = 0; i < numCan; i++) {
				allCan.put(scan.next(),allCan.size());
			}

			//update the position, names, vote, and the order for each supporter
			for(int i =0; i<numSup;i++) {
				position[i][1] = i;
				sNames[i] = scan.next();
				vNames[i] = scan.next();
				//if the candidate the supporter voted for is not in the linkedHashMap add it at the end
				if(!allCan.containsKey(vNames[i])) {
					allCan.put(vNames[i], allCan.size());
				}
				position[i][0] = allCan.get(vNames[i]);
			}
			// sort the position array by the order 
			Arrays.sort(position, new Comparator<Integer[]>() {
			    public int compare(Integer[] a, Integer[] b) {
			        return Integer.compare(a[0], b[0]);
			    }
			});
			// print out the supporter array in the correct order
			for(int i=0; i< numSup;i++) {
				System.out.println(sNames[position[i][1]]);
			}
			//scan the new numbers for supporters and candidates
			numCan = scan.nextInt();
			numSup = scan.nextInt();
		}
		scan.close();
	}
}