package hello.itemservice.web.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KakaoService {

    private static final String cid = "TC0ONETIME";
    private static final String kakaoAuthkey = "${3f1fc8bf765b861e5a71d6586697d0b1}";
    private KakaoReadyResponse kakaoReadyResponse;

    public KakaoReadyResponse kakaoReady(){

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>() ;
        param.add("cid", cid);
        param.add("partner_order_id", "가맹점 주문 번호");
        param.add("partner_user_id", "가맹점 회원 ID");
        param.add("item_name", "상품명");
        param.add("quantity", "주문 수량");
        param.add("total_amount", "총 금액");
        param.add("vat_amount", "부가세");
        param.add("tax_free_amount", "상품 비과세 금액");
        param.add("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        param.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        param.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url

        HttpEntity<MultiValueMap<String, String>> httpRequestEntity = new HttpEntity<>(param, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
         kakaoReadyResponse = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready", httpRequestEntity, KakaoReadyResponse.class);

         log.info("kakaores = {}", kakaoReadyResponse);
        return kakaoReadyResponse;

    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + kakaoAuthkey;
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", contentType);

        return httpHeaders;
    }


}
