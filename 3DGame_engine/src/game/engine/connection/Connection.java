package game.engine.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Networking.Chat;
import Networking.ChatMessage;
import game.connection.objects.ReceiveAction;
import game.connection.objects.WaiterObject;

public class Connection implements Runnable{

	private Socket socket;
	private Chat chat;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private List<WaiterObject> waiters = new ArrayList<WaiterObject>();

	public Connection(String ip, int port){
		try {
			this.socket = new Socket(ip, port);
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
			this.input = new ObjectInputStream(this.socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while(!socket.isClosed()){
			try {
				handleObject(input.readObject());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handleObject(Object o){
		if(o instanceof ReceiveAction){
			((ReceiveAction) o).unpack();
		}else if(o instanceof ChatMessage){
			chat.recieveMessage((ChatMessage) o);
		}else if(waiters.size() > 0){
			for(WaiterObject<?> waiter : waiters){
				if(waiter.checkType(o)){
					waiters.remove(waiter);
					break;
				}
			}
		}
	}

	public void send(Object o){
		try {
			output.writeObject(o);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setChat(Chat chat){
		this.chat=chat;
	}

	public void addWaiter(WaiterObject<?> waitForImage) {
		waiters.add(waitForImage);
	}
	
}
