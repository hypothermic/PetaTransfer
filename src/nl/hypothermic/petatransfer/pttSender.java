package nl.hypothermic.petatransfer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class pttSender {

	public static void send(int portNo,String fileLocation) throws IOException
	{
		FileInputStream fileInputStream = null;
		BufferedInputStream bufferedInputStream = null;

		OutputStream outputStream = null;
		ServerSocket serverSocket = null;
		Socket socket = null;

		if (pttInterface.conf[7] == 1) {
			pttInterface.outField.append("\nstarted sender thr ");
		}
		
		//creating connection between sender and receiver
		try {
			serverSocket = new ServerSocket(portNo);
			pttInterface.outField.append("\n[SERVER] Waiting for receiver...");
				try {
						socket = serverSocket.accept();
						pttInterface.outField.append("\n[SERVER] Accepted connection : " + socket);
						//connection established successfully
	
						//creating object to send file
						File file = new File (fileLocation);
						byte [] byteArray  = new byte [(int)file.length()];
						fileInputStream = new FileInputStream(file);
						bufferedInputStream = new BufferedInputStream(fileInputStream);
						bufferedInputStream.read(byteArray,0,byteArray.length); // copied file into byteArray
	
						//sending file through socket
						outputStream = socket.getOutputStream();
						pttInterface.outField.append("\n[SERVER] Transferring \'" + fileLocation + "\', total of \'" + byteArray.length + "\' bytes.");
						outputStream.write(byteArray,0,byteArray.length);			//copying byteArray to socket
						outputStream.flush();										//flushing socket
						pttInterface.outField.append("\n[SERVER] Done transferring file.");								//file has been sent
					}
					finally {
						if (bufferedInputStream != null) bufferedInputStream.close();
						if (outputStream != null) bufferedInputStream.close();
						if (socket!=null) socket.close();
					}		
			} catch (IOException x) {
				// TODO Auto-generated catch block
				x.printStackTrace();
				pttInterface.outField.append("[ERR] IOException in sender: " + x);
			}
			finally {
				if (serverSocket != null) serverSocket.close();
			}
	}
}