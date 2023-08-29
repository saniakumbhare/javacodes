/*************************************************************************
 *  Compilation:  javac PolygonTransform.java
 *  Execution:    java PolygonTransform
 *
 *  @author:
 *
 *************************************************************************/

    public class PolygonTransform {

        // Returns a new array object that is an exact copy of the given array. 
       // The given array is not mutated. 
       public static double[] copy(double[] array) {
           double[] arr = new double[array.length];
           for (int i = 0; i < array.length; i++) {
               arr[i] = array[i];
           }
           return arr;
       }
        
       // Scales the polygon by the factor alpha. 
       public static void scale(double[] x, double[] y, double alpha) {
           for (int i = 0; i < x.length; i++) {
               x[i] = x[i] * alpha;
           }
    
           for (int i = 0; i < y.length; i++) {
               y[i] = y[i] * alpha;
           }
       }
    
       // Translates the polygon by (dx, dy). 
       public static void translate(double[] x, double[] y, double dx, double dy) {
           for (int i = 0; i < x.length; i++) {
            x[i] = x[i] + dx;
           }
    
           for (int i = 0; i < y.length; i++) {
            y[i] = y[i] + dy;
           }
       }
        
       // Rotates the polygon theta degrees counterclockwise, about the origin. 
       public static void rotate(double[] x, double[] y, double theta) {
           double x1 = 0;
           double y1 = 0;
    
           for (int i = 0; i < x.length; i++) {
               x1 = x[i];
               y1 = y[i];
               x[i] = (x1*Math.cos(Math.toRadians(theta))) - (y1*Math.sin(Math.toRadians(theta)));
               y[i] = (y1*Math.cos(Math.toRadians(theta))) + (x1*Math.sin(Math.toRadians(theta)));
           }
       }
       // Tests each of the API methods by directly calling them. 
       public static void main(String[] args) {
         //Scales polygon by the factor 2
         StdDraw.setScale(-5.0, +5.0); 
         double[] x = { 0, 1, 1, 0 }; 
         double[] y = { 0, 0, 2, 1 }; 
         double alpha = 2.0; 
         StdDraw.setPenColor(StdDraw.RED); 
         StdDraw.polygon(x, y); 
         scale(x, y, alpha); 
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.polygon(x, y);
 
 
         // Translates polygon by (2, 1)
         double[] a = { 0, 1, 1, 0 };
         double[] b = { 0, 0, 2, 1 };
         double dx = 2.0;
         double dy = 1.0; 
         StdDraw.setPenColor(StdDraw.RED); 
         StdDraw.polygon(a, b); 
         translate(a, b, dx, dy); 
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.polygon(a, b);
 
         // Rotates polygon 45 degrees
         StdDraw.setScale(-5.0, +5.0); 
         double[] c = { 0, 1, 1, 0 }; 
         double[] d = { 0, 0, 2, 1 }; 
         double theta = 45.0; 
         StdDraw.setPenColor(StdDraw.RED); 
         StdDraw.polygon(c, d); 
         rotate(c, d, theta); 
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.polygon(c, d);
 
         // Returns Copied array
 
     }
 }
 

