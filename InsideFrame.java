
import com.mysql.cj.log.Log;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.sql.*;
import java.util.Objects;


public class InsideFrame extends JFrame {

    public JPanel contentPane;
    public JTextField tf_name;
    public JTextField tf_surname;
    public JTextField tf_phone;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InsideFrame frame = new InsideFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    String[] date = {"Select the date","2020-05-01", "2020-05-02", "2020-05-03", "2020-05-04", "2020-05-05",
            "2020-05-06", "2020-05-07", "2020-05-08", "2020-05-09", "2020-05-10", "2020-05-11",
            "2020-05-12", "2020-05-13", "2020-05-14", "2020-05-15", "2020-05-16", "2020-05-17", "2020-05-18",
            "2020-05-19", "2020-05-20", "2020-05-21", "2020-05-22", "2020-05-23", "2020-05-24", "2020-05-25",
            "2020-05-25", "2020-05-26", "2020-05-27", "2020-05-28", "2020-05-29", "2020-05-30", "2020-05-31"};
    Connection connection = null;

    DBHelper helper = new DBHelper();
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;
    Statement statement = null;
    ResultSet resultSet = null;
    PreparedStatement statement2 = null;
    ResultSet resultSet2 = null;



    /**
     * Create the frame.
     */
    public InsideFrame() {

        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 616, 418);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tf_name = new JTextField();
        tf_name.setBounds(363, 101, 158, 20);
        contentPane.add(tf_name);
        tf_name.setColumns(10);

        tf_surname = new JTextField();
        tf_surname.setBounds(363, 144, 158, 20);
        contentPane.add(tf_surname);
        tf_surname.setColumns(10);

        tf_phone = new JTextField();
        tf_phone.setBounds(363, 186, 158, 20);
        contentPane.add(tf_phone);
        tf_phone.setColumns(10);

        JLabel lbl_name = new JLabel("NAME");
        lbl_name.setForeground(Color.WHITE);
        lbl_name.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_name.setBounds(304, 104, 49, 14);
        contentPane.add(lbl_name);

        JLabel lbl_surname = new JLabel("SURNAME");
        lbl_surname.setForeground(Color.WHITE);
        lbl_surname.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_surname.setBounds(304, 147, 83, 14);
        contentPane.add(lbl_surname);

        JLabel lbl_mobile = new JLabel("MOBILE");
        lbl_mobile.setForeground(Color.WHITE);
        lbl_mobile.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_mobile.setBounds(304, 189, 49, 14);
        contentPane.add(lbl_mobile);

        JLabel lbl_notes = new JLabel("NOTES");
        lbl_notes.setForeground(Color.WHITE);
        lbl_notes.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_notes.setBounds(304, 228, 49, 14);
        contentPane.add(lbl_notes);

        JTextField tf_notes = new JTextField();
        tf_notes.setBounds(363, 217, 160, 37);
        contentPane.add(tf_notes);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(282, 72, 260, 2);
        contentPane.add(separator_1);

        JSeparator separator_2 = new JSeparator();
        separator_2.setBounds(282, 278, 260, 2);
        contentPane.add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setOrientation(SwingConstants.VERTICAL);
        separator_3.setBounds(541, 72, 1, 208);
        contentPane.add(separator_3);

        JLabel lbl_time_select = new JLabel("Time Selection");
        lbl_time_select.setForeground(Color.WHITE);
        lbl_time_select.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lbl_time_select.setBounds(282, 60, 105, 14);
        contentPane.add(lbl_time_select);

        JComboBox cBox_date = new JComboBox();
        cBox_date.setMaximumRowCount(5);
        cBox_date.setBounds(33, 100, 122, 22);

        for (int i = 0; i < 32; i++) {
            cBox_date.addItem(date[i]);
        }
        contentPane.add(cBox_date);


        JRadioButton rdbtn_lunch = new JRadioButton("LUNCH");
        rdbtn_lunch.setForeground(Color.WHITE);
        rdbtn_lunch.setBackground(Color.DARK_GRAY);
        rdbtn_lunch.setSelected(true);
        rdbtn_lunch.setFont(new Font("Times New Roman", Font.BOLD, 11));
        rdbtn_lunch.setBounds(39, 185, 65, 23);
        contentPane.add(rdbtn_lunch);

