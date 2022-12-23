package com;
import java.util.Scanner;
import java.sql.*;
import com.User;

public class MainMenu  {
	
	Scanner myObj = new Scanner(System.in);
	User user = new User();
	Connection con;
	
	public void start() 
	{
    System.out.println("South Africa Bank Cellphone Banking");
	System.out.println("1)Login \n2)Register");
	int choice = myObj.nextInt();
	
	   if(choice == 1)
	  {
		Login();
	  }
	   else if(choice == 2)
	   {
		 Register();
	   }
	}
	
	public void Login()
	{
		 System.out.print("Please enter username: ");
		 user.setUsername(myObj.next());
		 System.out.print("Please enter password: ");
		 user.setPassword(myObj.next());

		
		try {
		 connectToDatabase();
		 PreparedStatement preparedStatement = con.prepareStatement("select * from User where username = ? and password = ? ");
	     preparedStatement.setString(1, user.getUsername());
		 preparedStatement.setString(2, user.getPassword());
			  

		 ResultSet rs = preparedStatement.executeQuery();
		 rs.next();
		 
		 if(rs.getRow() == 0)
		 {
			 throw new Exception("Incorrect Username and password");
		 } 
		 else 
		 {
			 System.out.println("Login Successful...");
		 }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		int i = 1;
		while(i <= 10)
		{
		  System.out.println("South Africa Bank Cellphone Banking");
	      System.out.println("1)Deposit \n2)Withdraw \n3)Transfer \n4)Check Balance \n5)Exit");
	      int choice = myObj.nextInt();
		
	      if(choice == 1)
		  {
	    	  System.out.println("Please enter amount");
	    	  int amount = myObj.nextInt();

			  user.deposit(amount, user.getUsername());
		  }
		   else if(choice == 2)
		   {
			   System.out.println("Please enter amount");
		    	  int amount = myObj.nextInt();
				  user.withdraw(amount,user.getUsername());
		   }
		   else if(choice == 3)
		   {
			   System.out.println("Please enter username of user you wish to transfer:");
			   String name = myObj.next();
			   System.out.println("Please enter users cellphone number you wish to transfer:");
			   String number = myObj.next();
			   checkAccount(name,number);
			   System.out.println("Please enter amount");
		       double amount = myObj.nextDouble();
			   user.transfer(amount,user.getUsername(),name);
		   }
		   else if(choice == 4)
		   {
			  System.out.println("Balance Remaining = " + user.getBalance(user.getUsername()));
		   }
		   else
		   {
			  exit();
		   }
		}
			  	 
	}
	
	public void Register()
	{
		 System.out.print("Please enter username: ");
		 user.setUsername(myObj.next());
		 System.out.print("Please enter password: ");
		 user.setPassword(myObj.next());
		 System.out.print("Please enter Fullname: ");
		 user.setName(myObj.next());
		 System.out.print("Please enter Cellphone Number: ");
		 user.setCellNumber(myObj.next());
		
		try {
			 connectToDatabase();
			 PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO User(username, password, fullname, cellnumber) VALUES (?,?,?,?)");
		     preparedStatement.setString(1, user.getUsername());
			 preparedStatement.setString(2, user.getPassword());
			 preparedStatement.setString(3, user.getName());
			 preparedStatement.setString(4, user.getCellNumber());
				  
		     preparedStatement.execute();
		     System.out.println("successful");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
			 
			
	}
	
	public void connectToDatabase()
	{
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/USSD","root","password");  
	       }
		catch(Exception e)
		{
			System.out.println("Database not connected");
		}
	}
	public void exit()
	{
		System.out.println("Bye...");
		System.exit(0);
	}
	
	
	public boolean checkAccount(String name, String number)
	{
		boolean passed = false;
	    try {
	    	Statement stm = con.createStatement();
			PreparedStatement preparedStatement; 
			preparedStatement = con.prepareStatement("SELECT * FROM User WHERE username = ? AND cellnumber = ?");
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getCellNumber());
			ResultSet rs = preparedStatement.executeQuery();
			passed = rs.next();
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	    return passed;
	}
	
	

}
