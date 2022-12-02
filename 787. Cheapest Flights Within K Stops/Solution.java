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
     * 动态规划算法计算最便宜的航班路线
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
        // src 到各个城市的最低票价+换乘数（由于有可能有多种方案，这里每个节点由列表呈现）
        Map<Integer, Node> minWeights = new HashMap<>(3);
        Queue<Node> workQueue = new LinkedList<>();
        workQueue.add(Node.of(src, 0, 0));
        while (!workQueue.isEmpty()) {
            Node curr = workQueue.poll();
            addNextCitiesToMinWeights(minWeights, workQueue, map, k, curr);
        }
        return theCheapestPrice(minWeights, dst);
    }

    /**
     * 把当前节点指向的下一个节点集合加入到 FIFO
     *
     * @param minWeights     minWeightMap(city->price,stops)
     * @param flights        航班信息
     * @param k              最大换乘数
     * @param curr           当前节点
     */
    private void addNextCitiesToMinWeights(Map<Integer, Node> minWeights, Queue<Node> workQueue, int[][] flights, int k, Node curr) {
        for (int i = 0; i < flights[curr.city].length; i++) {
            int currStops = curr.stops;
            int currPrice = curr.price;
            Node tar = minWeights.get(i);
            int tarPrice = Objects.nonNull(tar) ? tar.price : Integer.MAX_VALUE;
            if (flights[curr.city][i] > 0 && currStops <= k && flights[curr.city][i] + currPrice < tarPrice) {
                Node tarNode = Node.of(i, flights[curr.city][i] + currPrice, currStops + 1);
                minWeights.put(i, tarNode);
                workQueue.add(tarNode);
            }
        }
    }

    private int theCheapestPrice(Map<Integer, Node> minWeights, int dst) {
        Node node = minWeights.get(dst);
        if (Objects.nonNull(node)) {
            return node.price;
        }
        return -1;
    }

    static class Node {

        public int city;

        public int price;

        public int stops;

        public static Node of(int city, int price, int stops) {
            Node tuple = new Node();
            tuple.city = city;
            tuple.price = price;
            tuple.stops = stops;
            return tuple;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "city=" + city +
                    ", price=" + price +
                    ", stops=" + stops +
                    '}';
        }
    }
}