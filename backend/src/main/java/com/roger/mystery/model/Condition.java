package com.roger.mystery.model;

public class Condition {
    private String variable;
    private Operator operator = Operator.EQ;
    private Integer value;
    private Boolean boolValue;

    public enum Operator { EQ, NE, GT, LT, GTE, LTE, EXISTS, NOT_EXISTS }

    public String getVariable() { return variable; }
    public void setVariable(String variable) { this.variable = variable; }
    public Operator getOperator() { return operator; }
    public void setOperator(Operator operator) { this.operator = operator; }
    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }
    public Boolean getBoolValue() { return boolValue; }
    public void setBoolValue(Boolean boolValue) { this.boolValue = boolValue; }
}
