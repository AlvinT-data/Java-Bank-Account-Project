package Bank;

import java.io.Serializable;

public class Transaction implements Serializable {
    private final int transNumber; //transaction type of the transaction
    private final int transId; //ID of the transaction
    private final double transAmt; //transaction amount

    /**
     * constructor
     * @param number is transaction type
     * @param id is transaction ID
     * @param amount is transaction amount
     */
    public Transaction(int number, int id, double amount)
    {
        transNumber = number;
        transId = id;
        transAmt = amount;
    }

    public int getTransNumber()
    {
        //1 is check, 2 is deposit, 3 is ser. chg.
        return transNumber;
    }
    public int getTransId()
    {
        return transId;
    }
    public double getTransAmount()
    {
        return transAmt;
    }
}
