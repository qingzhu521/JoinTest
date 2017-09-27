import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by sanquan.qz on 2017/9/26.
 */
public class sortMerge {

    private static int X_MAX_LEN;
    private static int MAX_LEN;
    private static int[] x;
    private static int[] ix;
    private static int[] lx;

    private static int[] y;
    private static int[] iy;
    private static int[] ly;
    public static void main(String[] args){
        X_MAX_LEN = Integer.parseInt(args[0]);
        MAX_LEN = Integer.parseInt(args[1]);
        x = new int[X_MAX_LEN];
        ix = new int[X_MAX_LEN];
        lx = new int[X_MAX_LEN];

        y = new int[MAX_LEN];
        iy = new int[MAX_LEN];
        ly = new int[MAX_LEN];

        Random rand = new Random(5);
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
        int z;
        for (; i < ix.length && ix[i] != -1 && j < iy.length && iy[j] != -1; ){
            if(x[ix[i]] < y[iy[j]]){
                i++;
            } else if(x[ix[i]] > y[iy[j]]){
                j++;
            } else{
                for (int f = ix[i]; f < ix[i] + lx[i]; f++) {
                    for (int k = iy[j]; k < iy[j] + ly[j]; k++) {
                         z = x[f] + y[k];
                    }
                }
                i++; j++;
            }
        }
        System.out.println(LocalDateTime.now());

    }

    private static void build_indexandlen_for(int[] fx, int[] fix, int[] flx){
        int last = -1, beg = -1, len = 0, al = 0;
        for (int i = 0; i < fx.length; i++){
            if(last != fx[i]){
                if(last != -1){
                    fix[al] = beg;
                    flx[al] = len;
                    al++;
                }
                beg = i;
                len = 1;
                last = fx[i];
            } else{
                len++;
            }
        }
        fix[al] = beg;
        flx[al] = len;
        //System.out.println(al + " " + fx.length);
        if(al + 1< fx.length) {
            fix[al + 1] = -1;
            flx[al + 1] = -1;
        }
    }
}
