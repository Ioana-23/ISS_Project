package project.domain;

public class Pair<T1,T2>
{
    private T1 value1;
    private T2 value2;

    public Pair(T1 value1, T2 value2)
    {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getValue1() {
        return value1;
    }

    public T2 getValue2() {
        return value2;
    }

    public void setValue1(T1 value1) {
        this.value1 = value1;
    }

    public void setValue2(T2 value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "(" + getValue1() + ", " + getValue2() +")";
    }
}
