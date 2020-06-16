package nl.ou.se.rest.fuzzer.components.reporter.coverage;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReportTable {

    public SortedSet<Object> rowKeys = new TreeSet<>();
    public SortedSet<Object> columnKeys = new TreeSet<>();

    private Object origin;

    private HashMap<ReportTableId, Object> values = new HashMap<>();

    private boolean reverse = false;

    // method(s)
    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public void addValue(Object columnKey, Object rowKey, Object value) {
        if (!this.rowKeys.contains(rowKey)) {
            this.rowKeys.add(rowKey);
        }

        if (!this.columnKeys.contains(columnKey)) {
            this.columnKeys.add(columnKey);
        }

        this.values.put(new ReportTableId(rowKey, columnKey), value);
    }

    public void reverse() {
        this.reverse = true;
    }

    public void printTable() {
        System.out.print(this.origin);
        System.out.print(", ");

        for (Object columnKey : this.getColumnKeys()) {
            System.out.print(columnKey);
            System.out.print(", ");
        }
        System.out.println();

        for (Object rowKey : this.getRowKeys()) {
            System.out.print(rowKey);
            System.out.print(", ");

            for (Object columnKey : this.getColumnKeys()) {
                ReportTableId reportTableId = new ReportTableId(rowKey, columnKey);
                if (this.reverse) {
                    reportTableId = new ReportTableId(columnKey, rowKey);
                }

                Object value = this.values.get(reportTableId);
                System.out.print(value);
                System.out.print(", ");
            }
            System.out.println();
        }
    }

    // getter(s) and setter(s)
    public SortedSet<Object> getRowKeys() {
        return this.reverse ? columnKeys : rowKeys;
    }

    public void setRowKeys(SortedSet<Object> rowKeys) {
        this.rowKeys = rowKeys;
    }

    public SortedSet<Object> getColumnKeys() {
        return this.reverse ? rowKeys : columnKeys;
    }

    public void setColumnKeys(SortedSet<Object> columnKeys) {
        this.columnKeys = columnKeys;
    }

    public HashMap<ReportTableId, Object> getValues() {
        return values;
    }

    public void setValues(HashMap<ReportTableId, Object> values) {
        this.values = values;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}