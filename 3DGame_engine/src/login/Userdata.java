package login;
import javax.persistence.*;

@Entity
@Table(name="3Dpeli")
public class Userdata {
        
        @Id
        @Column(name ="user")
	private String username;
        
        @Column(name ="email")
	private String email;
        
        @Column(name ="recq")
	private String recoveryQuestion1;
        
        @Column(name ="reca")
	private String recoveryAnswer1;
        
        @Column(name ="hashedpass")
        private String hash;
        
        @Column(name ="salt")
        private String salt;

	public Userdata(String username, String hash, String salt, String email, String recoveryQuestion1, String recoveryAnswer1){
		this.username = username;
		this.email = email;
		this.recoveryQuestion1 = recoveryQuestion1;
		this.recoveryAnswer1 = recoveryAnswer1;
                this.hash = hash;
                this.salt = salt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

        /**
         * @return the hash
         */
        public String getHash() {
            return hash;
        }

        /**
         * @param hash the hash to set
         */
        public void setHash(String hash) {
            this.hash = hash;
        }

        /**
         * @return the salt
         */
        public String getSalt() {
            return salt;
        }

        /**
         * @param salt the salt to set
         */
        public void setSalt(String salt) {
            this.salt = salt;
        }

}
