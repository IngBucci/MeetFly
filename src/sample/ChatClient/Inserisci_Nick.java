package sample.ChatClient;/*QUESTA CLASSE DISEGNA SULLO SCHERMO UNA PICCOLA GUI CHE PERMETTE ALL'UTENTE DI INSERIRE UN NICK*/

import sample.ChatServer.IOsms;
import sample.Main;
import sample.mysql.Utenti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Inserisci_Nick extends JFrame implements ActionListener{

	private Main main;
	private Utenti utente;

	public void initNik(Utenti utente, Main main){
		this.main = main;
		this.utente=utente;

		T1.setText(utente.getNickname());
		T1.setEnabled( false );
	}


	/*METODO MAIN, QUESTA E' LA CLASSE CHE DETERMINA L'ENTRY POINT DELL'APPLICAZIONE*/

		public static void main(String args[]){
           // Lancia_Server.main(args);
		    Inserisci_Nick IN = new Inserisci_Nick();
		}//CHIUDO IL MAIN


	/*DICHIARO TUTTI I COMPONENTI GRAFICI CHE UTILIZZERO'*/

JPanel pan=new JPanel();
JLabel L1 = new JLabel("INSERISCI UN NICK :");
JTextField T1 = new JTextField(10);
JButton ok = new JButton("OK");
JButton esci = new JButton("ESCI");


	/*DICHIARO GLI OGGETTI CHE UTILIZZERO'*/

Invia_Messaggio IM;
InetAddress mio_IP;
Interfaccia_Chat IC;
BufferedReader Br;

	/*METODO COSTRUTTORE*/
public Inserisci_Nick(){

	super("BENVENUTO");

	IOsms.newFile();

	L1.setBounds(100,1,400,20);
	T1.setBounds(10,30,500,20);
	T1.addKeyListener(new MyKeyListener());

	ok.setBounds(10,60,100,25);
	ok.setActionCommand("OK");
	ok.addActionListener(this);


	esci.setBounds(210,60,100,25);
	esci.setActionCommand("ESCI");
	esci.addActionListener(this);

	pan.add(L1);
	pan.add(T1);
	pan.add(ok);
	pan.add(esci);

	setContentPane(pan);
	pan.setLayout(null);

	setBounds(200,200,325,120);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setResizable(false);
	show();

}//CHIUDO IL COSTUTTORE


/*METODO ACTION PERFORMED, LO POSSO UTILIZZARE PERCHE' IMPLEMENTO L'INTERFACCIA ACTION LISTENER,
  ASCOLTERA' L'EVENTO GENERATO DALL'EVENTUALE PRESSIONE D'UN TASTO*/

public void actionPerformed(ActionEvent evt){
	String command = evt.getActionCommand();


if (command.equals("ESCI")){

    Window win = SwingUtilities.getWindowAncestor(pan);
    win.dispose();
    //System.exit(0);
}//CHIUDE ESCI

if (command.equals("OK")){
	String nick=T1.getText();
	nick=nick.trim();
	String IP_da_trasmettere="";
	String porta="";
	String server_IP="";
	int server_porta=0;

	try{//ISTRUZIONI PER LEGGERE IL FILE DI CONFIGURAZIONE "FileConfig.txt"
		Br=new BufferedReader(new FileReader("src/sample/FileConfig.txt"));
		System.out.println("c");
		porta=Br.readLine();
		server_IP=Br.readLine();
		server_porta=Integer.parseInt(Br.readLine());
	}catch (IOException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null,"IMPOSSIBILE ACCEDERE AL FILE DI CONFIGURAZIONE");

	}

	try{//ISTRUZIONI PER SCOPRIRE IL PROPRIO INDIRIZZO IP
			mio_IP = InetAddress.getLocalHost();
			IP_da_trasmettere = mio_IP.getHostAddress();
	}catch(UnknownHostException e){JOptionPane.showMessageDialog(null,"IMPOSSIBILE OTTENERE L'IP");}

		if(nick.equals("")){//CONTROLLO SULL'INSERIMENTO DI UN NICK NAME
			JOptionPane.showMessageDialog(null,"IMPOSSIBILE CONNETTERSI SENZA UN NICK");
			T1.setText("");
			T1.grabFocus();
		}//CHIUDO IF
		else{				//SE E' STATO INSERITO UN NICK PROCEDO
			IM=new Invia_Messaggio(server_IP,server_porta);//CONTROLLO SE SI STABILISCE LA CONNESSIONE
			if(!(IM.ci_sei)){JOptionPane.showMessageDialog(null,"ASSICURATEVI DI ESSERE CONNESSI IN RETE"
												  +"\n"+"SE IL PROBLEMA PERSISTE CONTATTATE"
												  +"\n"+"L'AMMINISTRATORE DEL SERVER");
												  T1.setText("");
												  T1.grabFocus();
							}//CHIUDO IF ANNIDATO
			else{			//UTILIZZO IL METODO CONNESSIONE
				IM.connessione(nick, IP_da_trasmettere, porta);
				setVisible(false);

				try {
					IC=new Interfaccia_Chat(nick,porta,IP_da_trasmettere,server_IP,server_porta);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}//CHIUDO ELSE ANNIDATO
			}//CHIUDO ELSE
}//CHIUDE OK

}//CHIUDE ACTION PERFORMED


/*INNER CLASS CHE CONTROLLERA I CARATTERI DIGITATI NELLA CASELLA DI TESTO*/

class MyKeyListener extends KeyAdapter{

	public void keyPressed(KeyEvent evt){
		if (evt.getKeyChar()==KeyEvent.VK_ENTER){
			ok.doClick();
			}
		} // FINE METODO keyPressed

	public void keyTyped(KeyEvent evt){
		if (evt.getKeyChar()==KeyEvent.VK_ENTER){
		T1.setText("");
		T1.grabFocus();
		}//CHIUDO IF

		char a=evt.getKeyChar();//CONVERTO UN CARATTERE NEL SUO VALORE NUMERICO (UNICODE?!?)
		int b=a;				//COSI' DA POTERNE CONTROLLARE L'EVENTUALE PRESSIONE E IMPEDIRLA
		if (b==37||b==36||b==163||b==94){				//D'ORA IN POI NON SI POTRA' PIU' DIGITARE IL CARATTERE '%'
			evt.setKeyChar((char)KeyEvent.VK_CANCEL);
			}//CHIUDO IF
	}// FINE METODO keyTyped

} // FINE INNER CLASS MyKeyListener



}//CHIUDO LA CLASSE