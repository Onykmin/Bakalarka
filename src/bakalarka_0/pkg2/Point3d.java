
package bakalarka_0.pkg2;

import java.math.BigDecimal;

public class Point3d {
    double x;
    double y;
    double z;
    double t;
    int id;
    //vlastní třída pro 3D bod - drží si hlavní parametry (x,y,z), čas a ID 
    public Point3d(double x, double y, double z,double t,int id){
        this.x=x;
        this.y=y;
        this.z=z;
        this.t=t;
        this.id=id;
    }
    @Override
    public String toString(){
        return "x:"+x+" y:"+y+" z:"+z+" t:"+t+" id:"+id;
    }
}
