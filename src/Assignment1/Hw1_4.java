public class Hw1_4 {
    public static void main(String[]args){
        int tmp;
        int i ,j;
        int [][]arr = new int[4][4];//4 X 4 배열 선언
        for(int cnt = 0;cnt<10;cnt++){
            i = (int)((Math.random()*10)%4);//열값 랜덤
            j = (int)((Math.random()*10)%4);//행값 랜덤
            tmp = (int)(Math.random()*10+1);//저장할 값
            if(arr[i][j]>0)cnt--;
            else arr[i][j] = tmp;

        }

        for(int []e:arr){//for-each문으로 이중배열 출력
            for(var x : e){
                System.out.print(x+ "    ");
            }
            System.out.println();
        }
    }
}
