package sample.ChatClient;/*CLASSE CHE DISEGNA L'INTERFACCIA SULLO SCHERMO. E' STATO UTILIZZATO IL PACKAGE SWING PERCHE' L'APPLICAZIONE
GIRERA' COME STAND ALONE*/


import sample.ChatServer.IOsms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


public class Interfaccia_Chat extends JFrame implements ActionListener{

								//DICHIARO TUTTI I COMPONENTI CHE UTILIZZERO'

	JTextArea lettura = new JTextArea();
	JTextArea scrittura = new JTextArea();
	JTextArea utenti = new JTextArea();
	JButton esci = new JButton("ESCI");
	JButton invia = new JButton("INVIA");
	JPanel pan=new JPanel();
	JScrollPane scrollettura;
	JScrollPane scrolscrittura;
	JScrollPane scrolutenti;
	JScrollBar SB=new JScrollBar();
	JLabel L1 = new JLabel();
	JLabel L2 = new JLabel("utenti connessi :");
	int contatore = 0;
	int i=0;
	static ArrayList<String> messaggi = new ArrayList<>();

								//DICHIARO TUTTE LE VARIABILI (PASSATEMI DA INSERISCI_NICK)

	String nick;
	String porta;
	String IP;
	String server_IP;
	int server_porta;


	Ascolta_Chat AC;
	Invia_Messaggio IM;

	/*METODO COSTRUTTORE*/


public Interfaccia_Chat(String nick_utente, String mia_porta, String mio_IP, String IP_target, int porta_target) throws IOException {
	super("chatta con noi");

							//VALORIZZO LE VARIABILI CON I VALORI PASSATI DA IN

	nick=nick_utente;
	porta=mia_porta;
	IP=mio_IP;
	server_IP=IP_target;
	server_porta=porta_target;


	lettura.setBounds(10,10,400,185);
	lettura.setEditable(false);
	lettura.setLineWrap(true);
	lettura.setText(String.valueOf(IOsms.readFile()));
	scrollettura = new JScrollPane(lettura);
	scrollettura.setBounds(10,10,400,185);
	scrollettura.setVerticalScrollBar(SB);


	scrittura.addKeyListener(new MyKeyListener());
	scrittura.setBounds(10,245,400,50);
	scrittura.setLineWrap(true);
	scrolscrittura = new JScrollPane(scrittura);
	scrolscrittura.setBounds(10,245,400,50);


	utenti.setBounds(420,35,100,260);
	utenti.setEditable(false);
	utenti.setLineWrap(true);
	utenti.addMouseListener(new MouseSelector());
	scrolutenti = new JScrollPane(utenti);
	scrolutenti.setBounds(420,35,100,260);

	L1.setBounds(10,210,150,25);
	L1.setText(nick+" :");

	L2.setBounds(420,10,150,25);

	esci.setBounds(200,210,100,25);
	esci.setActionCommand("ESCI");
	esci.addActionListener(this);

	invia.setBounds(310,210,100,25);
	invia.setActionCommand("INVIA");
	invia.addActionListener(this);

	pan.add(scrolscrittura);
	pan.add(scrollettura);
	pan.add(scrolutenti);
	pan.add(L1);
	pan.add(L2);
	pan.add(esci);
	pan.add(invia);

	setContentPane(pan);
	pan.setLayout(null);

	setBounds(100,100,535,325);
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//QUI CI VA UN BEL COMMENTO
	setResizable(false);
	show();

	addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e){
				AC.spegni();
				IM=new Invia_Messaggio(server_IP,server_porta);
				if(!(IM.ci_sei)){JOptionPane.showMessageDialog(null,"ASSICURATEVI DI ESSERE CONNESSI IN RETE"
																  +"\n"+"SE IL PROBLEMA PERSISTE CONTATTATE"
																  +"\n"+"L'AMMINISTRATORE DEL SERVER");

											}//CHIUDO IF ANNIDATO
			else{
				IM.disconnessione(nick, IP, porta);}
				System.exit(0);
			}
		}
	);

	AC=new Ascolta_Chat(porta,lettura,SB,utenti);
	AC.start();
}

