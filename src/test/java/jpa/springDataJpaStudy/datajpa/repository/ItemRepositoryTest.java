package jpa.springDataJpaStudy.datajpa.repository;

import jpa.springDataJpaStudy.datajpa.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;

    @Test
    public void checkPk() throws Exception {
        // given
        Item item = new Item();
        // when
        itemRepository.save(item);
        // then

    }
}