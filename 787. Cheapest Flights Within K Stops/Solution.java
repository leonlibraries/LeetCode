import java.util.*;

class Solution {

    private final static Set<String> DISCOVERY_TMP = new HashSet<>(3);

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] flights = new int[24][3];
        flights[0] = new int[]{3, 4, 4};
        flights[1] = new int[]{2, 5, 6};
        flights[2] = new int[]{4, 7, 10};
        flights[3] = new int[]{9, 6, 5};
        flights[4] = new int[]{7, 4, 4};
        flights[5] = new int[]{6, 2, 10};
        flights[6] = new int[]{6, 8, 6};
        flights[7] = new int[]{7, 9, 4};
        flights[8] = new int[]{1, 5, 4};
        flights[9] = new int[]{1, 0, 4};
        flights[10] = new int[]{9, 7, 3};
        flights[11] = new int[]{7, 0, 5};
        flights[12] = new int[]{6, 5, 8};
        flights[13] = new int[]{1, 7, 6};
        flights[14] = new int[]{4, 0, 9};
        flights[15] = new int[]{5, 9, 1};
        flights[16] = new int[]{8, 7, 3};
        flights[17] = new int[]{1, 2, 6};
        flights[18] = new int[]{4, 1, 5};
        flights[19] = new int[]{5, 2, 4};
        flights[20] = new int[]{1, 9, 1};
        flights[21] = new int[]{7, 8, 10};
        flights[22] = new int[]{0, 4, 2};
        flights[23] = new int[]{7, 2, 8};
        int totalPrice = s.findCheapestPrice(10, flights, 6, 0, 7);
        System.out.println("需要付费金额为：" + totalPrice);
    }

    /**
     * 利用 Dijkstra 算法计算最便宜的航班路线
     *
     * @param n       n个城市 (base0)
     * @param flights 航班信息 [[from,to,price],[from2,to2,price2]] 二维数组
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
        // 节点发现队列
        Queue<Integer> discovery = new LinkedList<>();
        // src 到各个城市的最低票价+换乘数（由于有可能有多种方案，这里每个节点由列表呈现）
        Map<Integer, List<Triple>> minWeights = new HashMap<>(3);
        addNextCitiesToMinWeights(minWeights, n, map, src, null, discovery);
        do {
            System.out.println(minWeights);
            Optional<Triple> cheapest = minWeights.values().stream()
                    .flatMap(Collection::stream)
                    .filter(triple -> discovery.contains(triple.city))
                    .filter(triple -> triple.stops < k)
                    .min(Comparator.comparingInt(o -> o.price));
            if (!cheapest.isPresent()) {
                break;
            }
            Triple triple = cheapest.get();
            discovery.remove(triple.city);
            addNextCitiesToMinWeights(minWeights, n, map, src, triple, discovery);
        } while (!discovery.isEmpty());
        System.out.println(minWeights);
        return theCheapestPrice(minWeights, dst);
    }

    /**
     * 把当前节点指向的下一个节点集合加入到 FIFO
     *
     * @param minWeights     minWeightMap(city->price,stops)
     * @param n              n 一共n个城市
     * @param flights        航班信息
     * @param src            起点
     * @param curr           当前节点
     */
    private void addNextCitiesToMinWeights(Map<Integer, List<Triple>> minWeights, int n, int[][] flights, int src, Triple curr, Queue<Integer> discovery) {
        int currCity = Objects.isNull(curr) ? src : curr.city;
        for (int i = 0; i < n; i++) {
            if (i != currCity) {
                if (flights[currCity][i] > 0 && i != src) {
                    addToDiscoveryList(discovery, currCity, i);
                    if (Objects.isNull(curr)) {
                        appendMinWeight(i, Triple.of(i, flights[currCity][i], 0), minWeights);
                    } else {
                        appendMinWeight(i, Triple.of(i, flights[curr.city][i] + curr.price, curr.stops + 1), minWeights);
                    }
                }
            }
        }
    }

    private void appendMinWeight(int city, Triple triple, Map<Integer, List<Triple>> minWeight) {
        List<Triple> list = minWeight.get(city);
        if (list == null) {
            list = new ArrayList<>(10);
            list.add(triple);
            minWeight.put(city, list);
        } else {
            Optional<Triple> replica = list.stream().filter(t -> t.stops == triple.stops).findAny();
            if (replica.isPresent()) {
                Triple r = replica.get();
                if (triple.price < r.price) {
                    r.price = triple.price;
                }
            } else {
                list.add(triple);
            }
        }
    }

    private int theCheapestPrice(Map<Integer, List<Triple>> minWeights, int dst) {
        List<Triple> list = minWeights.get(dst);
        if (Objects.nonNull(list)) {
            Optional<Triple> cheapest = list.stream().min(Comparator.comparingInt(t -> t.price));
            return cheapest.map(triple -> triple.price).orElse(-1);
        }
        return -1;
    }

    private void addToDiscoveryList(Queue<Integer> discovery, int from, int tar) {
        String tmpVal = String.format("%s-%s", from, tar);
        if (!DISCOVERY_TMP.contains(tmpVal)) {
            discovery.add(tar);
            DISCOVERY_TMP.add(tmpVal);
        }
    }

    static class Triple {

        public int city;

        public int price;

        public int stops;

        public static Triple of(int city, int price, int stops) {
            Triple tuple = new Triple();
            tuple.city = city;
            tuple.price = price;
            tuple.stops = stops;
            return tuple;
        }

        @Override
        public String toString() {
            return "Triple{" +
                    "city=" + city +
                    ", price=" + price +
                    ", stops=" + stops +
                    '}';
        }
    }
}