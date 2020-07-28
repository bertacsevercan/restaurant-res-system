
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagerFrame extends JFrame {

    public JPanel contentPane;



    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   ManagerFrame frame = new ManagerFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    InsideFrame insideFrame = new InsideFrame();
    Connection connection = null;
    Statement statement = null;
    DBHelper helper = new DBHelper();
    ResultSet resultSet = null;

    /**
     * Create the frame.
     */
    public ManagerFrame() {
        setType(Type.UTILITY);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 618, 418);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 21, 604, 360);
        contentPane.add(scrollPane);

        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        textArea.setEditable(false);

        try {
            connection = helper.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT reservDate, reserv_time, customerName, customerSurname, phone FROM today JOIN customer ON  customerUsername = userName ORDER BY reservDate");
            while(resultSet.next()) {
                textArea.append(resultSet.getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + " " +
                        resultSet.getString(4) + "  " + resultSet.getString(5) + "\n" );
            }

        } catch (SQLException e) {
            helper.showErrorMessage(e);
        } finally{
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                helper.showErrorMessage(e);
            }

        }


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);
        menuBar.setBounds(0, 0, 604, 22);
        contentPane.add(menuBar);

        JMenu mn_backReserv = new JMenu("Make Reservation");
        mn_backReserv.setForeground(Color.WHITE);
        menuBar.add(mn_backReserv);
        JButton btn_managerBack = new JButton("Go Back");
        btn_managerBack.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btn_managerBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insideFrame.setVisible(true);
            }
        });
        mn_backReserv.add(btn_managerBack);
    }
}
