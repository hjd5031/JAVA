package Assignment2;

import java.util.Arrays;
import java.util.Scanner;
class MySeatInfo {
    public String[] seat;

    public MySeatInfo(int n) {
        seat = new String[n];
        Arrays.fill(seat, "---");

    }

    public void printSeatStatus() {
        for (var e : seat) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    public void reserveSeat(String n, int num) {
        if (seat[num].equals("---")) {
            seat[num] = n;
        } else System.out.println("Seat Already Taken!!!");
    }
    public void cancelReservation(String n){
        boolean found = false;
        for(int i = 0;i<seat.length;i++){
            if(seat[i].equals(n)){
                found = true;
                seat[i] = "---";
            }
        }
        if(!found){
            System.out.println("Name Not Found Try Again");
        }
    }
}
class MyConcertReservation{
    Scanner sc = new Scanner(System.in);
    private MySeatInfo s[];
    private String grade[] = {"S","A","B"};
    MyConcertReservation(){
        s = new MySeatInfo[3];
        for(int i = 0;i<s.length;i++)
            s[i] = new MySeatInfo(10);
    }
    public void reservationSystem(){
        int select;
        int seatGrade;
        String name;
        int seatNumber;

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
                    checkSeatStatus(seatGrade);
                    System.out.print("이름>>"); name = sc.next();
                    System.out.print("번호>>"); seatNumber = sc.nextInt();
                    if(seatNumberCheck(seatNumber))break;
                    reserve(name, seatNumber-1, seatGrade);
                }
                break;
                case 2: {
                    for(int i = 1;i<=3;i++){
                        checkSeatStatus(i);
                    }
                    System.out.println("<<<조회를 완료하였습니다.>>>");
                }
                break;
                case 3:{
                    System.out.print("좌석 S:1, A:2, B:3>>");seatGrade = sc.nextInt();
                    if(seatGradeCheck(seatGrade))break;
                    checkSeatStatus(seatGrade);
                    System.out.print("이름>>");name = sc.next();
                    cancel(seatGrade, name);
                }
                break;
                case 4:
                    System.exit(-1);
                default:
                    System.out.println("Wrong Input Please Try Again");
            }
        }
    }
    public void reserve(String n, int num, int grade){
        switch (grade){
            case 1:
                s[0].reserveSeat(n,num);
                break;
            case 2:
                s[1].reserveSeat(n,num);
                break;
            case 3:
                s[2].reserveSeat(n,num);
                break;
            default:
                System.out.println("Wrong Input Try again");
                break;
        }
    }
    public void checkSeatStatus(int grade){
        switch (grade){
            case 1:{
                System.out.print("S>>>");
                s[0].printSeatStatus();
                break;
            }
            case 2:{
                System.out.print("A>>>");
                s[1].printSeatStatus();
                break;
            }
            case 3:{
                System.out.print("B>>>");
                s[2].printSeatStatus();
                break;
            }
            default:
                System.out.println("Wrong Input Try again");
                break;

        }
    }

    public void cancel(int grade, String name){
        switch (grade){
            case 1:
                s[0].cancelReservation(name);
                break;
            case 2:
                s[1].cancelReservation(name);
                break;
            case 3:
                s[2].cancelReservation(name);
                break;
            default:
                System.out.println("Wrong Input Try again");
                break;

        }
    }
    public boolean seatGradeCheck(int sg){
        if(sg!=1&&sg!=2&&sg!=3){
            System.out.println("Wrong Seat Grade Try  Again");
            return true;
        }
        return false;
    }
    public boolean seatNumberCheck(int sn){
        if(sn<1||sn>10){
            System.out.println("Wrong Seat Number Try  Again");
            return true;
        }
        return false;
    }
}
public class Hw2_1{

    public static void main(String[]args){
        //공연은 하루에 한 번
        //S석, A석, B석으로 나뉘며 각각 10개 좌석 존재
        //예약, 조회, 취소, 끝내기 존재
        //생성자 좌석타입, 예약자 이름, 좌석 번호
        MyConcertReservation ConcertHall = new MyConcertReservation();
        ConcertHall.reservationSystem();

    }
}
