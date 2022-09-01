package teleDemo.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
/**
 * @Project Name:trackmanage
 * @File Name: GlobalExceptionAdvice
 * @Description: 全局异常处理
 * @ HISTORY：
 *    Created   2022.8.22  WYJ
 *    Modified  2022.8.22  WYJ
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Object handleException(HttpServletRequest request, Exception e) throws Exception {
        log.error(e.getMessage(), e);
        // 如果某个自定义异常有@ResponseStatus注解，就继续抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        HashMap<String, Object> map = new HashMap<>(1);
        map.put("message", "您的输入有异常，请检查输入或联系管理员（QQ:805937849）" );
        return map;
    }
}
