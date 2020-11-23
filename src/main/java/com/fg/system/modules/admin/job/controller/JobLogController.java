package com.fg.system.modules.admin.job.controller;


import com.fg.system.common.annotation.Log;
import com.fg.system.modules.base.BaseController;
import com.fg.system.common.api.FgResult;
import com.fg.system.common.util.FileUtils;
import com.fg.system.common.util.page.QueryRequest;
import com.fg.system.modules.admin.job.entity.JobLog;
import com.fg.system.modules.admin.job.service.JobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  t_job_log 前端控制器
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
@Controller
@RequestMapping("/admin")
public class JobLogController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLogService jobLogService;

    @Log("获取调度日志信息")
    @GetMapping("/jobLog")
    public String jobLog() {
        return "admin/job/jobLog/jobLog";
    }

    @GetMapping("/jobLog/list")
    @ResponseBody
    public Map<String, Object> jobLogList(QueryRequest request, JobLog log) {
        return selectByPageNumSize(request, () -> this.jobLogService.findAllJobLog(log));
    }

    @PostMapping("/jobLog/excel")
    @ResponseBody
    public FgResult jobLogExcel(JobLog jobLog) {
        try {
            List<JobLog> list = this.jobLogService.findAllJobLog(jobLog);
            return FileUtils.createExcelByPOIKit("调度日志表", list, JobLog.class);
        } catch (Exception e) {
            log.error("导出调度日志信息Excel失败", e);
            return FgResult.failed("导出Excel失败，请联系网站管理员！");
        }
    }

    @PostMapping("/jobLog/csv")
    @ResponseBody
    public FgResult jobLogCsv(JobLog jobLog) {
        try {
            List<JobLog> list = this.jobLogService.findAllJobLog(jobLog);
            return FileUtils.createCsv("调度日志表", list, JobLog.class);
        } catch (Exception e) {
            log.error("导出调度日志信息Csv失败", e);
            return FgResult.failed("导出Csv失败，请联系网站管理员！");
        }
    }
}

