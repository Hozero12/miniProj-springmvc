package hello.itemservice.web;

import hello.itemservice.domain.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends SessionConst{

    //@GetMapping("/")
    public String home(){
        return "home";
    }

  //  @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member)session.getAttribute(LOGIN_MEMBER);

        if(loginMember == null){
            return  "home";
        }

        model.addAttribute("member", loginMember);
        return  "loginHome";

    }

    @GetMapping("/")
    public String homeLoginV2Spring(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        if(loginMember == null){
            return  "home";
        }

        model.addAttribute("member", loginMember);
        return  "loginHome";

    }

}