public void actionPerformed(ActionEvent evt){

String command = evt.getActionCommand();

if (command.equals("ESCI")){
	AC.spegni();
	IM=new Invia_Messaggio(server_IP,server_porta);
	if(!(IM.ci_sei)){JOptionPane.showMessageDialog(null,"ASSICURATEVI DI ESSERE CONNESSI IN RETE"
																	  +"\n"+"SE IL PROBLEMA PERSISTE CONTATTATE"
																	  +"\n"+"L'AMMINISTRATORE DEL SERVER");

												}//CHIUDO IF ANNIDATO
			else{
	IM.disconnessione(nick, IP, porta);}
    Window win = SwingUtilities.getWindowAncestor(pan);
    win.dispose();
}//chiude ESCI

if (command.equals("INVIA")){
	IM=new Invia_Messaggio(server_IP,server_porta);//CONTROLLO SE SI STABILISCE LA CONNESSIONE
		if(!(IM.ci_sei)){JOptionPane.showMessageDialog(null,"ASSICURATEVI DI ESSERE CONNESSI IN RETE"
													  +"\n"+"SE IL PROBLEMA PERSISTE CONTATTATE"
													  +"\n"+"L'AMMINISTRATORE DEL SERVER");
													scrittura.setText("");
													scrittura.grabFocus();
						}//CHIUDO IF
		else{
			String messaggio="";
			messaggio="<"+nick+"> :"+scrittura.getText();
			IM.Messaggio(messaggio,nick,IP,porta);
			scrittura.setText("");
			scrittura.grabFocus();
			try {
				messaggi_inviati(messaggio);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//CHIUDO ELSE
}//chiude INVIA
}//CHIUDE ACTION PERFORMED

	IOsms iOsms = new IOsms();
	public ArrayList<String> messaggi_inviati(String messaggio) throws IOException {

	if(IOsms.readFile().isEmpty()){
		System.out.println( IOsms.readFile().size());
		messaggi.add(contatore,messaggio+"\n");
		contatore++;
		return messaggi;
	}else{

		if (i == 0) {
			contatore = iOsms.readFile().size();
			System.out.println("contatore: " + contatore);

				for (int j = 0; j< contatore;j++){
					messaggi.add(IOsms.readFile().get( j ).toString());
				}
				i++;
		}
		messaggi.add(contatore, messaggio+"\n");
		contatore++;
		return  messaggi;
	}
}


class MyKeyListener extends KeyAdapter{

	public void keyPressed(KeyEvent evt){
		if (evt.getKeyChar()==KeyEvent.VK_ENTER){
			invia.doClick();
			}
		} // FINE METODO keyPressed

	public void keyTyped(KeyEvent evt){
		if (evt.getKeyChar()==KeyEvent.VK_ENTER){
		scrittura.setText("");
		scrittura.grabFocus();
		}//CHIUDO IF

		char a=evt.getKeyChar();//CONVERTO UN CARATTERE NEL SUO VALORE NUMERICO (UNICODE?!?)
		int b=a;				//COSI' DA POTERNE CONTROLLARE L'EVENTUALE PRESSIONE E IMPEDIRLA
		if (b==37||b==36||b==163||b==94){				//D'ORA IN POI NON SI POTRA' PIU' DIGITARE IL CARATTERE '%'
		evt.setKeyChar((char)KeyEvent.VK_CANCEL);
			}//CHIUDO IF
	}// CHIUDO keyTyped
} // FINE INNER CLASS MyKeyListener

class MouseSelector extends MouseAdapter{
	public void mouseClicked(MouseEvent evt){
		String n_privato=utenti.getSelectedText();
		if((n_privato!=null)&&(!(n_privato.equals(nick)))){
		IM=new Invia_Messaggio(server_IP,server_porta);
				if(!(IM.ci_sei)){JOptionPane.showMessageDialog(null,"ASSICURATEVI DI ESSERE CONNESSI IN RETE"
														  +"\n"+"SE IL PROBLEMA PERSISTE CONTATTATE"
														  +"\n"+"L'AMMINISTRATORE DEL SERVER");
														 									}//CHIUDO IF ANNIDATO
					else{			//UTILIZZO IL METODO CONNESSIONE

		IM.privato(nick, IP, porta,n_privato);}
		System.out.println(n_privato);}
}
}
}//CHIUDE LA CLASSE GRAFICA