package com.LubieKakao1212.neguns.data.util.condition;

public class IntCondition implements IVariableCondition<Integer> {

    private Integer eqlo = null;
    private Integer eqgr = null;
    private Integer lo = null;
    private Integer gr = null;
    private Integer eq = null;

    @Override
    public boolean solve(Integer value) {
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
        return "IntCondition{" +
                "eqlo=" + eqlo +
                ", eqgr=" + eqgr +
                ", lo=" + lo +
                ", gr=" + gr +
                ", eq=" + eq +
                '}';
        }
}
