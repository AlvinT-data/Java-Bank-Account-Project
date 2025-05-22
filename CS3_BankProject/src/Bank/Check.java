package Bank;

public class Check extends Transaction{
    private int checkNumber; // check number for each check transaction
    public Check(int tCount, int tId, double tAmt, int checkNumber) {
        super(tCount, tId, tAmt);
        this.checkNumber = checkNumber;
    }
    public int getCheckNumber() {
        return checkNumber;
    }
    public void setCheckNumber(int checkNumber) {
        this.checkNumber = checkNumber;
    }
}
