import java.util.Scanner;
import java.lang.Math;
public class Hw1_1 {
    public static void main(String []args){
        int pos_x1, pos_y1, r1;
        int pos_x2, pos_y2, r2;
        int r3;
        double dist;
        Scanner scanner = new Scanner(System.in);
        System.out.print("첫번째 원의 중심과 반지름 입력>>");
        pos_x1 = scanner.nextInt();
        pos_y1 = scanner.nextInt();
        r1 = scanner.nextInt();
        System.out.print("두번째 원의 중심과 반지름 입력>>");
        pos_x2 = scanner.nextInt();
        pos_y2 = scanner.nextInt();
        r2 = scanner.nextInt();
        r3 = r2 + r1;
        dist = Math.sqrt((pos_x1-pos_x2)*(pos_x1-pos_x2)+(pos_y1-pos_y2)*(pos_y1-pos_y2));
        if(r3>=dist) System.out.println("두 원은 서로 겹친다.");
        else System.out.println("두 원은 서로 겹치지 않는다.");
        scanner.close();

    }
}
