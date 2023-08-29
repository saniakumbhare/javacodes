public class PolyNodeClass {
    private int coef, exp;
    private PolyNodeClass next;

    public PolyNodeClass() {
        coef = 0;
        exp = 0;
        next = null;
    }

    public PolyNodeClass(int incoef, int inexp, PolyNodeClass innext) {
        this.coef = incoef;
        this.exp = inexp;
        this.next = innext;
    }

    public PolyNodeClass(PolyNodeClass a) {
        coef = a.coef;
        exp = a.exp;
        next = a.next;
    }

    public void setCoefficient(int newcoef) {
        coef = newcoef;
    }

    public void setExponent(int newexp) {
        exp = newexp;
    }

    public void setNext(PolyNodeClass newnext) {
        next = newnext;
    }

    public int getCoefficient() {
        return coef;
    }

    public int getExponent() {
        return exp;
    }

    public PolyNodeClass getNext() {
        return next;
    }
}
