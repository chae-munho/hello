package com.example.hello.controller;

import com.example.hello.dto.BoardDto;
import com.example.hello.service.BoardService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import io.awspring.cloud.s3.S3Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final S3Operations s3Operations;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public BoardController(BoardService boardService, S3Operations s3Operations) {
        this.boardService = boardService;
        this.s3Operations = s3Operations;
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
    public String create(@RequestParam("image") MultipartFile imageFile,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content) {
        String imageUrl = uploadImageToS3(imageFile);
        BoardDto dto = new BoardDto(null, title, content, imageUrl);
        boardService.save(dto);
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
    public String update(@PathVariable("id") Long id,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = uploadImageToS3(imageFile);
        }
        BoardDto dto = new BoardDto(id, title, content, imageUrl);
        boardService.update(id, dto);
        return "redirect:/boards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/boards";
    }

    private String uploadImageToS3(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try (InputStream inputStream = file.getInputStream()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = Instant.now().getEpochSecond() + extension;

            S3Resource s3Resource = s3Operations.upload(bucketName, filename, inputStream,
                    ObjectMetadata.builder().contentType(file.getContentType()).build());
            return s3Resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException("S3 upload failed", e);
        }
    }
}