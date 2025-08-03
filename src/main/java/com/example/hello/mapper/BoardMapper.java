package com.example.hello.mapper;

import com.example.hello.dto.BoardDto;
import com.example.hello.entity.Board;
import org.springframework.stereotype.Component;


@Component
public class BoardMapper {

    public BoardDto toDto(Board board) {
        return new BoardDto(board.getId(), board.getTitle(), board.getContent(), board.getImageUrl());
    }

    public Board toEntity(BoardDto dto) {
        return new Board(dto.getTitle(), dto.getContent(), dto.getImageUrl());
    }

    public void updateEntity(Board entity, BoardDto dto) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        if (dto.getImageUrl() != null) { // ✅ 새 이미지 업로드 시 업데이트
            entity.setImageUrl(dto.getImageUrl());
        }
    }
}
