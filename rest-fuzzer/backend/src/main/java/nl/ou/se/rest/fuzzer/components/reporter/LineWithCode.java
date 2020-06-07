package nl.ou.se.rest.fuzzer.components.reporter;

public class LineWithCode implements Comparable<LineWithCode> {

    // variable(s)
    private Integer number;
    private Integer count;

    // method(s)
    public int compareTo(LineWithCode other) {
        return this.getNumber().compareTo(other.getNumber());
    }

    public void merge(LineWithCode lineWithCode) {
        if (lineWithCode == null) {
            return;
        }

        if (!this.getNumber().equals(lineWithCode.getNumber())) {
            throw new IllegalArgumentException("Numbers should match");
        }

        if (lineWithCode.isExecuted()) {
            this.count++;
        }
    }

    public boolean isExecuted() {
        return this.getCount() > 0;
    }

    // getter(s) and setter(s)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}