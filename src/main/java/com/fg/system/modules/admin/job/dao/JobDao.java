package com.fg.system.modules.admin.job.dao;

import com.fg.system.modules.admin.job.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  t_job Mapper 接口
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
public interface JobDao extends BaseMapper<Job> {
    List<Job> queryList();

}
