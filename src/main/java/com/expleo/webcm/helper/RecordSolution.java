package com.expleo.webcm.helper;

import com.expleo.webcm.entity.expleodb.Record;
import com.expleo.webcm.entity.expleodb.Solution;

public class RecordSolution {

    private Record record;
    private Solution solution;

    public RecordSolution() {
    }

    public RecordSolution(Record record, Solution solution) {
        this.record = record;
        this.solution = solution;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "RecordSolution{" +
                "record=" + record +
                ", solution=" + solution +
                '}';
    }
}
