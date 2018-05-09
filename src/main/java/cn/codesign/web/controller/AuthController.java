package cn.codesign.web.controller;

import cn.codesign.base.BaseController;
import cn.codesign.common.util.SysConstant;
import cn.codesign.data.vo.ResInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with mj.
 * User: Sam
 * Date: 2018/5/9
 * Time: 12:46
 * Description: token验证
 */
@RestController
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/auth/token")
    public ResInfo auth() {
        ResInfo resInfo = new ResInfo();
        resInfo.setResCode(SysConstant.AJAX_RESULT_SUCCESS);
        return resInfo;
    }
}
