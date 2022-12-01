import java.util.*;
import java.util.stream.Collectors;

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] flights = new int[14][3];
        flights[0] = new int[]{0, 3, 3};
        flights[1] = new int[]{3, 4, 3};
        flights[2] = new int[]{4, 1, 3};
        flights[3] = new int[]{0, 5, 1};
        flights[4] = new int[]{5, 1, 100};
        flights[5] = new int[]{0, 6, 2};
        flights[6] = new int[]{6, 1, 100};
        flights[7] = new int[]{0, 7, 1};
        flights[8] = new int[]{7, 8, 1};
        flights[9] = new int[]{8, 9, 1};
        flights[10] = new int[]{9, 1, 1};
        flights[11] = new int[]{1, 10, 1};
        flights[12] = new int[]{10, 2, 1};
        flights[13] = new int[]{1, 2, 100};
        int totalPrice = s.findCheapestPrice(11, flights, 0, 2, 4);
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
        Set<String> discoveryPaths = new HashSet<>();
        Queue<Integer> discovery = new LinkedList<>();
        // src 到各个城市的最低票价+换乘数（由于有可能有多种方案，这里每个节点由列表呈现）
        Map<Integer, List<Triple>> minWeights = new HashMap<>(3);
        addNextCitiesToMinWeights(minWeights, n, map, src, null, discovery, discoveryPaths);
        do {
            List<Triple> cheapestList = minWeights.values().stream()
                    .flatMap(Collection::stream)
                    .filter(triple -> discovery.contains(triple.city))
                    .filter(triple -> triple.stops < k)
                    .collect(Collectors.toList());
            cheapestList.parallelStream().forEach(
                    triple -> {
                        discovery.remove(triple.city);
                        addNextCitiesToMinWeights(minWeights, n, map, src, triple, discovery, discoveryPaths);
                    }
            );
            if (cheapestList.isEmpty()) {
                break;
            }
        } while (!discovery.isEmpty());
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
    private void addNextCitiesToMinWeights(Map<Integer, List<Triple>> minWeights, int n, int[][] flights, int src, Triple curr, Queue<Integer> discovery, Set<String> discoveryPaths) {
        int currCity = Objects.isNull(curr) ? src : curr.city;
        for (int i = 0; i < n; i++) {
            if (i != currCity) {
                if (flights[currCity][i] > 0 && i != src) {
                    addToDiscoveryList(discovery, curr, i, discoveryPaths);
                    if (Objects.isNull(curr)) {
                        List<String> initChain = new ArrayList<>();
                        initChain.add(String.valueOf(i));
                        appendMinWeight(i, Triple.of(i, flights[currCity][i], 0, initChain), minWeights);
                    } else {
                        appendMinWeight(i, Triple.of(i, flights[curr.city][i] + curr.price, curr.stops + 1, curr.buildNewChain(i)), minWeights);
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

    private void addToDiscoveryList(Queue<Integer> discovery, Triple curr, int tar, Set<String> discoveryPaths) {
        String path;
        if (Objects.nonNull(curr)) {
            List<String> chain = new ArrayList<>(curr.chain);
            chain.add(String.valueOf(tar));
            path = pathToString(chain);
        } else {
            path = String.valueOf(tar);
        }
        if (!discoveryPaths.contains(path)) {
            discovery.add(tar);
            discoveryPaths.add(path);
        }
    }

    private String pathToString(List<String> path) {
        return String.join(",", path);
    }

    static class Triple {

        public int city;

        public int price;

        public int stops;

        public List<String> chain = new ArrayList<>(3);

        public static Triple of(int city, int price, int stops, List<String> chain) {
            Triple tuple = new Triple();
            tuple.city = city;
            tuple.price = price;
            tuple.stops = stops;
            tuple.chain = chain;
            return tuple;
        }

        public List<String> buildNewChain(int tar) {
            chain.add(String.valueOf(tar));
            return chain;
        }

        @Override
        public String toString() {
            return "Triple{" +
                    "city=" + city +
                    ", price=" + price +
                    ", stops=" + stops +
                    ", chain=" + chain +
                    '}';
        }
    }
}