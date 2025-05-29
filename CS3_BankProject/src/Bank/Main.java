//*************************************
// CheckingAccount Author: Alvin T.
// Simulates a bank checking account system
//*************************************
package Bank;
import javax.swing.*;
import java.text.DecimalFormat;
import java.awt.*;
import java.io.*;
import java.util.Vector;
// Fix saved, set false when new transactions added
public class Main {
    // set number displaying format with two decimal places
    public static DecimalFormat fmt = new DecimalFormat("0.00");
    //a CheckingAccount object to keep track of the account information
    public static CheckingAccount user_acc;
    //JFrame
    public static CheckOptionsPanel frame;
    // JTextArea
    public static JTextArea ta;
    //saving
    public static boolean saved = false;
    public static Vector<CheckingAccount> dataStore;
    public static String filename = "/Users/alvin/Downloads/Testing";
    public static void main(String[] args) {
        // a vector to store all checking accounts
        dataStore = new Vector<CheckingAccount>();
        // Set JTextArea
        ta = new JTextArea(10, 50);
        ta.setFont(new Font("Monospaced",Font.PLAIN, 12));
        // Set Frame
        frame = new CheckOptionsPanel("Transaction Options");
        frame.getContentPane().add(ta);
        frame.pack();
        frame.setVisible(true);
    }
    public static void enter_transaction() {
        if (dataStore.isEmpty())
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        int tCode = getTransCode();
        if(tCode == 0) {
            showEndMessage();
        }
        else if(tCode == 1) {
            int checkNumber = getCheckNumber();
            double transAmount = getTransAmt();
            processCheck(transAmount, checkNumber);
            saved = false;
        }
        else if(tCode == 2) {
            double cashAmt, checkAmt;
            //create text field and JPanel
            JTextField fCash = new JTextField();
            JTextField fCheck = new JTextField();
            // Set wider input fields (e.g. 200 pixels wide, 24 high)
            Dimension dim = new Dimension(150, 24);
            fCash.setPreferredSize(dim);
            fCash.setPreferredSize(dim);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            panel.add(new JLabel("Cash"));
            panel.add(fCash);
            //panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacer
            panel.add(new JLabel("Check"));
            panel.add(fCheck);
            //show input window
            int result;
            for (;;) {
                try {
                    result = JOptionPane.showConfirmDialog(null, panel, "Deposit Window", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String cash = fCash.getText().trim();
                        String check = fCheck.getText().trim();
                        cashAmt = (cash.isEmpty()) ? 0.0 : Double.parseDouble(cash);
                        checkAmt = (check.isEmpty()) ? 0.0 : Double.parseDouble(check);
                        processDeposit(cashAmt, checkAmt);
                        saved = false;
                        return;
                    }
                } catch (NumberFormatException e) {
                    String msg = "Invalid input: please enter a number.";
                    JOptionPane.showMessageDialog(null, msg);
                }
            }
        }
    }
    /**
     * asks user to input initial account balance
     * @return user input
     */
    public static double getAccBalance() {
        String input_balance;
        double balance;
        for (;;) {
            try {
                input_balance = JOptionPane.showInputDialog("Enter initial balance amount: ");
                balance = Double.parseDouble(input_balance);
                return balance;
            } catch(NumberFormatException e) {
                String msg = "Invalid input: please enter a number.";
                JOptionPane.showMessageDialog (null, msg);
            }
        }
    }

    public static String getAccName() {
        return JOptionPane.showInputDialog("Enter the account name: ");
    }
    /**
     * asks user to input transaction code
     * @return user input
     */
    public static int getTransCode() {
        String input_tCode;
        int code;
        for (;;) {
            try {
                input_tCode = JOptionPane.showInputDialog("Enter your transaction code: ");
                code = Integer.parseInt(input_tCode);
                if (code < 0 || code > 2) {
                    throw new IllegalArgumentException("Invalid Input: please enter a number 0-2.");
                }
                return code;
            } catch(NumberFormatException e) {
                String msg = "Invalid input: please enter a number 0-2.";
                JOptionPane.showMessageDialog (null, msg);
            } catch(IllegalArgumentException e) {
                JOptionPane.showMessageDialog (null, e.getMessage());
            }
        }
    }
    /**
     * asks user to input transaction amount
     * @return user input
     */
    public static double getTransAmt() {
        String input_transAmt;
        double transAmt;
        for (;;) {
            try {
                input_transAmt = JOptionPane.showInputDialog("Enter your transaction amount: ");
                transAmt = Double.parseDouble(input_transAmt);
                return transAmt;
            } catch(NumberFormatException e) {
                String msg = "Invalid input: please enter a number.";
                JOptionPane.showMessageDialog (null, msg);
            }
        }
    }

