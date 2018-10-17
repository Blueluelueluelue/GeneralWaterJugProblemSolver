public class ThrowAway extends Action {

    @Override
    public State act(State currentState, int...jugIndex) {
        State newState = currentState.copy();
        newState.setJug(0, jugIndex[0]);
        return newState;
    }

    @Override
    public String print(int... jugIndex) {
        return "throw away contents of jug " + (jugIndex[0] + 1);
    }
}
