package hello.spriingmvc.basic.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.spriingmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //HTTP 메시지 바디의 데이터를 InputStream 을 사용해서 직접 읽을 수 있다.
        ServletInputStream inputStream = request.getInputStream();
        //inputStream으로 받아온 메세지 바디 데이터를 UTF_8 문자열로 복사합니다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //messageBody 내용 log찍는거
        log.info("messageBody = {}", messageBody);
        //문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환한다
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        //username, age 내용 log찍는거
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    //@ResponseBody : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        //HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody
        //messageBody 내용 log찍는거
        log.info("messageBody = {}", messageBody);
        //문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper 를 사용해서 자바 객체로 변환한다
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        //username, age 내용 log찍는거
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    @ResponseBody
    //@ResponseBody : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        //HTTP 메시지 바디를 직접 조회하는 기능: @RequestBody
        //RequestBody에 직접 만든 객체를 지정할 수 있다.
        //HttpEntity , @RequestBody 를 사용하면 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해준다

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        //username, age 내용 log찍는거

        return "ok";
    }

    @ResponseBody
    //@ResponseBody : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity <HelloData> data) throws IOException {
        HelloData helloData = data.getBody();
        //HttpEntity를 사용하면 getBody로 꺼내서 써야함.
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        //username, age 내용 log찍는거

        return "ok";
    }

    @ResponseBody
    //@ResponseBody : View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());
        //username, age 내용 log찍는거

        return data;
        //위에 같은 방법으로 data를 return하면 json형태로 html에 응답이 나감
    }
}
