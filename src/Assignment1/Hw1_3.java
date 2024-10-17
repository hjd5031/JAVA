package Assignment1;

import java.util.Scanner;



public class Hw1_3 {
    public static void main(String[]args){
        Scanner input = new Scanner(System.in);
        int N;
        int tmp;
        int check = 0;
        N = input.nextInt();
//        System.out.println(Math.random()*100);
        int []arr = new int[N];
        for(int i = 0;i<N;i++){
            tmp = (int)(Math.random()*100+1);
            for(int j = 0;j<i;j++) {
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
        for(var e : arr){
            cnt++;
            System.out.print(e+" ");
            if(cnt%10 == 0) System.out.println();
        }
    }
}
