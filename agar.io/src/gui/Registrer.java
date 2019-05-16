package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import services.*;

public class Registrer extends JFrame implements ActionListener {

	public static final  String REGISTRER = "Registrer";
	
	private JLabel lbIconUser;
	private JLabel lbIconCorreo;
	private JLabel lbIconPasdword;
		
	private JLabel lbTitle;
	
	private JPlaceholderTextField txtUser;
	private JPlaceholderTextField txtEmail;
	private JPasswordField txtPass;
	
	private JButton butRegistrer; 
	private Main_Agario connection;
	
	public Registrer(Main_Agario connection) {
	
		this.connection = connection;
		
		setTitle("Agar.io");
		setSize(333, 333);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
		lbTitle = new JLabel("Registration", SwingConstants.CENTER);
		lbTitle.setFont(new java.awt.Font("Calibri", 1, 20));
		
		txtUser = new JPlaceholderTextField("User");
		txtUser.setFont(new java.awt.Font("Calibri", 1, 18));
		txtEmail = new JPlaceholderTextField("Email");
		txtEmail.setFont(new java.awt.Font("Calibri", 1, 18));
		txtPass = new JPasswordField();
		
		
		String path = "/icons/user.png";
		java.net.URL url = this.getClass().getResource(path);
		ImageIcon icon = new ImageIcon(url);
		lbIconUser = new JLabel("", SwingConstants.CENTER);
		lbIconUser.setIcon(icon);
		
		
		String path2 = "/icons/correo.png";
		java.net.URL url2 = this.getClass().getResource(path2);
		ImageIcon icon2 = new ImageIcon(url2);
		lbIconCorreo = new JLabel("", SwingConstants.CENTER);
		lbIconCorreo.setIcon(icon2);
		
		
		String path3 = "/icons/password.png";
		java.net.URL url3 = this.getClass().getResource(path3);
		ImageIcon icon3 = new ImageIcon(url3);
		lbIconPasdword = new JLabel("", SwingConstants.CENTER);
		lbIconPasdword.setIcon(icon3);
		
		butRegistrer= new JButton("Sing me up");
		butRegistrer.setFont(new java.awt.Font("Calibri", 1, 15));
		butRegistrer.setActionCommand(REGISTRER);
		butRegistrer.addActionListener(this);
		
		
		setLayout(new GridLayout(11,3));
		
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(lbTitle);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(lbIconUser);
		add(txtUser);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(lbIconCorreo);
		add(txtEmail);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(lbIconPasdword);
		add(txtPass);
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(new JLabel());
		add(butRegistrer);
		add(new JLabel());
		add(new JLabel());
		
		
	}

	
	public String getRegistrerUser() {
		return txtUser.getText();
	}
	
	public String getRegistrerEmail() {
		return txtEmail.getText();
	}
	
	public String getRegistrerPass() {
		return txtPass.getText();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comand = e.getActionCommand();
		
		if(comand.equals(REGISTRER)) {
			connection.getController().register(getRegistrerEmail(),getRegistrerPass(),getRegistrerUser());
			this.setVisible(false);
		}
	}
}
