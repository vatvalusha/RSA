package runner;

import abonent.Abonent;
import prime_number.GeneratePrimeNumber;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by vatva on 08.02.2017.
 */
public class Runner {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GeneratePrimeNumber generatePrimeNumber = new GeneratePrimeNumber();
        generatePrimeNumber.generaitPirime();
        Abonent abonentA = new Abonent(generatePrimeNumber.getP1(), generatePrimeNumber.getP2());
//        abonentA.setMessage(new BigInteger("1996"));


        abonentA.showParamrtrs(abonentA);
        System.out.println("Input Message: ");
        abonentA.setMessage(scanner.nextBigInteger());
        BigInteger encrypt = abonentA.encrypt(abonentA.getMessage(), abonentA.getE());
        System.out.println("Encrypt message: " + encrypt.toString(16));

        BigInteger decrypt = abonentA.decrypt(encrypt);
        System.out.println("Decrypt message: " + decrypt.toString(10));
    }
}
