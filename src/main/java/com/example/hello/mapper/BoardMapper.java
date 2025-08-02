package com.example.hello.mapper;

import com.example.hello.dto.BoardDto;
import com.example.hello.entity.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public BoardDto toDto(Board board) {
        return new BoardDto(board.getId(), board.getTitle(), board.getContent());
    }

    public Board toEntity(BoardDto dto) {
        return new Board(dto.getTitle(), dto.getContent());
    }

    // ✅ 기존 엔티티를 업데이트 (update용)
    public void updateEntity(Board entity, BoardDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
    }
}