import java.util.ArrayList;
import java.util.Arrays;

public class Pathfinder {
    public static ArrayList<ArrayList<Integer>> map = new ArrayList<>();
    
    public static void main(String[] args) {
        initializeTestMap();
        ArrayList<String> path = findPath();
        System.out.println("Path coordinates:");
        System.out.println(path);
        printPathVisualization(path);
    }
    
    private static void initializeTestMap() {
        map.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0)));
        map.add(new ArrayList<>(Arrays.asList(9, 0, 0, 1, 0, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0)));
        map.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1)));
    }
    
    public static ArrayList<String> findPath() {
        ArrayList<String> path = new ArrayList<>();
        ArrayList<int[]> starts = findStarts();
        
        for (int[] start : starts) {
            boolean[][] visited = new boolean[map.size()][map.get(0).size()];
            if (dfs(start[0], start[1], visited, path)) {
                return path;
            }
            path.clear();
        }
        
        return path;
    }
    
    private static boolean dfs(int row, int col, boolean[][] visited, ArrayList<String> path) {
        if (row < 0 || row >= map.size() || col < 0 || col >= map.get(0).size() || 
            map.get(row).get(col) != 1 || visited[row][col]) {
            return false;
        }
        
        visited[row][col] = true;
        path.add("A[" + row + "][" + col + "]");
        
        if (path.size() > 1 && isOnDifferentWall(path)) {
            return true;
        }
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            if (dfs(row + dir[0], col + dir[1], visited, path)) {
                return true;
            }
        }
        
        path.remove(path.size() - 1);
        return false;
    }
    
    private static ArrayList<int[]> findStarts() {
        ArrayList<int[]> starts = new ArrayList<>();
        int rows = map.size();
        int cols = map.get(0).size();
        
        for (int col = 0; col < cols; col++) {
            if (map.get(0).get(col) == 1) {
                starts.add(new int[]{0, col});
            }
            if (map.get(rows - 1).get(col) == 1) {
                starts.add(new int[]{rows - 1, col});
            }
        }
        
        for (int row = 1; row < rows - 1; row++) {
            if (map.get(row).get(0) == 1) {
                starts.add(new int[]{row, 0});
            }
            if (map.get(row).get(cols - 1) == 1) {
                starts.add(new int[]{row, cols - 1});
            }
        }
        
        return starts;
    }
    
    private static boolean isOnDifferentWall(ArrayList<String> path) {
        if (path.size() < 2) return false;
        
        String first = path.get(0);
        String last = path.get(path.size() - 1);
        
        int firstRow = Integer.parseInt(first.split("\\[|\\]")[1]);
        int firstCol = Integer.parseInt(first.split("\\[|\\]")[3]);
        int lastRow = Integer.parseInt(last.split("\\[|\\]")[1]);
        int lastCol = Integer.parseInt(last.split("\\[|\\]")[3]);
        
        boolean firstOnTop = firstRow == 0;
        boolean firstOnBottom = firstRow == map.size() - 1;
        boolean firstOnLeft = firstCol == 0;
        boolean firstOnRight = firstCol == map.get(0).size() - 1;
        
        boolean lastOnTop = lastRow == 0;
        boolean lastOnBottom = lastRow == map.size() - 1;
        boolean lastOnLeft = lastCol == 0;
        boolean lastOnRight = lastCol == map.get(0).size() - 1;
        
        return (firstOnTop && (lastOnLeft || lastOnRight)) ||
               (firstOnBottom && (lastOnLeft || lastOnRight)) ||
               (firstOnLeft && (lastOnTop || lastOnBottom)) ||
               (firstOnRight && (lastOnTop || lastOnBottom));
    }
    
    private static void printPathVisualization(ArrayList<String> path) {
        System.out.println("\nPath visualization:");
        
        char[][] visualization = new char[map.size()][map.get(0).size()];
        for (int i = 0; i < visualization.length; i++) {
            Arrays.fill(visualization[i], ' ');
        }
        
        for (String coord : path) {
            int row = Integer.parseInt(coord.split("\\[|\\]")[1]);
            int col = Integer.parseInt(coord.split("\\[|\\]")[3]);
            visualization[row][col] = '1';
        }
        
        for (char[] row : visualization) {
            System.out.println(Arrays.toString(row));
        }
    }
}