import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by sanquan.qz on 2017/9/26.
 */
public class hashMerge {
    private static final int X_MAX_LEN = 10000000;
    private static final int MAX_LEN = 20000000;
    private static int[] x = new int[X_MAX_LEN];
    private static int[] ix = new int[X_MAX_LEN];
    private static int[] lx = new int[X_MAX_LEN];

    private static int[] y = new int[MAX_LEN];
    private static int[] iy = new int[MAX_LEN];
    private static int[] ly = new int[MAX_LEN];
    private static HashMap<Integer,int[]> map = new HashMap<>();

    private static void buildHashfor(int[] y,HashMap<Integer,int[]> map){
        int last = -1,beg = 0, len = 0;
        int[] tuple;

        for (int i = 0; i < y.length; i++){
            if(y[i] != last){
                if(last != -1){
                    tuple = new int[2];
                    tuple[0] = beg;
                    tuple[1] = len;
                    map.put(last,tuple);
                }
                last = y[i];
                beg = i;
                len = 1;
            } else{
                len++;
            }
        }
        tuple = new int[2];
        tuple[0] = beg;
        tuple[1] = len;
        map.put(last,tuple);
    }
    public static void main(String[] args){
        Random rand = new Random();
        for (int i = 0; i < X_MAX_LEN; i++){
            x[i] = rand.nextInt(MAX_LEN);
        }
        for (int j = 0; j < MAX_LEN; j++){
            y[j] = rand.nextInt(MAX_LEN);
        }

        Arrays.sort(y);

        buildHashfor(y,map);
        System.out.println(LocalDateTime.now());
        for (int i = 0; i < x.length; i++){
            int f = x[i];
            int[] ind = map.get(f);
            if(ind != null) {
                for (int k = ind[0]; k < ind[1]; k++) {
                    int z = x[i] + y[k];
                }
            }
        }
        System.out.println(LocalDateTime.now());
    }
}
