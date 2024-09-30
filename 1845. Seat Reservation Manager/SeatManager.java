import java.util.*;

public class SeatManager {

    private final PriorityQueue<Integer> freeQueue;

    public SeatManager(int n) {
        freeQueue = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            freeQueue.add(i);
        }
    }

    public int reserve() {
        Integer seat = freeQueue.poll();
        if (Objects.nonNull(seat)) {
            return seat;
        }
        return -1;
    }

    public void unreserve(int seatNumber) {
        freeQueue.add(seatNumber);
    }
}

/**
 * Your SeatManager object will be instantiated and called as such:
 * SeatManager obj = new SeatManager(n);
 * int param_1 = obj.reserve();
 * obj.unreserve(seatNumber);
 */