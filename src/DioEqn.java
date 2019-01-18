/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.LongVbl;
import java.util.ArrayList;
/**
 *
 * @author Pat
 */
public class DioEqn extends Task{
    public ArrayList<long[]> solutionSet;
    /**
     * @param args the command line arguments
     */
    public void main(String[] args) {
        final Long in;
        
        if(args.length != 1){
            System.out.println("Unexpected Number of Parameters received " + args.length + " expected 1");
            System.exit(-1);
        }
        in = tryParseLong(args[0]);
        if(in == null){
            System.out.println("Parameter given was not a number");
            System.exit(-1);
        }
        if(in < 0){
            System.out.println("Parameter given was not greater than or equal to 0");
            System.exit(-1);
        }
        if(in == 0){
            System.out.println("0^2 + 0^2 = 0");
            return;
        }
        
        LongVbl resLow = new LongVbl.Min(Long.MAX_VALUE);
        LongVbl resHigh = new LongVbl.Max(Long.MIN_VALUE);
        parallelFor((long)0, ((long)(Math.sqrt(in) + 1)) ).exec(new LongLoop(){
            public void run (long i){
                long[] res = computeSingleDioph(i, in);
                if(res.length == 2){
                    if(res[0] <= res[1]){
                        resLow.reduce(res[0]);
                        resHigh.reduce(res[0]);
                    }
                }
            }
        });
        
        if(resLow.item != Long.MAX_VALUE){
            if(resLow.item != resHigh.item){
                long resLowY, resHighY;
                resLowY = in - (resLow.item*resLow.item);
                resLowY = (long)Math.sqrt(resLowY);
                
                resHighY = in - (resHigh.item*resHigh.item);
                resHighY = (long)Math.sqrt(resHighY);
                
                 System.out.println(resLow + "^2 + " + resLowY + "^2 = " + in);
                 System.out.println(resHigh + "^2 + " + resHighY + "^2 = " + in);
            }
            else{
                long resLowY;
                resLowY = in - (resLow.item*resLow.item);
                resLowY = (long)Math.sqrt(resLowY);
                
                 System.out.println(resLow + "^2 + " + resLowY + "^2 = " + in);
            }
        }
        else{
            System.out.println("No solutions");
        }
    }
    
    
    /**
     * Determines whether or not the given x value has a is a solution to the
     * Diophantine equation, if it is it's the smaller number of the two
     * Diophantine equation: X^2 + Y^2 = C, X >= 0, Y >= X
     * @param x The x value of the Diophantine equation
     * @param c The C value of the Diophantine equation
     * @return an array of two integers, 0 position has X 1 has Y if there was a 
     * solution. Otherwise it has one integer of just 0
     */
    public static long[] computeSingleDioph(long x, long c){
        long res = c - (long)Math.pow(x, 2);
        long y;
        if( (y = isPerfectSquare(res)) > 0 ){
            return new long[]{x, y};
        }
        else{
            return new long[]{0};
        }
    }
    
    /**
     * Determines whether or not the given number is a perfect square
     * @param x The number to check squareness
     * @return The square root if it is square, 0 otherwise
     */
    public static long isPerfectSquare(long x){
        if(x <= 0 )
            return 0;
        double root = Math.sqrt(x);
        long rootInt = (long)root;
        if (Math.pow(rootInt, 2) == x){
            return rootInt;
        }
        else{
            return 0;
        }
    }
    
    /**
     * Attempts to parse a long gracefully.
     * @param in The string to parse
     * @return The Long value of in if it is a long, otherwise null
     */
    public static Long tryParseLong(String in){
        Long out;
        try{
            out = Long.parseLong(in);
        }
        catch(Exception e){
            return null;
        }
        return out;
    }
}
