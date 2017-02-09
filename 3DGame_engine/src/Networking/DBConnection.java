/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

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

/*	public Valuutta readValuutta(String tunnus) {
		// TODO Auto-generated method stub
		return null;
	}

	public Valuutta[] readValuutat() {
		ArrayList<Valuutta> valuutat = new ArrayList<Valuutta>();
		PreparedStatement myStatement = null;
		ResultSet myRs = null;
		try {
			String sqlSelect = "SELECT * FROM tomiple.Valuutat ";
			myStatement = conn.prepareStatement(sqlSelect);
			myRs = myStatement.executeQuery();
			while (myRs.next()) {
				String tunnus = myRs.getString("tunnus");
				float vaihtokurssi = myRs.getFloat("vaihtokurssi");
				String nimi = myRs.getString("nimi");
				Valuutta valuutta = new Valuutta();
				valuutta.setTunnus(tunnus);
				valuutta.setVaihtokurssi(vaihtokurssi);
				valuutta.setNimi(nimi);
				valuutat.add(valuutta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (myRs != null)
					myRs.close();
				if (myStatement != null)
					myStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Valuutta[] returnArray = new Valuutta[valuutat.size()];
		return (Valuutta[])valuutat.toArray(returnArray);
	}

	public boolean updateValuutta(Valuutta valuutta) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("UPDATE "+taulukko+" SET VAIHTOKURSSI = ?"+ "WHERE TUNNUS = ?");
			stmt.setDouble(1, valuutta.getVaihtokurssi());
			stmt.setString(2, valuutta.getTunnus());
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

	public boolean deleteValuutta(String tunnus) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM "+taulukko+" WHERE TUNNUS = ?");
			stmt.setString(1, tunnus);
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

	*/
	protected void finalize() {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