        JRadioButton rdbtn_dinner = new JRadioButton("DINNER");
        rdbtn_dinner.setForeground(Color.WHITE);
        rdbtn_dinner.setBackground(Color.DARK_GRAY);
        rdbtn_dinner.setSelected(false);
        rdbtn_dinner.setFont(new Font("Tahoma", Font.BOLD, 11));
        rdbtn_dinner.setBounds(39, 224, 76, 23);
        contentPane.add(rdbtn_dinner);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtn_lunch);
        buttonGroup.add(rdbtn_dinner);



        JButton btn_reserv = new JButton("MAKE RESERVATION");
        btn_reserv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    connection = helper.getConnection();

                    String sql = "update customer set customerName=? ,customerSurname=? , phone=? , notes=?  where userName=? ";
                    String sql2 = "insert into  today(reservDate, reserv_time, customerUsername) values (?,?,?)";
                    ps1 = connection.prepareStatement(sql);
                    ps2 = connection.prepareStatement(sql2);


                    ps1.setString(1, tf_name.getText());
                    ps1.setString(2, tf_surname.getText());
                    ps1.setString(3, tf_phone.getText());
                    ps1.setString(4, tf_notes.getText());
                    ps1.setString(5, LoginPage.tf_username.getText());


                    ps2.setString(1, cBox_date.getSelectedItem().toString());
                    if (rdbtn_lunch.isSelected()) {
                        ps2.setString(2, "lunch");
                    } else {
                        ps2.setString(2, "dinner");
                    }
                    ps2.setString(3, LoginPage.tf_username.getText());

                    int res1 = ps1.executeUpdate();
                    int res2 = ps2.executeUpdate();


                    if (res1 > 0 && res2 > 0) {
                        JOptionPane.showMessageDialog(null, "Reservation made!");
                    }

                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                } finally {
                    try {
                        connection.close();
                        ps1.close();
                        ps2.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }


        });
        btn_reserv.setFont(new Font("Tahoma", Font.BOLD, 11));
        btn_reserv.setBounds(282, 319, 260, 23);
        contentPane.add(btn_reserv);


        cBox_date.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    connection = helper.getConnection();
                    String sql = "select  count(idtoday) as totalRes from today where reservDate= ? and reserv_time= 'lunch' ";
                    statement2 = connection.prepareStatement(sql);
                    statement2.setString(1, cBox_date.getSelectedItem().toString());
                    resultSet2 = statement2.executeQuery();

                    while(resultSet2.next()){
                        if(resultSet2.getInt("totalRes")< 8){
                            btn_reserv.setEnabled(true);
                        }
                        else if(resultSet2.getInt("totalRes")>= 8 && rdbtn_lunch.isSelected()) {
                            btn_reserv.setEnabled(false);
                        }

                    }
                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                }finally {
                    try {
                        connection.close();
                        statement2.close();
                        resultSet2.close();
                    } catch (SQLException ex) {
                        helper.showErrorMessage(ex);
                    }

                }

            }
        });
        rdbtn_lunch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connection = helper.getConnection();
                    String sql = "select  count(idtoday) as totalRes from today where reservDate= ? and reserv_time= 'lunch' ";
                    statement2 = connection.prepareStatement(sql);
                    statement2.setString(1, cBox_date.getSelectedItem().toString());
                    resultSet2 = statement2.executeQuery();

                    while(resultSet2.next()){
                        if(resultSet2.getInt("totalRes")< 8){
                            btn_reserv.setEnabled(true);
                        }
                        else if(resultSet2.getInt("totalRes")>= 8 && rdbtn_lunch.isSelected()) {
                            btn_reserv.setEnabled(false);
                        }
                    }
                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                }finally {
                    try {
                        connection.close();
                        statement2.close();
                        resultSet2.close();
                    } catch (SQLException ex) {
                        helper.showErrorMessage(ex);
                    }
                }

            }
        });

        rdbtn_dinner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connection = helper.getConnection();
                    String sql = "select  count(idtoday) as totalRes from today where reservDate= ? and reserv_time= 'dinner' ";
                    statement2 = connection.prepareStatement(sql);
                    statement2.setString(1, cBox_date.getSelectedItem().toString());
                    resultSet2 = statement2.executeQuery();
                    while(resultSet2.next()){
                        if(resultSet2.getInt("totalRes")< 8){
                            btn_reserv.setEnabled(true);
                        }
                        else if(resultSet2.getInt("totalRes")>= 8 && rdbtn_dinner.isSelected() ) {
                            btn_reserv.setEnabled(false);
                        }

                    }
                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                }
                finally {
                    try {
                        connection.close();
                        statement2.close();
                        resultSet2.close();
                    } catch (SQLException ex) {
                        helper.showErrorMessage(ex);
                    }

                }


            }
        });







        JSeparator separator_4 = new JSeparator();
        separator_4.setBounds(33, 162, 100, 2);
        contentPane.add(separator_4);

        JSeparator separator_5 = new JSeparator();
        separator_5.setOrientation(SwingConstants.VERTICAL);
        separator_5.setBounds(132, 162, 1, 92);
        contentPane.add(separator_5);

        JSeparator separator_6 = new JSeparator();
        separator_6.setBounds(33, 252, 100, 2);
        contentPane.add(separator_6);

        JSeparator separator_7 = new JSeparator();
        separator_7.setOrientation(SwingConstants.VERTICAL);
        separator_7.setBounds(33, 217, 1, 37);
        contentPane.add(separator_7);

        JSeparator separator_8 = new JSeparator();
        separator_8.setOrientation(SwingConstants.VERTICAL);
        separator_8.setBounds(33, 162, 1, 70);
        contentPane.add(separator_8);

        JLabel lbl_mealSelect = new JLabel("Meal Selection");
        lbl_mealSelect.setForeground(Color.WHITE);
        lbl_mealSelect.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lbl_mealSelect.setBounds(33, 147, 100, 14);
        contentPane.add(lbl_mealSelect);


        JLabel lbl_dateSelect = new JLabel("Date Selection");
        lbl_dateSelect.setForeground(Color.WHITE);
        lbl_dateSelect.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
        lbl_dateSelect.setBounds(33, 87, 97, 14);
        contentPane.add(lbl_dateSelect);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);
        menuBar.setBounds(0, 0, 602, 22);
        contentPane.add(menuBar);

        JMenu mn_manager = new JMenu("See Reservation");
        mn_manager.setForeground(Color.WHITE);
        menuBar.add(mn_manager);

        JButton btn_managerShow = new JButton("Show");
        btn_managerShow.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btn_managerShow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerFrame managerFrame = new ManagerFrame();
                try {
                    connection = helper.getConnection();
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("select * from customer where userName= 'manager_rest' ");
                    resultSet.next();
                    if (LoginPage.tf_username.getText().equals(resultSet.getString("userName"))) {

                        managerFrame.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(null, "Only manager can access!!!");
                    }
                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                }

            }
        });
        mn_manager.add(btn_managerShow);


        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(282, 119, 1, 161);
        contentPane.add(separator);

        JSeparator separator_9 = new JSeparator();
        separator_9.setOrientation(SwingConstants.VERTICAL);
        separator_9.setBounds(282, 72, 1, 44);
        contentPane.add(separator_9);
    }
}
