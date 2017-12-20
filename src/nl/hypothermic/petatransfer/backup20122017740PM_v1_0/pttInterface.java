package nl.hypothermic.petatransfer.backup20122017740PM_v1_0;

/* PetaTransfer by hypothermic
 * Version 1.0, work in progress.
 * Support/bugreport: admin@hypothermic.nl
 * https://github.com/hypothermic
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Panel;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import javax.swing.JSplitPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class pttInterface {

	// PTT info
	public static String pttInfoVersion = "1.0";
	
	private JFrame frmPetatransfer;
	static JTextArea outField = new JTextArea();
	JProgressBar progressBar = new JProgressBar();
	static int conf[] = new int[10];
	static String[] langEN = new String[] {"","",""};
	static String[] langNL = new String[] {"","",""};
	static int portNumber;
	private static JTextField localFileNameField;
	public static JTextField localPortField;
	private static JTextField saveAsField;
	private static JTextField remoteAddrField;
	private static JTextField remotePortField;
	
	public static void main(String[] args) {
		initProps();
		if (conf[2] == 1) {
			conf[2] = 0;
			initProps();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pttInterface window = new pttInterface();
					window.frmPetatransfer.setVisible(true);
					if (conf[7] == 1) {
						pttInterface.outField.append("\nInitialized main runnable");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public pttInterface() {
		initialize();
	}

	public void initialize() {
		frmPetatransfer = new JFrame();
		frmPetatransfer.setAlwaysOnTop(true);
		frmPetatransfer.setResizable(false);
		frmPetatransfer.setTitle("PetaTransfer");
		frmPetatransfer.setBounds(100, 100, 629, 420);
		frmPetatransfer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmPetatransfer.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnEnableServerMode = new JButton("About");
		btnEnableServerMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pttAbout.main(null);
			}
		});
		btnEnableServerMode.setToolTipText("About");
		panel.add(btnEnableServerMode);
		
		panel.add(progressBar);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setToolTipText("Exit PetaTransfer");
		panel.add(btnExit);
		
		JPanel panel_1 = new JPanel();
		frmPetatransfer.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		ScrollPane panel_2 = new ScrollPane();
		frmPetatransfer.getContentPane().add(panel_2, BorderLayout.CENTER);
		outField.setFont(new Font("Monospaced", Font.PLAIN, 13));
		outField.setToolTipText("Log");
		outField.setLineWrap(true);
		
		outField.setText("------------------------------= PetaTransfer =-----------------------------");
		outField.setEditable(false);
		outField.setVisible(true);
		panel_2.add(outField);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.DARK_GRAY);
		splitPane.setForeground(Color.DARK_GRAY);
		splitPane.setEnabled(false);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmPetatransfer.getContentPane().add(splitPane, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		splitPane.setLeftComponent(panel_3);
		
		JTextPane localFileNameInfopanel = new JTextPane();
		localFileNameInfopanel.setToolTipText("Name of the local file you want to upload");
		localFileNameInfopanel.setText("Local file name:");
		localFileNameInfopanel.setEditable(false);
		panel_3.add(localFileNameInfopanel);
		
		localFileNameField = new JTextField();
		localFileNameField.setToolTipText("Enter File Name here");
		localFileNameField.setColumns(10);
		panel_3.add(localFileNameField);
		
		JTextPane localPortInfoPanel = new JTextPane();
		localPortInfoPanel.setToolTipText("Local port that you want to open");
		localPortInfoPanel.setText("Local port:");
		localPortInfoPanel.setEditable(false);
		panel_3.add(localPortInfoPanel);
		
		localPortField = new JTextField();
		localPortField.setHorizontalAlignment(SwingConstants.CENTER);
		localPortField.setText("9070");
		localPortField.setColumns(10);
		panel_3.add(localPortField);
		
		JButton enableServerButton = new JButton("Enable Server");
		enableServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (localFileNameField.getText() != "" && new File(localFileNameField.getText()).isFile() && localPortField.getText() != "") {
					try {
						//int portNumber = Integer.getInteger(pttInterface.localPortField.getText());
						new Thread(() -> {
							try {
								pttSender.send((Integer.parseInt(pttInterface.localPortField.getText())), localFileNameField.getText());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								pttInterface.outField.append(pttInterface.localPortField.getText());
							}
						}).start();
						//pttSender.send(portNumber, localFileNameField.getText());
					} catch (Exception x) {
						x.printStackTrace();
						pttInterface.outField.append(pttInterface.localPortField.getText());
					}
				} else {
					pttInterface.outField.append("\n[ERR] Make sure that the local file exists, and port is unused.");
					pttInterface.outField.append("[INFO] Local file path examples: /home/user/myfile.txt or C:\\myfile.txt");
				}
			}
		});
		panel_3.add(enableServerButton);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		splitPane.setRightComponent(panel_4);
		
		JTextPane saveAsInfopanel = new JTextPane();
		saveAsInfopanel.setToolTipText("File name to save as");
		saveAsInfopanel.setText("Save as:");
		saveAsInfopanel.setEditable(false);
		panel_4.add(saveAsInfopanel);
		
		saveAsField = new JTextField();
		saveAsField.setToolTipText("Enter File Name here");
		saveAsField.setColumns(10);
		panel_4.add(saveAsField);
		
		JTextPane remoteAddrInfopanel = new JTextPane();
		remoteAddrInfopanel.setToolTipText("Address of the server");
		remoteAddrInfopanel.setText("Remote Address and Port:");
		remoteAddrInfopanel.setEditable(false);
		panel_4.add(remoteAddrInfopanel);
		
		remoteAddrField = new JTextField();
		remoteAddrField.setColumns(10);
		panel_4.add(remoteAddrField);
		
		JButton receiveButton = new JButton("Receive File");
		receiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saveAsField.getText() != "" && remoteAddrField.getText() != "" && remotePortField.getText() != "") {
					try {
						//int portNumber = Integer.getInteger(pttInterface.localPortField.getText());
						new Thread(() -> {
							try {
								pttClient.receiveFile(remoteAddrField.getText(), Integer.parseInt(pttInterface.remotePortField.getText()), saveAsField.getText());
							} catch (IOException x) {
								// TODO Auto-generated catch block
								x.printStackTrace();
								pttInterface.outField.append("[ERR] IOException in pttClient: " + x);
							}
						}).start();
						//pttSender.send(portNumber, localFileNameField.getText());
					} catch (Exception x) {
						x.printStackTrace();
						pttInterface.outField.append("[ERR] Exception in creating pttClient thread: "+ x);
					}
				} else {
					pttInterface.outField.append("\n[ERR] Make sure that the local file exists, and port is unused.");
					pttInterface.outField.append("[INFO] Local file path examples: /home/user/myfile.txt or C:\\myfile.txt");
				}
			}
		});
		
		remotePortField = new JTextField();
		remotePortField.setHorizontalAlignment(SwingConstants.CENTER);
		remotePortField.setText("9070");
		remotePortField.setToolTipText("Enter remote port here.");
		panel_4.add(remotePortField);
		remotePortField.setColumns(10);
		panel_4.add(receiveButton);
		if (conf[7] == 1) {
			outField.append("\nDebug mode is enabled");
			pttInterface.outField.append("\nInitialized interface");
		}
		
		// lang set elements
		if (conf[6] == 0) {
			if (conf[7] == 1) {
				pttInterface.outField.append("\nLang: en");
			}
		} else if (conf[6] == 1) {
			if (conf[7] == 1) {
				pttInterface.outField.append("\nLang: nl");
			}
		} else { pttInterface.outField.append("\nError lang not set"); }
	}
	
	
	// Props
    public static void initProps(){
    	File propsExist = new File("petatransfer.properties");
    	if (propsExist.exists() && !propsExist.isDirectory()) {
    		try (FileReader reader = new FileReader("petatransfer.properties")) {
    			Properties props = new Properties();
    			props.load(reader);
    			// Load debug
    			try {
    				String propsShowInfo = props.getProperty("debug");
    				try {
    					int y = Integer.parseInt(propsShowInfo);
    					if (y == 1) {
    						conf[7] = 1;
    					} else {
    						conf[7] = 0;
    					}
    				} catch (NumberFormatException x) {
    					x.printStackTrace();
    					outField.append("\n[ERR] Exception in parse int debug: " + x);
    				}
    			} catch (Exception x5) {
    				x5.printStackTrace();
    				outField.append("\n[ERR] Exception in property debug: " + x5);
    			}
    			// Load lang
    			try {
    				String propsLang = props.getProperty("lang");
    				if (propsLang == "en") {
    					conf[6] = 0;
    					if (conf[7] == 1) {
    						
    					}
    				} else if (propsLang == "nl") {
    					conf[6] = 1;
    					if (conf[7] == 1) {
    						
    					}
    				} else {
    					// lang ! en of nl
    					outField.append("[ERR] Language not set correctly. Defaulting to English.");
    					conf[6] = 0;
    				}
    			} catch (Exception x5) {
    				x5.printStackTrace();
    				outField.append("\n[ERR] Exception in property lang: " + x5);
    			}
    		} catch (Exception x3) {
    			x3.printStackTrace();
    		}
    	} else {
    		// Set first-run
    		conf[2] = 1;
    		// Generate props file
    		FileWriter propwrite = null;
    		Properties props = new Properties();
    		props.setProperty("lang", "en");
    		props.setProperty("debug", "0");
    		try {
    			propwrite = new FileWriter("petatransfer.properties");
    			props.store(propwrite, "PetaTransfer by Hypothermic\nhttps://github.com/hypothermic");
    			propwrite.close();
    		} catch (IOException x1) {
    			x1.printStackTrace();
    			outField.append("[ERR] I/O Exception: " + x1 +  " \n");
    		} finally {
    			if (propwrite != null) {
    				try {
    					propwrite.close();
    				} catch (Exception x2) {
    					x2.printStackTrace();
    				}
    			}
    		}
    	}
    }
}