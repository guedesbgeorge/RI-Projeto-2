package queryprocessing;

import java.util.Arrays;

public class RankCorrelation {
	// Sum of square distance
	public static double ssd(double [] a, double[] b){
		double result = 0;
		for(int i = 0; i < a.length; i++){
			result = result + Math.pow((a[i] - b[i]), 2);
		}
		return result;
	 }
	 
	public static double spearman(double [] a, double[] b){
		double result = 0;
		double k = a.length;
	 	result = 1 - ((6 * ssd(a, b)) / (k * (k*k - 1)));
	 	return result;
	 }
	
	public static boolean isEqual(double[] pairA, double[] pairB){
		return (pairA[0] == pairB[0]) && (pairA[1] == pairB[1]);
	}
	
	private static int findIndex(double[] a, double e){
		for(int i = 0; i < a.length; i++){
			if(a[i] == e){
				return i;
			}
		}
		return -1;
	}
	
	public static double[][] sortArray(double[] a){
		double[][] output = new double[a.length][2];
		double[] aux = new double[a.length];
		System.arraycopy(a, 0, aux, 0, a.length);
		Arrays.sort(a);
		for(int i = 0; i < a.length; i++){
			output[i][0] = findIndex(aux, a[i]);
			output[i][1] = a[i];
		}
		return output;
	}
	
	public static double[][] pairs(double[] a){
		double[] aux = new double[a.length];
		System.arraycopy(a, 0, aux, 0, a.length);
		
		double[][] sorted = sortArray(a);
		double[][] pairs = new double[(a.length*(a.length-1))/2][2];
		int index = 0;
		
		for(int i = 0; i < sorted.length; i++){
			for(int j = 0; j < sorted.length; j++){
				if(i != j){
					if(sorted[i][1] < sorted[j][1]){
						pairs[index][0] = sorted[i][0] + 1;
						pairs[index][1] = sorted[j][0] + 1;
						index++;
					}
				}
			}
		}
		System.arraycopy(aux, 0, a, 0, aux.length);
		a = aux;
		return pairs;
	}
	
	public static void printPairs(double[][] pairs){
		for(int i = 0; i < pairs.length; i++){
			System.out.print("[");
			System.out.print(pairs[i][0] + ", " + pairs[i][1]);	
			System.out.println("]");
		}
	}
	
	public static int discordant(double[][] a, double[][] b){
		int output = 0;
		for(int i = 0; i < a.length; i++){
			if(!contains(a[i], b)){
				output += 1;
			}
		}
		return output;
	}
	
	public static boolean contains(double[] a, double[][] b){
		for(int i = 0; i < b.length; i++){
			if(a[0] == b[i][0] && a[1] == b[i][1]){
				return true;
			}
		}
		return false;
	}
	
	public static double kendaltau(double[] a, double[] b){
		int d = discordant(pairs(a), pairs(b)) + discordant(pairs(b), pairs(a));
		int k = a.length;
		return 1 - ((double)2*d)/((double)k*(k-1));
	}
	
	 public static void main(String[] args) {
	 	double[] a = {1,2,3,4,5,6,7,8,9,10};
	 	double[] b = {2,3,1,5,4,7,8,10,6,9};
	 	double[] c = {10,9,8,7,6,5,4,3,2,1};
	 	
	 	System.out.println(spearman(a, b));
	 	System.out.println(spearman(a, c));
	 	System.out.println(spearman(a, a));
	 	System.out.println();
	 	
	 	System.out.println(kendaltau(a, b));
	 	System.out.println(kendaltau(a, c));
	 	System.out.println(kendaltau(a, a));
	 	
	 	System.out.println();
	 	
	 	double[] d = {1,2,3,4,5};
	 	double[] e = {2,3,1,5,4};
	 	
	 	double[] x1 = {1,2,3,4,5,6,7,8,9,10,11,12};
	 	double[] x2 = {1,2,4,3,6,5,8,7,10,9,12,11};
	 	
	 	System.out.println(kendaltau(d, e));
	 	System.out.println();
	 	
	 	System.out.println(kendaltau(x1, x2));
	 	System.out.println();

	 	System.out.println(discordant(pairs(d), pairs(e)));
	 	System.out.println();
	 	
	 	printPairs(pairs(d));
	 	System.out.println();
	 	
	 	printPairs(pairs(e));
	 	System.out.println();
	 	
	 	printPairs(sortArray(e));
	 	System.out.println();
	 }
}
