import java.util.Arrays;
import java.util.Scanner;
class MyConcertReservation{
    int S;                  //remaining seats
    int A;
    int B;
    String [] SS;      //name of bookers
    String [] AS;
    String [] BS;
    MyConcertReservation(){
        S = 10;
        A = 10;
        B = 10;
        SS = new String[10];
        Arrays.fill(SS, "---");
        AS = new String[10];
        Arrays.fill(AS, "---");
        BS = new String[10];
        Arrays.fill(BS, "---");

    }
//    public boolean reserveAvailable(int s, int grade){
//        switch (grade){
//            case 1:{
//                if(SS[s].equals("---"))return true;
//            }
//            break;
//            case 2:{
//                if(AS[s].equals("---"))return true;
//            }
//            break;
//            case 3:{
//                if(BS[s].equals("---"))return true;
//                break;
//            }
//            default:
//                System.out.println("Wrong Input Try again");
//                return false;
//        }
//        return false;
//    }
    public void reserveSeat(String n, int s, int grade){
        switch (grade){
            case 1:{
                if(SS[s].equals("---")){
                    SS[s] = n;
                }
                else System.out.println("Seat Already Taken!!!");
                break;
            }
            case 2:{
                if(AS[s].equals("---")){
                    AS[s] = n;
                }
                else System.out.println("Seat Already Taken!!!");
                break;
            }
            case 3:{
                if(BS[s].equals("---")){
                    BS[s] = n;
                }
                else System.out.println("Seat Already Taken!!!");
                break;
            }
            default:
                System.out.println("Wrong Input Try again");
                break;

        }
    }
    public void checkSeatStatus(int grade){
        switch (grade){
            case 1:{
                System.out.print("S>>");
                printSeatStatus(SS);//print S-Grade seat status
                break;
            }
            case 2:{
                System.out.print("A>>");
                printSeatStatus(AS);//print A-Grade seat status
                break;
            }
            case 3:{
                System.out.print("B>>");
                printSeatStatus(BS);//print B-Grade seat status
                break;
            }
            default:
                System.out.println("Wrong Input Try again");
                break;

        }
    }
    public void printSeatStatus(String in[]){
        for(var e : in){
            System.out.print(e + " ");
        }
        System.out.println();
    }
    public void findName(String S[], String n ){
        boolean found = false;
        for(int i = 0;i<10;i++){
            if(S[i].equals(n)){
                found = true;
                S[i] = "---";
            }
        }
        if(!found){
            System.out.println("Name Not Found Try Again");
        }
    }
    public void cancelReservation(int grade, String name){
        switch (grade){
            case 1:{
                findName(SS, name);
                break;
            }
            case 2:{
                findName(AS, name);
                break;
            }
            case 3:{
                findName(BS, name);
                break;
            }
            default:
                System.out.println("Wrong Input Try again");
                break;

        }
    }
}
public class Hw2_1{
    public static boolean seatGradeCheck(int sg){
        if(sg!=1&&sg!=2&&sg!=3){
            System.out.println("Wrong Seat Grade Try  Again");
            return true;
        }
        return false;
    }
    public static boolean seatNumberCheck(int sn){
        if(sn<1||sn>10){
            System.out.println("Wrong Seat Number Try  Again");
            return true;
        }
        return false;
    }
    public static void main(String[]args){
        //공연은 하루에 한 번
        //S석, A석, B석으로 나뉘며 각각 10개 좌석 존재
        //예약, 조회, 취소, 끝내기 존재
        //생성자 좌석타입, 예약자 이름, 좌석 번호
        int select;
        int seatGrade;
        String name;
        int seatNumber;
        MyConcertReservation ConcertHall = new MyConcertReservation();
        Scanner sc = new Scanner(System.in);
        System.out.println("명품 콘서트 홀 예약 시스템입니다.");
        while(true){
        System.out.print("예약:1, 조회:2, 취소:3, 끝내기:4>>");
            select = sc.nextInt();
            switch (select){
                case 1: {//reserve
                    System.out.print("좌석구분 S(1), A(2), B(3)>>");
                    seatGrade = sc.nextInt();
                    if(seatGradeCheck(seatGrade))break;
                    ConcertHall.checkSeatStatus(seatGrade);
                    System.out.print("이름>>"); name = sc.next();
                    System.out.print("번호>>"); seatNumber = sc.nextInt();
                    if(seatNumberCheck(seatNumber))break;
                    ConcertHall.reserveSeat(name, seatNumber-1, seatGrade);
                }
                break;
                case 2: {
                    for(int i = 1;i<=3;i++){
                        ConcertHall.checkSeatStatus(i);
                    }
                    System.out.println("<<<조회를 완료하였습니다.>>>");
                }
                break;
                case 3:{
                    System.out.print("좌석 S:1, A:2, B:3>>");seatGrade = sc.nextInt();
                    if(seatGradeCheck(seatGrade))break;
                    ConcertHall.checkSeatStatus(seatGrade);
                    System.out.print("이름>>");name = sc.next();
                    ConcertHall.cancelReservation(seatGrade, name);
                }
                break;
                case 4:
                    System.exit(-1);
                default:
                    System.out.println("Wrong Input Please Try Again");
            }
        }

    }
}
