package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;   // int 는 기본값이 0으로 들어가지만 Integer 는 값이 없을경우 null 이면 다음에 널체크 가능해서?
    private Integer quantity ;

    private Boolean open;
    private List<String> regions;
    private ItemType  itemType;
    private String deliveryCode;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
