/* COP 3503C Assignment 3 Destroy 
This program is written by: Kyle Anthony Franklin */ 
import java.util.*;
public class destroy {
	public int[] parent;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		// the first three ints in the input
		int numNodes = scan.nextInt();
		int numConnections = scan.nextInt();
		int numBreaks = scan.nextInt();
		
		//disjoint set
		destroy set = new destroy(numNodes);
		
		//2d array for the connections
		int connections[][] = new int [numConnections][2];
		
		//array list, and tree set for the position of the breaks
		ArrayList<Integer> skips = new ArrayList<>();
		TreeSet<Integer> tempHolder = new TreeSet<Integer>();
		
		//out put array list
		ArrayList<Long> total = new ArrayList<>();
		int[] sizes = new int[numNodes];
		
		//reads in all the connections to a 2d array
		for(int i = 0; i<numConnections;i++) {
			connections[i][0] = scan.nextInt()-1;
			connections[i][1] = scan.nextInt()-1;
		}
		
		//stores the position that will be skipped to reference later 
		for(int i = 0; i< numBreaks;i++) {
			int temp = scan.nextInt()-1;
			skips.add(temp);
			tempHolder.add(temp);
		}
		
		//unions the connections with out the once that will be skipped
		int counter = 0;
		while (counter < numConnections) {
			int v1 = connections[counter][0];
			int v2 = connections[counter][1];
			//if it is a broken connection skip it
			if(!tempHolder.isEmpty()&&counter == tempHolder.first()) {
				counter++;
				tempHolder.pollFirst();
				continue;
			}
			set.union(v1, v2);
			counter++;
		}
		//calculates the size after the first part of unioning
		set.calulate(total,sizes);                                                                                                                                                           
		
		//adds in all the skipped connections
		for(int i=skips.size()-1;i>-1;i--) {
			int v1 = connections[skips.get(i)][0];
			int v2 = connections[skips.get(i)][1];
			boolean flag = false;
			//current size for the two numbers
			long one = sizes[set.find(v2)];
			long two = sizes[set.find(v1)];
			flag = set.union(v1, v2);
			//if the union changes the size calculate the new size
			if(!flag) {
				total.add(total.get(total.size()-1)-((one*one)+(two*two))+((one+two)*(one+two)));
				sizes[set.find(v2)] +=sizes[set.find(v1)];
				sizes[set.find(v1)] = sizes[set.find(v2)];	
			}
			else {
				total.add(total.get(total.size()-1));
			}
		}
		
		//prints out the output backwards
		for(int i = total.size()-1;i>-1;i--) {
			System.out.println(total.get(i));
		}
	}
	
	//creates the dj set
	public destroy(int n) {
		parent = new int[n];
		for(int i = 0; i<n; i++)
			parent[i] = i;
	}
	
	//find a specific element in the dj set
	public int find(int v) {
		if (parent[v] == v) return v;
		int res = find(parent[v]);
		parent[v] = res;
		return res;
	}
	
	//union function for the dj set, uses sort by rank
	public boolean union(int v1, int v2) {
		//boolean flag for a size check
		boolean sameRoot = false;
		int rootv1 = find(v1);
		int rootv2 = find(v2);
		if(rootv1 == rootv2) {
			sameRoot = true;
		}
		if(rootv2>rootv1) {
			parent[rootv2] = rootv1;
		}
		else {
			parent[rootv1] = rootv2;
		}
		return sameRoot;
	}
	
	//calculates and adds to the output array list
	public void calulate(ArrayList<Long> total, int [] sizes) {
		//a frequency array to help calculate easier 
		HashMap<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		for (int i = 0; i<parent.length; i++) {
			int holder = find(parent[i]);
			if(frequency.containsKey(holder)) {
				int temp = frequency.get(holder);
				temp++;
				frequency.put(holder,temp);
			}
			else {
				frequency.put(holder,1);
			}
		}
		//calculates the total connections
		long totalCon = 0;
		for(Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
		    int temp = entry.getValue();
		    sizes[entry.getKey()] = temp;
		    totalCon += (long)temp*temp;
		}
		total.add(totalCon);
	}
}