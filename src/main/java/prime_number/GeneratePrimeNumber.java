package prime_number;

import generators.L89;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by vatva on 10.02.2017.
 */
public class GeneratePrimeNumber {

    ArrayList<BigInteger> prime;


    private BigInteger p1 = null;
    private BigInteger p2 = null;


    public void generaitPirime() {
        prime = new ArrayList<>();
        PrimeBigIntegerGenerator.Operator operator = new PrimeBigIntegerGenerator.Operator();
        L89 l89s = new L89();
        ArrayList<Integer> sequence = l89s.L89Gen();
        ArrayList<BigInteger> l89 = L89.convertToBytes(sequence);
//        System.out.println("Byte:");
//        System.out.println(l89);


        for (int i = 0; i < 3000; i++) {
            if (l89.get(i).isProbablePrime(5) == true || operator.isProbablePrime(5, l89.get(i))) {
                prime.add(l89.get(i));
            }
        }
        System.out.println(prime.size());
        p1 = new BigInteger(prime.get(0).toString());
        p2 = new BigInteger(prime.get(1).toString());

    }


    public BigInteger getP1() {
        return p1;
    }

    public BigInteger getP2() {
        return p2;
    }

    public ArrayList<BigInteger> getPrime() {
        return prime;
    }
}
