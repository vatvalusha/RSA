package generators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vatva on 08.02.2017.
 */
public class L89 {
    static Random r = new Random();
    static int[] x = new int[1000000];
    ArrayList<Integer> arr = new ArrayList<>();
//    static byte[] bytes = new byte[2000000];

    public ArrayList<Integer> L89Gen() {


        for (int i = 0; i < 90; i++) {
            x[i] = r.nextInt(2);
        }
        for (int i = 90; i < x.length; i++) {
            x[i] = ((x[i - 38] + x[i - 89]) % 2);
            arr.add(Integer.valueOf(x[i]));
        }
        return arr;
    }

    public static ArrayList convertToBytes(ArrayList bits) {
        ArrayList<BigInteger> list = new ArrayList();
        String line = "";
        int index = 0;

        for (int i = 0; i < bits.size(); i++) {
            line = line + bits.get(i);
            index++;
            if (index == 256) {
                list.add(new BigInteger(line, 2));
                line = "";
                index = 0;
            }
        }
        return list;
    }
}
