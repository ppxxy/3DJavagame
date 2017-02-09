package login;

public class Userdata {

	private String username;
	private char[] password;
	private String email;
	private String recoveryQuestion1;
	private String recoveryAnswer1;

	public Userdata(String username, String email, char[] password, String recoveryQuestion1, String recoveryAnswer1){
		this.username = username;
		this.password = password;
		this.email = email;
		this.recoveryQuestion1 = recoveryQuestion1;
		this.recoveryAnswer1 = recoveryAnswer1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecoveryQuestion1() {
		return recoveryQuestion1;
	}

	public void setRecoveryQuestion1(String recoveryQuestion1) {
		this.recoveryQuestion1 = recoveryQuestion1;
	}

	public String getRecoveryAnswer1() {
		return recoveryAnswer1;
	}

	public void setRecoveryAnswer1(String recoveryAnswer1) {
		this.recoveryAnswer1 = recoveryAnswer1;
	}

}
