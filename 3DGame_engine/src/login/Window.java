package login;


import Networking.Clientside;
import Networking.Connect;

//javax.mail saatavissa osoitteesta https://java.net/projects/javamail/pages/Home#Download_JavaMail_Release

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class Window extends JPanel implements ActionListener{

    private static final long serialVersionUID = 7116463709442422088L;


    Connect connect;

    //missa ikkunassa kayttaja on
    private int ikkuna = 0;

    //alustetaan muuttujia
    private int securityCode = 0;
    private boolean usernameDoesntContainSpecialChars = true;
    private boolean usernameDoesntContainForbiddenWords = true;
    private String text;

    //esitell��n luokat
    Email emailClass = new Email();
    Borders borders = new Borders();

    //luodaan frame ja panel
    JFrame frame = new JFrame();
    JPanel panel = this;

    //Luodaan buttonit
    JButton newUserButton = new JButton();
    JButton exUserButton = new JButton();
    JButton createUser = new JButton("Create User");
    JButton login = new JButton();
    JButton backButton = new JButton();
    JButton securityButton = new JButton();
    JButton resetPasswordButton = new JButton();
    JButton emailVerificationButton = new JButton("Send security code to your email.");
    JButton changePasswordButton = new JButton("Change password");

    //luodaaan tekstit
    JLabel username = new JLabel("Username:");
    JLabel password = new JLabel("Password:");
    JLabel retypepass = new JLabel("Retype password:");
    JLabel email = new JLabel("Email address:");
    JLabel warning = new JLabel("Warning:");
    JLabel recoveryQuestion1 = new JLabel("Recovery Question 1:");
    JLabel recoveryQuestionAns = new JLabel("Question 1 answer:");
    JLabel securityCodeText = new JLabel("Security Code");
    JLabel securityPassInfo = new JLabel("Please check your email for security code. If you didn't get the code remember to check your trashbin or try again.");
    JLabel passwordSuccessText = new JLabel("Password changed succesfully!");
    JLabel passwordFailureText = new JLabel("Password change failed.");

    //luodaan tekstikentat
    JTextField usernameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField securityCodeField = new JTextField();

    //luodaan salasanakentat
    JPasswordField passwordField = new JPasswordField();
    JPasswordField retypepassField = new JPasswordField();

    //luodaan TextAreat
    JTextArea recoveryQuestionArea1 = new JTextArea();
    JTextArea recoveryQuestionAnswer1 = new JTextArea();

    //Luodaan listat eri komponenteista
    ArrayList<String> forbidden = new ArrayList<String>();
    ArrayList<JButton> buttons = new ArrayList<JButton>();
    ArrayList<JTextField> textFields = new ArrayList<JTextField>();
    ArrayList<JPasswordField> passFields = new ArrayList<JPasswordField>();
    ArrayList<JTextArea> textAreas = new ArrayList<JTextArea>();

    //Tyhja kontruktori
    public Window(){}

    //konstruktori jossa asetetaan ikkunan asetukset
    public Window(String title){

    	buttons.addAll(Arrays.asList(backButton, newUserButton, exUserButton, createUser, login, securityButton, resetPasswordButton, emailVerificationButton, changePasswordButton));
    	textFields.addAll(Arrays.asList(usernameField, emailField, securityCodeField));
    	passFields.addAll(Arrays.asList(passwordField, retypepassField));
    	textAreas.addAll(Arrays.asList(recoveryQuestionArea1, recoveryQuestionAnswer1));

        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMaximumSize(new Dimension(1280,720));
        frame.setMinimumSize(new Dimension(1280,720));
        frame.setSize(new Dimension(1280,720));
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        panel.setLayout(null);

        panel.add(newUserButton);
        newUserButton.setBounds(panel.getWidth()/2-280, panel.getHeight()/2-40, 300, 80);
        newUserButton.addActionListener(this);
        newUserButton.setBorder(BorderFactory.createEmptyBorder());
        newUserButton.setContentAreaFilled(false);
        panel.add(exUserButton);
        exUserButton.setBounds(panel.getWidth()/2+30, panel.getHeight()/2-40, 300, 80);
        exUserButton.addActionListener(this);
        exUserButton.setContentAreaFilled(false);
        exUserButton.setBorder(BorderFactory.createEmptyBorder());
        panel.add(securityButton);
        securityButton.setBounds(panel.getWidth()-315, panel.getHeight()-95, 300, 80);
        securityButton.setContentAreaFilled(false);
        securityButton.setBorder(BorderFactory.createEmptyBorder());

        securityButton.addActionListener(this);
        backButton.addActionListener(this);
        backButton.setContentAreaFilled(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        resetPasswordButton.addActionListener(this);
        login.setContentAreaFilled(false);
        login.setBorder(BorderFactory.createEmptyBorder());
        emailVerificationButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        resetPasswordButton.setContentAreaFilled(false);
        resetPasswordButton.setBorder(BorderFactory.createEmptyBorder());

        borders.addButtonBorders(buttons);
        borders.addTextFieldBorders(textFields);
        borders.addPassFieldBorders(passFields);
        borders.addTextAreaBorders(textAreas);
       

        addForbidden();
        limitChars();
        resize();

    }

    //repaint metodi joka on overridattu ja johon lisatty resize metodi
    @Override
    public void repaint(){
        resize();
        super.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		BufferedImage image;

		if(ikkuna == 0){
			try {
				image = ImageIO.read(getClass().getResource("/res/loginscreenphoto.png"));
				g.drawImage(image, 0, 0, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(ikkuna == 2){
			try {
				image = ImageIO.read(getClass().getResource("/res/loginscreen2photo.png"));
				g.drawImage(image, 0, 0, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(ikkuna == 4){
			try {
				image = ImageIO.read(getClass().getResource("/res/settingsphoto.png"));
				g.drawImage(image, 0, 0, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

    //buttoneiden actionlistenerit. Maariteaan mita tapahtuu kun nappia painetaan.
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == newUserButton){
            ikkuna = 1;
            panel.removeAll();
            panel.repaint();
            panel.add(username);
            username.setBounds(panel.getWidth()/20, panel.getHeight()/14, 200, 30);
            panel.add(email);
            email.setBounds(panel.getWidth()/20, username.getY()+username.getHeight()+10, 200, 30);
            panel.add(password);
            password.setBounds(panel.getWidth()/20, email.getY()+email.getHeight()+10, 200, 30);
            panel.add(retypepass);
            retypepass.setBounds(panel.getWidth()/20, password.getY()+password.getHeight()+10, 200, 30);
            panel.add(usernameField);
            usernameField.setBounds(username.getX()+username.getWidth()-55, username.getY(),200, 30);
            panel.add(emailField);
            emailField.setBounds(email.getX()+email.getWidth()-55, email.getY(), 200, 30);
            panel.add(passwordField);
            passwordField.setBounds(password.getX()+password.getWidth()-55, password.getY(), 200, 30);
            panel.add(retypepassField);
            retypepassField.setBounds(retypepass.getX()+retypepass.getWidth()-55, retypepass.getY(), 200, 30);
            panel.add(backButton);
            backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
            backButton.addActionListener(this);
            panel.add(recoveryQuestionArea1);
            recoveryQuestionArea1.setBounds(retypepassField.getX(), recoveryQuestion1.getY()+5, 200, 40);
            panel.add(recoveryQuestionAnswer1);
            recoveryQuestionAnswer1.setBounds(retypepassField.getX(), recoveryQuestionAns.getY()+5, 200, 40);
            panel.add(recoveryQuestion1);
            recoveryQuestion1.setBounds(panel.getWidth()/20, retypepass.getY()+retypepass.getHeight()+5, 200, 40);
            panel.add(recoveryQuestionAns);
            recoveryQuestionAns.setBounds(panel.getWidth()/20, recoveryQuestion1.getY()+recoveryQuestion1.getHeight()+10, 200, 40);
            panel.add(createUser);
            createUser.setBounds(retypepassField.getX()-35, recoveryQuestionAns.getY()+recoveryQuestionAns.getHeight()+15, 110, 30);
            createUser.addActionListener(this);
        }

        else if(e.getSource() == exUserButton){
            ikkuna = 2;
            panel.removeAll();
            panel.repaint();
            panel.add(username);
            username.setBounds(panel.getWidth()/2-150, panel.getHeight()/2-70, 80, 30);
            panel.add(usernameField);
            usernameField.setBounds(username.getX()+username.getWidth()+10, username.getY()-5, 200, 40);
            panel.add(password);
            password.setBounds(panel.getWidth()/2-150, panel.getHeight()/2-20, 80, 30);
            panel.add(passwordField);
            passwordField.setBounds(password.getX()+password.getWidth()+10, password.getY()-5, 200, 40);
            panel.add(login);
            login.setBounds(passwordField.getX(), passwordField.getY()+passwordField.getHeight()+52, 180, 50);
            login.addActionListener(this);
            panel.add(backButton);
            backButton.addActionListener(this);
            backButton.setBounds(panel.getWidth()-1265, panel.getHeight()-70, 200, 55);
        }

        else if(e.getSource() == backButton){
            ikkuna = 0;
            panel.removeAll();
            panel.repaint();
            panel.add(newUserButton);
            newUserButton.setBounds(panel.getWidth()/2-280, panel.getHeight()/2-40, 300, 80);
            newUserButton.addActionListener(this);
            panel.add(exUserButton);
            exUserButton.setBounds(panel.getWidth()/2+30, panel.getHeight()/2-40, 300, 80);
            panel.add(securityButton);
            securityButton.setBounds(panel.getWidth()-315, panel.getHeight()-95, 300, 80);
        }

        else if(e.getSource() == createUser){
        	changeChars();
        	checkUsername();
        	emailClass.isValidEmailAddress(emailField.getText());
            if(Arrays.equals(passwordField.getPassword(), retypepassField.getPassword()) && Array.getLength(passwordField.getPassword()) >= 6 && Array.getLength(passwordField.getPassword()) < 25
                    && usernameField.getText().length() > 3 && emailField.getText().contains("@") && emailField.getText().contains(".") && usernameDoesntContainSpecialChars && emailClass.emailOk
                    && usernameDoesntContainForbiddenWords){
                warning.setText("");
                System.out.println(usernameDoesntContainForbiddenWords+"-"+usernameDoesntContainSpecialChars);
                System.out.println("luotiin user");
                //generoidaan salt
                Password password = new Password();
                String salt = password.generateSalt();
                String hashedPassword = "";
                //lis�t��n k�ytt�j�n antamaan salasanaan salt
                String hashThis = (salt+(new String(passwordField.getPassword())));
                try {
					hashedPassword = password.hashPassword(hashThis);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
                //tallennetaan salt ja hashattu password tietokantaan
                connect = new Connect();
                connect.addUser(usernameField.getText(), hashedPassword, salt, emailField.getText(), recoveryQuestionArea1.getText(), recoveryQuestionAnswer1.getText());
                panel.add(warning);
                warning.setForeground(Color.red);
                warning.setBounds(400, 300, 400, 50);
                warning.setText(Connect.errorMessage);
                //kaynnista peli-->
                emailClass.sendEmail("Hi! You just created an account to Java 3D Game. Your username is: "+usernameField.getText()+" and your password is: "+new String(passwordField.getPassword()), emailField.getText());
            }else if(!Arrays.equals(passwordField.getPassword(), retypepassField.getPassword())){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Passwords do not match!");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
                passwordField.setText("");
                retypepassField.setText("");
                passwordField.requestFocus();
            }else if(usernameField.getText().length() <= 3){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Username too short!");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
            }else if(!emailField.getText().contains("@") || !emailField.getText().contains(".")){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Please enter valid email address");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
            }else if(Array.getLength(passwordField.getPassword()) < 6){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Password too short!");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
                passwordField.setText("");
                retypepassField.setText("");
                passwordField.requestFocus();
            }else if(Array.getLength(passwordField.getPassword()) >= 25){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Password too long!");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
                passwordField.setText("");
                retypepassField.setText("");
                passwordField.requestFocus();
            }

        }

        else if(e.getSource() == login){
            String username = usernameField.getText();
            if(usernameField.getText().length() == 0){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Please enter username.");
                warning.setBounds(login.getX()-20, login.getY()+login.getHeight()+10, 400, 30);
            }else if(Array.getLength(passwordField.getPassword()) == 0){
                panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Please enter password.");
                warning.setBounds(login.getX()-20, login.getY()+login.getHeight()+10, 400, 30);
            }else{
                //tarkista onko kyseista pelaajaa olemassa ja onko salasana oikein
            		connect = new Connect();
            		username = usernameField.getText();
                    //String databaseSalt = connect.getDatabaseSalt("SELECT PasswordSalt FROM Account WHERE Nimi='"+username+"';");
                    String databaseSalt = connect.getDatabaseSalt(username);
                    System.out.println("Salt on:"+databaseSalt);
                    //String databaseHash = connect.getDatabaseHash("SELECT PasswordHash FROM Account WHERE Nimi='"+username+"';");
                    String databaseHash = connect.getDatabaseHash(username);
                    System.out.println("Hash on:"+databaseHash);
                    String typedPassword = (new String (passwordField.getPassword()));
                    String hashThis = databaseSalt+typedPassword;
                    Password password = new Password();
                    String hashed;
					try {
						hashed = password.hashPassword(hashThis);
						if(hashed.equals(databaseHash)){
	                    	//salasana oikein --> käynnistä client
							System.out.println("Salasana oikein");
	                    	Clientside client = new Clientside(usernameField.getText());
	                    }else{
	                    	System.out.println("Wrong username or password.");
	                    }
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
            }

        }

        else if(e.getSource() == securityButton){
        	ikkuna = 4;
        	panel.removeAll();
            panel.repaint();
            panel.add(backButton);
            backButton.setBounds(panel.getWidth()-1265, panel.getHeight()-70, 200, 55);
            panel.add(resetPasswordButton);
            resetPasswordButton.setBounds(panel.getWidth()-1245, panel.getHeight()-665, 250, 70);
        }

        else if(e.getSource() == resetPasswordButton){
        	ikkuna = 6;
        	panel.removeAll();
        	panel.repaint();
        	panel.add(backButton);
        	 backButton.setBounds(panel.getWidth()-1265, panel.getHeight()-70, 200, 55);
        	panel.add(emailVerificationButton);
        	emailVerificationButton.setBounds(panel.getWidth()/2-125, panel.getHeight()/2-25, 250, 50);
        	panel.add(email);
        	email.setBounds(emailVerificationButton.getX()-25, emailVerificationButton.getY()-50, 100, 40);
        	panel.add(emailField);
        	emailField.setBounds(email.getX()+110, emailVerificationButton.getY()-52, 190, 40);
        }

        else if(e.getSource() == emailVerificationButton){
        	emailClass.emailaddr = emailField.getText();
        	int x = 999999;
        	int y = 100001;
        	securityCode = (int)(Math.random()*x)+y;
        	emailClass.sendEmail("Here is your security code: "+securityCode+" Put this number to the \"Security Code\" box and give your new password.", emailField.getText());
        	ikkuna = 5;
        	panel.removeAll();
        	panel.repaint();
        	panel.add(securityCodeField);
        	securityCodeField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2-80, 200, 40);
        	panel.add(securityCodeText);
        	securityCodeText.setBounds(panel.getWidth()/2-150, securityCodeField.getY(), 100, 40);
        	panel.add(passwordField);
        	passwordField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2-30, 200, 40);
        	panel.add(retypepassField);
        	retypepassField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2+20, 200, 40);
        	panel.add(backButton);
        	backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
        	panel.add(password);
        	password.setBounds(panel.getWidth()/2-150, passwordField.getY(), 100, 40);
        	panel.add(retypepass);
        	retypepass.setBounds(panel.getWidth()/2-150, retypepassField.getY(), 120, 40);
        	panel.add(securityPassInfo);
        	securityPassInfo.setBounds(securityCodeText.getX()-150, securityCodeText.getY()-50, 700, 40);
        	panel.add(changePasswordButton);
        	changePasswordButton.setBounds(retypepassField.getX()-20, retypepassField.getY()+50, 150, 40);
        }

        else if(e.getSource() == changePasswordButton){
        	panel.remove(passwordFailureText);
        	panel.remove(passwordSuccessText);
        	panel.repaint();

        	if(securityCode == Integer.parseInt(securityCodeField.getText()) && passwordField.getPassword().length >= 6 && Arrays.equals(passwordField.getPassword(), retypepassField.getPassword())){
        		panel.remove(password);
            	panel.remove(retypepass);
            	panel.remove(securityCodeText);
            	panel.remove(changePasswordButton);
            	panel.remove(passwordField);
            	panel.remove(securityCodeField);
            	panel.remove(retypepassField);
            	panel.remove(securityPassInfo);
            	panel.repaint();
        		panel.add(passwordSuccessText);
        		passwordSuccessText.setForeground(Color.green.darker());
        		passwordSuccessText.setBounds(panel.getWidth()/2-80, panel.getHeight()/2-25, 500, 50);
        	}

        	else{
        		panel.add(passwordFailureText);
        		passwordFailureText.setForeground(Color.red);
        		passwordFailureText.setBounds(changePasswordButton.getX(), changePasswordButton.getY()+50, 500, 50);
        	}
        }
    }

    //resize metodi joka asettelee komponentit uudelleen panelille kun ruudun kokoa muutetaan
    public void resize(){

        if(ikkuna == 3){
            newUserButton.setBounds(panel.getWidth()/2-newUserButton.getWidth()-10, panel.getHeight()/2, 200, 50);
            exUserButton.setBounds(panel.getWidth()/2+10, panel.getHeight()/2, 200, 50);
            securityButton.setBounds(panel.getWidth()-160, panel.getHeight()-60, 150, 50);
        }

        else if(ikkuna == 1){
            username.setBounds(panel.getWidth()/20, panel.getHeight()/14, 200, 30);
            email.setBounds(panel.getWidth()/20, username.getY()+username.getHeight()+10, 200, 30);
            password.setBounds(panel.getWidth()/20, email.getY()+email.getHeight()+10, 200, 30);
            retypepass.setBounds(panel.getWidth()/20, password.getY()+password.getHeight()+10, 200, 30);
            usernameField.setBounds(username.getX()+username.getWidth()-55, username.getY(),200, 30);
            emailField.setBounds(email.getX()+email.getWidth()-55, email.getY(), 200, 30);
            passwordField.setBounds(password.getX()+password.getWidth()-55, password.getY(), 200, 30);
            retypepassField.setBounds(retypepass.getX()+retypepass.getWidth()-55, retypepass.getY(), 200, 30);
            createUser.setBounds(retypepassField.getX()-35, recoveryQuestionAns.getY()+recoveryQuestionAns.getHeight()+15, 110, 30);
            backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
            recoveryQuestion1.setBounds(panel.getWidth()/20, retypepass.getY()+retypepass.getHeight()+5, 200, 40);
            recoveryQuestionArea1.setBounds(retypepassField.getX(), recoveryQuestion1.getY()+5, 200, 40);
            recoveryQuestionAns.setBounds(panel.getWidth()/20, recoveryQuestion1.getY()+recoveryQuestion1.getHeight()+10, 200, 40);
            recoveryQuestionAnswer1.setBounds(retypepassField.getX(), recoveryQuestionAns.getY()+5, 200, 40);

        }

        else if(ikkuna == 2){
        	username.setBounds(panel.getWidth()/2-150, panel.getHeight()/2-70, 80, 30);
        	usernameField.setBounds(username.getX()+username.getWidth()+10, username.getY()-5, 200, 40);
        	password.setBounds(panel.getWidth()/2-150, panel.getHeight()/2-20, 80, 30);
        	passwordField.setBounds(password.getX()+password.getWidth()+10, password.getY()-5, 200, 40);
        	login.setBounds(passwordField.getX(), passwordField.getY()+passwordField.getHeight()+10, 100, 40);
        	backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
        }

        else if(ikkuna == 5){
        	securityCodeField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2-80, 200, 40);
        	securityCodeText.setBounds(panel.getWidth()/2-150, securityCodeField.getY(), 100, 40);
        	passwordField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2-30, 200, 40);
        	retypepassField.setBounds(panel.getWidth()/2-20, panel.getHeight()/2+20, 200, 40);
        	backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
        	password.setBounds(panel.getWidth()/2-150, passwordField.getY(), 100, 40);
        	retypepass.setBounds(panel.getWidth()/2-150, retypepassField.getY(), 120, 40);
        	securityPassInfo.setBounds(securityCodeText.getX()-150, securityCodeText.getY()-50, 700, 40);
        	changePasswordButton.setBounds(retypepassField.getX()-20, retypepassField.getY()+50, 150, 40);
        	passwordSuccessText.setBounds(changePasswordButton.getX()-15, changePasswordButton.getY()+50, 500, 50);
        	passwordFailureText.setBounds(changePasswordButton.getX(), changePasswordButton.getY()+50, 500, 50);
        }

        else if(ikkuna == 4){
        	backButton.setBounds(panel.getWidth()/60, panel.getHeight()-60, 100, 40);
        	resetPasswordButton.setBounds(panel.getWidth()/20, panel.getHeight()/13, 200, 50);
        }/*else if(ikkuna == 0){
        	newUserButton.setBounds(panel.getWidth()/2-newUserButton.getWidth()-10, panel.getHeight()/2, 200, 50);
            exUserButton.setBounds(panel.getWidth()/2+10, panel.getHeight()/2, 200, 50);
        }*/
    }

    public void limitChars(){
    	final int MAX_LENGTH = 50;

    	recoveryQuestionArea1.setDocument(new PlainDocument() {

			private static final long serialVersionUID = 1105596992099482664L;

			@Override
    	    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    	        if (str == null || recoveryQuestionArea1.getText().length() >= MAX_LENGTH) {
    	            return;
    	        }
    	        super.insertString(offs, str, a);
    	    }
    	});

    	recoveryQuestionAnswer1.setDocument(new PlainDocument() {

			private static final long serialVersionUID = -5565970091212868473L;

			@Override
    	    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    	        if (str == null || recoveryQuestionAnswer1.getText().length() >= MAX_LENGTH) {
    	            return;
    	        }
    	        super.insertString(offs, str, a);
    	    }
    	});

    }

    public void checkUsername(){
    	for(int i = 0; i < forbidden.size(); i++){
    		if(usernameField.getText().toLowerCase().contains(forbidden.get(i)) || text.toLowerCase().contains(forbidden.get(i))){
    			System.out.println("L�ytyi:"+forbidden.get(i));
    			panel.add(warning);
                warning.setForeground(Color.RED);
                warning.setText("Unacceptable username.");
                warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
                usernameDoesntContainForbiddenWords = false;
                break;

    		}else{
    			usernameDoesntContainForbiddenWords = true;
    		}
    		System.out.println("Forbiddenwords="+usernameDoesntContainForbiddenWords);
    	}

    	String texte = usernameField.getText();
    	boolean match = texte.matches("[a-zA-Z0-9]+");

    	if(match == false){
    		usernameDoesntContainSpecialChars = false;
    		panel.add(warning);
            warning.setForeground(Color.RED);
            warning.setText("You can only use: [A-z], [0-9]");
            warning.setBounds(createUser.getX()-20, createUser.getY()+createUser.getHeight()+10, 400, 30);
    	}else{
    		usernameDoesntContainSpecialChars = true;
   		}
    	System.out.println("SpecialChars="+usernameDoesntContainSpecialChars);

    }

    public void changeChars(){

    	text = usernameField.getText();

    	while(text.contains("1") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("0")){
    		if(text.contains("1")){
        		text = text.replace("1", "i");
        	}else if(text.contains("3")){
        		text = text.replace("3", "e");
        	}else if(text.contains("4")){
        		text = text.replace("4", "a");
        	}else if(text.contains("5")){
        		text = text.replace("5", "s");
        	}else if(text.contains("0")){
        		text = text.replace("0", "o");
        	}
    	}
    	checkUsername();
    }

    public void addForbidden(){
    	forbidden.add("penis");
    	forbidden.add("asshole");
    	forbidden.add("fuck");
    	forbidden.add("shit");
    	forbidden.add("nigger");
    	forbidden.add("nigga");
    	forbidden.add("admin");
    	forbidden.add("moderator");
    	forbidden.add("mod");
    	forbidden.add("vagina");
    	forbidden.add("gay");
    	forbidden.add("homosexual");
    	forbidden.add("dick");
    	forbidden.add("niger");
    	forbidden.add("bastard");
    	forbidden.add("bitch");
    	forbidden.add("crap");
    	forbidden.add("cunt");
    	forbidden.add("whore");
    }
}