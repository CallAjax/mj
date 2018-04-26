package cn.codesign.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

/**
 * @User Sam
 * @Date 2018/4/19
 * @Time 15:44
 * @param 
 * @return 
 * @Description java对象转json
 */
public class JacksonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) throws Exception{
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, obj);
        return stringWriter.toString();
    }

    public static <T> T toObject(String json, Class<T> c) throws Exception{
        return (T) objectMapper.readValue(json, c.getClass());
    }
}
