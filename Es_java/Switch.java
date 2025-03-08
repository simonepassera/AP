import java.util.Scanner;

class Switch {
  public static void main(String[] args) {
    int z = -1;

    Scanner s = new Scanner(System.in);

    int i = s.nextInt();
    
    switch (i) {
        case 0:
            z = 10; 
            break;
        case 1:
            z = 20;
            break;
        case 2:
            z = 30;
            break;
        case 3:
            z =40;
            break;
        default:
            System.out.println("Default");
    }

    System.out.println("z --> " + z);
  }
}