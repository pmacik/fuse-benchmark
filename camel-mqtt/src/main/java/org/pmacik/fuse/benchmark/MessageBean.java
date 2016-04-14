package org.pmacik.fuse.benchmark;

public class MessageBean {
   public String sayHello(String msg) {
      System.out.println("Hello" + msg);
      return "Hello " + msg;
   }
}
