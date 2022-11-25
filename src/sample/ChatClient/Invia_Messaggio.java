package sample.ChatClient;/*QUESTA E' LA CLASSE CHE SI OCCUPA DI INVIARE UNA STRINGA AL SERVER, I FLUSSI DI INPUT ANCHE SE NON
NECESSARI DEVONO COMUNQUE ESSERE DICHIARATI ALL'INTERNO DEI METODI, TRANNE IL COSTRUTTORE, PENA UN
ERRORE IN COMPILAZIONE*/

import com.sun.xml.internal.ws.api.model.MEP;
import sample.ChatServer.IOsms;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Invia_Messaggio{


/*DICHIARO GLI OGGETTI CHE UTILIZZERO'*/

	String messaggio="";
	String server_IP="";
	int server_porta;
	Socket Ugola;
	OutputStream OS;
	PrintWriter PW;
	InputStream IS;
	BufferedReader IR;

	public boolean ci_sei;	//QUESTA VARIABILE CONTROLLA SE OTTENGO LA

	//CONNESSIONE CON IL SERVER



/*METODO COSTRUTTORE*/
	public Invia_Messaggio(String ip_cui_spedire, int porta_cui_spedire){
	ci_sei=true;
	server_IP=ip_cui_spedire;
	server_porta=porta_cui_spedire;

	try{
		Ugola=new Socket (server_IP,server_porta);
		Ugola.setSoTimeout(100000);
		OS=Ugola.getOutputStream();
		PW=new PrintWriter(OS);
		}catch (IOException e) {ci_sei=false;}
}//CHIUDO IL METODO COSTRUTTORE



    void connessione(String nick, String IP, String porta){
	  try{
		char i='%';
		char l='$';
		String first=nick+i+IP+l+porta;
		PW.println (i+"connetti"+i);
		PW.flush ();
		PW.println (first);
		PW.flush ();
/*********************************************************/
		IS=Ugola.getInputStream();						 	//FLUSSO DI INPUT
		IR=new BufferedReader(new InputStreamReader (IS));
/*********************************************************/
		Ugola.close();		//CHIUDO IL SOCKET
	}catch (IOException e) {ci_sei=false;}
finally {Ugola=null;}		//DISTRUGGO IL SOCKET
}//CHIUDO IL METODO CONNESSIONE


void Messaggio(String messaggio,String nick, String IP, String porta){
	try{

		char i='%';
		char l='$';
		String first=nick+i+IP+l+porta;
		PW.println (i+"messaggio"+i);
		PW.flush ();
		PW.println (first);
		PW.flush ();
		PW.println (messaggio);
		PW.flush ();
/*********************************************************/
		IS=Ugola.getInputStream();						 	//FLUSSO DI INPUT
		IR=new BufferedReader(new InputStreamReader (IS));

/*********************************************************/
		Ugola.close();		//CHIUDO IL SOCKET
	}catch (IOException e) {ci_sei=false;}
finally {Ugola=null;}		//DISTRUGGO IL SOCKET
}//CHIUDO IL METODO MESSAGGIO

	/*public ArrayList<String> messaggi_inviati(String messaggio){
		System.out.println(messaggio);
		messaggi.add(contatore,messaggio);
		contatore++;
		System.out.println(contatore);
		System.out.println(messaggi.size());
		return messaggi;
	}*/

void disconnessione(String nick, String IP, String porta){
	try{

		IOsms.writeFile(Interfaccia_Chat.messaggi);
		char i='%';
		char l='$';
		String first=nick+i+IP+l+porta;
		PW.println (i+"disconnetti"+i);
		PW.flush ();
		PW.println (first);
		PW.flush ();
/*********************************************************/
		IS=Ugola.getInputStream();						 	//FLUSSO DI INPUT
		IR=new BufferedReader(new InputStreamReader (IS));
/*********************************************************/
		Ugola.close();		//CHIUDO IL SOCKET
	}catch (IOException e) {ci_sei=false;}
finally {Ugola=null;}		//DISTRUGGO IL SOCKET
}//CHIUDO IL METODO DISCONNESSIONE


void privato(String nick, String IP, String porta,String n_privato){
	try{
		char i='%';
		char l='$';
		String first=nick+i+IP+l+porta;
		PW.println (i+"privato"+i);
		PW.flush ();
		PW.println (first);
		PW.flush ();
		PW.println (n_privato);
		PW.flush ();
/*********************************************************/
		IS=Ugola.getInputStream();						 	//FLUSSO DI INPUT
		IR=new BufferedReader(new InputStreamReader (IS));
/*********************************************************/
		Ugola.close();		//CHIUDO IL SOCKET
	}catch (IOException e) {ci_sei=false;}
finally {Ugola=null;}		//DISTRUGGO IL SOCKET
}//CHIUDO IL METODO DISCONNESSIONE



}//CHIUDO LA CLASSE


