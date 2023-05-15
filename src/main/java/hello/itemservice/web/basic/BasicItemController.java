package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    public final ItemRepository itemRepository;

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
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
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

    @PostMapping("/add")
    public String addItemV3(Item item ){
        itemRepository.save(item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item){

        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping
    public void init(){
        itemRepository.save(new Item("itmeA", 10000, 10));
        itemRepository.save(new Item("itmeB", 20000, 20));

    }
}
