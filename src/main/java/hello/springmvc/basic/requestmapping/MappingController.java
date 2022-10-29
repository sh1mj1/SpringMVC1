package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j // Logger 타입 log
public class MappingController {

    /**
     * 기본 요청.
     * 둘 다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용. (GET, HEAD, POST, PUT, PATCH, DELETE)
     */
    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("helloBasic");
        return "OK";
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mapping-get-v1");
        return "OK mapping-get-v1";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "OK mapping-get-v2";
    }

    //    @GetMapping("mapping/{userId}")
//    public String mappingPath(@PathVariable("userId") String userId) {
    @GetMapping("mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userID = {}", userId);
        return "OK mappingPath " + userId;
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId = {}", userId, orderId);
        return "OK userId " + userId + "orderId: " + orderId ;
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok mapping-param";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok mapping-header";
    }

    /**
     *Content- Type 헤더 기반 추가 매핑 MediaType
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    // @PostMapping(value = "/mapping-produce", consumes = "application/json") 아래 방식으로 표현하는 게 좋음.
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "OK mapping-consume";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mapping-produce");
        return "OK mapping-produce";
    }
}
