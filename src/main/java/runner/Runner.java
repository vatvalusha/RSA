package runner;

import abonent.Abonent;
import generators.L89;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by vatva on 08.02.2017.
 */
public class Runner {
    BigInteger p1 = null;
    BigInteger p2 = null;
    BigInteger p3 = null;
    BigInteger p4 = null;


    public void generaitPirime() {
        Operator operator = new Operator();
        L89 l89s = new L89();
        ArrayList<Integer> l89sequence = L89.L89Gen();
        ArrayList<BigInteger> l89 = L89.convertToBytes(l89sequence);
        System.out.println("Byte:");
        System.out.println(l89);

        ArrayList<BigInteger> prime = new ArrayList<>();


        for (int i = 0; i < 3000; i++) {
            if (l89.get(i).isProbablePrime(5) == true || operator.isProbablePrime(5, l89.get(i))) {
                prime.add(l89.get(i));
            }
        }
        System.out.println(prime.size());
        p1 = new BigInteger(prime.get(0).toString());
        p2 = new BigInteger(prime.get(1).toString());
        p3 = new BigInteger(prime.get(2).toString());
        p4 = new BigInteger(prime.get(3).toString());
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.generaitPirime();
        BigInteger pq1 = runner.p1.multiply(runner.p2);
        BigInteger pq2 = runner.p3.multiply(runner.p4);

        Abonent abonentA;
        Abonent abonentB;

        if (pq1.compareTo(pq2) == -1) {
            abonentB = new Abonent(runner.p1, runner.p2, new BigInteger("257"));
            abonentA = new Abonent(runner.p3, runner.p4, new BigInteger("65537"));
        } else {
            abonentA = new Abonent(runner.p1, runner.p2, new BigInteger("257"));
            abonentB = new Abonent(runner.p3, runner.p4, new BigInteger("65537"));
        }
//        System.out.println(abonentB.getP().toString(16));
//        System.out.println(abonentA.getP().toString());
        abonentA.showParamrtrs(abonentA);
        BigInteger encrypt = abonentA.encrypt(abonentA.getMessage(), abonentA.getE());
        System.out.println(encrypt.toString(16));

        BigInteger decrypt =  abonentA.decrypt(encrypt);
        System.out.println(decrypt.toString(16));
//        runner.encrypt(abonentA);
    }
}
