package Bank;

import java.io.Serializable;

public class Account implements Serializable {
    protected String name; // The person who owns the account
    protected double balance;// //remaining balance
    protected int transCount; // the count of Transaction objects and used as the ID for each transaction

    public Account(String acctName, double initBalance){
        balance = initBalance;
        name = acctName;
    }
    public String getName() {
        return name;
    }
    /**
     * @return The remaining balance
     */
    public double getBalance() {
        return balance;
    }
    /**
     * If tCode is 1, subtract account balance by transaction amount
     * If tCode is 2, increase account balance with transaction amount
     * @param transAmt is the amount of current transaction
     * @param tCode    is the transaction code of current transaction
     */
    public void setBalance(double transAmt, int tCode) {
        if (tCode == 1)
            balance = balance - transAmt;
        else if (tCode == 2)
            balance = balance + transAmt;
    }

    public int gettransCount() { //returns the current value of transCount
        return transCount;
    }
}
