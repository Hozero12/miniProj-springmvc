package hello.itemservice.web.basic;

import hello.itemservice.domain.item.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    public final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");

        return  regions;
    }
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        ItemType[] values = ItemType.values();
            return  values;
    }
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes(){
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른배송"));
        deliveryCodes.add(new DeliveryCode("NOMAL", "일반배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린배송"));
        return deliveryCodes;
    }

    @Autowired
    public BasicItemController(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
        init();
    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){

        Item item = itemRepository.findById(itemId);
      //  log.info("itemType = {}", item.getItemType());

        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        Item item = new Item();
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

   // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName, Integer price, Integer quantity, Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

   // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item ){  //@ModelAttribute 에서 name 값을 생략해도 Item 클래스 이름의 앞글자를 소문자로 만들어서 반환한다.
        itemRepository.save(item);
     //   model.addAttribute("item", item);
        return "basic/item";
    }

   // @PostMapping("/add")
    public String addItemV3(Item item ){
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItem(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){


        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/basic/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";  //PRG 적용
        // return "basic/item";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult){
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "basic/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping
    public void init(){
        itemRepository.save(new Item("itmeA", 10000, 10));
        itemRepository.save(new Item("itmeB", 20000, 20));

    }
}
