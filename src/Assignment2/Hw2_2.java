package Assignment2;

abstract class PairMap{
    protected String keyArray[];
    protected String valueArray[];
    abstract String get(String key);
    abstract void put(String key, String value);
    abstract String delete(String key);//key, value delete return value
    abstract int length();//num of items
}
class Dictionary extends PairMap{
    int num;
    Dictionary(int N){
        num = 0;
        keyArray = new String[N];
        valueArray = new String[N];
    }
    @Override
    public int length(){
        return num;
    }
    @Override
    public void put(String key, String value){
        for(int i = 0;i<num;i++){
            if(keyArray[i].equals(key)){
                valueArray[i] = value;
                return;
            }
        }
        keyArray[num] = key;
        valueArray[num++] = value;
    }
    @Override
    public String get(String k){
        int idx = -1;
        for(int i = 0;i<num;i++){
            if(keyArray[i].equals(k))idx = i;
        }
        if(idx == -1) return null;
        return valueArray[idx];
    }
    @Override
    public String delete(String k){
        int idx=0;
        for(int i = 0;i<num;i++){
            if(keyArray[i].equals(k)){
                keyArray[i] = " ";
                idx = i;
                num--;
            }
        }
        return valueArray[idx];
    }
}
public class Hw2_2 {
    public static void main(String[]args){
        Dictionary dic = new Dictionary(10);
        dic.put("황기태", "자바");
        dic.put("이재문","파이썬");
        dic.put("이재문","C++");//이재문의 값을 C++로 수정
        System.out.println("이재문의 값은 " +  dic.get("이재문"));
        System.out.println("황기태의 값은 " +  dic.get("황기태"));
        dic.delete("황기태");
        System.out.println("황기태의 값은 " + dic.get("황기태"));
    }
}
