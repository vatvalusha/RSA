package Exaption;

/**
 * Created by vatva on 08.02.2017.
 */
public class NoInverseElementException extends Exception {
    public NoInverseElementException(int number, int mod) {
        super("There is no inverse element of " + number + " modulo " + mod);
    }
}
