public class Q3_SquareArray{

    public static int[] createArray(int size) {
        int[] ArrayS; 
        ArrayS = new int[size]; 
        for(int i=0; i<13; i++) {
            ArrayS [i] = i * i;
        }
        return ArrayS;
    }
    public static void main(String[]args){ 

        int[] sol = createArray(13);
        
        for(int i=0; i<13; i++) {
            System.out.println("The square of " + i + " is " + sol[i]);
        }    
    }
}