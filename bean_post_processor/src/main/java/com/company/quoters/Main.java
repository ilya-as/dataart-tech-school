package com.company.quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
    //    while (true){
//            Thread.sleep(1000);
        //    context.getBean(Quoter.class).sayQuote();
        context.getBean(Quoter.class).tell();
  //      }
    }
}
