package sample.ChatClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Ascolta_Chat_Privata extends Thread{
	public ServerSocket Orecchio;
	boolean ci_sento=false;
	Leggi L1;
	int porta;

public Ascolta_Chat_Privata(Leggi L_passata_interfaccia,int mia_porta) {
	L1=L_passata_interfaccia;
	porta=mia_porta;
}

public void run() {
	try {
	ci_sento=true;
	Orecchio = new ServerSocket(porta);
			while(ci_sento){
			Socket timpano = Orecchio.accept();
			Che_Hai_Detto_Privata C_H_D_P = new Che_Hai_Detto_Privata(L1,timpano);
			C_H_D_P.start();
			}//FINE RUN

	} catch (IOException e) {System.out.println("CONNESSIONE CHIUSA");}
	catch (Exception e) {System.out.println("ERRORE GENERALE :" + e);}
}


public void spegni(){
		try {
			Orecchio.close();
			Orecchio=null;
		} catch (IOException e) {System.out.println("ERRORE chiusura" + e);}
} // FINE SPEGNI

} // FINE CLASSE

