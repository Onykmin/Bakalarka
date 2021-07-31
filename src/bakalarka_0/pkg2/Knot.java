package bakalarka_0.pkg2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Knot {
    Pendulum x; //kyvadlo pro x souřadnici
    Pendulum y; //kyvadlo pro y souřadnici
    Pendulum[] z= new Pendulum[2]; //kyvadla pro z souřadnici
    ArrayList<Double> time1 = new ArrayList<>();
    ArrayList<Double> time2 = new ArrayList<>();
    ArrayList<Double> times=new ArrayList<>();
    //pořadí bodů je závislé od vstupní proměnné(času - seřazený vzestupně)
    String DT=""; //DT kód jako jeden řetězec znaků
    //kontruktory
    public Knot(){ // - bezparametrický pro testování
        this.x=(new Pendulum(2,0));
        this.y=(new Pendulum(3,Math.PI/4));
        this.z[0]=(new Pendulum(2,Math.PI/2));
        this.z[1]=(new Pendulum(1,Math.PI/4));
        init();
        System.out.println(DT);
    }
    
    public Knot(Pendulum x,Pendulum y,Pendulum z1,Pendulum z2){ // - 4 vstupní parametry(kyvadla)
        this.x=x;
        this.y=y;
        this.z=new Pendulum[]{z1,z2};
        init();
        
    } 
    
    public Knot(int f1,double p1,int f2,double p2,int f3,double p3,int f4, double p4){ // - 8 parametrů(4 páry) každá dvojice pro jedno kyvadlo
        this.x=new Pendulum(f1,p1);
        this.y=new Pendulum(f2,p2);
        this.z[0]=new Pendulum(f3,p3);
        this.z[1]=new Pendulum(f4,p4);
        init();
    }
    //konstruktory
    
    public void init(){ //inicializace - při vytvoření nového uzlu se vždy volá tato fce, vypočítá se alpha a pokud je nenulové vygeneruje se čas, body a nakonec se určí DT kód
        findPairs();
    }
    
    public void findPairs(){
        findPair1();
        findPair2();
        Collections.sort(times);
        generatePairDT();
    }
    
    public void findPair1(){
        for(int k=1;k<x.f;k++){
            double k1 = y.f*k;
            double a = (double)(k1/x.f);
            double b = (double)y.p/Math.PI;
            System.out.println(k1+" "+x.f);
            System.out.println(a+" "+b);
            int bottom = (int)Math.floor(1.0+(a+b));
            int top = (int)Math.floor(2*y.f-a+b);
            System.out.println(bottom+" "+top);
            for(int j=bottom;j<=top;j++){
                
                double k2 = ((double)k/x.f);
                double j1 = ((double)j/y.f);
                double om = (y.p/y.f);
                
                double t1 =(((-k2)+j1)*Math.PI + om);
                double t2 =(((k2)+j1)*Math.PI + om);
                
                this.times.add(t1);
                this.times.add(t2);
                this.time1.add(t1);
                this.time2.add(t2);
            }
        }
    }
    
    public void findPair2(){
        for(int k=1;k<y.f;k++){
            double k1 = x.f*k;
            double a = (double)(k1/y.f);
            double b = (double)x.p/Math.PI;
            int bottom = (int)Math.floor(1.0+(a+b));
            int top = (int)Math.floor(2*x.f-a+b);
            for(int j=bottom;j<=top;j++){
                
                double k2 = ((double)k/y.f);
                double j1 = ((double)j/x.f);
                double om = (x.p/x.f);
                
                double t1 =(((-k2)+j1)*Math.PI + om);
                double t2 =(((k2)+j1)*Math.PI + om);
                
                this.times.add(t1);
                this.times.add(t2);
                this.time1.add(t1);
                this.time2.add(t2);
            }
        }
    }
    
    public void generatePairDT(){
        for(int i=0;i<times.size();i+=2){
            if(time1.contains(times.get(i))){
                if(getZ(times.get(i))>getZ(time2.get(time1.indexOf(times.get(i))))){
                    DT+=" "+(times.indexOf(time2.get(time1.indexOf(times.get(i))))+1);
                }else{
                    DT+=" "+(times.indexOf(time2.get(time1.indexOf(times.get(i))))+1)*(-1);
                }
            }else{
                if(getZ(times.get(i))>getZ(time1.get(time2.indexOf(times.get(i))))){
                    DT+=" "+(times.indexOf(time1.get(time2.indexOf(times.get(i))))+1);
                }else{
                    DT+=" "+(times.indexOf(time1.get(time2.indexOf(times.get(i))))+1)*(-1);
                }
            }
        }
    }
    
    public boolean compare(BigDecimal t1, BigDecimal t2){
        boolean big = false;
        if(t1.signum()==1 && t2.signum()==1){
            if(t1.subtract(t2).signum()==1){
                big=true;
            }else{
                big=false;
            }
        }else if(t1.signum()==-1 && t2.signum()==-1){
            if(t1.negate().subtract(t2.negate()).signum()==1){
                big=false;
            }else{
                big=true;
            }
        }else if(t1.signum()==1 && (t2.signum()!=1)){
            big=true;
        }else if(t1.signum()==-1 && (t2.signum()!=-1)){
            big=false;
        }else if(t1.signum()==0 && t2.signum()==1){
            big=false;
        }else if(t1.signum()==0 && t2.signum()==-1){
            big=true;
        }else{
            try {throw new Exception("CHYBA");} 
            catch (Exception ex){Logger.getLogger(Knot.class.getName()).log(Level.SEVERE, null, ex);}
        }
        return big;
    }
    
    public Point3d getPoint(double t,int id){ //vrací bod - vstupní parametry čas a ID bodu
        return new Point3d(x.getValue(t),y.getValue(t),z[0].getValue(t)+z[1].getValue(t),t,id);
    }
    
    public double getZ(double t){
        return (z[0].getValue(t)+z[1].getValue(t));
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
