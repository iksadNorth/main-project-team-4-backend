package com.example.demo.wish.service;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.notification.Entity.NotificationType;
import com.example.demo.notification.dto.NotificationRequestDto;
import com.example.demo.notification.service.NotificationService;
import com.example.demo.wish.dto.WishListResponseDto;
import com.example.demo.wish.dto.WishReadResponseDto;
import com.example.demo.wish.entity.Wish;
import com.example.demo.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final ItemRepository itemRepository;
    private final WishRepository wishRepository;

    private final NotificationService notificationService;
    private final ApplicationEventPublisher eventPublisher;

    private void save(Member member, Long itemId) {
        Item itemEntity = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품은 존재하지 않습니다."));

        Wish entity = new Wish(member, itemEntity);
        wishRepository.save(entity);

        String content = member.getNickname() + "님이 " + itemEntity.getName() + " 상품을 찜하였습니다.";
        String url = "/api/items/"+itemId+"/wishes";
        notificationService.send(member, NotificationType.WISH, content, url);
    }

    public ResponseEntity<Void> toggle(Member member, Long itemId) {
        Optional<Wish> wishEntity = wishRepository.findByMember_IdAndItem_Id(member.getId(), itemId);

        wishEntity.ifPresentOrElse(
                wishRepository::delete,
                () -> this.save(member, itemId)
        );

        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity<List<WishListResponseDto>> readMyWishLists(Member member) {
        List<WishListResponseDto> dtoList = wishRepository.findByMemberOrderByItem_CreatedAt(member).stream()
                .map(WishListResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    public ResponseEntity<WishReadResponseDto> readWishRecord(Member member, Long itemId) {
        boolean isWished = wishRepository.existsByMember_IdAndItem_Id(member.getId(), itemId);
        return ResponseEntity.ok(new WishReadResponseDto(isWished));
    }

    private void notify(Member itemOwner, Member sender, NotificationType notificationType,
                        String content, Long itemId){
        eventPublisher.publishEvent(new NotificationRequestDto(itemOwner, sender, notificationType, content, itemId));
    }
}
