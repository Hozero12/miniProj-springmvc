package hello.itemservice.web.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoService kakaoService;

    //결제 요청
    @RequestMapping("/ready")
    public KakaoReadyResponse readyKakaoPay(){
        return kakaoService.kakaoReady();
    }

}
