package sample.ChatServer;/*CLASSE CON L'UNICO SCOPO DI FAR PARTIRE TUTTE LE ALTRE (ENTRY POINT). UNICA CLASSE CON IL METODO MAIN, ISTANZIA
UN OGGETTO IC DALLA CLASSE ASCOLTA CHAT*/


public class Lancia_Server{
public static void main(String args[]){
	Ascolta_Chat IC = new Ascolta_Chat();
	IC.start();//FA' PARTIRE IL METODO RUN DI ASCOLTA CHAT(CHE ESTENDE THREAD)
	System.out.println("f");
	}//CHIUDO MAIN
}//CHIUDO LA CLASSE