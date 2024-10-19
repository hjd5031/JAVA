import java.util.Scanner;

public class Hw1_3 {
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        int N;
        int tmp;
        int check = 0;
        System.out.print("정수 몇개?");
        N = input.nextInt();//배열 크기
//        System.out.println(Math.random()*100);
        int []arr = new int[N];//입력 받은 배열 크기에 맞게 할당
        for(int i = 0;i<N;i++){
            tmp = (int)(Math.random()*100+1);//1~100까지 랜덤
            for(int j = 0;j<i;j++) {//중복 검사
                check = 0;
                if(arr[j] == tmp){
                    check = 1;
                    break;
                }
            }
            if(check == 1)i--;
            else arr[i] = tmp;
        }
        int cnt = 0;
        for(var e : arr){//모두 출력
            cnt++;
            System.out.print(e+" ");
            if(cnt%10 == 0) System.out.println();//한줄에 숫자 10개씩 출력
        }
    }
}
