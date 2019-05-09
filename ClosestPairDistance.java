/**
 *
 * @author Riya Bhardwaj
 */
import java.util.*;
public class ClosestPair {
    
    public static void display2darray(int a[][],int n,int m)
    {
        System.out.println();
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
    }
    
   static double dist(int x1,int x2,int y1,int y2) 
{ 
    return (Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*(y1-y2))));
} 
    
   static double bruteForce(int P[][], int n) 
{ 
    double min = Double.MAX_VALUE; 
    for (int i = 0; i < n; i++) 
        for (int j = i+1; j < n; j++) 
            if (dist(P[i][0], P[j][0],P[i][1],P[j][1]) < min) 
                min = dist(P[i][0], P[j][0],P[i][1],P[j][1]);
    
    return min; 
} 
    
   static double min(double x, double y) 
{ 
    return (x < y)? x : y; 
} 
    
  static double stripClosest(int strip[][], int size, double d) 
{ 
    double min = d;  // Initialize the minimum distance as d 
  
    // Pick all points one by one and try the next points till the difference 
    // between y coordinates is smaller than d. 
    // This is a proven fact that this loop runs at most 6 times 
    for (int i = 0; i < size; ++i) 
        for (int j = i+1; j < size && (strip[j][1] - strip[i][1]) < min; ++j) 
            if (dist(strip[i][0],strip[j][0],strip[i][1],strip[j][1]) < min) 
                min = dist(strip[i][0],strip[j][0],strip[i][1],strip[j][1]); 
  
    return min; 
} 
    public static double findsmallestdistance(int Point1[][],int Point2[][],int n)
    {
        // If there are 2 or 3 points, then use brute force 
    if (n <= 3) 
        return bruteForce(Point1, n); 
  
    // Find the middle point 
    int mid = n/2; 
    if(n%2==0)
    {
        mid-=1;
    }
    int midx=Point1[mid][0];
    int midy=Point1[mid][1];
    // Divide points in y sorted array around the vertical line. 
    // Assumption: All x coordinates are distinct. 
    int Pyl[][]=new int[mid+1][2];   // y sorted points on left of vertical line 
    int Pyr[][]=new int[n-mid-1][2];   // y sorted points on right of vertical line 
    
    int Pxl[][]=new int[mid+1][2];   
    int Pxr[][]=new int[n-mid-1][2];
    int li1=0,ri1=0;
    int li = 0, ri = 0;  // indexes of left and right subarrays 
    for (int i = 0; i < n; i++) 
    { 
        
      if (Point2[i][0] <= midx && li<=mid) 
      {
          Pyl[li][0]=Point2[i][0];
          Pyl[li][1]=Point2[i][1];
          li++;
      }
        
      else
          {
          Pyr[ri][0]=Point2[i][0];
          Pyr[ri][1]=Point2[i][1];
          ri++;
      }
      
      if(i<=mid)
      {
          Pxl[li1][0]=Point1[i][0];
          Pxl[li1][1]=Point1[i][1];
          li1++;
      }
      else
      {
          Pxr[ri1][0]=Point1[i][0];
          Pxr[ri1][1]=Point1[i][1];
          ri1++;
      }
    } 
    
    // Consider the vertical line passing through the middle point 
    // calculate the smallest distance dl on left of middle point and 
    // dr on right side 
   // System.out.println(display2darray(Pxl,mid,2));
    
    double dl = findsmallestdistance(Pxl, Pyl, mid+1); 
    double dr = findsmallestdistance(Pxr, Pyr, n-mid-1);
  
    // Find the smaller of two distances 
    double d = min(dl, dr); 
  
    // Build an array strip[] that contains points close (closer than d) 
    // to the line passing through the middle point 
  int strip[][]=new int[n][2]; 
    int j = 0; 
    for (int i = 0; i < n; i++) 
        if (Math.abs(Point2[i][0] - midx) < d) 
        {
            strip[j][0] = Point1[i][0];  
            strip[j][1] = Point1[i][1]; j++; 
        }
    // Find the closest points in strip.  Return the minimum of d and closest 
    // distance is strip[] 
    return min(d, stripClosest(strip, j, d) ); 
    }
    
    public static void main(String[]args)
    {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int Point1[][]=new int[n][2];
        int Point2[][]=new int[n][2];
        for(int i=0;i<n;i++)
        {
            Point1[i][0]=sc.nextInt();
            Point2[i][0]=Point1[i][0];
            Point1[i][1]=sc.nextInt();
            Point2[i][1]=Point1[i][1];
        }
        
        Arrays.sort(Point1, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]>o2[0])
                    return 1;
                else
                    return -1;
            }
        });
        Arrays.sort(Point2, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[1]>o2[1])
                    return 1;
                else
                    return -1;
            }
        });
        //display2darray(Point1,n,2);
        //display2darray(Point2,n,2);
       System.out.println( findsmallestdistance(Point1,Point2,n));
    }
    
}
