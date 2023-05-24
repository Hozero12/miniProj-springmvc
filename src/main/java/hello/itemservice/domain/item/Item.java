package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Item {

    private Long id;

    @NotBlank(message = "공백 허용 x")
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;   // int 는 기본값이 0으로 들어가지만 Integer 는 값이 없을경우 null 이면 다음에 널체크 가능해서?

    @NotNull
    @Max(9999)
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
