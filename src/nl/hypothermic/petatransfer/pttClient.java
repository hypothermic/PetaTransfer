package nl.hypothermic.petatransfer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class pttClient {


		///pttClient.receiveFile(ipAddress, portNo, fileLocation);


	public static void receiveFile(String ipAddress,int portNo,String fileLocation) throws IOException
	{

		int bytesRead=0;
		int current = 0;
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		Socket socket = null;
		try {

			//creating connection.
			socket = new Socket(ipAddress,portNo);
			System.out.println("\n[CLIENT] Connected to " + ipAddress);
			
			// receive file
			byte [] byteArray  = new byte [6022386];					//I have hard coded size of byteArray, you can send file size from socket before creating this.
			System.out.println("\n[CLIENT] Downloading file");
			
			//reading file from socket
			InputStream inputStream = socket.getInputStream();
			fileOutputStream = new FileOutputStream(fileLocation);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bytesRead = inputStream.read(byteArray,0,byteArray.length);					//copying file from socket to byteArray

			current = bytesRead;
			do {
				bytesRead =inputStream.read(byteArray, current, (byteArray.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);
			bufferedOutputStream.write(byteArray, 0 , current);							//writing byteArray to file
			bufferedOutputStream.flush();												//flushing buffers
			
			System.out.println("\n[CLIENT] Content saved as \'" + fileLocation  + "\', total of \'" + current + "\' bytes.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (fileOutputStream != null) fileOutputStream.close();
			if (bufferedOutputStream != null) bufferedOutputStream.close();
			if (socket != null) socket.close();
		}
	}
}