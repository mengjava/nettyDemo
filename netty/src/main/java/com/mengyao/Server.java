package com.mengyao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: mengyao
 * @Date: 2019/7/1 0001 16:53
 * @Description:
 */
public class Server {
	public static void main(String[] args) throws IOException {
		//服务端必备
		ServerSocket serverSocket = new ServerSocket();
		//绑定监听端口
		serverSocket.bind(new InetSocketAddress(1001));
		System.out.println("Server start ....");
		while (true){
			new Thread(new ServerTask(serverSocket.accept())).start();
		}

	}

	private static  class ServerTask implements Runnable{
		private Socket socket = null;
		public ServerTask(Socket socket){
			this.socket = socket;
		}
		@Override
		public void run() {
			//拿和客户端通信的输入输出流
			try(ObjectInputStream inputStream
					    = new ObjectInputStream(socket.getInputStream());
			    ObjectOutputStream outputStream
					    = new ObjectOutputStream(socket.getOutputStream())){

				/*服务器的输入*/
				String userName = inputStream.readUTF();
				System.out.println("Accept clinet message:"+userName);

				outputStream.writeUTF("Hello,"+userName);
				outputStream.flush();

			} catch (Exception e){
			e.printStackTrace();
		} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
