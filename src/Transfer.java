public class Transfer extends Action {
    @Override
    public State act(State current, int... jugs) {
        int fromJugIndex = jugs[0];
        int toJugIndex = jugs[1];
        int maxOfToJug = current.getMaxJugValue(toJugIndex);
        int toJugValue = current.getJug(toJugIndex);
        int fromJugValue = current.getJug(fromJugIndex);
        if (toJugValue + fromJugValue > maxOfToJug) {
            fromJugValue -= (maxOfToJug - toJugValue);
            toJugValue = maxOfToJug;
        } else {
            toJugValue += fromJugValue;
            fromJugValue = 0;
        }
        State newState = current.copy();
        newState.setJug(toJugValue, toJugIndex);
        newState.setJug(fromJugValue, fromJugIndex);
        return newState;
    }

    @Override
    public String print(int... jugIndices) {
        return "transfer from jug " + (jugIndices[0] + 1) + " to jug " + (jugIndices[1] + 1);
    }
}
