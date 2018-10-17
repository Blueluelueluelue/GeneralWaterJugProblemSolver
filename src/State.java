import java.util.ArrayList;

public class State {
    private int[] jugs;
    private int[] maxJugValues;

    public State(int numberOfJugs, int[] maxValues) {
        jugs = new int[numberOfJugs];
        maxJugValues = maxValues.clone();
    }

    public State(int[] jugValues, int[] maxValues) {
        jugs = new int[jugValues.length];
        System.arraycopy(jugValues, 0, jugs, 0, jugs.length);
        maxJugValues = new int[maxValues.length];
        System.arraycopy(maxValues, 0, maxJugValues, 0, maxValues.length);
    }

    public int getJug(int index) {
        return jugs[index];
    }

    public void setJug(int value, int index) {
        this.jugs[index] = value;
    }

    public State copy() {
        return new State(jugs, maxJugValues);
    }

    public int getMaxJugValue(int index) {
        return maxJugValues[index];
    }

    public int[] getJugs() {
        return jugs;
    }

    public boolean isPossibleToFit(int liters) {
        for (int max: maxJugValues) {
            if (max >= liters) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<State> waysToFit(int liters) {
        ArrayList<State> ways = new ArrayList<>();
        for (int i = 0; i < maxJugValues.length; i++) {
            int max = maxJugValues[i];
            if (max >= liters) {
                State s = new State(maxJugValues.length, maxJugValues);
                s.setJug(liters, i);
                ways.add(s);
            }
        }
        return ways;
    }

    public int hashCode() {
        StringBuilder s = new StringBuilder();
        for (int jug: jugs) {
            s.append(jug).append(" ");
        }
        return s.toString().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof State)) {
            return false;
        }
        State s = (State)o;
        if (s.getJugs().length != jugs.length) {
            return false;
        }
        for (int i = 0; i < jugs.length; i++) {
            if (s.getJug(i) != jugs[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("(");
        for (int jug: jugs) {
            s.append(jug).append(",").append(" ");
        }
        s.replace(s.length()-2, s.length(), ")");
        return s.toString();
    }
}
