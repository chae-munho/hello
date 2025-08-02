package com.example.hello.controller;

import com.example.hello.dto.BoardDto;
import com.example.hello.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "boards/list";
    }

    @GetMapping("/new")
    public String newBoardForm(Model model) {
        model.addAttribute("board", new BoardDto());
        return "boards/form";
    }

    @PostMapping
    public String create(@ModelAttribute BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/boards";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "boards/detail";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "boards/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute BoardDto boardDto) {
        boardService.update(id, boardDto);
        return "redirect:/boards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/boards";
    }
}