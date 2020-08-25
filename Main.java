package blockchain;

public class Main {
    public static void main(String[] args) {
        BlockChain blockChain = BlockChain.getInstance();
        BlockChainManager blockChainManager = new BlockChainManager(blockChain);
        blockChainManager.startBlockChain(4, 15);
    }
}
