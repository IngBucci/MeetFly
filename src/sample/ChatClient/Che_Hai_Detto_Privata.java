package sample.ChatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Che_Hai_Detto_Privata extends Thread{
	Socket MioTimpano;
	String messaggio="";
	Leggi L2;

public Che_Hai_Detto_Privata (Leggi L_passata_ascolta,Socket timpano_passato_ascolta){
		MioTimpano=timpano_passato_ascolta;
		L2=L_passata_ascolta;
		}

public void run(){
	try{

	InputStream IS=MioTimpano.getInputStream();
	BufferedReader IR=new BufferedReader(new InputStreamReader (IS));

messaggio = IR.readLine();
L2.scrivo_su_area_testo(messaggio);
MioTimpano.close();

}catch (IOException e) {System.out.println("ERRORE "+e);} //CHIUDO TRY

}//CHIUDO RUN

}//CHIUDO LA CLASSE