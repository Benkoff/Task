import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ChatFrame extends Frame{
	public ChatFrame(){
		setSize(500, 500);
		setTitle("Chat Frame");
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
		add(new ChatPanel(), BorderLayout.CENTER);
		setVisible(true);
	}
	public static void main(String[] args){
		new ChatFrame();
	}
}

class ChatPanel extends Panel implements ActionListener, Runnable{  //INCOMPLETE!!!
	TextArea ta;
	TextField tf;
	Button connect, disconnect;
	Thread thread;
	java.awt.List list;
	
	public ChatPanel(){
			setLayout(new BorderLayout());
			tf = new TextField();
			tf.addActionListener(this);
			tf.addActionListener(this);
			ta = new TextArea();
			add(tf, BorderLayout.NORTH);
			add(ta, BorderLayout.CENTER);
			connect = new Button("Connect");
			disconnect = new Button("Disconnect");
			Panel buttonPanel = new Panel();
			buttonPanel.add(connect);
			buttonPanel.add(disconnect);
			add(buttonPanel, BorderLayout.SOUTH);
			list = new java.awt.List(4, true);
			add(list, BorderLayout.EAST);
			thread = new Thread(this);
			thread.start();
		
		
		
	}
	public void actionPerformed(ActionEvent ae){
		//dummy content - this is where you react to the user
		System.out.println(tf.getText());
		tf.setText("");
	}
	public void run(){
		//dummy content - replace this
		System.out.println("This is the thread. I should be listeneing for messages from the server.");
	}
}