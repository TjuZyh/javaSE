package com.javaSE.networkProgramming;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

    @Test
    public void test1() throws UnknownHostException {

        //实例化
        InetAddress inetAddress1 = InetAddress.getByName("192.168.10.14");
        System.out.println(inetAddress1);

        InetAddress inetAddress2 = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress2);

        //获取本机Id
        System.out.println(InetAddress.getLocalHost());

        //获取域名
        System.out.println(inetAddress2.getHostName());

        //获取地址
        System.out.println(inetAddress2.getHostAddress());


    }
}
