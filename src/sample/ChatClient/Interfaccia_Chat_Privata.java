package sample.ChatClient;

import javax.swing.*;
import java.awt.event.*;


class Interfaccia_Chat_Privata extends JFrame implements ActionListener{

    JTextArea lettura = new JTextArea();
    JTextArea scrittura = new JTextArea();
    JButton esci = new JButton("ESCI");
    JButton invia = new JButton("INVIA");
    JPanel pan=new JPanel();
    JScrollPane scrollettura;
    JScrollPane scrolscrittura;
    JScrollBar SB=new JScrollBar();
    JLabel L1 = new JLabel();
    JLabel L2 = new JLabel();
    Leggi L=new Leggi(lettura,SB);
    Ascolta_Chat_Privata ACP;
    Invia_Messaggio_Privato IMP;
    String nick;
    String ricevente;
    String IP;
    int mia_porta;
    int sua_porta;

    public Interfaccia_Chat_Privata(String mie_info){
        super("Comunicazione privata...");
        /*ECCO DOVE ESTRAE GLI ARGOMENTI CHE GLI NECESSITANO PER POTER FUNZIONARE*/
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        for(int i=0;i<mie_info.length();i++){
            char x=mie_info.charAt(i);
            if(x=='�'){
                int i1 = a = i;
            }
            if(x=='$'){
                int i2 = b= i;
            }
            if(x=='%'){
                final int i1 = c = i;
            }
            if(x=='^'){
                d = i;
            }
        }
        nick=mie_info.substring(0,a);
        mia_porta=Integer.parseInt(mie_info.substring(a+1,b));
        ricevente=mie_info.substring(b+1,c);
        IP=mie_info.substring(c+1,d);
        sua_porta=Integer.parseInt(mie_info.substring(d+1,mie_info.length()));


        lettura.setBounds(10,40,300,85);
        lettura.setEditable(false);
        lettura.setLineWrap(true);
        scrollettura = new JScrollPane(lettura);
        scrollettura.setBounds(10,40,300,85);
        scrollettura.setVerticalScrollBar(SB);


        scrittura.addKeyListener(new MyKeyListener());
        scrittura.setBounds(10,175,300,20);
        scrittura.setLineWrap(true);
        scrolscrittura = new JScrollPane(scrittura);
        scrolscrittura.setBounds(10,175,300,20);

        esci.setBounds(100,140,100,25);
        esci.setActionCommand("ESCI");
        esci.addActionListener(this);

        invia.setBounds(210,140,100,25);
        invia.setActionCommand("INVIA");
        invia.addActionListener(this);

        L1.setBounds(10,10,150,25);
        L1.setText("...con "+ricevente);

        L2.setBounds(10,140,100,25);
        L2.setText(nick+" :");

        pan.add(scrolscrittura);
        pan.add(scrollettura);
        pan.add(esci);
        pan.add(invia);
        pan.add(L1);
        pan.add(L2);


        setContentPane(pan);
        pan.setLayout(null);

        setBounds(450,10,330,230);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        show();
        addWindowListener(new WindowAdapter(){
                              public void windowClosing(WindowEvent e){
                                  String messaggio="<SERVER ADMINISTRATOR> :"+nick+" � uscito dalla comunicazione privata.";
                                  IMP=new Invia_Messaggio_Privato(L,messaggio,IP,sua_porta);
                                  ACP.spegni();
                                  setVisible(false);
                              }
                          }
        );
        ACP=new Ascolta_Chat_Privata(L,mia_porta);
        ACP.start();
    }

    public void actionPerformed(ActionEvent evt){

        String command = evt.getActionCommand();

        if (command.equals("ESCI")){
            String messaggio="<SERVER ADMINISTRATOR> :"+nick+" � uscito dalla comunicazione privata.";
            IMP=new Invia_Messaggio_Privato(L,messaggio,IP,sua_porta);
            ACP.spegni();
            setVisible(false);
        }//chiude ESCI

        if (command.equals("INVIA")){
            String messaggio="";
            messaggio="<"+nick+"> :"+scrittura.getText();
            IMP=new Invia_Messaggio_Privato(L,messaggio,IP,sua_porta);
            scrittura.setText("");
            scrittura.grabFocus();
        }//chiude INVIA
    }//CHIUDE ACTION PERFORMED

    class MyKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent evt){
            if (evt.getKeyChar()==KeyEvent.VK_ENTER){
                invia.doClick();
            }
        } // FINE METODO keyPressed

        public void keyTyped(KeyEvent evt){
            if (evt.getKeyChar()==KeyEvent.VK_ENTER){
                scrittura.setText("");
                scrittura.grabFocus();
            }//CHIUDO IF

/*INABILITO LA PRESSIONE DEL CARATTERE % D'ORA IN POI L'APPLICAZIONE LO RICONOSCERA' COME SPECIALE
		PER USI INTERNI ALLA STESSA*/

            char a=evt.getKeyChar();
            int b=a;
            if (b==37||b==36||b==163||b==94){
                evt.setKeyChar((char)KeyEvent.VK_CANCEL);}
        }// CHIUDO keyTyped

    } // FINE INNER CLASS MyKeyListener*/

}//CHIUDE LA CLASSE GRAFICA