import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    HashSet<State> visitedStates;
    HashSet<State> goalStates;
    ArrayList<Action> possibleActions;
    ArrayList<Pair> potentialPath;
    ArrayList<Pair> actualPath;
    Queue stateQ;

    private void addAction(Action action) {
        if (possibleActions == null) {
            possibleActions = new ArrayList<>();
        }
        possibleActions.add(action);
    }

    private void addState(State state) {
        if (visitedStates == null) {
            visitedStates = new HashSet<>();
        }
        visitedStates.add(state);
    }

    private void solve(State initialState, int liters) {
        stateQ = new Queue();
        potentialPath = new ArrayList<>();
        goalStates = new HashSet<>();
        goalStates.addAll(initialState.waysToFit(liters));
        stateQ.push(initialState);

        while (!stateQ.isEmpty()) {
            State currentState = stateQ.pop();
            if (goalStates.contains(currentState)) {
                System.out.println("reached goal");
                constructPath(currentState);
                break;
            }
            actOn(currentState);
        }
    }

    private void actOn(State currentState) {
        // get the jugs from the current state
        int[] jugs = currentState.getJugs();
        // go through all possible actions
        for (Action action: possibleActions) {
            // since throwaway and fill has the same method signature
            if (action instanceof ThrowAway || action instanceof Fill) {
                // go through all the jugs and throwaway or fill it to get the nextState
                for (int i = 0; i < jugs.length; i++) {
                    State nextState = action.act(currentState, i);
                    // if the state hasn't been visited before then add that to the queue
                    if (!visitedStates.contains(nextState)) {
                        visit(currentState, nextState, action, i);
                    }
                }
            } else if (action instanceof Transfer) {
                // go through all the jugs and transfer it's contents to all other jugs to get the nextStates
                for (int fromIndex = 0; fromIndex < jugs.length; fromIndex++) {
                    for (int toIndex = 0; toIndex < jugs.length; toIndex++) {
                        // since transferring from a jug to the same jug is illegal
                        if (fromIndex != toIndex) {
                            State nextState = action.act(currentState, fromIndex, toIndex);
                            // if the state hasn't been visited before then add that to the queue
                            if (!visitedStates.contains(nextState)) {
                                visit(currentState, nextState, action, fromIndex, toIndex);
                            }
                        }
                    }
                }
            }
        }
    }

    private void visit(State current, State next, Action action, int...jugIndices) {
        // add the next state to the visited states list
        addState(next);
        // create a current -> action -> next relationship for path construction later
        Pair p = new Pair(current, next, action, jugIndices);
        potentialPath.add(p);
        // add the next state to the queue
        stateQ.push(next);
    }

    private void constructPath(State last) {
        actualPath = new ArrayList<>();
        Pair lastPair = null;
        int lastIndex = 0;
        for (int i = potentialPath.size() - 1; i >= 0; i--) {
            if (potentialPath.get(i).to.equals(last)) {
                lastPair = potentialPath.get(i);
                lastIndex = i;
                break;
            }
        }
        if (lastPair != null) {
            actualPath.add(lastPair);
            for (int i = lastIndex - 1; i >= 0; i--) {
                lastPair = actualPath.get(actualPath.size() - 1);
                if (lastPair.from.equals(potentialPath.get(i).to)) {
                    actualPath.add(potentialPath.get(i));
                }
            }
            for (int i = actualPath.size() - 1; i >= 0; i--) {
                System.out.println(actualPath.get(i));
            }
            System.out.println("No. of steps: " + actualPath.size());
        }
    }

    public static void main(String[] args) {
        Action throwAway = new ThrowAway();
        Action fill = new Fill();
        Action transfer = new Transfer();

        Main main = new Main();
        main.addAction(throwAway);
        main.addAction(fill);
        main.addAction(transfer);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of jugs");
        int numOfJugs = sc.nextInt();
        System.out.println("Enter the maximum values for those jugs");
        int[] maxJugValues = new int[numOfJugs];
        for (int i = 0; i < numOfJugs; i++) {
            maxJugValues[i] = sc.nextInt();
        }
        State initialState = new State(numOfJugs, maxJugValues);
        main.addState(initialState);
        System.out.println("Enter the number of liters you want");
        int liters = sc.nextInt();
        if (!initialState.isPossibleToFit(liters)) {
            System.out.println("Goal can't be reached because the amount is too large to fit in the jugs");
            return;
        }
        main.solve(initialState, liters);
    }
}
