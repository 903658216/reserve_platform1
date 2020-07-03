package com.jh.micro_search;

import com.jh.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunningApplication  implements CommandLineRunner {

    @Autowired
    private ISearchService iSearchService;


    /**
     * 在该springboot项目启动时，会触发该方法
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        //springboot项目启动时触发
        System.out.println("搜索服务已经启动，正在尝试构建索引库。。。");
        //构建索引库
        iSearchService.createIndex();

    }
}
