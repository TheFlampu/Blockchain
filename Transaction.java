package blockchain;

import java.security.PublicKey;

public class Transaction {
    private final User sender;
    private final User recipient;
    private final VCoin amount;
    private final PublicKey publicKey;
    private final long id;
    private final byte[] sign;

    public Transaction(User sender, User recipient, VCoin amount, PublicKey publicKey, long id, byte[] sign) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.publicKey = publicKey;
        this.id = id;
        this.sign = sign;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public VCoin getAmount() {
        return amount;
    }

    public PublicKey getPublicKeyKey() {
        return publicKey;
    }

    public long getId() {
        return id;
    }

    public byte[] getSign() {
        return sign;
    }
}
