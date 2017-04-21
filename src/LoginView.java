import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.http.impl.client.HttpClients;

public class LoginView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfNum;
	private JLabel num;
	private JLabel password;
	private JPasswordField tfPassword;
	private JButton btnLogin;
	private JLabel title;
	boolean isSuccess=false;

	
	public LoginView() {
		setTitle("广工教务系统");
		setSize(315,234);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{80, 194, 59, 0};
		gbl_panel.rowHeights = new int[]{73, 0, 0, 30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		title = new JLabel("\u6559\u52A1\u7CFB\u7EDF");
		title.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		GridBagConstraints gbc_title = new GridBagConstraints();
		gbc_title.gridwidth = 3;
		gbc_title.insets = new Insets(0, 0, 5, 5);
		gbc_title.gridx = 0;
		gbc_title.gridy = 0;
		panel.add(title, gbc_title);
		
		num = new JLabel("\u5B66\u53F7: ");
		GridBagConstraints gbc_num = new GridBagConstraints();
		gbc_num.insets = new Insets(0, 0, 5, 5);
		gbc_num.anchor = GridBagConstraints.EAST;
		gbc_num.gridx = 0;
		gbc_num.gridy = 1;
		panel.add(num, gbc_num);
		
		tfNum = new JTextField();
		GridBagConstraints gbc_tfNum = new GridBagConstraints();
		gbc_tfNum.insets = new Insets(0, 0, 5, 5);
		gbc_tfNum.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNum.gridx = 1;
		gbc_tfNum.gridy = 1;
		panel.add(tfNum, gbc_tfNum);
		tfNum.setColumns(10);
		
		password = new JLabel("\u5BC6\u7801: ");
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.anchor = GridBagConstraints.EAST;
		gbc_password.gridx = 0;
		gbc_password.gridy = 2;
		panel.add(password, gbc_password);
		
		tfPassword = new JPasswordField();
		GridBagConstraints gbc_tfPassword = new GridBagConstraints();
		gbc_tfPassword.insets = new Insets(0, 0, 5, 5);
		gbc_tfPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPassword.gridx = 1;
		gbc_tfPassword.gridy = 2;
		panel.add(tfPassword, gbc_tfPassword);
		
		btnLogin = new JButton("\u767B\u5F55");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.fill = GridBagConstraints.BOTH;
		gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 3;
		panel.add(btnLogin, gbc_btnLogin);
	
		
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						String num=tfNum.getText();
						String pwd=String.valueOf(tfPassword.getPassword());
						if(Http.loginIn(num, pwd)){
							new MainPanel();
							dispose();
						}else{
							int i=JOptionPane.showConfirmDialog(null, "密码或账号错误!","重连", JOptionPane.YES_NO_OPTION);
							if(i==JOptionPane.YES_OPTION){
								Http.client=HttpClients.createDefault();
								btnLogin.doClick();
							}
						}
					}
		});
		
		
		getRootPane().setDefaultButton(btnLogin);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
}
