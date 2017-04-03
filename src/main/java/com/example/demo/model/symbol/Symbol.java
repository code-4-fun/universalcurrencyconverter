package com.example.demo.model.symbol;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class Symbol implements Comparable<Symbol> {

    private Character key;
    private Integer value;
    private boolean isRepetationAllowed;
    private boolean isSubtractionAllowed;

    public Symbol(Character key, Integer value, boolean isRepetationAllowed, boolean isSubtractionAllowed) {
        this.key = key;
        this.value = value;
        this.isRepetationAllowed = isRepetationAllowed;
        this.isSubtractionAllowed = isSubtractionAllowed;
    }

    @Override
    public int compareTo(Symbol symbol) {
        return Integer.compare(this.value, symbol.value);
    }

    public Character getKey() {
        return key;
    }

    public void setKey(Character key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isRepetationAllowed() {
        return isRepetationAllowed;
    }

    public void setRepetationAllowed(boolean repetationAllowed) {
        isRepetationAllowed = repetationAllowed;
    }

    public boolean isSubtractionAllowed() {
        return isSubtractionAllowed;
    }

    public void setSubtractionAllowed(boolean subtractionAllowed) {
        isSubtractionAllowed = subtractionAllowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;

        Symbol symbol = (Symbol) o;

        if (isRepetationAllowed() != symbol.isRepetationAllowed()) return false;
        if (isSubtractionAllowed() != symbol.isSubtractionAllowed()) return false;
        if (getKey() != null ? !getKey().equals(symbol.getKey()) : symbol.getKey() != null) return false;
        return getValue() != null ? getValue().equals(symbol.getValue()) : symbol.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (isRepetationAllowed() ? 1 : 0);
        result = 31 * result + (isSubtractionAllowed() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "key=" + key +
                ", value=" + value +
                ", isRepetationAllowed=" + isRepetationAllowed +
                ", isSubtractionAllowed=" + isSubtractionAllowed +
                '}';
    }
}
