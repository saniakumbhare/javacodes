public class PolynomialDataStrucClass implements PolynomialADT {
    private PolyNodeClass firstnode;

    public PolynomialDataStrucClass() {
        firstnode = null;
    }

    public PolynomialDataStrucClass(PolyNodeClass node) {
        firstnode = node;
    }

    public PolynomialDataStrucClass(PolynomialDataStrucClass a) {
        firstnode = a.firstnode;
    }

    public boolean isEmpty() {
        return firstnode == null;
    }

    public void setFirstNode(PolyNodeClass node) {
        firstnode = node;
    }

    public PolyNodeClass getFirstNode() {
        return firstnode;
    }

    public void addPolyNodeFirst(int incoef, int inexp) {
        if (isEmpty())
            firstnode = new PolyNodeClass(incoef, inexp, null);
        else
            firstnode = new PolyNodeClass(incoef, inexp, firstnode);
    }

    public void addPolyNodeLast(int incoef, int inexp) {
        if (isEmpty())
            firstnode = new PolyNodeClass(incoef, inexp, null);
        else {
            PolyNodeClass current = firstnode;
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(new PolyNodeClass(incoef, inexp, null));
        }
    }

    public void addPolyNode(PolyNodeClass a) {
        if (isEmpty())
            firstnode = new PolyNodeClass(a);
        else {
            PolyNodeClass current = firstnode;
            while (current.getNext() != null)
                current = current.getNext();
            current.setNext(new PolyNodeClass(a));
        }
    }

    public PolynomialDataStrucClass addPolynomials(PolynomialDataStrucClass list2, boolean descending) {
        PolynomialDataStrucClass list1 = this;
        PolynomialDataStrucClass sumlist = new PolynomialDataStrucClass();

        PolyNodeClass x = list1.getFirstNode();
        PolyNodeClass y = list2.getFirstNode();

        if (descending) {
            while (x != null || y != null) {
                PolyNodeClass z = null;

                if (x == null) {
                    z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
                    y = y.getNext();
                } else if (y == null) {
                    z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
                    x = x.getNext();
                } else if (x.getExponent() > y.getExponent()) {
                    z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
                    x = x.getNext();
                } else if (x.getExponent() < y.getExponent()) {
                    z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
                    y = y.getNext();
                } else {
                    int sumcoef = x.getCoefficient() + y.getCoefficient();
                    int sumexp = x.getExponent();
                    z = new PolyNodeClass(sumcoef, sumexp, null);
                    x = x.getNext();
                    y = y.getNext();
                }
                sumlist.addPolyNode(z);
            }
        } else {
            while (x != null || y != null) {
                PolyNodeClass z = null;

                if (x == null) {
                    z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
                    y = y.getNext();
                } else if (y == null) {
                    z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
                    x = x.getNext();
                } else if (x.getExponent() > y.getExponent()) {
                    z = new PolyNodeClass(y.getCoefficient(), y.getExponent(), null);
                    y = y.getNext();
                } else if (x.getExponent() < y.getExponent()) {
                    z = new PolyNodeClass(x.getCoefficient(), x.getExponent(), null);
                    x = x.getNext();
                } else {
                    int sumcoef = x.getCoefficient() + y.getCoefficient();
                    int sumexp = x.getExponent();
                    z = new PolyNodeClass(sumcoef, sumexp, null);
                    x = x.getNext();
                    y = y.getNext();
                }

                sumlist.addPolyNode(z);
            }
        }
        return sumlist;
    }

    public String toString() {
        if (isEmpty())
            return "empty.";
        else {
            String s = "";
            PolyNodeClass current = firstnode;
            if (current.getExponent() == 0)
                s = s + current.getCoefficient();
            else if (current.getExponent() == 1)
                s = s + current.getCoefficient() + "x";
            else
                s = s + current.getCoefficient() + "x^" + current.getExponent();
            current = current.getNext();
            while (current != null) {
                if (current.getCoefficient() > 0) {
                    if (current.getCoefficient() == 1) {
                        if (current.getExponent() == 0)
                            s = s + " + 1";
                        else if (current.getExponent() == 1)
                            s = s + " + x";
                        else
                            s = s + " + " + "x^" + current.getExponent();
                    } else {
                        if (current.getExponent() == 0)
                            s = s + " + " + current.getCoefficient();
                        else if (current.getExponent() == 1)
                            s = s + " + " + current.getCoefficient() + "x";
                        else
                            s = s + " + " + current.getCoefficient() + "x^" + current.getExponent();
                    }
                } else {
                    if (current.getExponent() == 0)
                        s = s + " - " + Math.abs(current.getCoefficient());
                    else if (current.getExponent() == 1)
                        s = s + " - " + Math.abs(current.getCoefficient()) + "x";
                    else
                        s = s + " - " + Math.abs(current.getCoefficient()) + "x^" + current.getExponent();
                }
                current = current.getNext();
            }
            return s;
        }
    }
}