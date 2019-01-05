package com.saras.archetype.api.marshall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.*;
import com.saras.archetype.utils.Dates;

import java.lang.reflect.Type;

/**
 * description:
 * saras_xu@163.com 2017-11-29 14:27 创建
 */
public class JsonUtil {
    private static final JsonUtil INSTANCE = new JsonUtil();

    static {
        init();
    }

    private final SerializeConfig SERIALIZE_CONFIG = new SerializeConfig();
    private Feature[] features;
    private ParserConfig parserConfig;
    private int featureValues = 0;

    private JsonUtil() {
    }

    private static void init() {
        INSTANCE.parserConfig = new ParserConfig();
        INSTANCE.features = new Feature[0];
        //取消SortFeidFastMatch特性.
        INSTANCE.featureValues = (~Feature.SortFeidFastMatch.getMask()) & JSON.DEFAULT_PARSER_FEATURE;
    }

    /**
     * 转换字符串为对象
     *
     * @param source 字符串
     * @param clazz  类型
     * @param <T>    返回对象
     * @return
     */
    public static <T> T parse(String source, Type clazz) {
        return JSON.parseObject(source, clazz, INSTANCE.parserConfig, INSTANCE.featureValues, INSTANCE.features);
    }

    public static final <T> T parseObject(String text, TypeReference<T> type) {
        return JSON.parseObject(text, type);
    }

    /**
     * 转换对象为json字符串
     *
     * @param object 原对象
     * @return json字符串
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return "{}";
        }
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = INSTANCE.getJsonSerializer(out);
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }

    private JSONSerializer getJsonSerializer(SerializeWriter out) {
        JSONSerializer serializer = new JSONSerializer(out, INSTANCE.SERIALIZE_CONFIG);
        serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
        serializer.setDateFormat(Dates.CHINESE_DATETIME_FORMAT_LINE);
        serializer.config(SerializerFeature.QuoteFieldNames, true);
        serializer.config(SerializerFeature.DisableCircularReferenceDetect, false);

        return serializer;
    }

    public void addSerializer(Class<?> clazz, ObjectSerializer objectSerializer) {
        SERIALIZE_CONFIG.put(clazz, objectSerializer);
    }
}
