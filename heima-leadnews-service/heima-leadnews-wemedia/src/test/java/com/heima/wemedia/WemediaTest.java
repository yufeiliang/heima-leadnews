package com.heima.wemedia;


import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.file.service.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class WemediaTest {

    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testScanText() throws Exception {
        Map map = greenTextScan.greenTextScan("冰毒,我是一个好人");
        System.out.println(map);
    }

    @Test
    public void testScanImage() throws Exception {
//        byte[] bytes = fileStorageService.downLoadFile("http://192.168.200.130:9000/leadnews/2021/04/26/ef3cbe458db249f7bd6fb4339e593e55.jpg");
        byte[] bytes = fileStorageService.downLoadFile("http://192.168.71.82:9000/leadnews/2023/02/07/fc2fa34c-0c5a-461d-86c5-a374da6ae5d5.jpg");
        byte[] bytes2 = fileStorageService.downLoadFile("http://192.168.71.82:9000/leadnews/2023/02/07/be96bada-67d0-425b-8f77-6bec32b0d9b0.jpg");
        List<byte[]> images=new ArrayList<>();
        images.add(bytes);
        images.add(bytes2);
        Map map = greenImageScan.greenImageScan(images);
        System.out.println(map);
    }
}