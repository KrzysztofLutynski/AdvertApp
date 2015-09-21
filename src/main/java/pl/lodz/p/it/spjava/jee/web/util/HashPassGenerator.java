package pl.lodz.p.it.spjava.jee.web.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 *
 * @author Krzysiek
 */
public class HashPassGenerator {

    public static String generateHash(String password) {
        String result = Hashing.sha256().hashString(password, Charsets.UTF_8).toString();
        return result;
    }

    public static void main(String[] args) {

        System.out.println(generateHash("haslo"));
    }

}
