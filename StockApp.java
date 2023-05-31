import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import com.mysql.jdbc.Driver;

public class StockApp extends JFrame implements ActionListener {
    private JTextField id;
    private JTextField productName;
    private JTextField amount;
    private JTextField price;
    private JButton submit;
    private JButton submit2;
    private JButton submit3;
    private JButton submit4;
    private static Connection conn;
    
    //constructor
    public StockApp() {
        // create the text fields and buttons
        id = new JTextField("id");
        productName = new JTextField("product name");
        amount = new JTextField("amount");
        price = new JTextField("price");
        submit = new JButton("Add Records");
        submit2 = new JButton("Show Records");
        submit3 = new JButton("Show Profit");
        submit4 = new JButton("List Prices");

        //set layout
        setLayout(new GridLayout(4,2));
        
        //add the text fields and button to the frame
        add(id);
        add(productName);
        add(amount);
        add(price);
        add(submit);
        add(submit2);
        add(submit3);
        add(submit4);
        
        //add action listeners
        submit.addActionListener(this);
        submit2.addActionListener(this);
        submit3.addActionListener(this);
        submit4.addActionListener(this);
        
        //set size and title
        setSize(500, 500);
        setTitle("e-Commerce");
        setVisible(true);
    }
    
    //show profit method
    public void showProfit() {
    	 try {
    		 //crate statement
 			Statement stmt = conn.createStatement();
 			//multiply amount and price
 			ResultSet rs = stmt.executeQuery("SELECT (amount * price) AS profit FROM product");
            
 			System.out.println("Profits:");
 			//show the results
 			while (rs.next()) {
                 System.out.println(rs.getString(1));
             }
 			//close statement
             stmt.close();
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    //show records method
    public void listPrices() {
    	try {
    		//Create a statement
    		Statement stmt = conn.createStatement();
    		//execute mysql code
    		ResultSet rs = stmt.executeQuery("SELECT price FROM product ORDER BY price ASC");
    		System.out.println("Prices:");
    		//show result
    		while (rs.next()) {
    			System.out.println(rs.getDouble(1));
    		}
    		//Close the statement
    		stmt.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	        }
    }
    
    //show records method
    public void showRecords() {
    	 try {
    		//create statement
			Statement stmt = conn.createStatement();
			//select all from the market
			ResultSet rs = stmt.executeQuery("SELECT * FROM product");
			//show the contents of table
			while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
			// Close the statement
            stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    //connect to database method
    public static Connection connect() {
        try {
            //Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/e-commerce", "root", "root1234");

            System.out.println("Connected to MySQL database!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    //add record method
    public void addRecord() {
        try {    	
            //Create a statement
            Statement stmt = conn.createStatement();

            //Get the values from the text fields
            String idg = id.getText();
            String product_name = productName.getText();
            String amountg = amount.getText();
            String priceg = price.getText();

            //Insert the values into the table
            stmt.executeUpdate("INSERT INTO product (id, product_name, amount, price) VALUES ('" + idg + "', '" + product_name + "', '" + amountg + "', '" + priceg + "')");

            //Close the statement
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	//call the connect method
        connect();
        //call the constructor
    	new StockApp();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//When buttons are clicked call the proper function
		if(e.getSource()==submit) {
			addRecord();
		}
		if(e.getSource()==submit2) {
			showRecords();
		}
		if(e.getSource()==submit3) {
			showProfit();
		}
		if(e.getSource()==submit4) {
			listPrices();
		}
		
	}
}

