package blockchain;

public class Miner extends Thread{
    private final User owner;
    private final BlockChain blockChain;
    private final int countBlocks;
    private int currentBlocks;

    public Miner(BlockChain blockChain, int countBlocks, String name, User owner) {
        super(name);
        this.blockChain = blockChain;
        this.countBlocks = countBlocks;
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    private Block generateBlock(long id, long timestamp, int countZeros, String previousHash) {
        int magicNumber;
        String hash;
        do {
            if (blockHasBeenAdded()) return null;
            magicNumber = (int) (Math.random() * Integer.MAX_VALUE);
            hash = StringUtil.applySha256(id + timestamp + magicNumber + previousHash);
        } while (!hash.startsWith("0".repeat(countZeros)));
        double generateTime = (System.currentTimeMillis() - timestamp) / 1000D;
        return new Block(id, timestamp, magicNumber, previousHash, hash, generateTime, this, blockChain.getNewMessages(), blockChain.getReward());
    }

    private boolean blockHasBeenAdded() {
        return blockChain.getCountBlocks() > currentBlocks;
    }


    @Override
    public void run() {
        currentBlocks = blockChain.getCountBlocks();
        while (currentBlocks < countBlocks) {
            Block lastBlock = blockChain.getLastBlock();
            String previousHash = currentBlocks > 0 ? lastBlock.getHash() : "0";
            long id = currentBlocks > 0 ? lastBlock.getId() + 1 : 1;
            long timestamp = blockChain.getTimestamp();
            int countZeros = blockChain.getCountZeros();
            Block block = generateBlock(id, timestamp,countZeros, previousHash);
            if (block != null) {
                synchronized (Miner.class) {
                    blockChain.addBlock(block);
                }
            }
            currentBlocks = blockChain.getCountBlocks();
        }
    }
}
