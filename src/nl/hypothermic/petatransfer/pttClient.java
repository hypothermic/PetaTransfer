package nl.hypothermic.petatransfer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class pttClient {
	public static void receiveFile(String ipAddress,int portNo,String fileLocation) throws IOException {

		int bytesRead=0;
		int current = 0;
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		Socket socket = null;
		try {
			long allocatedMemory = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
			long freeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
			if (freeMemory < 2000000000) {
				pttInterface.outField.append("[CLIENT] Not enough memory availible. Exiting.");
				return;
			}
			socket = new Socket(ipAddress,portNo);
			pttInterface.outField.append("\n[CLIENT] Connected to " + ipAddress);
			
			///progressbar
			pttInterface.progressBar.setIndeterminate(true);
			
			byte [] byteArray  = new byte [2000000000]; //6022386 ~= 6MB, 2000000000 ~= 2GB
			pttInterface.outField.append("\n[CLIENT] Downloading file");
			InputStream inputStream = socket.getInputStream();
			fileOutputStream = new FileOutputStream(fileLocation);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bytesRead = inputStream.read(byteArray,0,byteArray.length);
			pttInterface.outField.append("\n[CLIENT] Retrieved file from socket, now writing to file.");
			current = bytesRead;
			do {
				bytesRead =inputStream.read(byteArray, current, (byteArray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);
			pttInterface.outField.append("\n[CLIENT] Writing..");
			bufferedOutputStream.write(byteArray, 0 , current);
			bufferedOutputStream.flush();
			// ram cleanup
			byteArray = null;
			System.gc();
			
			//progressbar
			pttInterface.progressBar.setIndeterminate(false);
			pttInterface.outField.append("\n[CLIENT] Content saved as \'" + fileLocation  + "\', total of \'" + pttFormatSize.formatDecimaal(current) + "\'");
			pttInterface.clRunning = 0;
		} catch (UnknownHostException xh) {
			pttInterface.outField.append(pttInterface.lang[8]);
			if (pttInterface.conf[7] == 1) {
				pttInterface.outField.append("\nUnknownHostException in pttClient");
			}
			System.out.println("UnknownHostException");
			pttInterface.clRunning = 0;
		} catch (ConnectException xc) {
			pttInterface.outField.append(pttInterface.lang[8]);
			if (pttInterface.conf[7] == 1) {
				pttInterface.outField.append("\nConnectException in pttClient");
			}
			System.out.println("ConnectException");
			pttInterface.clRunning = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pttInterface.clRunning = 0;
		}
		finally {
			if (fileOutputStream != null) fileOutputStream.close();
			if (bufferedOutputStream != null) bufferedOutputStream.close();
			if (socket != null) socket.close();
			pttInterface.clRunning = 0;
			pttInterface.progressBar.setIndeterminate(false);
		}
	}
}