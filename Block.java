package blockchain;

import java.util.List;

public class Block {
    private final long id;
    private final long timestamp;
    private final int magicNumber;
    private final String previousHash;
    private final String hash;
    private final double generateTime;
    private final Miner miner;
    private final List<Transaction> data;
    private final VCoin reward;

    public Block(long id, long timestamp, int magicNumber, String previousHash, String hash, double generateTime, Miner miner, List<Transaction> data, VCoin reward) {
        this.id = id;
        this.timestamp = timestamp;
        this.magicNumber = magicNumber;
        this.previousHash = previousHash;
        this.hash = hash;
        this.generateTime = generateTime;
        this.miner = miner;
        this.data = data;
        this.reward = reward;
    }

    public long getId() {
        return id;
    }

    public Miner getMinerId() {
        return miner;
    }

    public String getHash() {
        return hash;
    }

    public double getGenerateTime() {
        return generateTime;
    }

    public List<Transaction> getData() {
        return data;
    }

    @Override
    public String toString() {
        return  "Block: " + "\n" +
                "Created by " + miner.getName() + "\n" +
                miner.getName() + " gets " + reward + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:" + "\n" +
                previousHash + "\n" +
                "Hash of the block:" + "\n" +
                hash + "\n" +
                "Block data:" +
                dataToString() +
                "Block was generating for " + generateTime + " seconds";
    }

    private String dataToString() {
        if (data.size() != 0) {
            StringBuilder result = new StringBuilder();
            result.append("\n");
            for (Transaction msg : data) {
                result.append(msg.getSender() == null ? "mining" : msg.getSender().getName()).append(" send ").append(msg.getAmount().getAmount()).append(" to ").append(msg.getRecipient().getName()).append("\n");
            }
            return result.toString();
        }
        return " no transaction" + "\n";
    }
}
