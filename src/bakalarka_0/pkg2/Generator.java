
package bakalarka_0.pkg2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {
    ArrayList<Knot> good = new ArrayList();
    File_access file = new File_access();
    public Generator(){
        generate();
    }
    public void generate(){
        file.CreateFile();
        Knot k;
        for(int i=0;i<1;i++){
            k = new Knot();
            if(k.err){
                Knot_Big kb = new Knot_Big(k.x, k.y, k.z);
                k.DT=kb.DT;
                System.out.println("chybny uzel ++");
            }
            good.add(k);
            file.saveDT(k.getNSCode());
        }
        file.closeBW();
        System.out.println("KNOTSCAPE");
        evaluate();
    }
    public void evaluate(){ 
        //funkce pro zapnutí knotscapu - jakmile je knotscape hotov vypíše "Ok". Tato zpráva se zachytí pokračuje se dále, pokud je přijatá zpráva rozdílná
        //vyhodí se výjimka
        try{
            Process p = Runtime.getRuntime().exec(new String[]{"C:/cygwin64/bin/bash.exe","-c", "tclsh /home/knotscap-master/tcl/knotscape_test.tcl"},new String[]{"PATH=/cygdrive/c/cygwin64/bin"});
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String output = br.readLine();
            if(output.equalsIgnoreCase("Ok")){
                //work in progress
            }else{
                throw new Exception("Chyba pri hledani uzlu");
            }
        }catch(IOException e){
            System.out.println("Chyba pri ziskavani jmena uzlu");
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
