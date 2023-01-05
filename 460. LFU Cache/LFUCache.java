import java.util.*;

/**
 * Least Frequently Used (LFU)
 *
 * @author leonwong
 */
class LFUCache {

    private final int capacity;

    private final Map<Integer, Integer> data = new HashMap<>(3);

    private final Map<Integer, Queue<Integer>> frequencyKeys = new HashMap<>(3);

    private final Map<Integer, Integer> keyFrequencies = new HashMap<>(3);

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        int value = data.getOrDefault(key, -1);
        if (value >= 0) {
            incrKeyFreq(key);
        }
        return value;
    }

    public void put(int key, int value) {
        if (capacity > 0) {
            int cache = get(key);
            if (cache >= 0) {
                incrKeyFreq(key);
                data.put(key, value);
            } else {
                int cacheSize = data.size();
                if (cacheSize == capacity) {
                    // evict cache data
                    evictCache();
                }
                addCacheValue(key, value);
            }
        }
    }

    private void incrKeyFreq(int key) {
        int freq = keyFrequencies.getOrDefault(key, -1);
        if (freq <= 0) {
            keyFrequencies.put(key, 1);
            Queue<Integer> value = frequencyKeys.get(1);
            if (value == null || value.isEmpty()) {
                Queue<Integer> linkedList = new LinkedList<>();
                linkedList.add(key);
                frequencyKeys.put(1, linkedList);
            } else {
                value.add(key);
            }
        } else {
            int currFreq = freq + 1;
            keyFrequencies.put(key, currFreq);
            Queue<Integer> value = frequencyKeys.get(currFreq);
            if (value == null || value.isEmpty()) {
                Queue<Integer> linkedList = new LinkedList<>();
                linkedList.add(key);
                frequencyKeys.put(currFreq, linkedList);
            } else {
                value.add(key);
            }
            frequencyKeys.get(freq).remove(key);
        }
    }

    private void addCacheValue(int key, int value) {
        data.put(key, value);
        Queue<Integer> linkedList = frequencyKeys.get(1);
        if (linkedList == null || linkedList.isEmpty()) {
            linkedList = new LinkedList<>();
        }
        linkedList.add(key);
        frequencyKeys.put(1, linkedList);
        keyFrequencies.put(key, 1);
    }

    private void evictCache() {
        OptionalInt minFreq = frequencyKeys.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 0)
                .mapToInt(Map.Entry::getKey)
                .min();
        minFreq.ifPresent(
                minValue -> {
                    Integer evictKey = frequencyKeys.get(minValue).poll();
                    data.remove(evictKey);
                    keyFrequencies.remove(evictKey);
                }
        );
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 *
 * Input
 * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
 */