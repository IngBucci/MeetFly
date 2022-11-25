package sample.ChatClient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Invia_Messaggio_Privato{
	String messaggio="";
	String IP="";
	Leggi L;
	int porta;
	Socket Ugola;
	OutputStream OS;
	PrintWriter PW;
	boolean ci_sei=true;

public Invia_Messaggio_Privato(Leggi L1,String mex_pass,String suo_IP,int sua_porta){
	L=L1;
	messaggio=mex_pass;
	IP=suo_IP;
	porta=sua_porta;
	try{
		Ugola=new Socket (IP,porta);
		Ugola.setSoTimeout(100000);
		OS=Ugola.getOutputStream();
		PW=new PrintWriter(OS);
		PW.println (messaggio);
		PW.flush ();
		L.scrivo_su_area_testo(messaggio);
		Ugola.close();
	}catch (IOException e) {ci_sei=false;}
finally {Ugola=null;}

}//CHIUDE SPEDISCI

}//CHIUDO LA CLASSE


