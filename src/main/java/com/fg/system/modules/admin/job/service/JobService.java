package com.fg.system.modules.admin.job.service;

import com.fg.system.modules.admin.job.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  t_job服务类
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
public interface JobService extends IService<Job> {
    Job findJobById(Long jobId);

    List<Job> findAllJob();

    List<Job> findAllJob(Job job);

    void addJob(Job job);

    void updateJob(Job job);

    void deleteJobBatch(String jobIds);

    int updateJobBatch(String jobIds, String status);

    void run(String jobIds);

    void pause(String jobIds);

    void resume(String jobIds);

    //@Cacheable(key = "#p0")
    List<Job> getSysCronClass(Job job);
}
