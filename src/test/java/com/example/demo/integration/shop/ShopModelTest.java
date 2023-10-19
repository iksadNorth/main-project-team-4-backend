package com.example.demo.integration.shop;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.utils.LoadEnvironmentVariables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@LoadEnvironmentVariables
public class ShopModelTest {
    @Autowired
    private ItemService itemService;

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-shop.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseShop {}

    @LoadTestCaseShop
    @Test
    @DisplayName("[정상 작동] readItemsOfShop")
    void readItemsOfShop() {
        // given
        Long shopId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.readItemsOfShop(shopId, pageable);

        // then
        assertThat(result.getBody().getContent())
                .hasSize(4)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(1L, 3L, 5L, 7L));
    }

    @LoadTestCaseShop
    @Test
    @DisplayName("[비정상 작동] readItemsOfShop - 존재하지 않는 shopId")
    void readItemsOfShop_whenGivenNonExistedShopId() {
        // given
        Long shopId = 100000L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Executable func = () -> itemService.readItemsOfShop(shopId, pageable);

        // then
        assertThrows(Throwable.class, func);
    }
}