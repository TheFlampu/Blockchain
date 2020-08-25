package blockchain;

import java.security.*;

public class User {
    private final String name;
    private  final PublicKey publicKey;
    private  final PrivateKey privateKey;

    public User(String name) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyGen.genKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
