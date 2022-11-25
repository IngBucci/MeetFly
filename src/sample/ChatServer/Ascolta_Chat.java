package sample.ChatServer;/*QUESTA CLASSE ESTENDE THREAD, IL CHE LE PERMETTE DI ESEGUIRE CODICE IN MANIERA INDIPENDENTE
DAL RESTO DELL' APPLICAZIONE, TUTTO CIO' CHE SI TROVA ALL'INTERNO DEL METODO RUN VIENE ESEGUITO
IN CONCOMITANZA CON QUALUNQUE ALTRO PROCESSO.
INOLTRE DESCRIVE UN SERVER SOCKET, OSSIA UN SOCKET CHE SE INTERROGATO DA UN ALTRO,
E' IN GRADO DI RISPONDERE.PER POTER PERMETTERE AL SERVER SOCKET DI RIMANERE COSTANTEMENTE IN
ASCOLTO ALLE MOLTEPLICI RICHIESTE DI EVENTUALI SOCKET CLIENT SI INSERISCE IL CODICE CHE
GENERA LA RISPOSTA (NORMALMENTE DETTO SOCKET FIGLIO) ALL'INTERNO DI UN
CICLO INFINITO E PER FAR SI CHE DETTO CICLO NON MONOPOLIZZI LE RISORSE DELLA MACCHINA VIENE
INSERITO ALL'INTERNO DI RUN*/

import java.net.ServerSocket;
import java.net.Socket;

class Ascolta_Chat extends Thread{	//NELLA DICHIARAZIONE DELLA CLASSE ESTENDO THREAD
	public ServerSocket Orecchio; 	//DICHIARO L'ESISTENZA DEL SERVER SOCKET
	boolean ci_sento=false;			//LA CONDIZIONE CHE USERO' PER CONTROLLARE IL CICLO
	Che_Hai_Detto C_H_D;			//QUESTA CLASSE MI FORNIRA' GLI OGGETTI SOCKET FIGLIO
	Registro R=new Registro();		//QUESTA CLASSE CONTIENE UN VETTORE NEL QUALE MEMORIZZERO'
									//TUTTI GLI UTENTI CHE SI CONNETTONO

public Ascolta_Chat() {				//METODO COSTRUTTORE VUOTO
}

public void run() {		//IL METODO RUN ESEGUE CODICE INDIPENDENTE DAL RESTO DELL'APPLICAZIONE
	System.out.println("Server in ascolto...");
	try {
	ci_sento=true;
	Orecchio = new ServerSocket(666);//ISTANZIO IL SERVER SOCKET IN ASCOLTO SULLA PORTA 666
			while(ci_sento){
			Socket timpano = Orecchio.accept();//OGNI VOLTA CHE ARRIVA UNA RICHIESTA PARTE IL
			C_H_D = new Che_Hai_Detto(timpano,R);//SOCKET FIGLIO CHE VIENE PASSATO IN INGRESSO
			C_H_D.start();//ALLA CLASSE CHD CHE ISTANZIO E FACCIO PARTIRE (ESTENDE I THREAD)
			}//FINE WHILE

	}catch (Exception e) {System.out.println("ERRORE GENERALE :" + e);}
}//FINE RUN

} // FINE CLASSE

