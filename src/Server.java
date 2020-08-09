
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener{
	
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JPanel a1;
	static JFrame f1 = new JFrame();
	
	static Box vertical = Box.createVerticalBox();
	
	static ServerSocket skt;
	static Socket s;
	static DataInputStream dIn;
	static DataOutputStream dOut;
	
	Boolean typing;
	
	Server(){
		f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(29,161,242));
		p1.setBounds(0, 0, 450, 70);
		f1.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/pngwave.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/Octocat.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/3dd.png"));
		Image i8 = i7.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		
		
		JLabel l5 = new JLabel(i9);
		l5.setBounds(390, 4, 60, 60);
		p1.add(l5);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		
		JLabel l6 = new JLabel(i12);
		l6.setBounds(350, 20, 35, 30);
		p1.add(l6);
		
		JLabel l3 = new JLabel("Server");
		l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 100, 18);
		p1.add(l3);
		
		JLabel l4 = new JLabel("Active Now");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 100, 20);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				if( ! typing) {
					l4.setText("Active Now");
				}
			}
		
			
		});
		
		t.setInitialDelay(900);
			
		
		
		a1 = new JPanel();                                   
		a1.setBounds(5, 75, 440, 550);                          
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));      		
		f1.add(a1);
		
		t1 = new JTextField();
		t1.setBounds(5, 630, 330, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		f1.add(t1);
		
		t1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l4.setText("typing..");
				t.stop();
				typing = true;
				
			}
			public void keyReleased(KeyEvent ke) {
				typing = false;
				
				if( ! t.isRunning()) {
					t.start();
				}
			}
		});
		
		b1 = new JButton("Send");
		b1.setBounds(342, 630, 100, 40);
		b1.setBackground(new Color(29, 161, 242));
		b1.setForeground(Color.BLUE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		b1.addActionListener(this);
		f1.add(b1);
		
		f1.getContentPane().setBackground(Color.WHITE);
		f1.setLayout(null);
		f1.setSize(450, 700);
		f1.setLocation(400, 200);
		
		
//		f1.setUndecorated(true);
		f1.setVisible(true);

		

	}
	public void actionPerformed(ActionEvent ae) {
		try{
		String out = t1.getText();
		JPanel p2 = formatLabel(out);
		
		a1.setLayout(new BorderLayout());
		
		JPanel right = new JPanel(new BorderLayout());
		right.add(p2, BorderLayout.LINE_END);
		vertical.add(right);
		
		vertical.add(Box.createVerticalStrut(15));
		
		a1.add(vertical, BorderLayout.PAGE_START);
	
		dOut.writeUTF(out);
		t1.setText("");
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	public static JPanel formatLabel(String out) {
		
	JPanel p3 = new JPanel();
	p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
	
	JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
	l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
	l1.setBackground(new Color(38, 190, 255));
	l1.setOpaque(true);
	l1.setBorder(new EmptyBorder(15,15,15,50));
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	JLabel l2 = new JLabel();
	l2.setText(sdf.format(cal.getTime()));
	
	p3.add(l1);
	p3.add(l2);
	return p3;
		
	}
	
	public static void main(String[] args) {
		new Server().f1.setVisible(true);
		
		String messageInput = "";
		
		try {
			skt = new ServerSocket(4001);
			while(true) {
			s = skt.accept();
			dIn = new DataInputStream(s.getInputStream());
			dOut = new DataOutputStream(s.getOutputStream());
			
			while(true) {
			messageInput = dIn.readUTF();
			JPanel p2 = formatLabel(messageInput);
			
			JPanel left = new JPanel(new BorderLayout());
			left.add(p2, BorderLayout.LINE_START);
			vertical.add(left);
			f1.validate();
			
			
			skt.close();
			s.close();
			}
		   } 
			
		   }catch(Exception e){
			
		   }
	}
}


