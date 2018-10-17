public abstract class Action {
    public abstract State act(State current, int...jugs);
    public abstract String print(int...jugIndices);
}
