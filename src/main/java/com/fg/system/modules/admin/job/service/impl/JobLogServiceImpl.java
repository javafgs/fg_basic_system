package com.fg.system.modules.admin.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fg.system.modules.admin.job.entity.JobLog;
import com.fg.system.modules.admin.job.dao.JobLogDao;
import com.fg.system.modules.admin.job.service.JobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  t_job_log 服务实现类
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
@Service("JobLogService")
public class JobLogServiceImpl extends ServiceImpl<JobLogDao, JobLog> implements JobLogService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobLogDao jobLogDao;
    @Override
    public List<JobLog> findAllJobLog(JobLog jobLog) {
        try {
            QueryWrapper<JobLog> queryWrapper=new QueryWrapper();
            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                queryWrapper.lambda().eq(JobLog::getBeanName,jobLog.getBeanName());
            }
            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                queryWrapper.lambda().eq(JobLog::getMethodName, jobLog.getMethodName());
            }
            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                queryWrapper.lambda().eq(JobLog::getStatus, Long.valueOf(jobLog.getStatus()));
            }
            queryWrapper.lambda().orderByDesc(JobLog::getLogId);
            return this.list(queryWrapper);
        } catch (Exception e) {
            log.error("获取调度日志信息失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void saveJobLog(JobLog log) {
        this.save(log);
    }

}
