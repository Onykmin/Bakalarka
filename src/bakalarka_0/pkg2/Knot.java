
package bakalarka_0.pkg2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Knot {
    Pendulum x; //kyvadlo pro x souřadnici
    Pendulum y; //kyvadlo pro y souřadnici
    Pendulum[] z= new Pendulum[2]; //kyvadla pro z souřadnici
    ArrayList<Double> time=new ArrayList<>(); //List pro vygenerované časy
    
    //pořadí bodů je závislé od vstupní proměnné(času - seřazený vzestupně)
    ArrayList<Point3d> odd=new ArrayList<>(); //List pro "liché" body
    ArrayList<Point3d> even=new ArrayList<>(); //List pro "sudé" body
    
    String DT=""; //DT kód jako jeden řetězec znaků
    double alpha; //vypočítená z parametrů, později dosazované dle vzorečků
    
    //kontruktory
    public Knot(){ // - bezparametrický pro testování
        this.x=(new Pendulum(2,0));
        this.y=(new Pendulum(3,Math.PI/4));
        this.z[0]=(new Pendulum(2,Math.PI/2));
        this.z[1]=(new Pendulum(1,Math.PI/4));
        init();
        //uprava
    }
    public Knot(Pendulum x,Pendulum y,Pendulum z1,Pendulum z2){ // - 4 vstupní parametry(kyvadla)
        this.x=x;
        this.y=y;
        this.z=new Pendulum[]{z1,z2};
        init();
        
    } 
    public Knot(int f1,int p1,int f2,int p2,int f3,int p3,int f4, int p4){ // - 8 parametrů(4 páry) každá dvojice pro jedno kyvadlo
        this.x=new Pendulum(f1,p1);
        this.y=new Pendulum(f2,p2);
        this.z[0]=new Pendulum(f3,p3);
        this.z[1]=new Pendulum(f4,p4);
        init();
    }
    //konstruktory
    
    public void init(){ //inicializace - při vytvoření nového uzlu se vždy volá tato fce, vypočítá se alpha a pokud je nenulové vygeneruje se čas, body a nakonec se určí DT kód
        this.alpha=-1*(y.p*x.f)/Math.PI;
        if(this.alpha!=0){
            this.genTimeA(alpha);
            this.genPointsA();
            genDTA();
        }else{
            //work in progress
        }
    }
    
    public void genTimeA(double alpha){ //generování které jsou v průsečíku
        for(int l=0;l<2*(x.f*y.f);l++){
            if((l%x.f!=0)){ //filtrování nežádoucích časů
                this.time.add(((l+alpha)*Math.PI)/((x.f*y.f))); //přidání času do Listu
            }
            if((l%y.f!=0)){ //filtrování nežádoucích časů
                this.time.add(((l)*Math.PI)/((x.f*y.f))); //přidání času do Listu
            }
        }
        Collections.sort(this.time); //po generování se čas vzestupně setřídí
    }
    
    public void genTime0(){ //work in progress
        for(int l=0;l<2*(x.f*y.f);l++){
            if(!((alpha==0 && l%y.f==0) || (alpha!=0 && l%x.f==0))){
                
            }
        }
    }
    
    public void genPointsA(){ //generátor bodů - rovnou je třídi sudý/lichý
        for(int i=0;i<time.size();i++){
            if((i+1)%2==1){
                this.odd.add(getPoint(this.time.get(i), i+1));
            }else{
                this.even.add(getPoint(this.time.get(i),i+1));
            }
        }
    }
    
    public String genDTA(){ //generování DT kódu
        for(int i=0;i<odd.size();i++){
            for(int j=0;j<even.size();j++){
                if(round(odd.get(i).x,12)==round(even.get(j).x,12) && round(odd.get(i).y,12)==round(even.get(j).y,12)){ //podmínka pro zjistění rovnosti souřadnic x, y
                    if(round(odd.get(i).z,12)!=round(even.get(j).z,12)){ //kontrola zda se nejedná o ten samý bod (uzel by protínal sám sebe)
                        if(odd.get(i).z>even.get(j).z){ //zjišťování který bod je nad a který pod
                            DT+=" "+even.get(j).id; //přidání ID bodu do DT kódu
                        }else{
                            DT+=" "+even.get(j).id*(-1); //přidání ID bodu do DT kódu
                        }
                        even.remove(even.get(j)); //odstranění bodu
                    }else{
                        try {
                            throw new Exception("Prusecik v uzlu"); //vyhození výjimky když uzel protíná sám sebe
                        } catch (Exception ex) {
                            Logger.getLogger(Knot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        return this.DT; //vrácení DT kódu - je i uložení v globální proměnné pro možné pozdější využití
    }
    
    public double round(double d,int place) { //zaokrouhlování
        return (double)Math.round(d*Math.pow(10, place))/(10^place);
      }
    
    public Point3d getPoint(double t,int id){ //vrací bod - vstupní parametry čas a ID bodu
        return new Point3d(x.getValue(t),y.getValue(t),z[0].getValue(t)+z[1].getValue(t),t,id);
    }
    
    public int crossNumber(){ //získání počtu průsečíků
        return 2*x.f*y.f-x.f-y.f;
    }
    
    public String getNSCode(){ //vrací text ve formátu pro Knotscape
        return crossNumber()+" 1 "+this.DT;
    }
    
    public String[] toStringArray(){ //převede konkrétní uzel na pole řetezců(parametrů)
        return new String[]{""+this.x.f,""+this.x.p,""+this.y.f,""+this.y.p,""+this.z[0].f,""+this.z[0].p,""+this.z[1].f,""+this.z[1].p,this.DT};
    }
    
    @Override
    public String toString(){ //převede konkrétní uzel na jeden textový řetězec
        return ""+this.x.f+" "+this.x.p+" "+this.y.f+" "+this.y.p+" "+this.z[0].f+" "+this.z[0].p+" "+this.z[1].f+" "+this.z[1].p+" "+this.DT;
    }
}
