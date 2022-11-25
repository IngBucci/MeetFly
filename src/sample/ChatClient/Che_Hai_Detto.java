package sample.ChatClient;/*IDENTICO AL SUO CORRISPETTIVO SUL LATO SERVER, LE DUE TEXT ARRIVANO DALL'INTERFACCIA, COSI' DA POTERCI SCRIVERE
SOPRA*/

import sample.ChatServer.IOsms;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Che_Hai_Detto extends Thread{
	Socket MioTimpano;
	String comando="";
	String messaggio="";
	JTextArea mia_lettura;
	JScrollBar mia_sb;
	JTextArea mia_utenti;
	Interfaccia_Chat_Privata ICP;

public Che_Hai_Detto (Socket timpano_passato_ascolta,JTextArea lettura_interfaccia,
							JScrollBar SB_interfaccia,JTextArea utenti_interfaccia){

		MioTimpano=timpano_passato_ascolta;
		mia_lettura=lettura_interfaccia;
		mia_sb=SB_interfaccia;
		mia_utenti=utenti_interfaccia;
		}

public void run(){
	try{

	InputStream IS=MioTimpano.getInputStream();
	BufferedReader IR=new BufferedReader(new InputStreamReader (IS));

comando = IR.readLine();
messaggio = IR.readLine();

if (comando.equals("%messaggio%")){
mia_lettura.append(messaggio+"\n");
int a=mia_sb.getMaximum();
mia_sb.setValue(a);
MioTimpano.close();
}//CHIUDO IF

if (comando.equals("%utenti%")){
	//ArrayList<String> messaggi = new ArrayList<>();
char j='\n';
String new_messaggio="";
for (int u=0;u<messaggio.length();u++){
	char f=messaggio.charAt(u);
	if(f=='$'){
		f=j;}//chiude if
	new_messaggio+=f;

	}//chiude for
	IOsms contr= new IOsms();
    contr.initSms(messaggio);
mia_utenti.setText(new_messaggio);
MioTimpano.close();

}//CHIUDO IF

/*SE MI ARRIVA QUESTO MESSAGGIO DAL SERVER FACCIO PARTIRE LA CHAT PRIVATA*/
if (comando.equals("%privato%")){
	ICP=new Interfaccia_Chat_Privata(messaggio);
	MioTimpano.close();
}

}catch (IOException e) {System.out.println("ERRORE "+e);} //CHIUDO TRY

}//CHIUDO RUN



}//CHIUDO LA CLASSE