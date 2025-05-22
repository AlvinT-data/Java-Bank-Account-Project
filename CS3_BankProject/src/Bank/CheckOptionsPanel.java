package Bank;
import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.event.*;

/**
 * the CheckOptionsPanel class enables an object editing the user interface including buttons, background, etc.
 */
public class CheckOptionsPanel extends CheckPaneL {
//    private final JLabel prompt;
//    private final JRadioButton enter_trans;
//    private final JRadioButton list_trans;
//    private final JRadioButton list_check;
//    private final JRadioButton list_dep;
//    private final JRadioButton list_serv;
//    private final JRadioButton open_file;
//    private final JRadioButton save_file;
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private JMenu fileMenu, accMenu, tranMenu;
    private JMenuItem enter_trans,list_trans,list_check, add_acc,
            list_dep,list_serv, find_acc, list_acc, open_file, save_file;
    private JMenuBar bar;

    public CheckOptionsPanel(String title) {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileMenu = new JMenu("File");
        MenuListener ml = new MenuListener();
        open_file = new JMenuItem("Open File");
        open_file.addActionListener(ml);
        fileMenu.add(open_file);
        save_file = new JMenuItem("Save File");
        save_file.addActionListener(ml);
        fileMenu.add(save_file);

        accMenu = new JMenu("Accounts");
        add_acc = new JMenuItem("Add New Account");
        add_acc.addActionListener(ml);
        accMenu.add(add_acc);

        list_trans = new JMenuItem("List All Transactions");
        list_trans.addActionListener(ml);
        accMenu.add(list_trans);

        list_check = new JMenuItem("List All Checks");
        list_check.addActionListener(ml);
        accMenu.add(list_check);

        list_dep = new JMenuItem("List All Deposits");
        list_dep.addActionListener(ml);
        accMenu.add(list_dep);

        list_serv = new JMenuItem("List All Service Charges");
        list_serv.addActionListener(ml);
        accMenu.add(list_serv);

        find_acc = new JMenuItem("Find An Account");
        find_acc.addActionListener(ml);
        accMenu.add(find_acc);

        list_acc = new JMenuItem("List All Accounts");
        list_acc.addActionListener(ml);
        accMenu.add(list_acc);

        tranMenu = new JMenu("Transactions");
        enter_trans = new JMenuItem("Enter Transaction");
        enter_trans.addActionListener(ml);
        tranMenu.add(enter_trans);

        bar = new JMenuBar( );
        bar.add(fileMenu);
        bar.add(accMenu);
        bar.add(tranMenu);
        setJMenuBar(bar);
    }

    private class MenuListener implements ActionListener {
        // Calls the method to process the option for which radio
        // button was pressed.
        public void actionPerformed(ActionEvent event) {
            String source = event.getActionCommand();
            if (source.equals("Enter Transaction")) {
                Main.enter_transaction();
            }
            else if (source.equals("List All Transactions")) {
                Main.showTransTable();
            }
            else if (source.equals("List All Checks")) {
                Main.showCheckTable();
            }
            else if (source.equals("List All Deposits")){
                Main.showDepTable();
            }
            else if (source.equals("List All Service Charges")) {
                Main.showServTable();
            }
            else if (source.equals("Open File")) {
                Main.readAccounts();
            }
            else if (source.equals("Save File")) {
                Main.writeAccounts();
            }
            else if (source.equals("Add New Account")) {
                Main.addAcc();
            }
            else if (source.equals("Find An Account")) {
                Main.findAcc();
            }
            else { // source.equals("List All Accounts")
                Main.listAcc();
            }
        }
    }
}
