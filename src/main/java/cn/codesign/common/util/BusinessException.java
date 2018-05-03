package cn.codesign.common.util;

import cn.codesign.sys.data.model.SysErrorInfo;

import java.text.MessageFormat;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/5/27
 * Time: 14:54
 * Description:业务异常处理
 */
public class BusinessException extends Exception {

    private SysErrorInfo sysErrorInfo;

    public BusinessException(SysErrorInfo sysErrorInfo) {
        super(sysErrorInfo.getErrorMsg());
        this.sysErrorInfo = sysErrorInfo;
    }

    public BusinessException(SysErrorInfo sysErrorInfo, Object[] params){
        super(MessageFormat.format(sysErrorInfo.getErrorMsg(), params));
        sysErrorInfo.setErrorMsg(MessageFormat.format(sysErrorInfo.getErrorMsg(), params));
        this.sysErrorInfo = sysErrorInfo;
    }



    public SysErrorInfo getSysErrorInfo() {
        return sysErrorInfo;
    }
    public void setSysErrorInfo(SysErrorInfo sysErrorInfo) {
        this.sysErrorInfo = sysErrorInfo;
    }


}
