package com.fg.system.modules.admin.job.controller;


import com.fg.system.common.annotation.Log;
import com.fg.system.modules.base.BaseController;
import com.fg.system.common.api.FgResult;
import com.fg.system.common.util.FileUtils;
import com.fg.system.common.util.page.QueryRequest;
import com.fg.system.modules.admin.job.entity.Job;
import com.fg.system.modules.admin.job.service.JobService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * t_job 前端控制器
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
@Controller
@Api(tags = "定时任务管理")
@RequestMapping("/admin")
public class JobController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobService jobService;

    @Log("获取定时任务信息")
    @GetMapping("/job")
    public String job() {
        return "admin/job/job/job";
    }

    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "新增",notes = "新增定时任务")
    @Log("新增定时任务")
    @PostMapping("/job/add")
    @ResponseBody
    public FgResult addJob(Job job) {
        try {
            this.jobService.addJob(job);
            return FgResult.success("新增定时任务成功！");
        } catch (Exception e) {
            log.error("新增定时任务失败", e);
            return FgResult.failed("新增定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 5)
    @PostMapping("/job/update")
    @ApiOperation(value = "修改",notes = "修改定时任务")
    @Log("修改定时任务 ")
    @ResponseBody
    public FgResult updateJob(Job job) {
        try {
            this.jobService.updateJob(job);
            return FgResult.success("修改定时任务成功！");
        } catch (Exception e) {
            log.error("修改定时任务失败", e);
            return FgResult.failed("修改定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "批量删除",notes = "批量删除定时任务")
    @ApiImplicitParam(required = true,value = "定时任务id",name = "ids")
    @Log("批量删除定时任务")
    @PostMapping("/job/deleteBatch")
    @ResponseBody
    public FgResult deleteJob(String ids) {
        try {
            this.jobService.deleteJobBatch(ids);
            return FgResult.success("删除定时任务成功！");
        } catch (Exception e) {
            log.error("删除定时任务失败", e);
            return FgResult.failed("删除定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 15)
    @ApiOperation(value = "分页查询",notes = "分页查询定时任务数据")
    @GetMapping("/job/list")
    @ResponseBody
    public Map<String, Object> jobList(QueryRequest request,Job job) {
        return selectByPageNumSize(request, () -> this.jobService.findAllJob(job));
    }

    @ApiOperationSupport(order = 20)
    @ApiImplicitParam(required = true,value = "定时任务id",name = "jobId")
    @GetMapping("/job/getJob")
    @ApiOperation(value = "获取单条数据",notes = "获取单条定时任务")
    @ResponseBody
    public FgResult getJob(Long jobId){
        try {
            Job job = this.jobService.getById(jobId);
            return FgResult.success(job);
        } catch (Exception e) {
            log.error("获取定时任务信息失败", e);
            return FgResult.failed("获取定时任务信息失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 25)
    @ApiOperation(value = "执行定时任务",notes = "执行定时任务")
    @ApiImplicitParam(required = true,value = "定时任务id",name = "jobIds")
    @Log("执行定时任务")
    @PostMapping("/job/run")
    @ResponseBody
    public FgResult runJob(String jobIds) {
        try {
            this.jobService.run(jobIds);
            return FgResult.success("执行定时任务成功！");
        } catch (Exception e) {
            log.error("执行定时任务失败", e);
            return FgResult.failed("执行定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 30)
    @ApiOperation(value = "暂停定时任务",notes = "暂停定时任务")
    @ApiImplicitParam(required = true,value = "定时任务id",name = "jobIds")
    @Log("暂停定时任务")
    @PostMapping("/job/pause")
    @ResponseBody
    public FgResult pauseJob(String jobIds) {
        try {
            this.jobService.pause(jobIds);
            return FgResult.success("暂停定时任务成功！");
        } catch (Exception e) {
            log.error("暂停定时任务失败", e);
            return FgResult.failed("暂停定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 35)
    @ApiOperation(value = "恢复定时任务",notes = "恢复定时任务")
    @ApiImplicitParam(required = true,value = "定时任务id",name = "jobIds")
    @Log("恢复定时任务")
    @PostMapping("/job/resume")
    @ResponseBody
    public FgResult resumeJob(String jobIds) {
        try {
            this.jobService.resume(jobIds);
            return FgResult.success("恢复定时任务成功！");
        } catch (Exception e) {
            log.error("恢复定时任务失败", e);
            return FgResult.failed("恢复定时任务失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 40)
    @ApiOperation(value = "导出Excel",notes = "导出定时任务表Excel")
    @Log("导出定时任务表Excel")
    @PostMapping("/job/excel")
    @ResponseBody
    public FgResult jobExcel(Job job) {
        try {
            List<Job> list = this.jobService.findAllJob(job);
            return FileUtils.createExcelByPOIKit("定时任务表", list, Job.class);
        } catch (Exception e) {
            log.error("导出定时任务信息Excel失败", e);
            return FgResult.failed("导出Excel失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 45)
    @ApiOperation(value = "导出csv",notes = "导出定时任务表csv")
    @Log("导出定时任务表csv")
    @PostMapping("/job/csv")
    @ResponseBody
    public FgResult jobCsv(Job job) {
        try {
            List<Job> list = this.jobService.findAllJob(job);
            return FileUtils.createCsv("定时任务表", list, Job.class);
        } catch (Exception e) {
            log.error("导出定时任务信息Csv失败", e);
            return FgResult.failed("导出Csv失败，请联系网站管理员！");
        }
    }

    @ApiOperationSupport(order = 50)
    @ApiOperation(value = "判断表达式",notes = "判断表达式是否正确")
    @ApiImplicitParam(required = true,value = "cron表达式",name = "cron")
    @GetMapping("/job/checkCron")
    @ResponseBody
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    /*@PostMapping("/job/getSysCronClass")
    @ResponseBody
    public FgResult getSysCronClass(Job job) {
        List<Job> sysCronClass = this.jobService.getSysCronClass(job);
        return FgResult.success(sysCronClass);
    }*/
}

