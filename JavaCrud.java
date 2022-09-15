import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.mysql.cj.xdevapi.Schema.CreateCollectionOptions;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.NotActiveException;
import javax.swing.ImageIcon;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtBookName;
	private JTextField txtEdition;
	private JTextField txtPrice;
	private JTable table;
	private JTextField txtbid;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JavaCrud() {
		initialize();
		connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void connect() {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javacrud","root","rajput");
			
//			String q = "create table bookshop(id int(11) primary key auto_increment,bName varchar(225)";
			
			if(con.isClosed()) {
				System.out.println("Connection closed");
			}else {
				System.out.println("Connection created");
			}
//			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void table_load() {
		
		try {
			
			pst = con.prepareStatement("select * from bookshop");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(216, 215, 211));
		frame.setBounds(100, 100, 874, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.getContentPane().setLayout(null);
		
		JLabel BookShop = new JLabel("Book Shop");
		BookShop.setForeground(new Color(128, 0, 0));
		BookShop.setFont(new Font("Tahoma", Font.BOLD, 30));
		BookShop.setBounds(316, 0, 176, 59);
		frame.getContentPane().add(BookShop);
		
		JPanel formPanel = new JPanel();
		formPanel.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ragistration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		formPanel.setBounds(26, 57, 415, 193);
		frame.getContentPane().add(formPanel);
		formPanel.setLayout(null);
		
		JLabel edition = new JLabel("Edition");
		edition.setFont(new Font("Tahoma", Font.BOLD, 14));
		edition.setBounds(65, 78, 98, 32);
		formPanel.add(edition);
		
		JLabel bName = new JLabel("Book Name");
		bName.setFont(new Font("Tahoma", Font.BOLD, 14));
		bName.setBounds(65, 35, 98, 32);
		formPanel.add(bName);
		
		JLabel price = new JLabel("Price");
		price.setFont(new Font("Tahoma", Font.BOLD, 14));
		price.setBounds(65, 121, 98, 32);
		formPanel.add(price);
		
		txtBookName = new JTextField();
		txtBookName.setBounds(165, 43, 214, 20);
		formPanel.add(txtBookName);
		txtBookName.setColumns(10);
		
		txtEdition = new JTextField();
		txtEdition.setColumns(10);
		txtEdition.setBounds(165, 86, 214, 20);
		formPanel.add(txtEdition);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(165, 129, 214, 20);
		formPanel.add(txtPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price;
				
				bname = txtBookName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				
				try {
					
					pst = con.prepareStatement("insert into bookshop(bName,edition,price)values(?,?,?)");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Addeddd!!!!!!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnSave.setBounds(36, 261, 111, 47);
		frame.getContentPane().add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtBookName.setText("");
				txtEdition.setText("");
				txtPrice.setText("");
				txtBookName.requestFocus();
				
			}
		});
		btnClear.setBounds(316, 261, 111, 47);
		frame.getContentPane().add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnExit.setBounds(182, 261, 111, 47);
		frame.getContentPane().add(btnExit);
		
		JScrollPane jtable = new JScrollPane();
		jtable.setBounds(451, 57, 389, 251);
		frame.getContentPane().add(jtable);
		
		table = new JTable();
		jtable.setViewportView(table);
		JPanel Panel = new JPanel();
		Panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Panel.setBounds(26, 324, 415, 65);
		frame.getContentPane().add(Panel);
		Panel.setLayout(null);
		
		JLabel bID = new JLabel("Book ID");
		bID.setBounds(60, 21, 74, 26);
		bID.setFont(new Font("Tahoma", Font.BOLD, 14));
		Panel.add(bID);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					String id = txtbid.getText();
					
					pst = con.prepareStatement("select bName,edition,price from bookshop where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if(rs.next()==true){
						String bName = rs.getString(1);
						String edition = rs.getString(2);
						String price = rs.getString(3);
						
						txtBookName.setText(bName);
						txtEdition.setText(edition);
						txtPrice.setText(price);
						
					}
					else {
						txtBookName.setText("");
						txtEdition.setText("");
						txtPrice.setText("");
					}
					

					} catch (Exception e2) {
					
				}
				
			}
		});
		txtbid.setBounds(144, 26, 261, 20);
		txtbid.setColumns(10);
		Panel.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,edition,price,bID;
				
				bname = txtBookName.getText();
				edition = txtEdition.getText();
				price = txtPrice.getText();
				bID = txtbid.getText();
				
				try {
					
					pst = con.prepareStatement("update bookshop set bName= ?,edition= ?,price= ? where id =?");
					pst.setString(1, bname);
					pst.setString(2, edition);
					pst.setString(3, price);
					pst.setString(4, bID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!!!!!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnUpdate.setBounds(517, 328, 111, 47);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bID;
				
				bID = txtbid.getText();
				
				try {
					
					pst = con.prepareStatement("delete from bookshop where id =?");
					pst.setString(1, bID);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!!!!!!");
					table_load();
					txtBookName.setText("");
					txtEdition.setText("");
					txtPrice.setText("");
					txtBookName.requestFocus();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
				
			
		});
		btnDelete.setBounds(673, 328, 111, 47);
		frame.getContentPane().add(btnDelete);
	}
}
