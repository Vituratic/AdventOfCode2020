package day8;

public class Result {

    private int acc;
    private State state;

    public Result(int acc, State state) {
        this.acc = acc;
        this.state = state;
    }

    public enum State {
        FINISHED, ABORTED
    }

    public int getAcc() {
        return this.acc;
    }

    public State getState() {
        return this.state;
    }
}
