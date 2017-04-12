/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import java.io.File;
import java.io.FileInputStream;
/**
 *
 * @author Tomi
 */
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class DBConnection {
	private Connection conn;
	private static final String taulukko = "3Dpeli.Account";

	public DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://mysql-raivoairlines.crenqljciaae.eu-central-1.rds.amazonaws.com:3306/3Dpeli","ohjelmauser","Salasana159");
		} catch (Exception e) {
			//System.err.println("Virhe tietokantayhteyden muodostamisessa.");
			//System.exit(0);
			e.printStackTrace();
		}
	}

	public boolean createAccount() {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO "+taulukko+" VALUES(?,?,?)");
			stmt.setDouble(1, 1234);
			stmt.setString(2, "testinimi");
			stmt.setString(3, "testisalasana");
			int count = stmt.executeUpdate();
			if(count > 0){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//updates the picture in database with the latest version every time user closes the whiteboard.
	public void updateWhiteboard(){
		FileInputStream fis = null;
	    PreparedStatement stmt = null;
	    try {
	      conn.setAutoCommit(false);
	      File file = new File("/res/whiteboardImg.png");
	      fis = new FileInputStream(file);
	      stmt = conn.prepareStatement("UPDATE 3Dpeli.Whiteboard SET PICTURE = ?"+ "WHERE NAME = ?");
	      stmt.setBinaryStream(1, fis, (int) file.length());
	      stmt.setString(2, "Whiteboard");
	      stmt.executeUpdate();
	      conn.commit();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
				if (stmt != null)
					stmt.close();
				if(fis != null){
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}

	
	public void insertWhiteboard(){

	    FileInputStream fis = null;
	    PreparedStatement stmt = null;
	    try {
	      conn.setAutoCommit(false);
	      File file = new File("whiteboardImg.png");
	      fis = new FileInputStream(file);
	      stmt = conn.prepareStatement("INSERT INTO 3Dpeli.Whiteboard VALUES(?,?)");
	      stmt.setString(1, "Whiteboard");
	      stmt.setBinaryStream(2, fis, (int) file.length());
	      stmt.executeUpdate();
	      conn.commit();
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
				if (stmt != null)
					stmt.close();
				if(fis != null){
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}

	protected void finalize() {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

