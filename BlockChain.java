package blockchain;

import java.security.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BlockChain {
    private static BlockChain instance;

    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Block> blocks = new ArrayList<>();
    private int countZeros = 0;
    private long timestamp = 0;
    private long lastMessage = 0;
    private long lastMessageId = 0;
    private VCoin reward = new VCoin(100);

    private BlockChain() {
    }

    public static BlockChain getInstance() {
        if (instance == null) {
            instance = new BlockChain();
        }
        return instance;
    }

    public VCoin getReward() {
        return reward;
    }

    public int getCountBlocks() {
        return blocks.size();
    }

    public Block getLastBlock() {
        return blocks.size() > 0 ? blocks.get(blocks.size() - 1) : null;
    }

    public long getTimestamp() {
        if (timestamp == 0) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public int getCountZeros() {
        return countZeros;
    }

    public List<Transaction> getNewMessages() {
        return transactions.stream().skip(lastMessage).collect(Collectors.toList());
    }

    public void addBlock(Block block) {
        if (block.getId() > blocks.size()) {
            transactions.add(new Transaction(null, block.getMinerId().getOwner(), reward, null, generateLastId(), null));
            System.out.println(block);
            blocks.add(block);
            lastMessage += block.getData().size();
            if (block.getGenerateTime() < 1) {
                System.out.println("N was increased to " + countZeros);
            } else if (block.getGenerateTime() > 2) {
                countZeros--;
                System.out.println("N was decreased by " + countZeros);
            } else {
                System.out.println("N stays the same");
            }
            timestamp = System.currentTimeMillis();
            System.out.println();
        }
    }

    public void sendMessage(Transaction transaction) {
        if (transaction.getAmount().getAmount() > getBalance(transaction.getSender())) {
            return;
        }
        if (transactions.isEmpty() || transactions.get(transactions.size() - 1).getId() > transaction.getId()) {
            return;
        }
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(transaction.getPublicKeyKey());
            if (sig.verify(transaction.getSign())) {
                transactions.add(transaction);
            }
        } catch (Exception ignored) {
        }
    }

    public long generateLastId() {
        return lastMessageId += new Random().nextInt(9 ) + 1;
    }

    public long getBalance(User user) {
        long balance = 0;
        List<Transaction> sender = transactions.stream().filter(el -> el.getSender() != null && el.getSender().equals(user)).collect(Collectors.toList());
        for (Transaction transaction : sender) {
            balance -= transaction.getAmount().getAmount();
        }
        List<Transaction> recipient = transactions.stream().filter(el -> el.getRecipient().equals(user)).collect(Collectors.toList());
        for (Transaction transaction : recipient) {
            balance += transaction.getAmount().getAmount();
        }
        return balance;
    }
}
