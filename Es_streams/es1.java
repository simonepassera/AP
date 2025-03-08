import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class es1 {
    public static int sumOdd(List<Integer> l) {
        int sum = 0;

        for (int x : l)
            if (x % 2 != 0) sum += x;
            
        return sum;
    }

    public static int sumOddStream(List<Integer> l) {
        int sum = l.stream().filter(x -> ((x % 2) != 0)).reduce(0, (z, y) -> z + y);
        return sum;
    }
  public static void main(String[] args) {
    Integer v[] = {1, 2, 3, 4};
    List<Integer> l = new ArrayList<>(Arrays.asList(v));

    System.out.println(sumOdd(l));
    System.out.println(sumOddStream(l));
  }
}