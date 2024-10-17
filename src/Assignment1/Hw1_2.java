package Assignment1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;
public class Hw1_2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String operator;
        BigDecimal operand1;
        BigDecimal operand2;
        BigDecimal result = new BigDecimal(0);
        System.out.print("연산>>");
        operand1 = input.nextBigDecimal();
        operator = input.next();
        operand2 = input.nextBigDecimal();
        switch (operator){
            case "+":
                result = operand1.add(operand2);
                break;
            case "-":
                result = operand1.subtract(operand2);

                break;
            case "*":
                result = operand1.multiply(operand2);

                break;
            case "/": {
                if (operand2.equals(result)) {
                    System.out.println("0으로 나눌 수 없습니다.");
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