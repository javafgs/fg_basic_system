package com.fg.system.modules.admin.job.service;

import com.fg.system.modules.admin.job.entity.JobLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  t_job_log服务类
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
public interface JobLogService extends IService<JobLog> {
    List<JobLog> findAllJobLog(JobLog jobLog);

    void saveJobLog(JobLog log);
}
