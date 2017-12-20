package nl.hypothermic.petatransfer;

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
	
	public static void show() {
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
		frmAboutPetatransfer.setIconImage(Toolkit.getDefaultToolkit().getImage(pttAbout.class.getResource("/nl/hypothermic/petatransfer/util/petatransfer_256x256.png")));
		frmAboutPetatransfer.setAlwaysOnTop(true);
		frmAboutPetatransfer.setFont(new Font("Dialog", Font.PLAIN, 16));
		frmAboutPetatransfer.setTitle("About PetaTransfer");
		frmAboutPetatransfer.setBounds(100, 100, 450, 440);
		frmAboutPetatransfer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextPane txtpnAboutPetatransfer = new JTextPane();
		txtpnAboutPetatransfer.setContentType("text/html");
		txtpnAboutPetatransfer.setEditable(false);
		txtpnAboutPetatransfer.setText(pttInterface.htmlAbout);
		frmAboutPetatransfer.getContentPane().add(txtpnAboutPetatransfer, BorderLayout.CENTER);
		
		JLabel lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setIcon(new ImageIcon(pttAbout.class.getResource("/nl/hypothermic/petatransfer/util/petatransfer_256x256.png")));
		frmAboutPetatransfer.getContentPane().add(lblImg, BorderLayout.NORTH);
	}

}
