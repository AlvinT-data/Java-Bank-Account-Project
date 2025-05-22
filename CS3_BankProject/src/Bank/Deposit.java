package Bank;

public class Deposit extends Transaction{
    private double checkAmt;
    private double cashAmt;

    public Deposit(int tCount, int tId, double checkAmt, double cashAmt) {
        super(tCount, tId, checkAmt + cashAmt);
        this.checkAmt = checkAmt;
        this.cashAmt = cashAmt;
    }
    public double getCheckAmt() {
        return checkAmt;
    }
    public double getCashAmt() {
        return cashAmt;
    }
}
