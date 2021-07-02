package bakalarka_0.pkg2;

//import java.math.BigDecimal;

public class Pendulum {
    int f;
    double p;
    public Pendulum(int f, double p){//při vytváření kyvadla přijímám základní 2 parametry
        this.f=f;
        this.p=p;
    }
    
    public double getValue(double t){
        double out=0;
        out=Math.cos(t*f+p); //vypočtu hodnotu pro dané t a vracím ji(využíváno při generování bodu)
        return out;
    }
    
//    private static final int SCALE = 30;
//    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
//
//    public static BigDecimal cosine(BigDecimal x) {
//
//        BigDecimal currentValue = BigDecimal.ONE;
//        BigDecimal lastVal = currentValue.add(BigDecimal.ONE);
//        BigDecimal xSquared = x.multiply(x);
//        BigDecimal numerator = BigDecimal.ONE;
//        BigDecimal denominator = BigDecimal.ONE;
//        int i = 0;
//
//        while (lastVal.compareTo(currentValue) != 0) {
//            lastVal = currentValue;
//
//            int z = 2 * i + 2;
//
//            denominator = denominator.multiply(BigDecimal.valueOf(z));
//            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
//            numerator = numerator.multiply(xSquared);
//
//            BigDecimal term = numerator.divide(denominator, SCALE + 5,
//                    ROUNDING_MODE);
//
//            if (i % 2 != 0) {
//                currentValue = currentValue.add(term);
//            } else {
//                currentValue = currentValue.subtract(term);
//            }
//            i++;
//        }
//
//        return currentValue;
//    }

}
