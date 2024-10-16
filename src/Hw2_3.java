public class Hw2_2 {
    public static void main(String[]args){
        int tmp;
        int i ,j;
        int [][]arr = new int[4][4];
        for(int cnt = 0;cnt<10;cnt++){
            i = (int)((Math.random()*10)%4);
            j = (int)((Math.random()*10)%4);
            tmp = (int)(Math.random()*10+1);
            if(arr[i][j]>0)cnt--;
            else arr[i][j] = tmp;

        }
        int cnt = 0;
        for(i = 0;i<4;i++){
            for(j = 0;j<4;j++){
                System.out.print(arr[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
