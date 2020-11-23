package com.fg.system.modules.admin.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *  t_job_log 实体类
 * </p>
 *
 * @author wfg
 * @since 2020-10-20
 */
@TableName("t_job_log")
@ApiModel(value="JobLog对象", description="")
public class JobLog implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "任务日志id",required = true)
    @TableId(value = "LOG_ID", type = IdType.AUTO)
    private Long logId;

    @ApiModelProperty(value = "任务id",required = true)
    @TableField("JOB_ID")
    private Long jobId;

    @ApiModelProperty(value = "spring bean名称",required = true)
    @TableField("BEAN_NAME")
    private String beanName;

    @ApiModelProperty(value = "方法名",required = true)
    @TableField("METHOD_NAME")
    private String methodName;

    @ApiModelProperty(value = "参数")
    @TableField("PARAMS")
    private String params;

    @ApiModelProperty(value = "任务状态    0：成功    1：失败",required = true)
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "失败信息")
    @TableField("ERROR")
    private String error;

    @ApiModelProperty(value = "耗时(单位：毫秒)")
    @TableField("TIMES")
    private Long times;

    @ApiModelProperty(value = "创建时间",required = true)
    @TableField("CREATE_TIME")
    private Date createTime;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "JobLog{" +
        "logId=" + logId +
        ", jobId=" + jobId +
        ", beanName=" + beanName +
        ", methodName=" + methodName +
        ", params=" + params +
        ", status=" + status +
        ", error=" + error +
        ", times=" + times +
        ", createTime=" + createTime +
        "}";
    }
}
