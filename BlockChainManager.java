package blockchain;

import java.util.ArrayList;
import java.util.List;

public class BlockChainManager {
    BlockChain blockchain;

    public BlockChainManager(BlockChain blockchain) {
        this.blockchain = blockchain;
    }

    public void startBlockChain(int countThreads, int countBlocks) {
        int totalBlocks = countBlocks + blockchain.getCountBlocks();

        List<Thread> miners = new ArrayList<>(countThreads);

        UserManager userManager = UserManager.getInstance();

        for (int i = 0; i < countThreads; i++) {
            String name = "miner #" + i;
            miners.add( new Miner(blockchain, totalBlocks, name, userManager.getRandomUser()));
            miners.get(i).start();
        }

        Thread chatThread = startMessageSender();

        for (Thread miner : miners) {
            try {
                miner.join();
            } catch (Exception ignored) {
            }
        }

        chatThread.interrupt();
    }

    private Thread startMessageSender() {
        Thread chatThread = ChatSimulator.getInstance();
        chatThread.start();
        return chatThread;
    }
}
