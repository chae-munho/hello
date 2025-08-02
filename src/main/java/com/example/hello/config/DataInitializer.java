package com.example.hello.config;

import com.example.hello.dto.BoardDto;
import com.example.hello.service.BoardService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final BoardService boardService;

    public DataInitializer(BoardService boardService) {
        this.boardService = boardService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (boardService.findAll().isEmpty()) {  // 중복 초기화 방지
                boardService.save(new BoardDto(null, "Title 1", "Content 1"));
                boardService.save(new BoardDto(null, "Title 2", "Content 2"));
                boardService.save(new BoardDto(null, "Title 3", "Content 3"));
                boardService.save(new BoardDto(null, "Title 4", "Content 4"));
                boardService.save(new BoardDto(null, "Title 5", "Content 5"));
                boardService.save(new BoardDto(null, "Title 6", "Content 6"));
                System.out.println("✅ 초기 데이터 6개 삽입 완료");
            }
        };
    }
}  