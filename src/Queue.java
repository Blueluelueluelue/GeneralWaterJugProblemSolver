public class Queue {
    State[] myQ;
    int head;
    int tail;

    public Queue() {
        myQ = new State[1000000];
        head = -1;
        tail = -1;
    }

    public void push(State item) {
        tail = (tail + 1) % myQ.length;
        if (tail == head) {
            System.out.println("OVERFLOW");
            return;
        }
        if (head == -1) {
            head++;
        }
        myQ[tail] = item.copy();
    }

    public State pop() {
        if (head == -1) {
            System.out.println("UNDERFLOW");
            return null;
        }
        State toPop = myQ[head];
        if (head == tail) {
            head = -1;
            tail = -1;
            return toPop;
        }
        head = (head + 1) % myQ.length;
        return toPop;
    }

    public boolean isEmpty() {
        return head == -1;
    }
}
