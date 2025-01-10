package com.springboard.controller;

import com.springboard.dto.BoardDTO;
import com.springboard.entity.Board;
import com.springboard.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardService boardService;

    @GetMapping({"/","/list"})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Page<BoardDTO> posts = boardService.readPosts(page);

        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        return "list";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @PostMapping("/write")
    public String writePost(BoardDTO boardDTO, HttpServletRequest req) {
        log.info(boardDTO);
        String userIP = req.getRemoteAddr();
        boardDTO.setRegIp(userIP);
        boardService.createPost(boardDTO);

        return "redirect:/list";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam(value = "id") int id, Model model) {
        log.info("수정할 글 번호 "+id);
        BoardDTO board = boardService.readPost(id);
        model.addAttribute("board", board);
        return "modify";
    }

    @PostMapping("/modify")
    public String modifypost(BoardDTO boardDTO) {
        log.info("수정할 글 번호 "+boardDTO.toString());
        boardService.updatePost(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") int id) {
        log.info("삭제할 글 번호 "+id);
        boardService.deletePost(id);
        return "redirect:/list";
    }
}
