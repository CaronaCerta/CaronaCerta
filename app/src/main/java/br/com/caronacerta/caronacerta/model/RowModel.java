package br.com.caronacerta.caronacerta.model;

public class RowModel {
    String label;
    public float rating = 2.0f;

    public RowModel(String label) {
        this.label = label;
    }

    public String toString() {
        if (rating >= 3.0) {
            return (label.toUpperCase());
        }
        return (label);
    }
}