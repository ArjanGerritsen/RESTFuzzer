package nl.ou.se.rest.fuzzer.components.reporter.coverage;

public class ReportTableId {

    // variables
    private Object rowKey;
    private Object columnnKey;

    // constructor(s)
    public ReportTableId(Object rowKey, Object columnKey) {
        this.rowKey = rowKey;
        this.columnnKey = columnKey;

    }

    // method(s)
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ReportTableId)) {
            return false;
        }

        ReportTableId other = (ReportTableId) o;

        return this.getRowKey().equals(other.getRowKey()) && this.getColumnnKey().equals(other.getColumnnKey());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.rowKey.hashCode();
        result = 31 * result + this.columnnKey.hashCode();
        return result;
    }

    // getter(s) and setter(s)
    public Object getRowKey() {
        return rowKey;
    }

    public void setRowKey(Object rowKey) {
        this.rowKey = rowKey;
    }

    public Object getColumnnKey() {
        return columnnKey;
    }

    public void setColumnnKey(Object columnnKey) {
        this.columnnKey = columnnKey;
    }
}