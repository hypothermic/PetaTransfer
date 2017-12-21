package nl.hypothermic.petatransfer;

/** PetaTransfer by Hypothermic
 * Version 1.0, work in progress.
 * Support/bugreport: admin@hypothermic.nl
 * https://github.com/hypothermic
 * https://www.hypothermic.nl **/

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
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;
import javax.swing.JSplitPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

public class pttInterface {

	public static String pttInfoVersion = "1.0";
	public static String htmlAbout = "<center><b>PetaTransfer v" + pttInterface.pttInfoVersion + "</b><br>Made by Hypothermic<br>Contains code from Mohamadali Shaikh<br><br>GitHub:<i> https://github.com/hypothermic<br></i>Contact:<i> admin@hypothermic.nl</i></center>";
	
	private JFrame frmPetatransfer;
	static JTextArea outField = new JTextArea();
	public static JProgressBar progressBar = new JProgressBar();
	static int conf[] = new int[10];
	static String[] langEN = new String[] {"Local File Name:","Local Port:","Enable Server","Save as:","Remote Address and Port","Receive File","About","Exit","\n[ERR] Server not reachable.","todo","",""};
	static String[] langNL = new String[] {"Lokale Bestandsnaam:","Lokale Poort:","Server Inschakelen","Opslaan als:","Extern Adres en Poort:","Ontvang Bestand","Info","Afsluiten","\n[ERR] Server niet bereikbaar.","todo","",""};
	static String[] langENTT = new String[] {"todo","","","","","","",""};
	static String[] langNLTT = new String[] {"todo","","","","","","",""};
	static String[] lang = new String[langEN.length];
	static String[] langTT = new String[langENTT.length];
	static int portNumber; // testing only, not in release.
	public static JTextField localFileNameField;
	public static JTextField localPortField;
	public static JTextField saveAsField;
	public static JTextField remoteAddrField;
	public static JTextField remotePortField;
	static int fcPassed = 1;
	public static JButton receiveButton;
	public static JButton enableServerButton;
	public static int srvRunning = 0;
	public static int clRunning = 0;
	
