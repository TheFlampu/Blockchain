package blockchain;

public class VCoin {
    private final long amount;

    public VCoin(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return amount + " VCoin";
    }
}
