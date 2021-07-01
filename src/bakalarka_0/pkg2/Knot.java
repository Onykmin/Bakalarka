
package bakalarka_0.pkg2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Knot {
    Pendulum x;
    Pendulum y;
    Pendulum[] z= new Pendulum[2];
    ArrayList<Double> time=new ArrayList<>();
    ArrayList<Point3d> odd=new ArrayList<>();
    ArrayList<Point3d> even=new ArrayList<>();
    String DT="";
    double alpha;
    public Knot(){
        this.x=(new Pendulum(2,0));
        this.y=(new Pendulum(3,Math.PI/4));
        this.z[0]=(new Pendulum(2,Math.PI/2));
        this.z[1]=(new Pendulum(1,Math.PI/4));
        init();
    }
    
    public Knot(Pendulum x,Pendulum y,Pendulum z1,Pendulum z2){
        this.x=x;
        this.y=y;
        this.z=new Pendulum[]{z1,z2};
        init();
        
    }
    
    public Knot(int f1,int p1,int f2,int p2,int f3,int p3,int f4, int p4){
        this.x=new Pendulum(f1,p1);
        this.y=new Pendulum(f2,p2);
        this.z[0]=new Pendulum(f3,p3);
        this.z[1]=new Pendulum(f4,p4);
        init();
    }
    
    public void init(){
        this.alpha=-1*(y.p*x.f)/Math.PI;
        if(this.alpha!=0){
            this.genTimeA(alpha);
            this.genPointsA();
            genDTA();
        }
    }
    
    public void genTimeA(double alpha){
        for(int l=0;l<2*(x.f*y.f);l++){
            if((l%x.f!=0)){
                this.time.add(((l+alpha)*Math.PI)/((x.f*y.f)));
            }
            if((l%y.f!=0)){
                this.time.add(((l)*Math.PI)/((x.f*y.f)));
            }
        }
        Collections.sort(this.time);
    }
    
    public void genTime0(){
        for(int l=0;l<2*(x.f*y.f);l++){
            if(!((alpha==0 && l%y.f==0) || (alpha!=0 && l%x.f==0))){
                
            }
        }
    }
    
    public void genPointsA(){
        for(int i=0;i<time.size();i++){
            if((i+1)%2==1){
                this.odd.add(getPoint(this.time.get(i), i+1));
            }else{
                this.even.add(getPoint(this.time.get(i),i+1));
            }
        }
    }
    
    public String genDTA(){
        for(int i=0;i<odd.size();i++){
            for(int j=0;j<even.size();j++){
                if(round(odd.get(i).x,12)==round(even.get(j).x,12) && round(odd.get(i).y,12)==round(even.get(j).y,12)){
                    if(round(odd.get(i).z,12)!=round(even.get(j).z,12)){
                        if(odd.get(i).z>even.get(j).z){
                            DT+=" "+even.get(j).id*(-1);
                        }else{
                            DT+=" "+even.get(j).id;
                        }
                        even.remove(even.get(j));
                    }else{
                        try {throw new Exception("Prusecik uzlu");} catch (Exception ex) {
                            Logger.getLogger(Knot.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        return this.DT;
    }
    
    public double round(double d,int place) {
        return (double)Math.round(d*Math.pow(10, place))/(10^place);
      }
    
    public Point3d getPoint(double t,int id){
        return new Point3d(x.getValue(t),y.getValue(t),z[0].getValue(t)+z[1].getValue(t),t,id);
    }
    
    public int crossNumber(){
        return 2*x.f*y.f-x.f-y.f;
    }
    
    public String getNSCode(){
        return crossNumber()+" 1 "+this.DT;
    }
    
    public String[] toStringArray(){
        return new String[]{""+this.x.f,""+this.x.p,""+this.y.f,""+this.y.p,""+this.z[0].f,""+this.z[0].p,""+this.z[1].f,""+this.z[1].p,this.DT};
    }
    
    @Override
    public String toString(){
        return ""+this.x.f+" "+this.x.p+" "+this.y.f+" "+this.y.p+" "+this.z[0].f+" "+this.z[0].p+" "+this.z[1].f+" "+this.z[1].p+" "+this.DT;
    }
}
