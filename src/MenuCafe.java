import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JCheckBox;

public class MenuCafe {

	private JFrame frame;
	private JTextField textName;
	private JTextField textVariant;
	private JTextField textPrice;
	private JTable table;
	private JTextField textPID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuCafe window = new MenuCafe();
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
	public MenuCafe() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;

	 public void Connect()
	    {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection("jdbc:mysql://localhost/db_menucafe", "root","");
	        }
	        catch (ClassNotFoundException ex) 
	        {
	          ex.printStackTrace();
	        }
	        catch (SQLException ex) 
	        {
	        	   ex.printStackTrace();
	        }

	    }
	 
	  public void table_load()
	    {
	    	try {
			    pst = con.prepareStatement("select * from menu");
			    rs = pst.executeQuery();
			    table.setModel(DbUtils.resultSetToTableModel(rs));
	    	} 
	    	catch (SQLException e) {
	    				e.printStackTrace();
	    	} 
	    }
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 786, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ngebuck Cafe");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(290, 10, 211, 84);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Input Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(48, 181, 370, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nama Produk");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(24, 37, 91, 26);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Varian");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(24, 88, 91, 26);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Harga");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(24, 135, 91, 26);
		panel.add(lblNewLabel_1_2);
		
		textName = new JTextField();
		textName.setBounds(125, 42, 216, 19);
		panel.add(textName);
		textName.setColumns(10);
		
		textVariant = new JTextField();
		textVariant.setColumns(10);
		textVariant.setBounds(125, 93, 216, 19);
		panel.add(textVariant);
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(125, 140, 216, 19);
		panel.add(textPrice);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pname,variant,price;
				pname = textName.getText();
				variant = textVariant.getText();
				price = textPrice.getText();
				try {
					pst = con.prepareStatement("insert into menu(name,variant,price)values(?,?,?)");
					pst.setString(1, pname);
					pst.setString(2, variant);
					pst.setString(3, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data Berhasil Ditambah!");
					table_load();
					          
					textName.setText("");
					textVariant.setText("");
					textPrice.setText("");
					textName.requestFocus();
				   }
				catch (SQLException e1)
				        {
				e1.printStackTrace();
				}
				
			}
		});
		btnSave.setBounds(48, 399, 85, 38);
		frame.getContentPane().add(btnSave);
		
		JButton btnSave_1 = new JButton("Exit");
		btnSave_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSave_1.setBounds(664, 399, 85, 38);
		frame.getContentPane().add(btnSave_1);
		
		JButton btnSave_2 = new JButton("Clear");
		btnSave_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPID.setText("");
				textName.setText("");
	            textVariant.setText("");
	            textPrice.setText("");
	            textName.requestFocus();
			}
		});
		btnSave_2.setBounds(333, 399, 85, 38);
		frame.getContentPane().add(btnSave_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(438, 104, 311, 273);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Cari Menu", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(48, 94, 370, 67);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Produk ID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(23, 20, 91, 26);
		panel_1.add(lblNewLabel_1_1_1);
		
		textPID = new JTextField();
		textPID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = textPID.getText();
		 
		                pst = con.prepareStatement("select name,variant,price from menu where id = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true)
		            {
		                String pname = rs.getString(1);
		                String variant = rs.getString(2);
		                String price = rs.getString(3);
		                
		                textName.setText(pname);
		                textVariant.setText(variant);
		                textPrice.setText(price);
		            }  
		            else
		            {
		             textName.setText("");
		             textVariant.setText("");
		             textPrice.setText("");
		            }
		        }
				catch (SQLException ex) {
		          
		        }
			}
		});
		textPID.setColumns(10);
		textPID.setBounds(124, 25, 217, 19);
		panel_1.add(textPID);
		
		
		JButton btnSave_1_1 = new JButton("Update");
		btnSave_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pname,variant,price,pid;
				pname = textName.getText();
				variant = textVariant.getText();
				price = textPrice.getText();
				pid  = textPID.getText();
				try {
				pst = con.prepareStatement("update menu set name= ?,variant=?,price=? where id =?");
				pst.setString(1, pname);
				            pst.setString(2, variant);
				            pst.setString(3, price);
				            pst.setString(4, pid);
				            pst.executeUpdate();
				            JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate!");
				            table_load();
				          
				            textName.setText("");
				            textVariant.setText("");
				            textPrice.setText("");
				            textName.requestFocus();
				}
				 
				            catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
		});
		btnSave_1_1.setBounds(143, 399, 85, 38);
		frame.getContentPane().add(btnSave_1_1);
		
		JButton btnSave_1_1_1 = new JButton("Delete");
		btnSave_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String pid;
                pid  = textPID.getText();
	
				try {
						pst = con.prepareStatement("delete from menu where id =?");
				
			            pst.setString(1, pid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
			            table_load();
			           
			            textName.setText("");
			            textVariant.setText("");
			            textPrice.setText("");
			            textName.requestFocus();
				}
				catch (SQLException e1) {
						e1.printStackTrace();
				}
			}
		});
		btnSave_1_1_1.setBounds(238, 399, 85, 38);
		frame.getContentPane().add(btnSave_1_1_1);
	}
}
