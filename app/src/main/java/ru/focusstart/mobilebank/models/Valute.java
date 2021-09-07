package ru.focusstart.mobilebank.models;

public class Valute {
    private String id;
    private String numCode;
    private String charCode;
    private long nominal;
    private String name;
    private double value;
    private double previous;

    public Valute() {}

    public Valute(String id, String numCode, String charCode, long nominal, String name, double value, double previous) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public long getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPrevious() {
        return previous;
    }

    public void setPrevious(double previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "ID='" + id + '\'' +
                ", NumCode='" + numCode + '\'' +
                ", CharCode='" + charCode + '\'' +
                ", Nominal=" + nominal +
                ", Name='" + name + '\'' +
                ", Value=" + value +
                ", Previous=" + previous +
                '}';
    }
}
