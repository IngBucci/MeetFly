package sample.ChatServer;/*CLASSE CHE INVIA I MESSAGGI A TUTTI GLI UTENTI PRESENTI IN CHAT. VUOLE IN INGRESSA IL MESSAGGIO CHE DEVE
INVIARE, IL NUMERO DEGLI UTENTI PRESENTI, TUTTI I LORO IP E TUTTE LE LORO PORTE*/

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Invia_Messaggio{

	/*TUTTI GLI OGGETTI CHE UTILIZZERO'*/

	String comando="";
	String messaggio="";
	String[] IP;
	int[] porta;
	int n_utenti;
	Socket Ugola;
	OutputStream OS;
	PrintWriter PW;

	/*METODO COSTRUTTORE*/

public Invia_Messaggio(String com_pass,String mex_pass,String[] IP_passati,int[] porta_passate,int n_utenti_passati){

	comando=com_pass;
	messaggio=mex_pass;
	IP=IP_passati;
	porta=porta_passate;
	n_utenti=n_utenti_passati;
	int i=0;

	try{
			for(i=0;i<n_utenti;i++){				//GIRA PER IL NUMERO DI UTENTI PRESENTI
				Ugola=new Socket (IP[i],porta[i]);	//SI CONNETTE AD OGNUNO
				Ugola.setSoTimeout(100000);
				OS=Ugola.getOutputStream();
				PW=new PrintWriter(OS);
				PW.println (comando);
				PW.flush ();
				PW.println (messaggio);				//INVIA IL MESSAGGIO AD OGNUNO
				PW.flush ();
				Ugola.close();						//CHIUDE OGNI SINGOLA CONNESSIONE
		}//CHIUDE FOR
	}catch (IOException e) {System.out.println("impossibile comunicare con"+IP[i]+","+porta[i]);}


finally {Ugola=null;}		//DISTRUGGE IL SOCKET

}//CHIUDE COSTRUTTORE

}//CHIUDO LA CLASSE