    public static int getCheckNumber() {
        String input_checkNum;
        int checkNum;
        for (;;) {
            try {
                input_checkNum = JOptionPane.showInputDialog("Enter your check number: ");
                checkNum = Integer.parseInt(input_checkNum);
                return checkNum;
            } catch(NumberFormatException e) {
                String msg = "Invalid input: please enter a whole number.";
                JOptionPane.showMessageDialog (null, msg);
            }
        }
    }
    /**
     * processes a check transaction and calls auxiliary methods
     * @param transAmt is transaction amount
     */
    public static void processCheck(double transAmt, int checkNumber) {
        user_acc.setBalance(transAmt, 1);
        Transaction newTrans = new Check(1, user_acc.gettransCount(), transAmt, checkNumber);
        user_acc.addTrans(newTrans);
        user_acc.addtransCount();
        user_acc.setServiceCharge(0.15);
        showCheckMessage(transAmt, checkNumber);

    }
    /**
     * processes a deposit transaction and calls auxiliary methods
     * @param cashAmt is cash deposit amount
     * @param checkAmt is check deposit amount
     */
    public static void processDeposit(double cashAmt, double checkAmt) {
        double transAmt = cashAmt + checkAmt;
        user_acc.setBalance(transAmt, 2);
        Transaction newTrans = new Deposit(2, user_acc.gettransCount(),cashAmt, checkAmt);
        user_acc.addTrans(newTrans);
        user_acc.addtransCount();
        user_acc.setServiceCharge(0.10);
        showDepositMessage(transAmt);
    }
    /**
     * shows a message about account information when transaction code is zero
     */
    public static void showEndMessage() {
        double curr_balance = user_acc.getBalance();
        double total_charge = user_acc.getServiceCharge();
        double final_balance = curr_balance - total_charge;
        String msg = "Transaction: End\n";
        msg +=  "Current Balance: ";
        if (user_acc.getBalance() < 0.0) {
            msg += "($" + fmt.format(Math.abs(curr_balance)) + ")\n";
        }
        else {
            msg += "$" + fmt.format(Math.abs(curr_balance)) + "\n";
        }
        msg += "Total Service Charges: $" + fmt.format(total_charge) + "\n";
        msg += "Final Balance: ";
        if (final_balance < 0.0) {
            msg += "($" + fmt.format(Math.abs(final_balance)) + ")\n";
        }
        else {
            msg += "$" + fmt.format(Math.abs(final_balance)) + "\n";
        }
        JOptionPane.showMessageDialog (null, msg);
    }
    /**
     * show a message about the transaction and account information when transaction code is one
     * @param transAmt is transaction amount
     */
    public static void showCheckMessage(double transAmt, int checkNumber) {
        double curr_balance = user_acc.getBalance();
        double total_charge = user_acc.getServiceCharge();
        String msg = user_acc.getName() + "'s Account\n";
        msg += "Transaction: Check #" + checkNumber;
        msg += " in Amount of $" + fmt.format(transAmt) + "\n";
        msg += "Current Balance: ";
        if (curr_balance < 0.0) {
            msg += "($" + fmt.format(Math.abs(curr_balance)) + ")\n";
        } else {
            msg += "$" + fmt.format(Math.abs(curr_balance)) + "\n";
        }
        msg += "ServiceCharge: Check --- charge $0.15\n";
        if (!user_acc.getMonthlyCheckingCharge() && user_acc.getBalance() < 500) {
            msg += "Service Charge: Below $500 --- charge $5.00\n";
        }
        if (curr_balance < 50.0) {
            msg += "Warning: Balance below $50\n";
        }
        if (user_acc.getBalanceBelowZero()) {
            msg += "Service Charge: Below $0 --- charge $10.00\n";
        }
        msg += "Total Service Charge: $" + fmt.format(total_charge) + "\n";
        JOptionPane.showMessageDialog(null, msg);
    }
    /**
     * show a message about the transaction and account information when transaction code is two
     * @param transAmt is transaction amount
     */
    public static void showDepositMessage(double transAmt) {
        double curr_balance = user_acc.getBalance();
        double total_charge = user_acc.getServiceCharge();
        String msg = user_acc.getName() + "'s Account\n";
        msg += "Transaction: Deposit in Amount of $" + fmt.format(transAmt) + "\n";
        msg += "Current Balance: ";
        if (curr_balance < 0.0) {
            msg += "($" + fmt.format(Math.abs(curr_balance)) + ")\n";
        } else {
            msg += "$" + fmt.format(Math.abs(curr_balance)) + "\n";
        }
        msg += "ServiceCharge: Deposit --- charge $0.10\n";
        msg += "Total Service Charge: $" + fmt.format(total_charge) + "\n";
        JOptionPane.showMessageDialog(null, msg);
    }
    /**
     * print out all transactions table
     */
    public static void showTransTable() {
        if (dataStore.isEmpty())
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        String msg = "List All Transactions\n";
        msg += "Name: " + user_acc.getName() + "\n";
        msg += "Balance: " + ((user_acc.getBalance()>=0) ? "$" + fmt.format(user_acc.getBalance()) :
                "($" + fmt.format(Math.abs(user_acc.getBalance())) + ")") + "\n";
        msg += "Total Service Charge: $" + fmt.format(user_acc.getServiceCharge()) + "\n\n";
        msg += "ID\tType\t\tAmount\n";
        for (int i = 0; i < user_acc.gettransCount(); i++) {
            Transaction temp = user_acc.getTrans(i);
            msg += temp.getTransId() + "\t";
            if (temp.getTransNumber() == 1) {
                msg += "Check" + "\t\t";
            }
            else if (temp.getTransNumber() ==2) {
                msg += "Deposit" + "\t\t";
            }
            else {
                msg += "Svc. Chg.\t";
            }
            msg += "$" + fmt.format(temp.getTransAmount());
            msg += "\n";
        }
        ta.setText(msg);
    }
    /**
     * print out check transactions table
     */
    public static void showCheckTable() {
        if (dataStore.isEmpty())
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        String msg = "List All Checks\n";
        msg += "Name: " + user_acc.getName() + "\n";
        msg += "Balance: " + ((user_acc.getBalance()>=0) ? "$" + fmt.format(user_acc.getBalance()) :
                "($" + fmt.format(Math.abs(user_acc.getBalance())) + ")") + "\n";
        msg += "Total Service Charge: $" + fmt.format(user_acc.getServiceCharge()) + "\n\n";
        msg += "ID\tCheck\t\tAmount\n";
        for (int i = 0; i < user_acc.gettransCount(); i++) {
            Transaction temp = user_acc.getTrans(i);
            if (temp.getTransNumber() == 1) {
                msg += temp.getTransId() + "\t";
                msg += ((Check)temp).getCheckNumber();
                msg += (((Check)temp).getCheckNumber()<100) ? "\t\t": "\t\t";
                msg += "$" + fmt.format(temp.getTransAmount());
                msg += "\n";
            }
        }
        // Adjust the tab size and set to monospace
        ta.setText(msg);
    }
    /**
    * print out deposit transactions table
    */
    public static void showDepTable() {
        if (dataStore.isEmpty())
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        String msg = "List All Deposits\n";
        msg += "Name: " + user_acc.getName() + "\n";
        msg += "Balance: " + ((user_acc.getBalance()>=0) ? "$" + fmt.format(user_acc.getBalance()) :
                "($" + fmt.format(Math.abs(user_acc.getBalance())) + ")") + "\n";
        msg += "Total Service Charge: $" + fmt.format(user_acc.getServiceCharge()) + "\n\n";
        msg += "ID\tCash\tCheck\tAmount\n";
        for (int i = 0; i < user_acc.gettransCount(); i++) {
            Transaction temp = user_acc.getTrans(i);
            if (temp.getTransNumber() == 2) {
                msg += temp.getTransId() + "\t";
                msg += "$" + fmt.format(((Deposit)temp).getCashAmt());
                msg += (((Deposit)temp).getCashAmt()<10) ? "\t": "\t";
                msg += "$" + fmt.format(((Deposit)temp).getCheckAmt());
                msg += (((Deposit)temp).getCheckAmt()<10) ? "\t": "\t";
                msg += "$" + fmt.format(temp.getTransAmount());
                msg += "\n";
            }
        }
        ta.setText(msg);
    }

