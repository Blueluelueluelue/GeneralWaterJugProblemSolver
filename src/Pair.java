public class Pair {
    State from;
    State to;
    Action action;
    int[] jugIndices;
    public Pair(State f, State t, Action a, int...ji) {
        from = f;
        to = t;
        action = a;
        jugIndices = ji;
    }

    @Override
    public String toString() {
        return "From: " + from + " " + action.print(jugIndices) + " To: " + to;
    }
}
