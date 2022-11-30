import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] flights = new int[4][3];
        flights[0] = new int[]{0, 1, 1};
        flights[1] = new int[]{0, 2, 5};
        flights[2] = new int[]{1, 2, 1};
        flights[3] = new int[]{2, 3, 1};
        int totalPrice = s.findCheapestPrice(4, flights, 0, 3, 1);
        System.out.println("需要付费金额为：" + totalPrice);
    }

    /**
     * 利用 Dijkstra 算法计算最便宜的航班路线
     *
     * @param n       n个城市 (base0)
     * @param flights 航班信息 from,to => price
     * @param src     出发地
     * @param dst     目的地
     * @param k       最大k次换乘
     * @return
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // 构建航班信息图
        int[][] map = new int[n][n];
        for (int[] flight : flights) {
            map[flight[0]][flight[1]] = flight[2];
        }
        // 取当前 minWeight 最小值
        Map<Integer, Tuple<Integer, Integer>> finished = new HashMap<>(3);
        // src 到各个城市的最低票价
        Map<Integer, Tuple<Integer, Integer>> minWeight = new HashMap<>(3);
        addNextCitiesToQueue(minWeight, n, map, src, src, k, finished);
        while (!finished.containsKey(dst)) {
            System.out.println(minWeight);
            Optional<Map.Entry<Integer, Tuple<Integer, Integer>>> cheapest = minWeight.entrySet().stream()
                    .filter(e -> src != e.getKey() && !finished.containsKey(e.getKey()))
                    .min(Comparator.comparing(t -> t.getValue().price));
            cheapest.ifPresent(entry -> {
                finished.put(entry.getKey(), entry.getValue());
                addNextCitiesToQueue(minWeight, n, map, src, entry.getKey(), k, finished);
            });
        }
        // System.out.println(finished);
        return Objects.nonNull(finished.get(dst)) ? finished.get(dst).price : -1;
    }

    /**
     * 把当前节点指向的下一个节点集合加入到 FIFO
     *
     * @param minWeight      minWeightMap(city->price,stops)
     * @param n              n 一共n个城市
     * @param flights        航班信息
     * @param src            起点
     * @param curr           当前节点
     * @param k              最大换乘数
     * @param finished       已经标记完成的集合
     */
    private void addNextCitiesToQueue(Map<Integer, Tuple<Integer, Integer>> minWeight, int n, int[][] flights, int src, int curr, int k, Map<Integer, Tuple<Integer, Integer>> finished) {
        // src -> curr 的 minWeight
        Tuple<Integer, Integer> currMinWeight = minWeight.get(curr);
        for (int i = 0; i < n; i++) {
            if (i != curr && !finished.containsKey(i)) {
                if (flights[curr][i] > 0 && i != src) {
                    if (src == curr) {
                        minWeight.put(i, Tuple.of(flights[curr][i], 0));
                    } else {
                        // src -> i    的 minWeight （可能为空）
                        Tuple<Integer, Integer> oldMinWeight = minWeight.get(i);
                        Tuple<Integer, Integer> newMinWeight;
                        assert currMinWeight != null;
                        if (Objects.nonNull(oldMinWeight)) {
                            if (flights[curr][i] + currMinWeight.price < oldMinWeight.price) {
                                // 出现更优惠的价格并且换乘数可以接受的情况下
                                newMinWeight = Tuple.of(flights[curr][i] + currMinWeight.price, currMinWeight.stops + 1);
                            } else {
                                newMinWeight = oldMinWeight;
                            }
                        } else {
                            // 没有旧值
                            newMinWeight = Tuple.of(flights[curr][i] + currMinWeight.price, currMinWeight.stops + 1);
                        }
                        minWeight.put(i, newMinWeight);
                    }

                }
            }
        }
    }

    static class Tuple<K, V> {

        public K price;

        public V stops;

        public static Tuple<Integer, Integer> of(int price, int stops) {
            Tuple<Integer, Integer> tuple = new Tuple<>();
            tuple.price = price;
            tuple.stops = stops;
            return tuple;
        }

        @Override
        public String toString() {
            return "Tuple{" +
                    "price=" + price +
                    ", stops=" + stops +
                    '}';
        }
    }
}