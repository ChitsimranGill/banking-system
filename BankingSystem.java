import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.*;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	private static String customerid;
	private static String accountNumm;
	private static boolean done = false;
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static Statement stmt1;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	  public static void createTable()
	{
		try{
			System.out.println(":: CREATE NEW TABLE - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String createTable1 = String.format("CREATE table if not exists P1.Customer(ID INT NOT NULL Primary Key GENERATED BY DEFAULT AS IDENTITY\n" + 
										 "(START WITH 100, INCREMENT BY 1, NO CACHE),\n" + 
										 " Name varchar(15) not null,\n" + 
										 " Gender char not null CHECK (Gender in ('M','F')),\n" + 
										 " Age int not null CHECK (Age >= 0),\n" + 
									     " Pin int not null CHECK (Pin >= 0));"); 
			stmt.executeUpdate(createTable1);
			String createTable2 = String.format("CREATE table if not exists P1.Account(NUMBER INT NOT NULL Primary Key GENERATED ALWAYS AS IDENTITY\n" + 
										  "(START WITH 1000, INCREMENT BY 1, NO CACHE),\n" + 
										  " ID int not null,\n" + 
										  " Balance int not null CHECK (Balance >= 0),\n" + 
										  " Type char not null CHECK (Type in ('C', 'S')),\n" + 
										  " Status char not null CHECK (Status in ('A','I')),\n" + 
										  " constraint my_constraint foreign key(ID)\n" + 
										  " REFERENCES P1.Customer(ID) ON DELETE CASCADE);");
			stmt.executeUpdate(createTable2);
			stmt.close();
			con.commit();
			con.close();
			System.out.println(":: CREATE NEW TABLE - SUCCESS");
			
		}
		catch(Exception e){
			System.out.println(":: TABLE NOT CREATED - FAILED");

		}
	}

	public static void dropTable()
	{
		try{
			System.out.println(":: DROP TABLE - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String createTable1 = String.format("DROP TABLE if exists P1.Customer;"); 
			stmt.executeUpdate(createTable1);
			String createTable2 = String.format("DROP TABLE if exists P1.Account;");
			stmt.executeUpdate(createTable2);
			stmt.close();
			con.commit();
			con.close();
			System.out.println(":: DROP NEW TABLE - SUCCESS");
			
		}
		catch(Exception e){
			System.out.println(":: TABLE NOT DROPPED - FAILED");

		}
	}

	public static boolean login(String id, String pin)
	{
		Boolean k = false;
	try {
		//System.out.println(":: login - STARTING");
		Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    con = DriverManager.getConnection (url, username, password);                 
	  	stmt = con.createStatement();
		String login_customer = String.format("SELECT * FROM P1.Customer WHERE ID = %s and Pin = %s", id, pin); 
		rs = stmt.executeQuery(login_customer);
		k = rs.next();
		}
		catch(Exception e)
		{
			System.out.println(":: login - FAILED");
		}
		return k;
	}

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
		try {
			System.out.println(":: CREATE NEW CUSTOMER - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String new_customer = String.format("Insert into P1.CUSTOMER(Name, Gender, Age, Pin) values ('%s', '%s', %s, %s)", name, gender, age, pin);
			stmt.executeUpdate(new_customer);
			String get_id = String.format("SELECT * FROM P1.CUSTOMER WHERE Pin = %s and Age = %s", pin, age);
			rs = stmt.executeQuery(get_id);
			while (rs.next())
			{
				customerid = rs.getString(1);
			}
			stmt.close();
			con.commit();
			con.close();
			System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
			System.out.println("Customer id: "+ customerid);
			
		}
		
		catch(Exception e)
		{
			System.out.println(":: NEW CUSTOMER NOT CREATED - FAILED");
		}
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
		try {
			System.out.println(":: OPEN NEW ACCOUNT - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String open_account = String.format("Insert into P1.ACCOUNT(id, balance, type, status) values (%s, %s, '%s','%s')", id, amount, type, 'A'); 
			stmt.executeUpdate(open_account);
			String get_num = String.format("SELECT * FROM P1.ACCOUNT WHERE ID = %s",id);
			rs = stmt.executeQuery(get_num);
			while (rs.next())
			{
				accountNumm = rs.getString(1);
			}
			stmt.close();
			con.commit();
			con.close();
			System.out.println(":: OPEN NEW ACCOUNT - SUCCESS");
			System.out.println("Account number: "+accountNumm);
		
		}
		
		catch(Exception e)
		{
			System.out.println(":: OPEN ACCOUNT NOT CREATED - FAILED");
	
		}
	
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
		
		try {
			System.out.println(":: CLOSE ACCOUNT - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String close_account = String.format("UPDATE P1.ACCOUNT set balance = 0, status = 'I' where status = 'A' and P1.ACCOUNT.Number = %s",accNum); 
			stmt.executeUpdate(close_account);
			stmt.close();
			con.commit();
			con.close();
			
			System.out.println(":: CLOSE ACCOUNT - SUCCESS");
	
		}
		catch(Exception e)
		{
			System.out.println(":: CLOSE ACCOUNT - FAILED");
		}
	
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
		try {
			System.out.println(":: DEPOSIT ACCOUNT - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String deposit_account = String.format("UPDATE P1.ACCOUNT set balance = balance + %s where status = 'A' and P1.ACCOUNT.Number = %s",amount,accNum); 
			stmt.executeUpdate(deposit_account);
			stmt.close();
			con.commit();
			con.close();
			
			System.out.println(":: DEPOSIT ACCOUNT - SUCCESS");
	
		}
		catch(Exception e)
		{
			System.out.println(":: DEPOSIT ACCOUNT - FAILED");
		}
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
		try {
			System.out.println(":: WITHDRAW ACCOUNT - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String withdraw_account = String.format("UPDATE P1.ACCOUNT set balance = balance - %s where status = 'A' and P1.ACCOUNT.Number = %s",amount,accNum); 
			stmt.executeUpdate(withdraw_account);
			stmt.close();
			con.commit();
			con.close();
			
			System.out.println(":: WITHDRAW ACCOUNT - SUCCESS");
	
		}
		catch(Exception e)
		{
			System.out.println(":: WITHDRAW ACCOUNT - FAILED");
		}
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
		try {
			System.out.println(":: TRANSFER ACCOUNT - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
		    String transfer_account = String.format("UPDATE P1.ACCOUNT set balance = balance - %s where status = 'A' and P1.ACCOUNT.Number = %s",amount,srcAccNum);
			stmt.executeUpdate(transfer_account);
			String transfer_account1 = String.format("UPDATE P1.ACCOUNT set balance = balance + %s where status = 'A' and P1.ACCOUNT.Number = %s",amount,destAccNum); 
			stmt.executeUpdate(transfer_account1);
			stmt.close();
			con.commit();
			con.close();
			
			System.out.println(":: TRANSFER ACCOUNT - SUCCESS");
	
		}
		catch(Exception e)
		{
			System.out.println(":: TRANSFER ACCOUNT - FAILED");
		}
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) 
	{
		try {
			System.out.println(":: ACCOUNT SUMMARY - STARTING");
			Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    	con = DriverManager.getConnection (url, username, password);                 
	  		stmt = con.createStatement(); 
			String summary_account = String.format("SELECT Number, Balance From P1.ACCOUNT WHERE status = 'A' and P1.ACCOUNT.ID = %s ORDER BY Balance DESC",cusID); 
			rs = stmt.executeQuery(summary_account);
			int total = 0;
			System.out.println("Account summary");
			System.out.println("Number      Balance            ");
			System.out.println("----------- ------------ ");
			while (rs.next())
			{
				System.out.printf("%11.10s  %11.10s\n", rs.getString(1),rs.getString(2));
				total = total + rs.getInt(2);

			}
			System.out.println("-------------------------- ");
			System.out.println("Total                "+ total);
			stmt.close();
			con.commit();
			con.close();
			
			System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
	
		}
		catch(Exception e)
		{
			System.out.println(":: ACCOUNT SUMMARY - FAILED");
		}
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
		try {
	      	System.out.println("\n:: REPORT A - STARTING\n");
	        Class.forName("com.ibm.db2.jcc.DB2Driver");                                                                  
	        con = DriverManager.getConnection(url, username, password);                  
	        stmt = con.createStatement();     
	        
	        String view = "CREATE OR REPLACE VIEW REPORTA As SELECT ID, Name, Gender, Age, balance FROM P1.customer ,(SELECT ID as accountID, SUM(balance) as balance FROM P1.ACCOUNT WHERE status = 'A' GROUP BY id) WHERE accountID = P1.customer.id ";
			stmt.executeUpdate(view);
			
	        String query = String.format("SELECT * FROM REPORTA order by balance Desc");
	        System.out.println("ID          NAME         GENDER   AGE          TOTAL");
			System.out.println("----------- ------------ ------- ------------- -----------");
	        rs = stmt.executeQuery(query);                                                
	        while(rs.next()) 
	        {
	        	int id = rs.getInt(1);
	        	String name = rs.getString(2);
	        	String gender = rs.getString(3);
	        	int age = rs.getInt(4);	       
	        	int balance = rs.getInt(5);
	        	
	        	System.out.printf("%10.10s  %-10.10s   %-10s   %8s  %9s\n", id, name, gender, age , balance); 
	        }                                                                             
	        stmt.close();                                                                           
	        con.close();
			System.out.println("\n:: REPORT A - SUCCESS\n\n");
			
		} 
	 catch (Exception e) {
	        	System.out.println("\n:: REPORT A - FAILED");

	      }
	}
	

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
		try {
	      	System.out.println("\n:: REPORT B - STARTING\n");
	        Class.forName("com.ibm.db2.jcc.DB2Driver");                                                                  
	        con = DriverManager.getConnection(url, username, password);                  
	        stmt = con.createStatement();     
	        String view = "CREATE OR REPLACE VIEW REPORTB As SELECT DISTINCT ID, Name, Gender, Age, balance FROM P1.customer ,(SELECT ID as accountID, SUM(balance) as balance FROM P1.ACCOUNT WHERE status = 'A' GROUP BY id) WHERE accountID = P1.customer.id ";
			stmt.executeUpdate(view);
	        String query = String.format("SELECT * FROM REPORTB");
	        System.out.println("Average");
			System.out.println("-----------");
	        rs = stmt.executeQuery(query);  
	        int count = 0;
	        double balance = 0;                                              
	        while(rs.next()) 
	        { 
	        	int tempage = rs.getInt(4);
	        	if (tempage>=Integer.parseInt(min) && tempage<= Integer.parseInt(max))
	        	{   
	        		count++;
	        		balance = balance + rs.getInt(5);
	        	}
	        	
	        	//System.out.printf("%10.10s  %-10.10s   %-10s   %8s  %9s\n", id, name, gender, age , balance); 
	        } 
	        double avg = balance/count;
	        System.out.printf("%11s\n",avg);  
	        stmt.close();                                                                           
	        con.close();
			System.out.println("\n:: REPORT B - SUCCESS\n\n");		
		} 
	 catch (Exception e) 
	 {
	       System.out.println("\n:: REPORTB - FAILED");
	     
	  }


	}

	public static Boolean checkID(String i)
	{
		Boolean k = false;
	try {
		Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    con = DriverManager.getConnection (url, username, password);                 
	  	stmt = con.createStatement();
		String c = String.format("SELECT ID FROM P1.Customer WHERE P1.CUSTOMER.ID = %s", i);
		rs = stmt.executeQuery(c);
		k = rs.next();
		}
		catch(Exception e)
		{
			System.out.println(":: checkID- FAILED");
		}
		return k;

	}

	public static Boolean checkNumber(String i)
	{
		Boolean k = false;
	try {
		Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    con = DriverManager.getConnection (url, username, password);                 
	  	stmt = con.createStatement();
		String c = String.format("SELECT Number FROM P1.Account WHERE P1.ACCOUNT.Number = %s", i);
		rs = stmt.executeQuery(c);
		k = rs.next();
		}
		catch(Exception e)
		{
			System.out.println(":: checkNumber - FAILED");
		}
		return k;
	}

	public static Boolean authen(int i, String j)
	{
		Boolean k = false;
	try {
		Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    con = DriverManager.getConnection (url, username, password);                 
	  	stmt = con.createStatement();
		String c = String.format("SELECT Number FROM P1.Account WHERE P1.ACCOUNT.Number = %s and P1.ACCOUNT.ID = %s",i,j);
		rs = stmt.executeQuery(c);
		k = rs.next();
		}
		catch(Exception e)
		{
			System.out.println(":: authen - FAILED");
		}
		return k;
	}

	public static Boolean balanceCheck(int i, String j)
	{
		Boolean k = false;
	try {
		Class.forName("com.ibm.db2.jcc.DB2Driver");                             
	    con = DriverManager.getConnection (url, username, password);                 
	  	stmt = con.createStatement();
		String c = String.format("SELECT Balance FROM P1.Account WHERE P1.ACCOUNT.Balance >= %s and P1.ACCOUNT.Number = %s",i,j);
		rs = stmt.executeQuery(c);
		k = rs.next();
		}
		catch(Exception e)
		{
			System.out.println(":: balanceCheck - FAILED");
		}
		return k;	
	}
}