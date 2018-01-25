package nl.hypothermic.petatransfer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class pttSender {

	public static void send(int portNo,String fileLocation) throws IOException {
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;

		OutputStream outputStream = null;
		ServerSocket serverSocket = null;
		Socket socket = null;
	

		if (pttInterface.conf[7] == 1) {
			pttInterface.outField.append("\nstarted sender thr ");
		}
		
		///creating connection between sender and receiver
		try {
			serverSocket = new ServerSocket(portNo);
			pttInterface.outField.append("\n[SERVER] Waiting for receiver...");
				try {
						socket = serverSocket.accept();
						pttInterface.outField.append("\n[SERVER] Accepted connection : " + socket);
						
						// bytearray ~= 2GB
						long allocatedMemory = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
						long freeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
						File file = new File (fileLocation);
						if (freeMemory < (int)file.length()) {
							pttInterface.outField.append("[SERVER] Not enough memory availible. Exiting.");
							return;
						}
						pttInterface.progressBar.setIndeterminate(true);
						long startTime = System.currentTimeMillis();
						byte [] byteArray  = new byte [(int)file.length()];
						fileInputStream = new FileInputStream(file);
						bufferedInputStream = new BufferedInputStream(fileInputStream);
						bufferedInputStream.read(byteArray,0,byteArray.length);
	
						outputStream = socket.getOutputStream();
						pttInterface.outField.append("\n[SERVER] Transferring \'" + fileLocation + "\', total of \'" + byteArray.length + "\' bytes.");
						outputStream.write(byteArray,0,byteArray.length);
						outputStream.flush();
						//progressBar
						pttInterface.progressBar.setIndeterminate(false);
						long stopTime = System.currentTimeMillis();
						pttInterface.outField.append("\n[SERVER] Done transferring file, total time: " + ((stopTime - startTime) / 1000) + "s");	//file sent
						pttInterface.localPortField.setEnabled(true);
						pttInterface.localFileNameField.setEnabled(true);
						pttInterface.srvRunning = 0;
						//ram cleanup
						byteArray = null;
						System.gc();
					}
					finally {
						if (bufferedInputStream != null) bufferedInputStream.close();
						if (outputStream != null) bufferedInputStream.close();
						if (socket!=null) socket.close();
					}		
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
				pttInterface.outField.append("\n[ERR] IOException: Connection to client has been lost: " + x);
			}
			finally {
				if (serverSocket != null) serverSocket.close();
				pttInterface.localPortField.setEnabled(true);
				pttInterface.localFileNameField.setEnabled(true);
				pttInterface.progressBar.setIndeterminate(false);
				pttInterface.srvRunning = 0;
			}
	}
}