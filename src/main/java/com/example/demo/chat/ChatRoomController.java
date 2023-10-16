package com.example.demo.chat;

import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 모든 채팅방 목록 반환 - 유저별
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getAllChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getAllChatRooms(userDetails.getMember().getId());
    }

    // 채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행
    @PostMapping("/room/{itemId}")
    @ResponseBody
    public ChatRoom createRoom(@PathVariable Long itemId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(itemId, userDetails.getMember());
    }

    // 채팅방 상세 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto getChatRoomDetails(@PathVariable String roomId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getChatRoomDetails(roomId, userDetails.getMember());
    }

    // 채팅방 입장 화면
    @GetMapping("/room/{itemId}/{roomId}")
    public ModelAndView roomDetail(Model model, @PathVariable String roomId, @PathVariable Long itemId) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("itemId", itemId);
        ModelAndView page = new ModelAndView("/chat/roomdetail");
        return page;
    }
}