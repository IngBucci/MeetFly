package sample.ChatServer;/*QUESTA E' LA CLASSE CHE CONTIENE IL VETTORE NEL QUALE SONO REGISTRATI GLI UTENTI CONNESSI. A SECONDA
DELL'OPERAZIONE RICHIESTA DAL CLIENT AL VETTORE VIENE AGGIUNTO O ELIMINATO UN UTENTE. SE, INVECE, SI
TRATTA DI UN MESSAGGIO DA INOLTRARE A TUTTI GLI UTENTI CONNESSI VERRA' CREATA UN ISTANZA DELLA CLASSE
INVIA MESSAGGIO(SOCKET) CHE SI OCCUPERA' DI INOLTRARE IL MESSAGGIO A TUTTI I PARTECIPANTI DELLA CHAT*/

import java.util.Vector;

public class Registro{

	Vector utenti=new Vector();
	Invia_Messaggio IM;		//DICHIARO L'ESISTENZA DI INVIA MESSAGGIO

	public Registro(){}	//METODO COSTRUTTORE VUOTO


/*METODO CHE AGGIUNGE UN UTENTE AL VETTORE*/
void nuovo_utente(String utente){
	String comando="%utenti%";
	String messaggio_utente="";
	String T="";
	String P="";
	String Z="";
	String Y="";
	int controllo=0;
/*COMINCIAMO CON CONTROLLARE CHE IL NICK DIGITATO DALL'UTENTE NON SIA GIA' PRESENTE,E ANCHE
CHE UN UTENTE NON CERCHI DI CONNETTERSI CONTEMPORANEAMENTE PIU' DI UNA VOLTA*/
for(int u=0;u<utente.length();u++){
	char k=utente.charAt(u);
	if(k=='%'){T=utente.substring(0,u);
			   Z=utente.substring(u+1,utente.length());}}
for(int u=0;u<utenti.size();u++){
String dati_utente=(String)utenti.elementAt(u);
for(int l=0;l<dati_utente.length();l++){
			char m=dati_utente.charAt(l);
			if(m=='%'){P=dati_utente.substring(0,l);
					   Y=dati_utente.substring(l+1,dati_utente.length());
			if(T.equals(P)){controllo=1;}
			if(Z.equals(Y)){controllo=2;}
			}}}

/*SE IL NICK E' GIA' PRESENTE INVIO UN MESSAGGIO ALL'UTENTE AVVERTENDOLO DELL'ERRORE E NON
LO INSERISCO NEL VETTORE*/
if(controllo==1){System.out.println("un utente cerca di entrare con un nick gia' in uso");
int o=0;
int n=0;
for(int u=0;u<utente.length();u++){
	char k=utente.charAt(u);
	if(k=='%'){n=u;}
	if(k=='$'){o=u;}}
String[] A={utente.substring(n+1,o)};
int[] B={Integer.parseInt(utente.substring(o+1,utente.length()))};
IM=new Invia_Messaggio("%messaggio%","<SERVER ADMINISTRATOR>:    Il nick che hai scelto � gia presente!!!"+
"      Per favore chiudi l'applicazione e riprova.",A,B,A.length);
String w="";
for(int u=0;u<utenti.size();u++){
String dati_utente=(String)utenti.elementAt(u);
for(int l=0;l<dati_utente.length();l++){
			char m=dati_utente.charAt(l);
			if(m=='%'){w+=dati_utente.substring(0,l)+'$';}}}
IM=new Invia_Messaggio("%utenti%",w,A,B,A.length);}
/*SE INVECE E' UN UTENTE CHE CERCA DI CONNETTERSI PIU' DI UNA VOLTA MI LIMITO A NON INSERIRLO NEL
VETTORE, IL FATTO CHE NON GLI INVIO NESSUNA RISPOSTA FARA' SI CHE L'APPLICAZIONE SI CHIUDA AUTOMATICAMENTE*/
if(controllo==2){}

/*SE INVECE E' UN NUOVO UTENTE LO INSERISO NEL VETTORE E AVVERTO TUTTI DEL SUO ARRIVO*/
if(controllo==0){

	utenti.addElement(utente);
		System.out.println("");
		System.out.println("Sono presenti n."+utenti.size()+" utenti :");
for(int i=0;i<utenti.size();i++){
	System.out.println(utenti.elementAt(i));
	}//CHIUDO FOR
String[] IP=new String[utenti.size()];//PER POTER CREARE UN SOCKET CHE INVII UN MESSAGGIOA TUTTI
int [] porta=new int[utenti.size()];//GLI UTENTI DOVRO' PASSARLI L'IP E LA PORTA DI OGNUNO
String temp_IP="";					//SOTTOFORMA DI ARRAY
int temp_porta;

for(int i=0;i<utenti.size();i++){//INCOMINCIO ESTRAENDO TUTTI GLI UTENTI CHE HO IN MEMORIA
String dati_utente=(String)utenti.elementAt(i);//OPERAZIONE DI CAST, IL VETTORE MEMORIZZA
									//OGGETTI, SE L'OGETTO E' UNA STRINGA LO DEVO CONVERTIRE
int n=0;
int o=0;

		for(int l=0;l<dati_utente.length();l++){//ANALIZZO I SINGOLI CARATTERI DI CUI SONO
			char m=dati_utente.charAt(l);//COMPOSTE LE STRINGHE CHE INDIVIDUANO OGNI UTENTE
			if(m=='%'){	//OGNI VOLTA CHE TROVO UN CARATTERE SPECIALE NE MEMORIZZO LA POSIZIONE
				n=l;}//CHIUDO IL PRIMO IF
			if(m=='$'){
				o=l;}//CHIUDO IL SECONDO IF
		}//CHIUDO FOR ANNIDATO

/*AVENDO MEMORIZZATO LE POSIZIONI DEI CARATTERI SPECIALI POSSO CREARE DELLE SOTTOSTRINGHE CON
L'ARGOMENTO (IP E PORTA) CHE MI SARA' NECESSARIO PASSARE A INVIA_MESSAGGIO*/
	temp_IP=dati_utente.substring(n+1, o);
	temp_porta=Integer.parseInt(dati_utente.substring(o+1,dati_utente.length()));
	messaggio_utente+=dati_utente.substring(0, n)+'$';
	IP [i]=temp_IP;//RIEMPIO GLI ARRAY CON LE SOTTOSTRINGHE
	porta [i]=temp_porta;
}//CHIUDO FOR ESTERNO

IM=new Invia_Messaggio(comando,messaggio_utente,IP,porta,utenti.size());//ISTANZIO INVIA_MESSAGGIO E
}//chiude else

}//CHIUDE NUOVO_UTENTE										GLI PASSO TUTTO IL NECESSARIO



/*METODO CHE ELIMINA UN UTENTE DAL VETTORE */
void elimina_utente(String utente){
/*CONTROLLO CHE L'UTENTE SIA PRESENTE ALTRIMENTI IL TENTATIVO DI ELIMINARE UN OGGETTO CHE NON C'E'
NEL VETTORE MI CREEREBBE DEI PROBLEMI NEL SERVER*/
boolean Y=false;
for(int i=0;i<utenti.size();i++){
	String dati_utente=(String)utenti.elementAt(i);
	if(utente.equals(dati_utente)){Y=true;}}
/*SE INVECE L'UTENTE C'E' LO ELIMINO DAL VETTORE E AVVERTO TUTTI DELLA SUA USCITA*/
if(Y){
		String comando="%utenti%";
		String messaggio_utente="";
		System.out.println("");
		boolean fatto=utenti.remove(utente);
	for(int i=0;i<utenti.size();i++){
	System.out.println(utenti.elementAt(i));
		}//CHIUDO FOR
		System.out.println("Sono presenti n."+utenti.size()+" utenti :");
	if(!fatto){
	System.out.println("non sono riuscito a eliminare l'oggetto");
	}//CHIUDO IF

String[] IP=new String[utenti.size()];//PER POTER CREARE UN SOCKET CHE INVII UN MESSAGGIOA TUTTI
int [] porta=new int[utenti.size()];//GLI UTENTI DOVRO' PASSARLI L'IP E LA PORTA DI OGNUNO
String temp_IP="";					//SOTTOFORMA DI ARRAY
int temp_porta;

for(int i=0;i<utenti.size();i++){//INCOMINCIO ESTRAENDO TUTTI GLI UTENTI CHE HO IN MEMORIA
String dati_utente=(String)utenti.elementAt(i);//OPERAZIONE DI CAST, IL VETTORE MEMORIZZA
									//OGGETTI, SE L'OGETTO E' UNA STRINGA LO DEVO CONVERTIRE
int n=0;
int o=0;

		for(int l=0;l<dati_utente.length();l++){//ANALIZZO I SINGOLI CARATTERI DI CUI SONO
			char m=dati_utente.charAt(l);//COMPOSTE LE STRINGHE CHE INDIVIDUANO OGNI UTENTE
			if(m=='%'){	//OGNI VOLTA CHE TROVO UN CARATTERE SPECIALE NE MEMORIZZO LA POSIZIONE
				n=l;}//CHIUDO IL PRIMO IF
			if(m=='$'){
				o=l;}//CHIUDO IL SECONDO IF
		}//CHIUDO FOR ANNIDATO

/*AVENDO MEMORIZZATO LE POSIZIONI DEI CARATTERI SPECIALI POSSO CREARE DELLE SOTTOSTRINGHE CON
L'ARGOMENTO (IP E PORTA) CHE MI SARA' NECESSARIO PASSARE A INVIA_MESSAGGIO*/
	temp_IP=dati_utente.substring(n+1, o);
	temp_porta=Integer.parseInt(dati_utente.substring(o+1,dati_utente.length()));
	messaggio_utente+=dati_utente.substring(0, n)+'$';
	IP [i]=temp_IP;//RIEMPIO GLI ARRAY CON LE SOTTOSTRINGHE
	porta [i]=temp_porta;
}//CHIUDO FOR ESTERNO

IM=new Invia_Messaggio(comando,messaggio_utente,IP,porta,utenti.size());//ISTANZIO INVIA_MESSAGGIO E
}//CHIUDO L'IF CHE CONTROLLA L'ESISTENZA DELL' UTENTE
}//CHIUDO ELIMINA_UTENTE										GLI PASSO TUTTO IL NECESSARIO



/*METODO CHE INOLTRA UN MESSAGGIO DELL'UTENTE A TUTTI I PRESENTI*/
void messaggio_utente(String autore,String messaggio_utente){
/*CHIARAMENTE ANCHE QUI CONTROLLO L'ESISTENZA DELL'UTENTE*/
boolean Y=false;
for(int i=0;i<utenti.size();i++){
	String dati_utente=(String)utenti.elementAt(i);
	if(autore.equals(dati_utente)){Y=true;}}
if(Y){
String comando="%messaggio%";
String[] IP=new String[utenti.size()];//PER POTER CREARE UN SOCKET CHE INVII UN MESSAGGIOA TUTTI
int [] porta=new int[utenti.size()];//GLI UTENTI DOVRO' PASSARLI L'IP E LA PORTA DI OGNUNO
String temp_IP="";					//SOTTOFORMA DI ARRAY
int temp_porta;

for(int i=0;i<utenti.size();i++){//INCOMINCIO ESTRAENDO TUTTI GLI UTENTI CHE HO IN MEMORIA
String dati_utente=(String)utenti.elementAt(i);//OPERAZIONE DI CAST, IL VETTORE MEMORIZZA
									//OGGETTI, SE L'OGETTO E' UNA STRINGA LO DEVO CONVERTIRE
int n=0;
int o=0;

		for(int l=0;l<dati_utente.length();l++){//ANALIZZO I SINGOLI CARATTERI DI CUI SONO
			char m=dati_utente.charAt(l);//COMPOSTE LE STRINGHE CHE INDIVIDUANO OGNI UTENTE
			if(m=='%'){	//OGNI VOLTA CHE TROVO UN CARATTERE SPECIALE NE MEMORIZZO LA POSIZIONE
				n=l;}//CHIUDO IL PRIMO IF
			if(m=='$'){
				o=l;}//CHIUDO IL SECONDO IF
		}//CHIUDO FOR ANNIDATO

/*AVENDO MEMORIZZATO LE POSIZIONI DEI CARATTERI SPECIALI POSSO CREARE DELLE SOTTOSTRINGHE CON
L'ARGOMENTO (IP E PORTA) CHE MI SARA' NECESSARIO PASSARE A INVIA_MESSAGGIO*/
	temp_IP=dati_utente.substring(n+1, o);
	temp_porta=Integer.parseInt(dati_utente.substring(o+1,dati_utente.length()));

	IP [i]=temp_IP;//RIEMPIO GLI ARRAY CON LE SOTTOSTRINGHE
	porta [i]=temp_porta;
}//CHIUDO FOR ESTERNO

IM=new Invia_Messaggio(comando,messaggio_utente,IP,porta,utenti.size());//ISTANZIO INVIA_MESSAGGIO E
}//CHIUDO L'IF CHE CONTROLLA L'ESISTENZA DELL' UTENTE
}//CHIUDO MESSAGGIO_UTENTE										GLI PASSO TUTTO IL NECESSARIO


/*METODO CHE APRE UNA CONNESSIONE PRIVATA TRA DUE UTENTI, E' IMPOSSIBILE CHE GLI UTENTI NON
ESISTANO, QUINDI INVIO A OGNIUNO DI LORO UN NUMERO DI PORTA NUOVO SUL QUALE METTERSI IN ASCOLTO
E L'INDIRIZZO E LA PORTA DELL'ALTRO(IL NUVO NUMERO DI PORTA E GENERATO IN MANIERA CASUALE)*/
void privato(String autore,String messaggio_utente){
boolean Y=false;
for(int i=0;i<utenti.size();i++){
	String dati_utente=(String)utenti.elementAt(i);
	if(autore.equals(dati_utente)){Y=true;}}
if(Y){
boolean ok=false;
boolean okk=false;
String comando="%privato%";
String autore_nome="";
String ricevente_nome=messaggio_utente;
String []IP_autore;
String IP="";
int [] porta_autore;
int porta=0;
String messaggio_autore="";
String messaggio_ricevente="";
System.out.println("");
System.out.println("richiesta di chat privata");
System.out.println("da "+autore);
System.out.println("per "+messaggio_utente);
int n=0;
int o=0;
for(int i=0;i<autore.length();i++){
	char m=autore.charAt(i);
	if(m=='%'){n=i;}//CHIUDO IL PRIMO IF
	if(m=='$'){o=i;}//CHIUDO IL SECONDO IF
}
autore_nome=autore.substring(0,n);
IP_autore= new String[]{autore.substring(n+1, o)};
porta_autore=new int[]{Integer.parseInt(autore.substring(o+1,autore.length()))};

for(int i=0;i<utenti.size();i++){
String temp_nome_ricevente="";
String dati_utente=(String)utenti.elementAt(i);
boolean ws=false;
for(int l=0;l<dati_utente.length();l++){
			char m=dati_utente.charAt(l);
			if(m=='%'){n=l;}
			if(m=='$'){o=l;}
				temp_nome_ricevente=dati_utente.substring(0,l);
				if(ricevente_nome.equals(temp_nome_ricevente)){ws=true;}}
				if(ws){
				IP=dati_utente.substring(n+1, o);
				porta=Integer.parseInt(dati_utente.substring(o+1,dati_utente.length()));
				}
}
while (!(ok)){
                n = ((int) (Math.random() * 65000));
				if(n>1024){ok=true;}
					}
while (!(okk)){
                o = ((int) (Math.random() * 65000));
				if(o>1024){okk=true;}
					}

String []IP_ricevente={IP};
int [] porta_ricevente={porta};

messaggio_autore=autore_nome+'�'+n+'$'+ricevente_nome+'%'+IP_ricevente[0]+'^'+o;
messaggio_ricevente=ricevente_nome+'�'+o+'$'+autore_nome+'%'+IP_autore[0]+'^'+n;
IM=new Invia_Messaggio(comando,messaggio_autore,IP_autore,porta_autore,IP_autore.length);//ISTANZIO INVIA_MESSAGGIO E
IM=new Invia_Messaggio(comando,messaggio_ricevente,IP_ricevente,porta_ricevente,IP_ricevente.length);//ISTANZIO INVIA_MESSAGGIO E

}//CHIUDO IF
}//CHIUDO PRIVATO

}//CHIUDE LA CLASSE