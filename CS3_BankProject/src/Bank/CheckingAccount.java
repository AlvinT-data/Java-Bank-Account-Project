package Bank;
import java.util.ArrayList;
import java.io.Serializable;

public class CheckingAccount extends Account {
    private double totalServiceCharge; //total amount of service charge
    private boolean balanceBelowZero; //record of whether the transaction cause balance goes below $0.00
    private boolean monthlyCheckingCharge; //record of whether extra charge of $5 when balance fall below
                                                  //$500 due to a transaction
    private ArrayList<Transaction> transList; // keeps a list of Transaction objects for the account
    /**
     * the CheckingAccount class enables an object that represents
     * a user's checking account in a bank system
     *
     * @param initialBalance is the initialize balance in a new account
     */
    public CheckingAccount(String name, double initialBalance) {
        super(name, initialBalance);
        balance = initialBalance;
        totalServiceCharge = 0;
        monthlyCheckingCharge = false;
        balanceBelowZero = false;
        transCount = 0;
        transList = new ArrayList<>();
    }

    /**
     * @return total amount of service charge
     */
    public double getServiceCharge() {
        return totalServiceCharge;
    }

    /**
     * Adjust total service charge
     *
     * @param newServiceCharge is the service charge for current transaction
     */
    public void setServiceCharge(double newServiceCharge) {
        totalServiceCharge += newServiceCharge;
        Transaction newTrans = new Transaction(3, gettransCount(), newServiceCharge );
        addTrans(newTrans);
        addtransCount();
        if (newServiceCharge == 0.15) {
            extraCheckCharge();
        }
    }

    /**
     * @return true if monthly charge is applied, false if not
     */
    public boolean getMonthlyCheckingCharge() {
        if (!monthlyCheckingCharge) {
            monthlyCheckingCharge = true;
            return false;
        }
        return true;
    }

    /**
     * @return true if account balance is below zero, false if not
     */
    public boolean getBalanceBelowZero() {
        if (balanceBelowZero) {
            balanceBelowZero = false;
            return true;
        }
        return false;
    }

    /**
     * calculates amount of extra service charge for the check of
     * current transaction
     */
    public void extraCheckCharge() {
        if (balance < 500.0 && !monthlyCheckingCharge) {
            totalServiceCharge += 5;
            Transaction newTrans = new Transaction(3, gettransCount(), 5);
            addTrans(newTrans);
            addtransCount();
        }
        if (balance < 0.0) {
            totalServiceCharge += 10;
            balanceBelowZero = true;
            Transaction newTrans = new Transaction(3, gettransCount(), 10);
            addTrans(newTrans);
            addtransCount();
        }
    }

    public void addTrans(Transaction newTrans) { // adds a transaction object to the transList
        transList.add(newTrans);
    }

    public Transaction getTrans(int i) {// returns the i-th Transaction object in the list
        return transList.get(i);
    }

    public void addtransCount() {
        transCount++;
    }

}
