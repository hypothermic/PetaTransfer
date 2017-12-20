package nl.hypothermic.petatransfer.backup20122017740PM_v1_0;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class pttAbout {

	private JFrame frmAboutPetatransfer;
	
	static String htmlAbout = "<center><b>PetaTransfer</b></center>";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pttAbout window = new pttAbout();
					window.frmAboutPetatransfer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*public pttAbout() {
		initialize();
	}*/

	public pttAbout() {
		frmAboutPetatransfer = new JFrame();
		frmAboutPetatransfer.setResizable(false);
		frmAboutPetatransfer.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\MAIN\\eclipse-workspace\\PetaTransfer\\petatransfer.png"));
		frmAboutPetatransfer.setAlwaysOnTop(true);
		frmAboutPetatransfer.setFont(new Font("Dialog", Font.PLAIN, 16));
		frmAboutPetatransfer.setTitle("About PetaTransfer");
		frmAboutPetatransfer.setBounds(100, 100, 450, 440);
		frmAboutPetatransfer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextPane txtpnAboutPetatransfer = new JTextPane();
		txtpnAboutPetatransfer.setContentType("text/html");
		txtpnAboutPetatransfer.setEditable(false);
		txtpnAboutPetatransfer.setText("<center><b>PetaTransfer v" + pttInterface.pttInfoVersion + "</b><br>Made by Hypothermic<br>Contains code from Mohamadali Shaikh<br><br>GitHub:<i> https://github.com/hypothermic<br></i>Contact:<i> admin@hypothermic.nl</i></center>");
		frmAboutPetatransfer.getContentPane().add(txtpnAboutPetatransfer, BorderLayout.CENTER);
		
		JLabel lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setIcon(new ImageIcon("C:\\Users\\MAIN\\eclipse-workspace\\PetaTransfer\\petatransfer.png"));
		frmAboutPetatransfer.getContentPane().add(lblImg, BorderLayout.NORTH);
	}

}
