package model;

import java.util.Map;

public class Instruction {
    //服务名称
    private String name;
    //服务表示
    private String serSign;
    //注释
    private String annotation;
    //参数Key
    private String parameterKey;

    private Integer min;

    private Integer max;

    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    //    private String parameterValue;
    //下拉框标识 true:下拉框||false:输入框
    private Boolean sign;

    //参数Value(下拉框为固定可选值)
    private Map<Integer,String> parameterValue;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerSign() {
        return serSign;
    }

    public void setSerSign(String serSign) {
        this.serSign = serSign;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }

    public Map<Integer, String> getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(Map<Integer, String> parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "name='" + name + '\'' +
                ", serSign='" + serSign + '\'' +
                ", annotation='" + annotation + '\'' +
                ", parameterKey='" + parameterKey + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", sign=" + sign +
                ", parameterValue=" + parameterValue +
                '}';
    }
}
