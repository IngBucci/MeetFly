package sample.ChatServer;
import sample.ChatClient.Che_Hai_Detto;
import java.io.*;
import java.util.ArrayList;

public class IOsms{

    private static String messaggio;

    public void initSms(String messaggio){
    this.messaggio= messaggio;
    }


    public static void newFile(){
        String path="C:\\Users\\Antonio Natalino\\Desktop\\sms.txt";
        try{
            File file = new File(path);
            if (file.exists())
                System.out.println("Il file " + path + " esiste");
            else if (file.createNewFile())
                System.out.println("Il file " + path + " è stato creato");
            else
                System.out.println("Il file " + path + " non può essere creato");
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    public static void writeFile(ArrayList<String> messaggio) {
        String path = "C:\\Users\\Antonio Natalino\\Desktop\\sms.txt";
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            //bw.write("ciao");
            Che_Hai_Detto j;
            System.out.println(messaggio.size());
            for(int i=0;i<messaggio.size();i++){
                System.out.println(messaggio.get(i));
                bw.write(messaggio.get(i));
            }

            bw.flush();
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> messaggi = new ArrayList<>();
    static int i=0;

    public static ArrayList readFile() throws IOException {
        String path = "C:\\Users\\Antonio Natalino\\Desktop\\sms.txt";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        if(i==0){
        while((st=br.readLine())!=null){
            messaggi.add(st+"\n");
            System.out.println(st);;
            i++;}
        }return messaggi;


       /* char[] in = new char[50];
        int size = 0;
        try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            size = br.read(in);
            System.out.print("Caratteri presenti: " + size + "n");
            System.out.print("Il contenuto del file è il seguente:n");
            for(int i=0; i<size; i++) {
                System.out.print(in[i]);
                messaggi.add(String.valueOf(in[i]));
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();*/
        }
    }




