package prime_number;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

//import static until.BigIntegerUtil.generateRandomNumber;

/**
 * Created by vatva on 08.02.2017.
 */
public class PrimeBigIntegerGenerator {

    public BigInteger generatePrimeNumber(int length) {
        return findAttackResistantPrimeNumber(length);
    }

    private BigInteger findX(int length) {
        return generateRandomNumber(length);
    }

    private BigInteger generateRandomNumber(int length) {
        return BigInteger.valueOf(0);
    }

    /**
     * Finds a prime number p derived from another prime number
     * because {@code p - 1} has to contain big prime divisors (in the best case, p - 1 = 2p', where p' - prime)
     * in order to resist crypto-analytical attacks on a RSA schema key.
     *
     * @param length the number of bytes in the generated prime number
     * @return a prime number
     */
    private BigInteger findAttackResistantPrimeNumber(int length) {
        BigInteger prime = findPrimeNumber(length);
        BigInteger resultPrime;

        for (int i = 1; ; i++) {
            resultPrime = prime.multiply(BigInteger.valueOf(2))
                    .multiply(BigInteger.valueOf(i))
                    .add(BigInteger.ONE);
            if (isPrimeUsingTrialDivision(resultPrime) && isPrimeUsingMillerRabinTest(resultPrime, length)) {
                return resultPrime;
            }
        }
    }

    /**
     * Finds a prime number with the specified length checking its primality using
     * {@link #isPrimeUsingTrialDivision(BigInteger)} and {@link #isPrimeUsingMillerRabinTest(BigInteger, int)} methods.
     *
     * @param length the number of bytes in the generated prime number
     * @return a prime number
     */
    private BigInteger findPrimeNumber(int length) {
        BigInteger m = findM(length);
        for (int i = 0; i < 100; i++) {
            BigInteger p = m.add(BigInteger.valueOf(2 * i));
            if (isPrimeUsingTrialDivision(p) && isPrimeUsingMillerRabinTest(p, length)) {
                return p;
            }
        }

        return findPrimeNumber(length);
    }

    /**
     * Checks if the specified number is prime using trial division method
     * (simply dividing by prime numbers such as 2, 3, 5, 7, 11, 13, etc.).
     *
     * @param number a number to check if it is prime
     * @return {@code true} if the specified number is prime
     */
    public boolean isPrimeUsingTrialDivision(BigInteger number) {
        return number.mod(BigInteger.valueOf(2)).intValue() != 0
                && number.mod(BigInteger.valueOf(3)).intValue() != 0
                && number.mod(BigInteger.valueOf(5)).intValue() != 0
                && number.mod(BigInteger.valueOf(7)).intValue() != 0
                && number.mod(BigInteger.valueOf(11)).intValue() != 0
                && number.mod(BigInteger.valueOf(13)).intValue() != 0;
    }

    public boolean isPrimeUsingMillerRabinTest(BigInteger number, int length) {
        boolean isPrime = false;

        int k = 20;

        BigInteger d = number.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).intValue() == 0) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        int counter = 0;

        while (counter++ < k) {
            BigInteger x = findX(length, number); // 1 < x < number
            if (x.gcd(number).intValue() == 1) {
                if ((x.modPow(d, number).compareTo(BigInteger.ONE) == 0)
                        || (x.modPow(d, number).compareTo(number.subtract(BigInteger.ONE)) == 0)) {
                    isPrime = true;
                } else {
                    for (int r = 1; r < s; r++) {
                        BigInteger xR = x.modPow(d.multiply(BigInteger.valueOf(2).pow(r)),
                                d.multiply(BigInteger.valueOf(2).pow(r))).add(BigInteger.ONE)
                                .mod(number);

                        if (xR.compareTo(number.subtract(BigInteger.valueOf(1))) == 0) {
                            isPrime = true;
                            break;
                        } else if (xR.compareTo(BigInteger.ONE) == 0) {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }

            if (!isPrime) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds M that isn't lower than a newly found X and isn't a paired number.
     *
     * @param length length of a newly generated X in bytes
     * @return {@code BigInteger} M
     */
    private BigInteger findM(int length) {
        BigInteger m = findX(length);
        while (m.remainder(BigInteger.valueOf(2)).intValue() == 0) {
            m = m.add(BigInteger.ONE);
        }

        return m;
    }

    /**
     * Finds X of the specified length that is lower than the specified MAX value.
     *
     * @param length length of the generated X in bytes
     * @param max    upper limit of a {@code BigInteger} X
     * @return {@code BigInteger} X
     */
    private BigInteger findX(int length, BigInteger max) {
        BigInteger x = generateRandomNumber(length);
        while (x.compareTo(max) >= 0) {
            x = generateRandomNumber(length);
        }

        return x;
    }

    /**
     * Created by vatva on 09.02.2017.
     */
    public static class Operator {
        private static final BigInteger TWO = valueOf(2);

        public boolean isProbablePrime(int certainty, BigInteger value) {
            BigInteger w = value;
            if (w.equals(TWO))
                return true;
            if (!w.testBit(0) || w.equals(ONE))
                return false;
            int rounds = 0;
            int n = (Math.min(certainty, Integer.MAX_VALUE - 1) + 1) / 2;

            // The relationship between the certainty and the number of rounds
            // we perform is given in the draft standard ANSI X9.80, "PRIME
            // NUMBER GENERATION, PRIMALITY TESTING, AND PRIMALITY CERTIFICATES".
            int sizeInBits = value.bitLength();
            if (sizeInBits < 100) {
                rounds = 50;
                rounds = n < rounds ? n : rounds;
                return passesMillerRabin(rounds, value);
            }

            if (sizeInBits < 256) {
                rounds = 27;
            } else if (sizeInBits < 512) {
                rounds = 15;
            } else if (sizeInBits < 768) {
                rounds = 8;
            } else if (sizeInBits < 1024) {
                rounds = 4;
            } else {
                rounds = 2;
            }
            rounds = n < rounds ? n : rounds;

            return passesMillerRabin(rounds, value);
        }

        private boolean passesMillerRabin(int iterations, BigInteger value) {
            // Find a and m such that m is odd and this == 1 + 2**a * m
            BigInteger thisMinusOne = value.subtract(ONE);
            BigInteger m = thisMinusOne;
            int a = m.getLowestSetBit();
            m = m.shiftRight(a);

            // Do the tests

            for (int i = 0; i < iterations; i++) {
                // Generate a uniform random on (1, this)
                BigInteger b;
                do {
                    b = new BigInteger(value.bitLength(), ThreadLocalRandom.current());
                } while (b.compareTo(ONE) <= 0 || b.compareTo(value) >= 0);

                int j = 0;
                BigInteger z = b.modPow(m, value);
                while (!((j == 0 && z.equals(ONE)) || z.equals(thisMinusOne))) {
                    if (j > 0 && z.equals(ONE) || ++j == a)
                        return false;
                    z = z.modPow(TWO, value);
                }
            }
            return true;
        }
    }
}
