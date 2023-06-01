package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA", 1000, 10);
        //when
        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        //itemRepository.findById(item.getId()).equals(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll(){
        //given
        Item item1= new Item("item1", 1000, 10);
        Item item2 = new Item("item2", 333000, 22);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem(){
        //given
        Item item= new Item("item1", 1000, 10);
        Item savedItem = itemRepository.save(item);
        Long itmeId = savedItem.getId();

        //when
        Item updateParam = new Item("item2", 2000, 20);
        itemRepository.update(itmeId,updateParam);

        //then
        Item findItem = itemRepository.findById(itmeId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
     //   assertThat(findItem.getQuantaty()).isEqualTo(updateParam.getQuantaty());


    }

}