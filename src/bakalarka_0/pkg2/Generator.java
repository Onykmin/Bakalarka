
package bakalarka_0.pkg2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Generator {
    
    public Generator(){
        Knot k = new Knot();
        //evaluate("5 1 4 8 10 2 6");
        //System.out.println(k.getNSCode());
    }
    public void evaluate(String c){
        
        try{
        Process p = Runtime.getRuntime().exec(new String[]{"C:/cygwin64/bin/bash.exe","-c", "tclsh /home/perve/knotscap-master/tcl/knotscape_test.tcl"},new String[]{"PATH=/cygdrive/c/cygwin64/bin"});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String name = br.readLine();
        System.out.println(name);
        }catch(IOException e){
            System.out.println("Chyba pri ziskavani jmena uzlu");
        }
        
    }
}
