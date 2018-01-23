import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class FileBrowser extends JPanel implements ActionListener {
	JLabel label = new JLabel("File List: ");
	JButton newFile = new JButton("New File");
	JButton open = new JButton("Open File");
	JTextField newFileTF = new JTextField(10);
	ButtonGroup bg;
	File directory;
	
	public FileBrowser(String dir) {
		directory = new File(dir);
		directory.mkdir();
		JPanel fileList = new JPanel(new GridLayout(directory.listFiles().length + 3, 1));
		fileList.add(label);
		bg = new ButtonGroup();
		for (File file: directory.listFiles()) {
			JRadioButton radio = new JRadioButton(file.getName());
			radio.setActionCommand(file.getName());
			bg.add(radio);
			fileList.add(radio);
		}
		JPanel newP = new JPanel();
		newP.add(newFileTF);
		newP.add(newFile);
		open.addActionListener(this);
		newFile.addActionListener(this);
		fileList.add(open);
		fileList.add(newP);
		add(fileList);
	}

	public void actionPerformed(ActionEvent e) {
		LoginScreen login = (LoginScreen) getParent();
		if (e.getSource() == open) {
			login.add(new Editor(directory.getName()+"\\"+bg.getSelection().getActionCommand()), "editor");
			login.cl.show(login, "editor");
		}
		if (e.getSource() == newFile) {
			String file = directory.getName()+"\\"+newFileTF.getText()+".txt";
			if (newFile.getText().length() > 0 && !(new File(file).exists())) {
				login.add(new Editor(file), "editor");
				login.cl.show(login, "editor");
			}
		}
	}
	
	
}
