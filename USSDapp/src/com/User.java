package com;

import java.sql.*;

public class User {
	
	private int id;
	private String name;
	private String cellNumber;
	private String username;
	private String password;
    private double balance;

	Connection con;

	 public double getBalance(String username) {
		 connectToDatabase();	 	
		 double balanceph = 0;
		
		 PreparedStatement preparedStatement;
		try(Statement stm = con.createStatement()) {
			
			ResultSet res = stm.executeQuery("SELECT balance FROM User WHERE username = '"+username+"'");
			while(res.next()) {
				balanceph = res.getDouble(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return balanceph;
			
		}
	
	public void withdraw(double withdrawAmount, String username)
	{
		System.out.println(getBalance(username) - withdrawAmount);
	}
	
	public void transfer(double amount, String currentUser, String transferringUser)
	{
		connectToDatabase();
		try
		{
			Statement stm = con.createStatement();
			PreparedStatement preparedStatement; 
			PreparedStatement preparedStatement2;
			PreparedStatement preparedStatement3;
			preparedStatement = con.prepareStatement("UPDATE User SET balance = balance - ? WHERE username = ?");
			preparedStatement.setDouble(1, amount);
			preparedStatement.setString(2, currentUser);
			preparedStatement.executeUpdate();
			
			
			preparedStatement2 = con.prepareStatement("UPDATE User SET balance = balance + ? WHERE username = ?");
			preparedStatement2.setDouble(1, amount);
			preparedStatement2.setString(2, transferringUser);
			preparedStatement2.executeUpdate();
			
			System.out.println("Successful....");
			preparedStatement3 = con.prepareStatement("SELECT balance FROM USER WHERE username = ?");
		    preparedStatement3.setString(1, currentUser);
			ResultSet rs = preparedStatement3.executeQuery();
			rs.next();
			System.out.println("Remaining Balance: " + rs.getDouble(1));
	
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

	public double getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public User(String username, String password,int balance)
	{
		this.username = username;
		this.password = password;
	}
	

	
	public User()
	{
		
	}
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void deposit(double depositAmount, String Username)
	{
		System.out.println(getBalance(Username) + depositAmount);

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
	
	
	

	

	
	

}
