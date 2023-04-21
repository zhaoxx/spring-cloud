package com.mia.client.principle;

import java.math.BigDecimal;

/**
 * @Description
 * @Author zhaoxianxing
 * @Date 2023/4/21 9:26
 */
public class OpenClose {
    public static void main(String[] args) {
        FootballDiscountCourse footballDiscountCourse = new FootballDiscountCourse("足球课", new BigDecimal(100));
        System.out.println("课程名称：" + footballDiscountCourse.getName()
                + "\n原价：" + footballDiscountCourse.getPrice()
                + "\n折扣价：" + footballDiscountCourse.getDiscountPrice());
    }
}

abstract class AbstractCourse {
    abstract String getName();
    abstract BigDecimal getPrice();
}

class FootballCourse extends AbstractCourse {
    private String name;
    private BigDecimal price;

    public FootballCourse(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public BigDecimal getPrice() {
        return price;
    }
}

class FootballDiscountCourse extends FootballCourse {
    public FootballDiscountCourse(String name, BigDecimal price) {
        super(name, price);
    }

    public BigDecimal getDiscountPrice() {
        return super.getPrice().multiply(new BigDecimal(0.8));
    }
}