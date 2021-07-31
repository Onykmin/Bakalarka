package bakalarka_0.pkg2;

public class Pendulum {
    int f;
    double p;
    public Pendulum(int f, double p){//při vytváření kyvadla přijímám základní 2 parametry
        this.f=f;
        this.p=p;
    }
    
    public double getValue(double t){
        double out=0;
        out = Math.cos(t*f+p);//vypočtu hodnotu pro dané t a vracím ji(využíváno při generování bodu)
        return out;
    }

}
