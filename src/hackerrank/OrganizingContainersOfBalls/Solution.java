class Solution {
    // Complete the organizingContainers function below.
    static String organizingContainers(int[][] container) {
        /*
        Solution:
        Because the number of swaps is not limited, it's possible to always move any ball to any container.
        However, a swap cannot change the number of balls in a container, since when swapping a ball is removed but another is added.
        Hence to solve the problem it is enough to check that for each ball type there is an available container that has enough capacity to hold all balls of that type. Rationale: Any container with additional capacity would have more than one type of balls, which the problem statement disallows. Any container with too little capacity would mean balls would be left without a container, which also is disallowed.
        */

        long[] perTypeCounter = new long[container.length];
        long[] containerSizes = new long[container.length];
        for(int i=0; i < container.length; i++) {
            int[] typeCount = container[i];
            for (int type=0; type < typeCount.length; type++) {
                long count = typeCount[type];
                perTypeCounter[type] += count;
                containerSizes[i] += count;
            }
        }
        Arrays.sort(containerSizes);
        Arrays.sort(perTypeCounter);
        for(int i=0; i< containerSizes.length; i++) {
            if (perTypeCounter[i] != containerSizes[i]) {
                return "Impossible";
            }
        }
        return "Possible";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[][] container = new int[n][n];

            for (int i = 0; i < n; i++) {
                String[] containerRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < n; j++) {
                    int containerItem = Integer.parseInt(containerRowItems[j]);
                    container[i][j] = containerItem;
                }
            }

            String result = organizingContainers(container);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}