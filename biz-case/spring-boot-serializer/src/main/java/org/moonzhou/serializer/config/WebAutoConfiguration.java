package org.moonzhou.serializer.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

/**
 * @author moon zhou
 * @version 1.0
 * @description:
 * @date 2023/12/11 11:10
 */
@Configuration
@Slf4j
public class WebAutoConfiguration {

    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        //对不可信的输入输出进行编码
        simpleModule.addSerializer(String.class, dangerousCharactersSerializer());
        simpleModule.addDeserializer(String.class, dangerousCharactersDeSerializer());

        return Jackson2ObjectMapperBuilder
                .json()
                .failOnUnknownProperties(false)
                .modules(simpleModule, javaTimeModule())
                .build()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Consumer<ObjectMapper> configurer = objectMapper -> {
            // 反序列化的时候java对象中不存在对应属性的情况,不抛出异常
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 对于空的对象转json的时候不抛出错误
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // 如果是空对象的时候,不抛异常
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            // 允许属性名称没有引号
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            // 允许单引号
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        };
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .postConfigurer(configurer);
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule timeModule = new JavaTimeModule();

        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DEFAULT_DATE_TIME_FORMATTER));
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DEFAULT_DATE_FORMATTER));
        timeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DEFAULT_TIME_FORMATTER));

        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DEFAULT_DATE_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DEFAULT_DATE_FORMATTER));
        timeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DEFAULT_TIME_FORMATTER));

        return timeModule;
    }

    /**
     * 危险字符转换器
     */
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter(Environment environment) {
        Encoding encoding = Binder.get(environment).bindOrCreate("server.servlet.encoding", Encoding.class);
        DangerousCharactersHttpMessageConverter converter = new DangerousCharactersHttpMessageConverter(encoding.getCharset());
        converter.setWriteAcceptCharset(false);
        return converter;
    }

    class DangerousCharactersHttpMessageConverter extends StringHttpMessageConverter {
        public DangerousCharactersHttpMessageConverter(Charset defaultCharset) {
            super(defaultCharset);
        }

        @Override
        protected void writeInternal(String s, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            // Custom serialization logic here
            String htmlEscape = HtmlUtils.htmlEscape(s);
            outputMessage.getHeaders().setContentLength(htmlEscape.length());
            super.writeInternal(htmlEscape, outputMessage);
        }
    }

    /**
     * http response: filter result string field
     * @return
     */
    @Bean
    public JsonSerializer<String> dangerousCharactersSerializer() {
        return new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
                if (value != null) {

                    String encodedValue = HtmlUtils.htmlEscape(value);
                    jsonGenerator.writeString(encodedValue);
                }
            }
        };
    }

    /**
     * http request: filter param string field
     * @return
     */
    @Bean
    public JsonDeserializer<String> dangerousCharactersDeSerializer() {
        return new JsonDeserializer<String>() {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                String value = p.getValueAsString();
                if (value != null) {

                    return HtmlUtils.htmlUnescape(value);
                }
                return null;
            }
        };
    }
}
