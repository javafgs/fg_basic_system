package com.fg.system.modules.admin.job.task;

import com.fg.system.common.annotation.CronTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CronTag("testTask")
public class TestTask {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }

    public void test2() {
        log.info("我是不带参数的test2方法，正在被执行");
    }
}
