package com.example.demo.wish.service;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.wish.entity.Wish;
import com.example.demo.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final ItemRepository itemRepository;
    private final WishRepository wishRepository;

    private void save(Member member, Long itemId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품은 존재하지 않습니다."));

        Wish entity = new Wish(member, itemEntity);
        wishRepository.save(entity);
    }

    public ResponseEntity<Void> toggle(Member member, Long itemId) {
        Optional<Wish> wishEntity = wishRepository.findByMember_IdAndItem_Id(member.getId(), itemId);

        wishEntity.ifPresentOrElse(
                wishRepository::delete,
                () -> this.save(member, itemId)
        );

        return ResponseEntity.ok(null);
    }
}