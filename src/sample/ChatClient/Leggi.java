package sample.ChatClient;

import javax.swing.*;

class Leggi{
	JTextArea mia_lettura;
	JScrollBar mia_sb;

public Leggi(JTextArea lettura_interfaccia,JScrollBar SB_interfaccia) {
	mia_lettura=lettura_interfaccia;
	mia_sb=SB_interfaccia;
}


public synchronized void scrivo_su_area_testo(String r){
	mia_lettura.append(r+"\n");
	int a=mia_sb.getMaximum();
	mia_sb.setValue(a);
}
}