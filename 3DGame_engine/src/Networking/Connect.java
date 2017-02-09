package Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import login.Window;

public class Connect {

	Window window;

	ResultSet myRs;
	Statement myStmt;
	PreparedStatement myStmt2;

	public static Connection myConn;
	public static String errorMessage;


	public void connectDb(){
		try{
			myConn = DriverManager.getConnection("jdbc:mysql://mysql-raivoairlines.crenqljciaae.eu-central-1.rds.amazonaws.com:3306/3Dpeli","ohjelmauser","Salasana159");
			System.out.println("Created connection to the database.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void executeUpdate(String query){
		try {
			myStmt = myConn.createStatement();
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void executeQuery(String query){
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDatabaseSalt(String query){

		String salt = "";

		try{
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
			if(myRs.next()){
				salt = myRs.getString("PasswordSalt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return salt;
	}

	public String getDatabaseHash(String query){

		String hash = "";

		try{
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
			if(myRs.next()){
				hash = myRs.getString("PasswordHash");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return hash;
	}


	public void addUser(String username, String hash, String salt, String email, String recq, String reca){

		String sql = "INSERT INTO Account "
				+ " (Nimi, PasswordHash, PasswordSalt, Email, SecurityQ, SecurityA)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";

		try {
			myStmt2 = myConn.prepareStatement(sql);
			myStmt2.setString(1, username);
			myStmt2.setString(2, hash);
			myStmt2.setString(3, salt);
			myStmt2.setString(4, email);
			myStmt2.setString(5, recq);
			myStmt2.setString(6, reca);
			myStmt2.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
            if(e.toString().contains("Email_UNIQUE")){
            	errorMessage= "Account with this email already exists.";
            }else if(e.toString().contains("Nimi_UNIQUE")){
            	errorMessage="Account with this name already exists.";
            }
            }
		}
	}

