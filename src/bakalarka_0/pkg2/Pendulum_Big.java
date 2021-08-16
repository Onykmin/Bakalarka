package bakalarka_0.pkg2;

import ch.obermuhlner.math.big.BigDecimalMath;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Pendulum_Big {
    int f;
    BigDecimal p;
    public Pendulum_Big(int f, BigDecimal p){//při vytváření kyvadla přijímám základní 2 parametry
        this.f=f;
        this.p=p;
    }
    
    public BigDecimal getValue(BigDecimal t){
        BigDecimal out=BigDecimal.ZERO;
        out = BigDecimalMath.cos(t.multiply(BigDecimal.valueOf(f)).add(p), new MathContext(100, RoundingMode.HALF_EVEN));//vypočtu hodnotu pro dané t a vracím ji(využíváno při generování bodu)
        return out;
    }

}
