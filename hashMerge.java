import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by sanquan.qz on 2017/9/26.
 */
public class hashMerge {
    private static int X_MAX_LEN;
    private static int MAX_LEN;
    private static int[] x;
    private static int[] y;

    private static Hashtable<Integer,Integer> tbi;
    private static Hashtable<Integer,Integer> tbl;

    private static void buildidHashfor(int[] y, int[] idxy, int[] leny){
        int last = -1,beg = 0, len = 0;
        for (int i = 0; i < y.length; i++){
            if(y[i] != last){
                if(last != -1){
                   idxy[last] = beg;
                   leny[last] = len;
                }
                last = y[i];
                beg = i;
                len = 1;
            } else{
                len++;
            }
        }
        idxy[last] = beg;
        leny[last] = len;
    }
    //private static void buildHashfor(int[] y,Hashtable<Integer,Integer> tb1, Hashtable<Integer,Integer> tb2){
    //    int last = -1,beg = 0, len = 0;
    //    for (int i = 0; i < y.length; i++){
    //        if(y[i] != last){
    //            if(last != -1){
    //                tb1.put(last,beg);
    //                tb2.put(last,len);
    //            }
    //            last = y[i];
    //            beg = i;
    //            len = 1;
    //        } else{
    //            len++;
    //        }
    //    }
    //    tb1.put(last,beg);
    //    tb2.put(last,len);
    //}
    public static void main(String[] args){
        tbi = new Hashtable<>();
        tbl = new Hashtable<>();

        X_MAX_LEN = Integer.parseInt(args[0]);
        MAX_LEN = Integer.parseInt(args[1]);
        y = new int[MAX_LEN + 1];
        x = new int[X_MAX_LEN + 1];

        int[] idxy = new int[MAX_LEN + 1];
        int[] leny = new int[MAX_LEN + 1];
        Random rand = new Random(5);
        for (int i = 0; i < X_MAX_LEN; i++){
            x[i] = rand.nextInt(MAX_LEN);
        }
        for (int j = 0; j < MAX_LEN; j++){
            y[j] = rand.nextInt(MAX_LEN);
        }

        Arrays.sort(y);
        Arrays.fill(leny,-1);
        Arrays.fill(idxy,-1);
        buildidHashfor(y,idxy,leny);

        System.out.println(LocalDateTime.now());
        int f,ind0,ind1,z;
        for (int i = 0; i < x.length; i++){
            f = x[i];
            ind0 = idxy[f];
            if(ind0 != -1) {
                ind1 = leny[f];
                for (int k = ind0; k < ind0 + ind1; k++) {
                    z = y[k] + x[i];
                }
            }

        }
        System.out.println(LocalDateTime.now());
    }
}
