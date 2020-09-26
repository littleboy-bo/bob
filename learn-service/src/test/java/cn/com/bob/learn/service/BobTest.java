package cn.com.bob.learn.service;

public class BobTest {

    public static void main(String[] args) {
        System.out.println("---------------------------------------------------------start----------------------------------------------------------");
        /**
         * 基本类型(primitive types), 共有8种，即int, short, long, byte, float, double, boolean, char(注意，并没有string的基本类型)
         * 栈有一个很重要的特殊性，就是存在栈中的数据可以共享
         * 包装类数据,如Integer, String, Double等将相应的基本数据类型包装起来的类。这些类数据全部存在于堆中，Java用new()语句来显式地告诉编译器，
         * 在运行时才根据需要动态创建，因此比较灵活，但缺点是要占用更多的时间。
         */

        int i = 1;
        int j = 1;

        System.out.println(i==j);

        System.out.println("------------------------------------------------------华丽的分割线------------------------------------------------------");
        /**         Java把内存划分成两种：一种是栈内存，一种是堆内存。  栈内存可以共享，new()方法创建的是堆内存
         *      (1) 使用诸如String str = "abc"；的格式定义类时 ,对象可能并没有被创建！唯一可以肯定的是，指向 String类的引用被创建了
         * 除非你通过new()方法来显要地创建一个新的对象。因此，更为准确的说法是，我们创建了一个指向String类的对象的引用变量str，
         * 这个对象引用变量指向了某个值为"abc"的String类
         *      (2) 使用String str = "abc"；的方式，可以在一定程度上提高程序的运行速度，因为JVM会自动根据栈中数据的实际情况
         * 来决定是否有必要创建新对象。而对于String str = new String("abc")；的代码，则一概在堆中创建新对象，而不管
         * 其字符串值是否相等
         *      (3) 当比较包装类里面的数值是否相等时，用equals()方法；当测试两个包装类的引用是否指向同一个对象时，用==。
         *      (4) 由于String类的immutable性质，当String变量需要经常变换其值时，应该考虑使用StringBuffer类，以提高程序效率。
         */
        String str1 = new String("asd");
        String str2 = new String("asd");

        String str3 = "asd";
        String str4 = "asd";

        System.out.println(str1==str2);
        System.out.println(str3==str4);
        System.out.println(str1==str3);

        System.out.println("----------------------------------------------------------end-----------------------------------------------------------");
    }
}
