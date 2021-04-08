package com.kuang;

public class Testlambda2 {

    public static void main(String[] args) {
        Ilove l = null;
        //1.lambda表示简化
//        Ilove l = (int a) -> {
//            System.out.println("i love you-->" + a);
//        };
//
//        //简化1.参数类型
//        l = (a) -> {
//            System.out.println("i love you-->" + a);
//        };

        //简化2.简化括号
//        l = a -> {
//            System.out.println("i love you-->" + a);
//        };

        //简化3.去掉花括号
        l = a -> System.out.println("i love you-->" + a);

        //总结：
        //lambda表达式只能有一行代码的情况下才能简化为一行，如果有多行，那么就用代码块包裹。
        //前提是接口为函数式接口
        //多个接口可以去掉参数类型，要去掉就都去掉，必须加上括号


        l.love(520);
    }
}

interface Ilove {
    void love(int a);

}


