
package bakalarka_0.pkg2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {
    
    public Generator(){
        Knot k = new Knot();
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