	public static void main(String[] args) {
		initProps();
		if (conf[2] == 1) {
			conf[2] = 0;
			initProps();
		}
		System.out.println("Lang debug: " + conf[6]);
		if (conf[6] == 0) {
			lang = langEN;
			langTT = langNLTT;
		} else if (conf[6] == 1) {
			lang = langNL;
			langTT = langNLTT;
		} else {outField.append("Error in conf[6]");}
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
		frmPetatransfer.setIconImage(Toolkit.getDefaultToolkit().getImage(pttInterface.class.getResource("/nl/hypothermic/petatransfer/util/petatransfer_256x256.png")));
		frmPetatransfer.setAlwaysOnTop(true);
		frmPetatransfer.setResizable(false);
		frmPetatransfer.setTitle("PetaTransfer");
		frmPetatransfer.setBounds(100, 100, 750, 420);
		frmPetatransfer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmPetatransfer.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnEnableServerMode = new JButton(lang[6]);
		btnEnableServerMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pttAbout.show();
			}
		});
		btnEnableServerMode.setToolTipText("About");
		panel.add(btnEnableServerMode);
		
		panel.add(progressBar);
		
		JButton btnExit = new JButton(lang[7]);
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
		
		outField.setText("-------------------------------------= PetaTransfer =-------------------------------------");
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
		panel_3.setBackground(new Color(192, 192, 192));
		splitPane.setLeftComponent(panel_3);
		
		JTextPane localFileNameInfopanel = new JTextPane();
		localFileNameInfopanel.setToolTipText(langTT[0]);
		localFileNameInfopanel.setText(lang[0]);
		localFileNameInfopanel.setEditable(false);
		panel_3.add(localFileNameInfopanel);
		
		localFileNameField = new JTextField();
		localFileNameField.setToolTipText("Enter File Name here");
		localFileNameField.setColumns(10);
		panel_3.add(localFileNameField);
		
		JTextPane localPortInfoPanel = new JTextPane();
		localPortInfoPanel.setToolTipText(langTT[1]);
		localPortInfoPanel.setText(lang[1]);
		localPortInfoPanel.setEditable(false);
		panel_3.add(localPortInfoPanel);
		
		localPortField = new JTextField();
		localPortField.setHorizontalAlignment(SwingConstants.CENTER);
		localPortField.setText("9070");
		localPortField.setColumns(10);
		panel_3.add(localPortField);
		
		JButton enableServerButton = new JButton(lang[2]);
		enableServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (srvRunning == 1) {
					outField.append("[ERR] Server already running.");
					return;
				} else { ///{X1
				srvRunning = 1;
				outField.append("\ndebug");
				localPortField.setEnabled(false);
				localFileNameField.setEnabled(false);
				if (localFileNameField.getText() != "" && new File(localFileNameField.getText()).isFile() && localPortField.getText() != "") {
					try {
						//int portNumber = Integer.parseInt(pttInterface.localPortField.getText());
						new Thread(() -> {
							try {
								try {
									if (conf[7] == 1) {
										outField.append("\nCalculating file size");
										if (new File(localFileNameField.getText()).length() > Integer.MAX_VALUE) {
											outField.append("\n[ERR] File bigger than maximum int size, too big to parse.");
											fcPassed = 0;
											srvRunning = 0;
											return;
										}
									}
								} catch (Exception xe) {
									outField.append("\n[ERR] Exception occurred while trying to get file size: " + xe);
									xe.printStackTrace();
								}
								if (conf[7] == 1) {
									outField.append("\nSuccess creating thread, now executing pttSender");
								}
								if (fcPassed == 1) {
								pttSender.send((Integer.parseInt(pttInterface.localPortField.getText())), localFileNameField.getText());
								} else {
									srvRunning = 0;
									return;
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								outField.append("\n[ERR] IOException in thread pttSender: " + e1);
								srvRunning = 0;
								localPortField.setEnabled(true);
								localFileNameField.setEnabled(true);
							}
						}).start();
						//pttSender.send(portNumber, localFileNameField.getText());
					} catch (Exception x) {
						x.printStackTrace();
						outField.append("\n[ERR] Exception in creating thread for pttSender: " + x);
						srvRunning = 0;
						localPortField.setEnabled(true);
						localFileNameField.setEnabled(true);
					}
				} else {
					outField.append("\n[ERR] Make sure that the local file exists, and port is unused.");
					outField.append("\n[INFO] Local file path examples: /home/user/myfile.txt or C:\\myfile.txt");
					srvRunning = 0;
					localPortField.setEnabled(true);
					localFileNameField.setEnabled(true);
				}
				}///}X1
			}
		});
		panel_3.add(enableServerButton);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		splitPane.setRightComponent(panel_4);
		
		JTextPane saveAsInfopanel = new JTextPane();
		saveAsInfopanel.setToolTipText(langTT[3]);
		saveAsInfopanel.setText(lang[3]);
		saveAsInfopanel.setEditable(false);
		panel_4.add(saveAsInfopanel);
		
		saveAsField = new JTextField();
		saveAsField.setToolTipText(langTT[3]);
		saveAsField.setColumns(10);
		panel_4.add(saveAsField);
		
		JTextPane remoteAddrInfopanel = new JTextPane();
		remoteAddrInfopanel.setToolTipText(langTT[4]);
		remoteAddrInfopanel.setText(lang[4]);
		remoteAddrInfopanel.setEditable(false);
		panel_4.add(remoteAddrInfopanel);
		
		remoteAddrField = new JTextField();
		remoteAddrField.setColumns(10);
		panel_4.add(remoteAddrField);
		
		JButton receiveButton = new JButton(lang[5]);
		receiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clRunning == 1) {
					return;
				}
				clRunning = 1;
				if (saveAsField.getText() != "" && remoteAddrField.getText() != "" && remotePortField.getText() != "") {
					try {
						new Thread(() -> {
							try {
								pttClient.receiveFile(remoteAddrField.getText(), Integer.parseInt(remotePortField.getText()), saveAsField.getText());
							} catch (UnknownHostException xh) {
								outField.append(lang[8]);
								System.out.println("UnknownHostException");
								clRunning = 0;
							} catch (ConnectException xc) {
								outField.append(lang[8]);
								System.out.println("ConnectException");
								clRunning = 0;
							} catch (IOException x) {
								// TODO Auto-generated catch block
								x.printStackTrace();
								outField.append("\n[ERR] IOException in pttClient: " + x);
								clRunning = 0;
							} catch (Exception otherX) {
								otherX.printStackTrace();
								System.out.println(otherX);
								outField.append("\n[ERR] Unknown exception in pttClient: " + otherX);
								clRunning = 0;
							}
						}).start();
					} catch (Exception x) {
						x.printStackTrace();
						outField.append("\n[ERR] Exception in creating pttClient thread: "+ x);
						clRunning = 0;
					}
				} else {
					outField.append("\n[ERR] Fill in the required options.");
					clRunning = 0;
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
    				try {int propsLang = Integer.parseInt(props.getProperty("lang"));
    				if (propsLang == 0) {
    					System.out.println("Langset:en");
    					conf[6] = 0;
    				} else if (propsLang == 1) {
    					System.out.println("Langset:nl");
    					conf[6] = 1;
    				} else {
    					// lang ! en of nl
    					System.out.println("[ERR] Language not set correctly. Defaulting to English.");
    					conf[6] = 0;
    				} } catch (NumberFormatException x) {System.out.println(x);conf[6]=0;}
    			} catch (Exception x5) {
    				x5.printStackTrace();
    				outField.append("\n[ERR] Exception in property lang: " + x5);
    			}
    		} catch (Exception x3) {
    			x3.printStackTrace();
    		}
    	} else {
    		// Set first-run
    		System.out.println("firstrun");
    		conf[2] = 1;
    		// Generate props file
    		FileWriter propwrite = null;
    		Properties props = new Properties();
    		props.setProperty("lang", "0");
    		props.setProperty("debug", "0");
    		try {
    			propwrite = new FileWriter("petatransfer.properties");
    			props.store(propwrite, "PetaTransfer by Hypothermic\nLanguage option: 0 for English/EN, 1 for Dutch/NL. Not everything is translated yet.\nhttps://github.com/hypothermic");
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