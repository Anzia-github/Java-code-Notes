package com.kuang.demo02;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;

//练习Thread，实现多线程同步下载图片
public class TestCallable implements Callable<Boolean> {

    private String url;
    private String name;

    public TestCallable(String url, String name) {
        this.url = url;
        this.name = name;
    }

    //下载图片线程的执行体
    @Override
    public Boolean call() {
        WebDownloader webDownloader = new WebDownloader();
        webDownloader.downloader(url, name);
        System.out.println("下载了文件名为：" + name);
        return true;

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestCallable t1 = new TestCallable("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片1.jpg");
        TestCallable t2 = new TestCallable("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片2.jpg");
        TestCallable t3 = new TestCallable("https://i1.hdslb.com/bfs/face/abb12931aed341d6dcc67dd13162fddb35240622.jpg@68w_68h.webp", "图片3.jpg");

        //创建执行服务：
        ExecutorService ser = Executors.newFixedThreadPool(3);
        //提交执行：
        Future<Boolean> r1 = ser.submit(t1);
        Future<Boolean> r2 = ser.submit(t2);
        Future<Boolean> r3 = ser.submit(t3);
        //获取结果
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();
        //关闭服务
        ser.shutdownNow();
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