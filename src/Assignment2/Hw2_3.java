import java.util.Scanner;
abstract class Shape{
    private Shape next;
    public Shape(){next = null;}
    public void setNext(Shape obj){next = obj;}
    public Shape getNext(){return next;}
    public abstract void draw();
}
class Line extends Shape {
    @Override
    public void draw(){
        System.out.println("Line");
    }
}
class Rect extends Shape{
    @Override
    public void draw(){
        System.out.println("Rect");
    }
}
class Circle extends Shape{
    @Override
    public void draw(){
        System.out.println("Circle");
    }
}
class GraphicEditor{
    private final String name;
    Scanner sc = new Scanner(System.in);
    private Shape head = null, tail = null;

    public GraphicEditor(String n) {
        name = n;
    }

    public void graphicEditorSystem() {
        System.out.println("그래픽 에디터 "+name+"을 실행합니다.");
        while(true) {
            System.out.print("삽입(1), 삭제(2), 모두 보기(3), 종료(4)>>");
            int select = sc.nextInt();
            switch(select) {
                case 1:
                    System.out.print("Line(1), Rect(2), Circle(3)>>");
                    int addNum = sc.nextInt();
                    insertMyShape(addNum);
                    break;
                case 2:
                    System.out.print("삭제할 도형의 위치>>");
                    int delNum = sc.nextInt();
                    deleteMyShape(delNum);
                    break;
                case 3:
                    printMyShape();
                    break;
                case 4:
                    System.out.println(name+"을 종료합니다.");
                    return;
                default:
                    System.out.println("삭제할 수 없습니다.");
            }
        }
    }

    public void insertMyShape(int addNum) {
        Shape myShape;
        switch(addNum) {
            case 1:
                myShape = new Line();
                break;
            case 2:
                myShape = new Rect();
                break;
            case 3:
                myShape = new Circle();
                break;
            default:
                System.out.println("Wrong Input Please Try Again.");
                return;
        }
        if(head==null) {//공백리스트일때 head, tail에 shape연결
            head = myShape;
            tail = head;
        }
        else {
            tail.setNext(myShape);//shape연결
            tail = myShape;//tail 이동
        }
    }

    public void deleteMyShape(int delNum) {
        Shape cur = head;
        Shape tmp = head;
        int i;
        if(delNum==1) {//첫번째 리스트는 head에 저장돼있음
            if(head==tail) {//리스트가 하나 있을 때
                head = null;
                tail = null;
                return;
            }
            else {
                head = head.getNext();
                return;
            }
        }
        for(i=1; i<delNum; i++) {//삭제 위치까지 인덱스 이동
            tmp = cur;
            cur = cur.getNext();
            if(cur == null) {
                System.out.println("Number "+delNum+" node doesn't exist.");
                return;
            }
        }
        tmp.setNext(cur.getNext());
    }
    public void printMyShape() {
        Shape shape = head;
        while(shape != null) {//null일때까지 출력
            shape.draw();
            shape = shape.getNext();
        }
    }
}
public class Hw2_3 {
    public static void main(String[]args){
            GraphicEditor beauty = new GraphicEditor("beauty");
            beauty.graphicEditorSystem();
    }
}
