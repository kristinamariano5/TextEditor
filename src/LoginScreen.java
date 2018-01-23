import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JPanel implements ActionListener {
	JLabel username = new JLabel("Username: ");
	JTextField usernameText = new JTextField();
	JLabel password = new JLabel("Password: ");
	JPasswordField passwordText = new JPasswordField();
	JPanel login = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();
	JButton loginButton = new JButton("Login");
	JButton newButton = new JButton("New User");
	CardLayout cl;
	
	LoginScreen() {
		setLayout(new CardLayout());
		login.add(username);
		login.add(usernameText);
		login.add(password);
		login.add(passwordText);
		loginButton.addActionListener(this);
		newButton.addActionListener(this);
		login.add(loginButton);
		login.add(newButton);
		panel.add(login);
		add(panel, "login");
		cl = (CardLayout) getLayout();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String line = input.readLine();
				String user = usernameText.getText();
				String pass = new String(passwordText.getPassword());
				while (line != null) {
					String[] s = line.split("\\s");
					if(s[0].equals(user)) {
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(pass.getBytes());
						byte[] byteData = md.digest();
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < byteData.length; i++) {
							sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100, 16).substring(1));
						}
						pass = sb.toString();
						if (pass.equals(s[1])) {
							add(new FileBrowser(usernameText.getText()), "fb");
							cl.show(this, "fb");
						}
						else {
							System.out.println("Wrong username or password");
							return;
						}
					}
					line = input.readLine();
				}
				
				input.close();
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
		if (e.getSource() == newButton) {
			add(new newUser(), "newUser");
			cl.show(this, "newUser");
		}	
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		LoginScreen loginScreen = new LoginScreen();
		frame.add(loginScreen);
		frame.setVisible(true);
		
	}
}
