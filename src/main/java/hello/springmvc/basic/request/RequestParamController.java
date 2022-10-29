package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("OK");
    }

    /**
     * @RequestParam @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody @ResponseBody 추가
     * - View 조회를 무시하고 HTTP message body 에 직접 해당 내용 입력.
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "OK";
    }

    /**
     * @RequestParam @RequestParam 사용
     * - HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="XXXX") 을 생략 가능.
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @RequestParam @RequestParam 사용
     * String, int  등의 단순 타입이라면 @RequestParam 도 생략 가능.
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) Integer age
    ) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    /**
     * @RequestParam Map 혹은 MultiValueMap
     * Map(key = value)
     * MultiValueMap(key = [value1, value2] ex) (key=userIds, value = [id1, id2]
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestHeader Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }

}

//    /**
//     * @RequestParam Map, MultiValueMap
//     * Map(key=value)
//     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
//     */
//    @ResponseBody
//    @RequestMapping("/request-param-map")
//    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
//        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
//        return "ok";
//    }



