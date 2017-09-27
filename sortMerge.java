import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by sanquan.qz on 2017/9/26.
 */
public class sortMerge {
    private static final int X_MAX_LEN = 10000000;
    private static final int MAX_LEN = 20000000;
    private static int[] x = new int[X_MAX_LEN];
    private static int[] ix = new int[X_MAX_LEN];
    private static int[] lx = new int[X_MAX_LEN];

    private static int[] y = new int[MAX_LEN];
    private static int[] iy = new int[MAX_LEN];
    private static int[] ly = new int[MAX_LEN];

    public static void main(String[] args){
        Random rand = new Random();
        for (int i = 0; i < X_MAX_LEN; i++){
            x[i] = rand.nextInt(MAX_LEN);
        }
        for (int j = 0; j < MAX_LEN; j++){
            y[j] = rand.nextInt(MAX_LEN);
        }
        Arrays.sort(x);
        Arrays.sort(y);
        build_indexandlen_for(x,ix,lx);
        build_indexandlen_for(y,iy,ly);

        //for (int i = 0; i < X_MAX_LEN; i++){
        //    System.out.print(x[i] + " ");
        //}
        //System.out.println();
        //for (int i = 0; i < ix.length; i++){
        //    System.out.print(ix[i] + " ");
        //}

        int i = 0, j = 0;
        System.out.println(LocalDateTime.now());

        for (; i < ix.length && ix[i] != -1 && j < iy.length && iy[j] != -1; ){
            if(x[ix[i]] < y[iy[j]]){
                i++;
            } else if(x[ix[i]] > y[iy[j]]){
                j++;
            } else{
                for (int f = ix[i]; f < lx[i]; f++) {
                    for (int k = iy[j]; k < ly[j]; k++) {
                         int z = x[f] + y[k];
                    }
                }
                i++; j++;
            }
        }
        System.out.println(LocalDateTime.now());

    }

    private static void build_indexandlen_for(int[] x, int[] ix, int[] lx){
        int last = -1, beg = -1, len = 0, al = 0;
        for (int i = 0; i < x.length; i++){
            if(last != x[i]){
                if(last != -1){
                    ix[al] = beg;
                    lx[al] = len;
                    al++;
                }
                beg = i;
                len = 1;
                last = x[i];
            } else{
                len++;
            }
        }
        ix[al] = beg;
        lx[al] = len;
        ix[al + 1] = -1;
        lx[al + 1] = -1;
    }
}
