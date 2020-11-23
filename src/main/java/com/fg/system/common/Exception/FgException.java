package com.fg.system.common.Exception;

import com.fg.system.common.api.ResultCode;

/**
 * 自定义异常类
 */
public class FgException extends RuntimeException{
    private ResultCode resultCode;

    public FgException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }


    public FgException(String message) {
        super(message);
    }

    public FgException(String message, Throwable cause) {
        super(message, cause);
    }

    public FgException(Throwable cause) {
        super(cause);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
