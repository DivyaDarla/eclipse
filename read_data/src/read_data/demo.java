package read_data;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class demo {

	private JFrame frame;
	private JTextField t1;
	private JTextField t2;
	private JTable table;
	private JButton bt3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo window = new demo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public demo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 753, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		t1 = new JTextField();
		t1.setBounds(229, 70, 96, 19);
		frame.getContentPane().add(t1);
		t1.setColumns(10);
		
		t2 = new JTextField();
		t2.setBounds(229, 170, 96, 19);
		frame.getContentPane().add(t2);
		t2.setColumns(10);
		
		JButton b1 = new JButton("New button");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n=t1.getText();
				String m=t2.getText();
				try {
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mrec","root","root");
					String query="insert into student values('"+n+"','"+m+"')";
					Statement st=con.createStatement();
					st.executeUpdate(query);
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(b1, "Registration Successfull");
			}
		});
		b1.setBounds(198, 335, 110, 21);
		frame.getContentPane().add(b1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(429, 43, 275, 168);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane.setViewportView(table);
		
		JButton b2 = new JButton("show");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/mrec","root","root");
					String q2="select * from student";
					Statement st2=con1.createStatement();
					ResultSet rs=st2.executeQuery(q2);
					ResultSetMetaData rsmd=rs.getMetaData();
					DefaultTableModel model1=(DefaultTableModel) table.getModel();
					int cols=rsmd.getColumnCount();
					String[] colName=new String[cols];
					for(int i=0;i<cols;i++)
						colName[i]=rsmd.getColumnName(i+1);
					model1.setColumnIdentifiers(colName);
					String name = null,marks;
					while(rs.next()) {
						name=rs.getString(1);
					    marks=rs.getString(2);
					    String[] row= {name,marks};
					    model1.addRow(row);
					}
				
					con1.close();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		b2.setBounds(546, 335, 85, 21);
		frame.getContentPane().add(b2);
		
		bt3 = new JButton("clear");
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel());
			}
		});
		bt3.setBounds(546, 392, 85, 21);
		frame.getContentPane().add(bt3);
	}
}
