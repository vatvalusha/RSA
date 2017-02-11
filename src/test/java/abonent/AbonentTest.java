package abonent;

import org.junit.Test;
import prime_number.GeneratePrimeNumber;
import runner.Runner;

import java.math.BigInteger;

import static org.junit.Assert.*;

//import java.security.P

/**
 * Created by vatva on 09.02.2017.
 */
public class AbonentTest {
    Abonent abonent = new Abonent(new BigInteger("3679e37e7faed50aa21d4d4f1679ca0aeb336279250a6a02d8712cdd92d0f821", 16), new BigInteger("ce148f977eea88b0a2ca8dc5c8cce18862676bd744cf7f4b3b63f9cc1f586445", 16));

    @org.junit.Test
    public void encrypt() throws Exception {
        assertEquals(new BigInteger("23f6a5c008b9f487e7ca70620f74f2b7a2d734fe6a3c81d7c88c57c2598308283b4f7548a96044b161bc60c464b550a0d3ed7bafe1a47a613445a3dfa77fd358", 16), abonent.encrypt(new BigInteger("1996"), abonent.getE()));
    }

    @org.junit.Test
    public void decrypt() throws Exception {
        assertEquals(new BigInteger("1996"), abonent.decrypt(new BigInteger("23f6a5c008b9f487e7ca70620f74f2b7a2d734fe6a3c81d7c88c57c2598308283b4f7548a96044b161bc60c464b550a0d3ed7bafe1a47a613445a3dfa77fd358", 16)));
    }

    @Test
    public void cutMessageTest(){
        Abonent abonent = new Abonent(new BigInteger("3"), new BigInteger("7"));
        assertEquals("if message biggest then N, cutting this message message = message.mod(N)",new BigInteger("3"),abonent.generateMessage(new BigInteger("24")));
    }

}