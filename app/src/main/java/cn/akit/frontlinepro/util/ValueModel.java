package cn.akit.frontlinepro.util;

public class ValueModel {

    private String countryCode; //string文件所属国家代号，即value-后接的字符串
    private String name;        //字段的name
    private String defaultValue;    //字段的默认内容
    private String value;   //字段的多语言内容

    public ValueModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueModel{" +
                "countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
