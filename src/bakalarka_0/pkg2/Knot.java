package bakalarka_0.pkg2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Knot {
    Pendulum x; //kyvadlo pro x souřadnici
    Pendulum y; //kyvadlo pro y souřadnici
    Pendulum[] z= new Pendulum[2]; //kyvadla pro z souřadnici
    ArrayList<BigDecimal> time1 = new ArrayList<>();
    ArrayList<BigDecimal> time2 = new ArrayList<>();
    ArrayList<BigDecimal> times=new ArrayList<>();
    //pořadí bodů je závislé od vstupní proměnné(času - seřazený vzestupně)
    BigDecimal PI = new BigDecimal("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679");
    String DT=""; //DT kód jako jeden řetězec znaků
    
    //kontruktory
    public Knot(){ // - bezparametrický pro testování
        this.x=(new Pendulum(2,BigDecimal.ZERO));
        this.y=(new Pendulum(3,PI.divide(BigDecimal.valueOf(4))));
        this.z[0]=(new Pendulum(2,PI.divide(BigDecimal.valueOf(2))));
        this.z[1]=(new Pendulum(1,PI.divide(BigDecimal.valueOf(4))));
        init();
    }
    
    public Knot(Pendulum x,Pendulum y,Pendulum z1,Pendulum z2){ // - 4 vstupní parametry(kyvadla)
        this.x=x;
        this.y=y;
        this.z=new Pendulum[]{z1,z2};
        init();
        
    } 
    
    public Knot(int f1,BigDecimal p1,int f2,BigDecimal p2,int f3,BigDecimal p3,int f4, BigDecimal p4){ // - 8 parametrů(4 páry) každá dvojice pro jedno kyvadlo
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
            BigDecimal a = BigDecimal.valueOf(k1).divide(BigDecimal.valueOf(x.f),100,RoundingMode.FLOOR);
            BigDecimal b = y.p.divide(this.PI,100,RoundingMode.FLOOR);
            int bottom = 1+((a.add(b)).setScale(0, RoundingMode.FLOOR)).intValue();
            int top = BigDecimal.valueOf(2*y.f).subtract(a).add(b).setScale(0, RoundingMode.FLOOR).intValue();
            for(int j=bottom;j<=top;j++){
                
                BigDecimal k2 = BigDecimal.valueOf(k).divide(BigDecimal.valueOf(x.f),100,RoundingMode.FLOOR);
                BigDecimal j1 = BigDecimal.valueOf(j).divide(BigDecimal.valueOf(y.f),100,RoundingMode.FLOOR);
                BigDecimal om = y.p.divide(BigDecimal.valueOf(y.f),100,RoundingMode.FLOOR);
                
                BigDecimal t1 = k2.negate().add(j1).multiply(PI).subtract(om);
                BigDecimal t2 = k2.add(j1).multiply(PI).subtract(om);
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
            BigDecimal a = BigDecimal.valueOf(k1).divide(BigDecimal.valueOf(y.f),100,RoundingMode.FLOOR);
            BigDecimal b = x.p.divide(this.PI,100,RoundingMode.FLOOR);
            int bottom = 1+((BigDecimal.valueOf(k1).divide(BigDecimal.valueOf(y.f),100,RoundingMode.FLOOR).add(b)).setScale(0, RoundingMode.FLOOR)).intValue();
            int top = BigDecimal.valueOf(2*x.f).subtract(a).add(b).setScale(0, RoundingMode.FLOOR).intValue();
            for(int j=bottom;j<=top;j++){
                
                BigDecimal k2 = BigDecimal.valueOf(k).divide(BigDecimal.valueOf(y.f),100,RoundingMode.FLOOR);
                BigDecimal j1 = BigDecimal.valueOf(j).divide(BigDecimal.valueOf(x.f),100,RoundingMode.FLOOR);
                BigDecimal om = x.p.divide(BigDecimal.valueOf(x.f),100,RoundingMode.FLOOR);
                
                BigDecimal t1 = k2.negate().add(j1).multiply(PI).subtract(om);
                BigDecimal t2 = k2.add(j1).multiply(PI).subtract(om);
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
                if(getZ(times.get(i)).compareTo(getZ(time2.get(time1.indexOf(times.get(i)))))==1){
                    DT+=" "+(times.indexOf(time2.get(time1.indexOf(times.get(i))))+1);
                }else{
                    DT+=" "+(times.indexOf(time2.get(time1.indexOf(times.get(i))))+1)*(-1);
                }
            }else{
                if(getZ(times.get(i)).compareTo(getZ(time1.get(time2.indexOf(times.get(i)))))==1){
                    DT+=" "+(times.indexOf(time1.get(time2.indexOf(times.get(i))))+1);
                }else{
                    DT+=" "+(times.indexOf(time1.get(time2.indexOf(times.get(i))))+1)*(-1);
                }
            }
        }
    }
    
    public BigDecimal round(BigDecimal d,int place) { //zaokrouhlování
        return d.setScale(place, RoundingMode.HALF_UP);
      }
    
    public Point3d getPoint(BigDecimal t,int id){ //vrací bod - vstupní parametry čas a ID bodu
        return new Point3d(x.getValue(t),y.getValue(t),z[0].getValue(t).add(z[1].getValue(t)),t,id);
    }
    
    public BigDecimal getZ(BigDecimal t){
        return z[0].getValue(t).add(z[1].getValue(t));
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