    /**
     * print out service charge table
    */
    public static void showServTable() {
        if (dataStore.isEmpty())
            JOptionPane.showMessageDialog(null, "You must select an account first.");
        String msg = "List All Service Charges\n";
        msg += "Name: " + user_acc.getName() + "\n";
        msg += "Balance: " + ((user_acc.getBalance()>=0) ? "$" + fmt.format(user_acc.getBalance()) :
                "($" + fmt.format(Math.abs(user_acc.getBalance())) + ")") + "\n";
        msg += "Total Service Charge: $" + fmt.format(user_acc.getServiceCharge()) + "\n\n";
        msg += "ID\tAmount\n";
        for (int i = 0; i < user_acc.gettransCount(); i++) {
            Transaction temp = user_acc.getTrans(i);
            if (temp.getTransNumber() == 3) {
                msg += temp.getTransId() + "\t";
                msg += "$" + fmt.format(temp.getTransAmount());
                msg += "\n";
            }
        }
        // Print on JTextArea
        ta.setText(msg);
    }

    /**
     * Method for opening files
     */
    public static void readAccounts()
    {
        int confirm;
        if (!saved)
        {
            String  message = "The data in the application is not saved.\n"+
                    "would you like to save it before reading the new file in?";
            confirm = JOptionPane.showConfirmDialog (null, message);
            if (confirm == JOptionPane.YES_OPTION)
                chooseFile(2);
        }
        chooseFile(1);
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            dataStore = (Vector<CheckingAccount>)in.readObject();
            in.close();
            saved = true;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        if (!dataStore.isEmpty()) {
            user_acc = dataStore.get(0);
        }
        saved = true;
    }

