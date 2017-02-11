package abonent;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by vatva on 08.02.2017.
 */
public class Abonent {
    private BigInteger p;
    private BigInteger q;
    private BigInteger e;
    private BigInteger d;
    private BigInteger n;
    private BigInteger message;

    private BigInteger phin;

    Scanner scanner = new Scanner(System.in);

    public Abonent(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        n = q.multiply(p);//open key
        phin = (q.subtract(BigInteger.ONE)).multiply(p.subtract(BigInteger.ONE));
        e = new BigInteger("65537"); //open key
        d = e.modInverse(phin);
        System.out.println("Enter message: ");
        message = generateMessage(inputMessage());
    }

    public String toString() {
        return "e = " + e.toString() + "\nd = " + d.toString();
    }

    public BigInteger encrypt(BigInteger massege, BigInteger e) {
        return massege.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger encMessege) {
        return encMessege.modPow(d, n);
    }

    public BigInteger generateMessage(BigInteger message){
        if(message.compareTo(getN()) == 1) return message.mod(getN());
        return message;
    }
    public BigInteger inputMessage(){
        return scanner.nextBigInteger();
    }

    public void showParamrtrs(Abonent abonent) {

        System.out.println("ABONENT A------------------------------------------------");
//        System.out.println("message: ");
//        BigInteger message = abonent.getMessage();
//        System.out.println(abonent.getMessage().toString(16));
        System.out.println("Get P: ");
        System.out.println(abonent.getP().toString(16));
        System.out.println("Get Q: ");
        System.out.println(abonent.getQ().toString(16));
        System.out.println("Get N: ");
        System.out.println(abonent.getN().toString(16));
        System.out.println("Get D: ");
        System.out.println(abonent.getD().toString(16));
        System.out.println("Get E:");
        System.out.println(abonent.getE().toString(16));
    }

    public BigInteger getP() {
        return p;
    }


    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getMessage() {
        return message;
    }

    public void setMessage(BigInteger message) {
        this.message = message;
    }
}
