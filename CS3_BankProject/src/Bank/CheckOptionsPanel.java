package Bank;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * the CheckOptionsPanel class enables an object editing the user interface including buttons, background, etc.
 */
public class CheckOptionsPanel extends CheckPaneL {
    private final JLabel prompt;
    private final JRadioButton enter_trans;
    private final JRadioButton list_trans;
    private final JRadioButton list_check;
    private final JRadioButton list_dep;
    private final JRadioButton list_serv;
    private final JRadioButton open_file;
    private final JRadioButton save_file;

    public CheckOptionsPanel(JFrame frame) {
        super(frame);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        prompt = new JLabel("Choose action:");
        prompt.setFont(new Font("Helvetica", Font.BOLD, 24));
        enter_trans = new JRadioButton("Enter Transaction");
        enter_trans.setBackground(Color.green);
        list_trans = new JRadioButton("List All Transaction");
        list_trans.setBackground(Color.green);
        list_check = new JRadioButton("List All Checks");
        list_check.setBackground(Color.green);
        list_dep = new JRadioButton("List All Deposits");
        list_serv = new JRadioButton("List All Service Charges");
        open_file = new JRadioButton("Open File");
        save_file = new JRadioButton("Save File");
        ButtonGroup group = new ButtonGroup();
        group.add(enter_trans);
        group.add(list_trans);
        group.add(list_check);
        group.add(list_dep);
        group.add(list_serv);
        group.add(open_file);
        group.add(save_file);

        //Listener
        EOptionListener listener = new EOptionListener();
        enter_trans.addActionListener(listener);
        list_trans.addActionListener(listener);
        list_check.addActionListener(listener);
        list_dep.addActionListener(listener);
        list_serv.addActionListener(listener);
        open_file.addActionListener(listener);
        save_file.addActionListener(listener);
        // add the components to the JPanel
        add(prompt);
        add(enter_trans);
        add(list_trans);
        add(list_check);
        add(list_dep);
        add(list_serv);
        add(open_file);
        add(save_file);
        setBackground(Color.yellow);
        setPreferredSize(new Dimension(350, 160));
    }

    private class EOptionListener implements ActionListener {
        // Calls the method to process the option for which radio
        // button was pressed.
        public void actionPerformed(ActionEvent event) {
            Object source = event.getSource();
            if (source == enter_trans) {
                Main.enter_transaction();
            }
            else if (source == list_trans) {
                Main.showTransTable();
            }
            else if (source == list_check) {
                Main.showCheckTable();
            }
            else if (source == list_dep){
                Main.showDepTable();
            }
            else if (source == list_serv){
                Main.showServTable();
            }
            else if (source == open_file) {
                Main.readAccounts();
            }
            else {
                Main.writeAccounts();
            }
        }
    }
}
