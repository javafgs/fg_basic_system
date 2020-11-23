package com.fg.system.modules.admin.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fg.system.common.annotation.CronTag;
import com.fg.system.common.util.job.ScheduleUtils;
import com.fg.system.modules.admin.job.entity.Job;
import com.fg.system.modules.admin.job.dao.JobDao;
import com.fg.system.modules.admin.job.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import org.reflections.Reflections;

/**
 * <p>
 *  t_job 服务实现类
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
@Service("JobService")
public class JobServiceImpl extends ServiceImpl<JobDao, Job> implements JobService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobDao jobDao;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = this.findAllJob();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public Job findJobById(Long jobId) {
        return this.getById(jobId);
    }

    @Override
    public List<Job> findAllJob() {
        return this.list();
    }

    @Override
    public List<Job> findAllJob(Job job) {
        try {
            QueryWrapper<Job> queryWrapper=new QueryWrapper();
            if (StringUtils.isNotBlank(job.getBeanName())) {
                queryWrapper.lambda().eq(Job::getBeanName, job.getBeanName());
            }
            if (StringUtils.isNotBlank(job.getMethodName())) {
                queryWrapper.lambda().eq(Job::getMethodName, job.getMethodName());
            }
            if (StringUtils.isNotBlank(job.getStatus())) {
                queryWrapper.lambda().eq(Job::getStatus, Long.valueOf(job.getStatus()));
            }
            queryWrapper.lambda().orderByDesc(Job::getJobId);
            return this.list(queryWrapper);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional//事务注解，出现异常进行回滚
    public void addJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        this.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    @Transactional
    public void updateJob(Job job) {
        ScheduleUtils.updateScheduleJob(scheduler, job);
        this.updateById(job);
    }

    @Override
    @Transactional
    public void deleteJobBatch(String jobIds) {
        List<String> list = Arrays.asList(jobIds.split(","));
        list.forEach(jobId -> ScheduleUtils.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.jobDao.deleteBatchIds(list);
    }
    @Override
    @Transactional
    public int updateJobBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(","));
        QueryWrapper<Job> queryWrapper=new QueryWrapper();
        queryWrapper.in("job_Id", list);
        Job job = new Job();
        job.setStatus(status);
        return this.jobDao.update(job, queryWrapper);
    }

    @Override
    @Transactional
    public void run(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.run(scheduler, this.findJobById(Long.valueOf(jobId))));
    }

    @Override
    @Transactional
    public void pause(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.pauseJob(scheduler, Long.valueOf(jobId)));
        this.updateJobBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(String jobIds) {
        String[] list = jobIds.split(",");
        Arrays.stream(list).forEach(jobId -> ScheduleUtils.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateJobBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }
    @SuppressWarnings("rawtypes")
    @Override
    public List<Job> getSysCronClass(Job job) {
        Reflections reflections = new Reflections("com.fg.system.modules.admin.job.task");
        List<Job> jobList = new ArrayList<>();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CronTag.class);
        for (Class cls : annotated) {
            String clsSimpleName = cls.getSimpleName();
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Job job1 = new Job();
                String methodName = method.getName();
                Parameter[] methodParameters = method.getParameters();
                String params = String.format("%s(%s)", methodName, Arrays.stream(methodParameters).map(item -> item.getType().getSimpleName() + " " + item.getName()).collect(Collectors.joining(", ")));

                job1.setBeanName(StringUtils.uncapitalize(clsSimpleName));
                job1.setMethodName(methodName);
                job1.setParams(params);
                jobList.add(job1);
            }
        }
        return jobList;
    }
}
