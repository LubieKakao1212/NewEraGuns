package com.LubieKakao1212.neguns.data.util.condition;

public class DoubleCondition implements IVariableCondition<Double> {

    private Double eqlo = null;
    private Double eqgr = null;
    private Double lo = null;
    private Double gr = null;
    private Double eq = null;

    @Override
    public boolean solve(Double value)  {
        boolean flag = value != null;
        flag &= eqlo == null || value <= eqlo;
        flag &= eqgr == null || value >= eqgr;
        flag &= lo == null || value < lo;
        flag &= gr == null || value > gr;
        flag &= eq == null || value == eq;
        return flag;
    }

    @Override
    public String toString() {
        return "DoubleCondition{" +
                "eqlo=" + eqlo +
                ", eqgr=" + eqgr +
                ", lo=" + lo +
                ", gr=" + gr +
                ", eq=" + eq +
                '}';
    }
}
