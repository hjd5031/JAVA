import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;
public class Hw1_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String operator;//연산자
        BigDecimal operand1;//1번 피연산자
        BigDecimal operand2;//2번 피연산자
        BigDecimal result = new BigDecimal(0);//연산 결과
        System.out.print("연산>>");
        operand1 = input.nextBigDecimal();
        operator = input.next();
        operand2 = input.nextBigDecimal();
        switch (operator){
            case "+":
                result = operand1.add(operand2);        //"덧셈"연산
                break;
            case "-":
                result = operand1.subtract(operand2);   //"뺄셈"연산

                break;
            case "*":
                result = operand1.multiply(operand2);       //"곱셈"연산

                break;
            case "/": {
                if (operand2.equals(result)) {                      //"나눗셈"연산
                    System.out.println("0으로 나눌 수 없습니다.");//0으로 나눌 시 오류문자 출력 종료
                    System.exit(-1);
                }
                result = operand1.divide(operand2, MathContext.DECIMAL128);

                }
                break;
            default: {
                System.out.println("입력이 잘못됐습니다");
                System.exit(-1);
            }
        }
        System.out.println(operand1 + operator+operand2 +"의 계산 결과는 "+result);
        input.close();
    }
}