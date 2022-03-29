package com.LubieKakao1212.modularguns.data.behaviour.condition;

public class DoubleCondition implements IVariableCondition<Double> {

    private Double eqlo = null;
    private Double eqgr = null;
    private Double lo = null;
    private Double gr = null;
    private Double eq = null;

    @Override
    public boolean solve(Double value)  {
        boolean flag = value != null;
        flag &= eqlo != null ? value <= eqlo : true;
        flag &= eqgr != null ? value >= eqgr : true;
        flag &= lo != null ? value < lo : true;
        flag &= gr != null ? value > gr : true;
        flag &= eq != null ? value == eq : true;
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
