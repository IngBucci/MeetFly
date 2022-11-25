package sample.ChatServer;/*ANCHE QUESTA CLASSE ESTENDE THREAD. ALL'INTERNO DEL METODO RUN VIENE DESCRITTO IL SOCKET
FIGLIO CHE RISONDERA' ALLE RICHIESTE DEI CLIENT (IN REALTA' NON LO FARA DIRETTAMENTE, COME SI
PUO' NOTARE NON VIENE IMPLEMENTATO NESSUN FLUSSO DI OUTPUT, MA PASSERA' LE RICHIESTE RICEVUTE ALLA CLASSE
REGISTRO CHE SI OCCUPERA' DI RISPONDERE), INFATTI IL METODO COSTRUTTORE PONE IL SOCKET
PASSATOGLI IN INGRESSO DALLA CLASSE ASCOLTA_CHAT UGUALE A QUELLO DESCRITTO DENTRO RUN. IL SOCKET
FIGLIO VIENE DESCRITTO ALL'INTERNO DI RUN PER POTER ESSERE INDIPENDENTE E QUINDI POTER ESSERE
ISTANZIATO TUTTE LE VOLTE CHE UN CLIENT NE FA' RICHIESTA, ANCHE CONTEMPORANEAMENTE*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class Che_Hai_Detto extends Thread{
	Socket MioTimpano;		//DICHIARO L'ESISTENZA DI UN SOCKET
	String comando="";
	Registro R1;			//DICHIARO L'ESISTENZA DI UNA CLASSE DI NOME REGISTRO
							//CIOE' QULLA CONTENENTE IL VETTORE

public Che_Hai_Detto (Socket timpano_passato_ascolta, Registro R_passato){
		MioTimpano=timpano_passato_ascolta;//PONGO IL MIO SOCKET UGUALE A QUELLO CHE HO RICEVUTO
		R1=R_passato;		//PONGO LA MIA CLASSE REGISTRO UGUALE A QUELLA CHE HO RICEVUTO
		}

/*IL SOCKET SI ASPETTA IN ARRIVO SEMPRE DUE STRINGHE O TRE STRINGHE A SECONDA DELLLA RICHIESTA FATTA
DALL'UTENTE*/
public void run(){
	try{

	InputStream IS=MioTimpano.getInputStream();
	BufferedReader IR=new BufferedReader(new InputStreamReader (IS));

comando = IR.readLine();

/*A SECONDA DI QUALE SIA LA RICHIESTA DEL CLIENT VIENE CHIAMATO L'APPOSITO METODO
DELLA CLASSE REGISTRO*/
if (comando.equals("%connetti%")){
	String messaggio = IR.readLine();
	R1.nuovo_utente(messaggio);}

if (comando.equals("%disconnetti%")){
	String autore=IR.readLine();
	R1.elimina_utente(autore);}

if (comando.equals("%messaggio%")){
	String autore=IR.readLine();
	String messaggio = IR.readLine();
	R1.messaggio_utente(autore,messaggio);}

if (comando.equals("%privato%")){
	String autore=IR.readLine();
	String messaggio = IR.readLine();
	R1.privato(autore,messaggio);}

MioTimpano.close();//SPENGO IL SOCKET FIGLIO

}catch (IOException e) {System.out.println("ERRORE "+e);} //CHIUDO TRY

}//CHIUDO RUN

}//CHIUDO LA CLASSE