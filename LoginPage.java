

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public  class LoginPage extends JFrame {

    public JPanel contentPane;
    public static JTextField tf_username;
    public static JPasswordField pwField;




    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage frame = new LoginPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
    }

    Connection connection = null;
    DBHelper helper = new DBHelper();
    PreparedStatement ps = null;
    Statement statement = null;
    ResultSet resultSet;
    InsideFrame insideFrame = new InsideFrame();
    /**
     * Create the frame.
     */
    public LoginPage()  {
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 520, 323);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbl_reservation = new JLabel("RESERVATIONS");
        lbl_reservation.setForeground(Color.WHITE);
        lbl_reservation.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_reservation.setFont(new Font("Tahoma", Font.BOLD, 16));
        lbl_reservation.setBounds(369, 25, 137, 39);
        contentPane.add(lbl_reservation);

        JLabel lbL_username = new JLabel("USERNAME");
        lbL_username.setBackground(Color.DARK_GRAY);
        lbL_username.setForeground(Color.WHITE);
        lbL_username.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbL_username.setBounds(321, 75, 89, 14);
        contentPane.add(lbL_username);

        tf_username = new JTextField();
        lbL_username.setLabelFor(tf_username);
        tf_username.setBounds(395, 72, 96, 20);
        contentPane.add(tf_username);
        tf_username.setColumns(10);

        pwField = new JPasswordField();
        pwField.setBounds(395, 103, 96, 20);
        contentPane.add(pwField);
        pwField.setColumns(10);

        JButton btn_login = new JButton("LOGIN");
        btn_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connection = helper.getConnection();
                    statement =  connection.createStatement();
                    resultSet = statement.executeQuery("select * from customer where userName='" +tf_username.getText()+ "' and pw='"+pwField.getText()+"' ");
                    if(resultSet.next()){
                        insideFrame.setVisible(true);
                        insideFrame.tf_name.setText(resultSet.getString("customerName"));
                        insideFrame.tf_surname.setText(resultSet.getString("customerSurname"));
                        insideFrame.tf_phone.setText(resultSet.getString("phone"));



                    } else{
                        JOptionPane.showMessageDialog(null,"Incorrect Username or Password");
                    }
                } catch (SQLException ex) {
                    helper.showErrorMessage(ex);
                }
                finally {
                    try {
                        connection.close();
                        statement.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });
        btn_login.setFont(new Font("Tahoma", Font.BOLD, 11));
        btn_login.setBounds(395, 131, 101, 23);
        contentPane.add(btn_login);

        JButton btn_register = new JButton("REGISTER");
        btn_register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connection = helper.getConnection();
                    ps = connection.prepareStatement("insert into customer(userName, pw) values(?,?)");
                    ps.setString(1,tf_username.getText());
                    ps.setString(2,pwField.getText());
                    int result = ps.executeUpdate();
                    if(result > 0){
                      JOptionPane.showMessageDialog(null,"You are registered, Login now!");



                    }

                } catch(SQLException ex) {
                    helper.showErrorMessage(ex);
                }
                finally{
                    try {
                        connection.close();
                        ps.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });
        btn_register.setFont(new Font("Tahoma", Font.BOLD, 11));
        btn_register.setBounds(395, 164, 101, 23);
        contentPane.add(btn_register);

        JLabel lbl_pw = new JLabel("PASSWORD");
        lbl_pw.setBackground(Color.DARK_GRAY);
        lbl_pw.setForeground(Color.WHITE);
        lbl_pw.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl_pw.setBounds(321, 106, 89, 14);
        contentPane.add(lbl_pw);

        JLabel lbl_bg_image = new JLabel("");
        lbl_bg_image.setBackground(Color.WHITE);
        lbl_bg_image.setIcon(new ImageIcon(LoginPage.class.getResource("/image/bgRestaruant.jpg")));
        lbl_bg_image.setBounds(0, 0, 506, 286);
        contentPane.add(lbl_bg_image);
    }
}
