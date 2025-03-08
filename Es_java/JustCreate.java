import java.util.Arrays;

class JustCreate {
  public static void main(String[] args) throws InterruptedException {
    for (int i = 0; i < 5000000; i++) {
      int[] array = new int[10000];
      Arrays.fill(array, i);
      String str = new String(array.toString());

      if (i % 1000 == 0) {
        System.out.println("Objects -> " + i);
        Thread.sleep(50);
      }
    }
  }
}
