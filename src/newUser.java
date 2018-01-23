import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;

public class newUser extends JPanel implements ActionListener {
	JLabel username = new JLabel("Choose Username: ");
	JTextField usernameText = new JTextField();
	JLabel password = new JLabel("Password: ");
	JPasswordField passwordText = new JPasswordField();
	JLabel password2 = new JLabel("Conform Password: ");
	JPasswordField passwordText2 = new JPasswordField();
	JButton register = new JButton("Register");
	JButton back = new JButton("Back");
	
	public newUser() {
		JPanel login = new JPanel();
		login.setLayout(new GridLayout(4,2));
		login.add(username);
		login.add(usernameText);
		login.add(password);
		login.add(passwordText);
		login.add(password2);
		login.add(passwordText2);
		login.add(register);
		login.add(back);
		register.addActionListener(this);
		back.addActionListener(this);	
		add(login);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register && passwordText.getPassword().length > 0 && usernameText.getText().length() > 0) {
			String pass = new String(passwordText.getPassword());
			String pass2 = new String(passwordText2.getPassword());
			String user = new String(usernameText.getText());
			if (pass.equals(pass2)) {
				try {
					BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
					String line  = input.readLine();
					while (line != null) {
						String[] st = line.split("\\s");
						if (exists(user, st)) {
							System.out.println("User already exists");
							return;
						}
						line = input.readLine();
					}
					input.close();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(pass.getBytes());
					byte[] byteData = md.digest();
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
					}
					BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt", true));
					output.write(usernameText.getText() + " " + sb.toString() + "\n");
					output.close();
					LoginScreen login = (LoginScreen) getParent();
					login.cl.show(login, "login");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 	
		}
		if (e.getSource() == back) {
			LoginScreen login = (LoginScreen) getParent();
			login.cl.show(login, "login");
		}
	}
	
	public boolean exists(String s, String[] st) {
		for (int i = 0; i < st.length; i++) {
			String check = st[i];
			if (check.equals(s)) {
				return true;
			}
		}
		return false;
	}
}