    /**
     * Method for saving files
     */
    public static void writeAccounts()
    {
        chooseFile(2);
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(dataStore);
            out.close();
            saved = true;
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public static void chooseFile(int ioOption)
    {
        int status, confirm;
        String  message = "Would you like to use the current default file: \n" + filename;
        confirm = JOptionPane.showConfirmDialog (null, message);
        if (confirm == JOptionPane.YES_OPTION)
            return;
        JFileChooser chooser = new JFileChooser();
        if (ioOption == 1)
            status = chooser.showOpenDialog (null);
        else
            status = chooser.showSaveDialog (null);
        if (status == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            filename = file.getPath();
        }
    }
    public static void addAcc() {
        double balance; String name;
        // get account name from the user
        name = getAccName();
        // get initial balance from the user
        balance = getAccBalance();
        user_acc = new CheckingAccount(name, balance);
        dataStore.add(user_acc);
        ta.setText("New account added for " + name);
        saved = false;
    }

    /**
     * find existing account by user name
     */
    public static void findAcc() {
        String name = getAccName();
        // prevent accessing empty list
        if (dataStore.isEmpty()) {
            ta.setText("Account not found for " + name + "\n");
            return;
        }
        for (int i=0; i<dataStore.size(); i++) {
            if (dataStore.get(i).getName().equals(name)) {
                ta.setText("Account found for " + name + "\n");
                user_acc = dataStore.get(i);
                return;
            }
        }
        ta.setText("Account not found for " + name + "\n");
    }

    /**
     * List all existing accounts information
     */
    public static void listAcc() {
        // prevent accessing empty list
        if (dataStore.isEmpty())
            return;
        String msg = "List of All Accounts:\n\n";
        for (int i=0; i<dataStore.size(); i++) {
            msg += "Name: " + dataStore.get(i).getName() + "\n";
            msg += "Balance: " + ((dataStore.get(i).getBalance()>=0) ? "$" + fmt.format(dataStore.get(i).getBalance()) :
                    "($" + fmt.format(Math.abs(dataStore.get(i).getBalance())) + ")") + "\n";
            msg += "Total Service Charge: $" + fmt.format(dataStore.get(i).getServiceCharge()) + "\n\n";
        }
        ta.setText(msg);
    }

}
