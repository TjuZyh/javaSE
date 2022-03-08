package com.javaSE.enumerate;

public class SeasonTest2 {
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        System.out.println(summer.toString());

        System.out.println();
        //values():返回枚举类型的对象数组
        Season1[] seasons = Season1.values();
        for(Season1 season1 : seasons){
            System.out.println(season1);
        }
        System.out.println();
        //valueOf(objName):返回枚举类中对象名是objName的对象
        Season1 winter = Season1.valueOf("WINTER");
        System.out.println(winter);
    }
}

enum Season1{

    //1. 提供当前枚举类的对象，多个对象之间用逗号隔开，末尾对象分号结束
    SPRING("春天", "春暖花开"),
    SUMMER("夏天", "夏日炎炎"),
    AUTUMN("秋天", "秋高气爽"),
    WINTER("冬天", "冰天雪地");

    //2. 声明Season对象的属性
    private final String seasonName;
    private final String seasonDesc;

    //3. 私有化构造器，并给对象属性赋值
    private Season1(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }
}
