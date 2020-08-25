package blockchain;

import java.security.Signature;

public class ChatSimulator extends Thread {
    private static ChatSimulator instance;
    private final BlockChain blockChain = BlockChain.getInstance();

    private ChatSimulator() {
    }

    public static ChatSimulator getInstance() {
        if (instance == null) {
            instance = new ChatSimulator();
        }
        return instance;
    }

    @Override
    public void run() {
        UserManager userManager = UserManager.getInstance();

        while (!isInterrupted()) {
            try {
                Thread.sleep(1500);
                User sender = userManager.getRandomUser();
                Signature rsa = Signature.getInstance("SHA1withRSA");
                rsa.initSign(sender.getPrivateKey());
                byte[] sign = rsa.sign();

                blockChain.sendMessage( new Transaction(
                        sender,
                        userManager.getRandomUser(),
                        new VCoin((long) (Math.random() * blockChain.getBalance(sender))),
                        sender.getPublicKey(),
                        blockChain.generateLastId(),
                        sign
                        )
                );
            } catch (InterruptedException ignored) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
