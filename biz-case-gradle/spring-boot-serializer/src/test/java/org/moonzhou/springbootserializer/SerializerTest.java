package org.moonzhou.springbootserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.moonzhou.springbootserializer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SerializerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerialize() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("张三");
        user.setPhone("13812345678");
        user.setEmail("zhangsan@example.com");
        user.setAge(25);
        user.setActive(true);

        String json = objectMapper.writeValueAsString(user);
        System.out.println("序列化结果: " + json);

        // 验证序列化结果包含原始字段和加密字段
        assertTrue(json.contains("\"phone\":\"13812345678\""));
        assertTrue(json.contains("\"phone_encrypted\":\"MTM4MTIzNDU2Nzg=\""));
        assertTrue(json.contains("\"email\":\"zhangsan@example.com\""));
        assertTrue(json.contains("\"email_encrypted\":\"zhangsan!fybnqmf/dpn\""));
    }

    @Test
    public void testDeserialize() throws Exception {
        String json = "{\"id\":1,\"name\":\"张三\",\"phone\":\"MTM4MTIzNDU2Nzg=\",\"email\":\"zhangsan!fybnqmf/dpn\",\"age\":25,\"active\":true}";

        User user = objectMapper.readValue(json, User.class);
        System.out.println("反序列化结果: " + user);

        // 验证反序列化结果正确
        assertEquals("13812345678", user.getPhone());
        assertEquals("zhangsan@example.com", user.getEmail());
        assertEquals("张三", user.getName());
        assertEquals(25, user.getAge());
        assertTrue(user.getActive());
    }
}