package sample.ChatClient;/*PRATICAMENTE IDENTICA ALLA CLASSE CON LO STESSO NOME SUL LATO SERVER, IN PIU' UTILIZZA DEGLI ELEMENTI GRAFICI
CHE PASSERA' AL SOCKET FIGLIO PER SCRIVERCI SOPRA*/

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Ascolta_Chat extends Thread{
	public ServerSocket Orecchio;
	boolean ci_sento=false;
	int porta;
	JTextArea mia_lettura;
	JScrollBar mia_sb;
	JTextArea mia_utenti;

public Ascolta_Chat(String mia_porta,JTextArea lettura_interfaccia,JScrollBar SB_interfaccia,
																JTextArea utenti_interfaccia) {

	mia_lettura=lettura_interfaccia;
	mia_sb=SB_interfaccia;
	mia_utenti=utenti_interfaccia;
	porta=Integer.parseInt(mia_porta);//OCCHIO CHE QUA SI POTREBBERO GENERARE ECCEZIONI
}										//CONTROLLA TUTTE LE CONVERSIONI DA STRINGA A NUMERO

public void run() {
	try {
	ci_sento=true;
	Orecchio = new ServerSocket(porta);
			while(ci_sento){
			Socket timpano = Orecchio.accept();
			Che_Hai_Detto C_H_D = new Che_Hai_Detto(timpano,mia_lettura,mia_sb,mia_utenti);
			C_H_D.start();
			}//FINE RUN

	} catch (IOException e) {System.out.println("CONNESSIONE CHIUSA");

	//System.exit(0);
		}
	catch (Exception e) {System.out.println("ERRORE GENERALE :" + e);}
}


public void spegni(){
		try {
			Orecchio.close();
			Orecchio=null;
		} catch (IOException e) {System.out.println("ERRORE chiusura" + e);}
} // FINE SPEGNI

} // FINE CLASSE

