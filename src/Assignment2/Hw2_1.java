import java.util.Arrays;
import java.util.Scanner;
class MySeatInfo {//좌석의 정보가 저장되는 클래스
    public String[] seat;

    public MySeatInfo(int n) {//좌석 "---"로 초기화
        seat = new String[n];
        Arrays.fill(seat, "---");

    }

    public void printSeatStatus() {//for-each문으로 좌석 정보 출력
        for (var e : seat) {
            System.out.print(e + " ");
        }
        System.out.println();
    }

    public void reserveSeat(String n, int num) {//좌석 예약 여부 확인 후 예약
        if (seat[num].equals("---")) {
            seat[num] = n;
        } else System.out.println("Seat Already Taken!!!");
    }
    public void cancelReservation(String n){//입력된 이름이 있는지 확인 후 예약 취소
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
    private MySeatInfo []s;

    MyConcertReservation(){
        s = new MySeatInfo[3];
        for(int i = 0;i<s.length;i++)
            s[i] = new MySeatInfo(10);
    }
    public void reserve(String n, int num, int grade){//좌석별 예약
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
    public void checkSeatStatus(int grade){//좌석별 정보 확인
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

    public void cancel(int grade, String name){//좌석별 예약 취소
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
    public boolean seatGradeCheck(int sg){//좌석등급에 대한 번호가 옳게 들어왔는지 판단
        if(sg!=1&&sg!=2&&sg!=3){
            System.out.println("Wrong Seat Grade Try  Again");
            return true;
        }
        return false;
    }
    public boolean seatNumberCheck(int sn){//좌석 번호가 인덱스 안에 있는지 판단
        if(sn<1||sn>10){
            System.out.println("Wrong Seat Number Try  Again");
            return true;
        }
        return false;
    }
    public void reservationSystem(){//전체적인 예매 시스템
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
                case 2: {//print status
                    for(int i = 1;i<=3;i++){
                        checkSeatStatus(i);
                    }
                    System.out.println("<<<조회를 완료하였습니다.>>>");
                }
                break;
                case 3:{//cancel reservation
                    System.out.print("좌석 S:1, A:2, B:3>>");seatGrade = sc.nextInt();
                    if(seatGradeCheck(seatGrade))break;
                    checkSeatStatus(seatGrade);
                    System.out.print("이름>>");name = sc.next();
                    cancel(seatGrade, name);
                }
                break;
                case 4://exit program
                    System.exit(-1);
                default:
                    System.out.println("Wrong Input Please Try Again");
            }
        }
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
