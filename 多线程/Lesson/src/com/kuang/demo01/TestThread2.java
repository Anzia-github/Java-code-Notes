package com.kuang.demo01;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

//练习Thread，实现多线程同步下载图片
public class TestThread2 extends Thread {

    private String url;
    private String name;

    public TestThread2(String url, String name) {
        this.url = url;
        this.name = name;
    }

    //下载图片线程的执行体
    @Override
    public void run() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为：" + name);

    }

    public static void main(String[] args) {
        TestThread2 t1 = new TestThread2("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片1.jpg");
        TestThread2 t2 = new TestThread2("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片2.jpg");
        TestThread2 t3 = new TestThread2("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片3.jpg");

        t1.start();
        t2.start();
        t3.start();
    }
}

class WebDownloader {
    //下载方法
    public void downloader(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常，downloader方法出现异常");
        }
    }
}