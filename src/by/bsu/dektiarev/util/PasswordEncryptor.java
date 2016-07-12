package by.bsu.dektiarev.util;

import by.bsu.dektiarev.exception.DAOException;
import by.bsu.dektiarev.exception.ExceptionalMessage;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by USER on 12.07.2016.
 */
public class PasswordEncryptor {

    private static final String ALGORITHM = "MD5";
    private static final String ENCODING = "UTF-8";

    public static String encryptPassword(String password) throws DAOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] passwordBytes = password.getBytes(Charset.forName(ENCODING));
            byte[] encryptedBytes = messageDigest.digest(passwordBytes);
            return (new HexBinaryAdapter()).marshal(encryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(ExceptionalMessage.ENCRYPTING_ERROR);
        }
    }

    private PasswordEncryptor() {
    }
}
