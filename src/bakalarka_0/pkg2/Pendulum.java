package bakalarka_0.pkg2;

public class Pendulum {
    int f;
    double p;
    double A=1; //amplituda
    public Pendulum(int f, double p){//při vytváření kyvadla přijímám základní 2 parametry
        this.f=f;
        this.p=p;
    }
    
    public Pendulum(int f, double p,double A){//při vytváření kyvadla přijímám základní 2 parametry
        this.f=f;
        this.p=p;
        this.A=A;
    }
    public double getValue(double t){
        double out=0;
        out = A*Math.cos(t*f+p);//vypočtu hodnotu pro dané t a vracím ji(využíváno při generování bodu)
        return out;
    }

}
