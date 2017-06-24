package queryprocessing;

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
		double length = a.length;
	 	result = 1 - ((6 * ssd(a, b)) / (length * (length*length - 1)));
	 	return result;
	 }
	 
	 public static void main(String[] args) {
	 	double[] a = {1,2,3,4,5,6,7,8,9,10};
	 	double[] b = {2,3,1,5,4,7,8,10,6,9};
	 	double[] c = {10,9,8,7,6,5,4,3,2,1};
	 	
	 	System.out.println(spearman(a, b) + "");
	 	System.out.println(spearman(a, c) + "");
	 	System.out.println(spearman(a, a) + "");
	 }
}
