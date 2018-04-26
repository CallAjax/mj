package cn.codesign.config.security;

import cn.codesign.common.util.SysConstant;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created with codesign.
 * User: Sam
 * Date: 2017/6/8
 * Time: 16:46
 * Description:
 */
@RestController
@RequestMapping("/service/image")
public class CaptchaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);

    @Autowired
    private Producer captchaProducer;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${request.ip}")
    private String ip;

    @RequestMapping
    public String getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {


            /**判断是否来自本站ip**/
            if(!this.ip.equals(request.getRemoteHost())) {
                return null;
            }

            Random random = new Random(100);

            String key = System.currentTimeMillis() + String.valueOf(random.nextInt(1000));

            response.setDateHeader("Expires", 0);

            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

            response.addHeader("Cache-Control", "post-check=0, pre-check=0");

            response.setHeader("Pragma", "no-cache");

            ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            String capText = captchaProducer.createText();
            BufferedImage image = captchaProducer.createImage(capText);
            ImageIO.write(image, "jpg", outputStream);

            response.addHeader(SysConstant.CODE_ID, key);

            /**验证码入redis同步**/
            this.redisTemplate.opsForValue().set(key, capText, 1, TimeUnit.MINUTES);

            BASE64Encoder encoder = new BASE64Encoder();

            return encoder.encode(outputStream.toByteArray());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            outputStream.close();
        }
        return null;
    }
}
