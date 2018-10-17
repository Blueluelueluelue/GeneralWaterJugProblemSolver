public class Fill extends Action {
    @Override
    public State act(State currentState, int...jugIndex) {
        State newState = currentState.copy();
        int index = jugIndex[0];
        newState.setJug(currentState.getMaxJugValue(index), index);
        return newState;
    }

    @Override
    public String print(int... jugIndex) {
        return "fill jug " + (jugIndex[0] + 1);
    }
}
