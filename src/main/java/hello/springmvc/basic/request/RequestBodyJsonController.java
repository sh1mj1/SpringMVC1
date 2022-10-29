package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 라이브러리의 ObjectMapper

    @PostMapping("/request-body-json-v1")
    public void requestBodyJson(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        response.getWriter().write("OK data= \n" + data);
    }

    /**
     * @RequestBody - HttpMessageConverter 사용(StringHttpMessageConverter 적용)
     * @ResponseBody - 모든 메서드에 @ResponseBody 적용. 메시지 바디 직접 반환.(view 조회X).
     * HttpMessageConverter 사용(StringHttpMessageConverter 적용)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "OK data = " + data;
    }

    /**
     * @RequestBody @RequestBody 생략 불가능(생략하면 @ModelAttribute 가 적용되어 버린다.)
     * HttpMessageConverter 사용(MappingJackson2HttpMessageConverter (contenttype: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "OK data= " + data;
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * @RequestBody @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림).
     * HttpMessageConverter 사용(MappingJackson2HttpMessageConverter (contenttype: application/json)).
     *
     * @ResponseBody @ResponseBody 적용.
     * - 메시지 바디 정보 직접 반환(view 조회X).
     * - HttpMessageConverter 사용(MappingJackson2HttpMessageConverter 적용 (Accept: application/json)).
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requesetBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }
}
