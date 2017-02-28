package Networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import login.Userdata;
import login.Window;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Connect {

	Window window;

	/*ResultSet myRs;
	Statement myStmt;
	PreparedStatement myStmt2;*/

	private static Connection myConn;
	public static String errorMessage;

	SessionFactory istuntotehdas= null;

	public Connect() {

		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

		try{
			istuntotehdas = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch(Exception e){
			System.out.println("Oh no");
			StandardServiceRegistryBuilder.destroy( registry );
			e.printStackTrace();
		}
        }

	/*public void executeUpdate(String query){
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
	}*/

	public String getDatabaseSalt(String query){

            Session istunto = istuntotehdas.openSession();

		try{
			istunto.beginTransaction();
			Userdata user = new Userdata();
			istunto.load(user, query);
			istunto.getTransaction().commit();
			istunto.close();
			return user.getSalt();

		}
		catch(Exception e){

			e.printStackTrace();
			return null;
		}

		/*String salt = "";

		try{
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
			if(myRs.next()){
				salt = myRs.getString("PasswordSalt");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return salt;*/
	}

	public String getDatabaseHash(String query){

            Session istunto = istuntotehdas.openSession();

                try{
			istunto.beginTransaction();
			Userdata user = new Userdata();
			istunto.load(user, query);
			istunto.getTransaction().commit();
			istunto.close();
			return user.getHash();

		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}

		/*String hash = "";

		try{
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(query);
			if(myRs.next()){
				hash = myRs.getString("PasswordHash");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return hash;*/
	}


	public void addUser(String username, String hash, String salt, String email, String recq, String reca){
                Userdata user = new Userdata(username, hash, salt, email, recq, reca);

                Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;

		try{
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(user);
			transaktio.commit();
		}
		catch(Exception e){
			if (transaktio!=null) transaktio.rollback();
			e.printStackTrace();
		}
		finally{
			istunto.close();
		}
		/*String sql = "INSERT INTO Account "
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
            }*/
		}
	}

