package com.example.hello.service;

import com.example.hello.dto.BoardDto;
import com.example.hello.entity.Board;
import com.example.hello.mapper.BoardMapper;
import com.example.hello.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public BoardService(BoardRepository boardRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
    }

    public List<BoardDto> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(boardMapper::toDto)
                .collect(Collectors.toList());
    }

    public BoardDto findById(Long id) {
        return boardRepository.findById(id)
                .map(boardMapper::toDto)
                .orElse(null);
    }

    // ✅ Create
    public void save(BoardDto dto) {
        boardRepository.save(boardMapper.toEntity(dto));
    }

    // ✅ Update
    public void update(Long id, BoardDto dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        boardMapper.updateEntity(board, dto);
        boardRepository.save(board);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}