
package bakalarka_0.pkg2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class File_access {
    private BufferedWriter bw;
    private boolean opened=false;
    public File_access(){
    }
    public void CreateFile(){
        String path = "C:/cygwin64/home/knotscap-master/tmp/knots.txt";
        File customDir = new File(path);
        if (customDir.exists()) {
            customDir.delete();
            CreateFile();
        } else {
            try {
                customDir.createNewFile();
            } catch (Exception e) {
                System.out.println("Chyba při vytváření souboru");
            }
        }
    }
    public void saveDT(String DT){
        try {
            if(!this.opened){
                bw=new BufferedWriter(new FileWriter("C:/cygwin64/home/knotscap-master/tmp/knots.txt",true));
                this.opened=true;
            }
            bw.write(DT);
            bw.newLine();
        } catch (Exception e) {
            System.out.println("Chyba pri zapisovani do/nacteni souboru");
        }
    }
    public void closeBW(){
        if(this.opened){
            try {
                bw.close();
            } catch (IOException ex) {
                System.out.println("Chyba pri zavirani bufferedwriteru");
            }
        }
    }
}
