package bakalarka_0.pkg2;

import java.util.ArrayList;
import java.util.Collections;

public class Knot {
    Pendulum x; //kyvadlo pro x souřadnici
    Pendulum y; //kyvadlo pro y souřadnici
    Pendulum[] z= new Pendulum[2]; //kyvadla pro z souřadnici
    ArrayList<Double> time1 = new ArrayList<>();
    ArrayList<Double> time2 = new ArrayList<>();
    ArrayList<Double> times=new ArrayList<>();
    //pořadí bodů je závislé od vstupní proměnné(času - seřazený vzestupně)
    String DT=""; //DT kód jako jeden řetězec znaků
    boolean err = false;
    //kontruktory
    public Knot(){ // - bezparametrický pro testování
        this.x=(new Pendulum(3,0));
        this.y=(new Pendulum(7,Math.PI/6));
        this.z[0]=(new Pendulum(2,.57805));
        this.z[1]=(new Pendulum(6,4.31026));
        init();
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
        generatePairDT();
    }
    
    public void findPairs(){
        findPair1();
        findPair2();
        Collections.sort(times);
    }
    
    public void findPair1(){
        for(int k=1;k<x.f;k++){
            double k1 = y.f*k;
            double a = (double)(k1/x.f);
            double b = (double)(y.p)/(Math.PI);
            int bottom = 1+(int)Math.floor((a+b));
            int top = (int)Math.floor(2*y.f-a+b);
            if(Math.abs((int)Math.round(a+b)-(a+b))<=10e-14 || Math.abs((int)Math.round(2*y.f-a+b)-(2*y.f-a+b))<=10e-14){
                this.err=true;
//                try{
//                    throw new Exception("Podozrele blizko celemu cislu");
//                }catch(Exception e){System.out.println("Vyskytla se chyba "+e.getMessage());}
            }
            for(int j=bottom;j<=top;j++){
                
                double k2 = ((double)k/x.f);
                double j1 = ((double)j/y.f);
                double om = (y.p/y.f);
                
                double t1 =(((-k2)+j1)*Math.PI - om);
                double t2 =(((k2)+j1)*Math.PI - om);
                
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
            int bottom = 1+(int)Math.floor((a+b));
            int top = (int)Math.floor(2*x.f-a+b);
            if(Math.abs((int)Math.round(a+b)-(a+b))<=10e-14 || Math.abs((int)Math.round(2*x.f-a+b)-(2*x.f-a+b))<=10e-14){
                this.err=true;
//                try{
//                    throw new Exception("Podozrele blizko celemu cislu");
//                }catch(Exception e){System.out.println("Vyskytla se chyba "+e.getMessage());}
            }
            for(int j=bottom;j<=top;j++){
                
                double k2 = ((double)k/y.f);
                double j1 = ((double)j/x.f);
                double om = (x.p/x.f);
                
                double t1 =(((-k2)+j1)*Math.PI - om);
                double t2 =(((k2)+j1)*Math.PI - om);
                
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
