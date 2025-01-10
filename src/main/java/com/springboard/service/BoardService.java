package com.springboard.service;

import com.springboard.dto.BoardDTO;
import com.springboard.entity.Board;
import com.springboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    public Page<Board> readPosts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Board> posts = boardRepository.findAll(pageable);
        log.info("게시글 목록 서비스 "+posts);
        return posts;
    }

    public void createPost(BoardDTO boardDTO) {
        Board board = Board.builder()
                        .title(boardDTO.getTitle())
                        .content(boardDTO.getContent())
                        .regIp(boardDTO.getRegIp())
                        .build();

        boardRepository.save(board);
    }

    public void deletePost(int id) {
        boardRepository.deleteById(id);
    }

    public BoardDTO readPost(int id) {
        Optional<Board> board = boardRepository.findById(id);
        if(board.isPresent()) {
            BoardDTO dto = BoardDTO.builder()
                            .id(board.get().getId())
                            .title(board.get().getTitle())
                            .content(board.get().getContent())
                            .build();
            return dto;
        }else{
            return null;
        }
    }

    public void updatePost(BoardDTO boardDTO) {
        Optional<Board> optBoard = boardRepository.findById(boardDTO.getId());
        if(optBoard.isPresent()) {
            Board board = optBoard.get().builder()
                    .id(optBoard.get().getId())
                    .title(boardDTO.getTitle())
                    .content(boardDTO.getContent())
                    .build();
            boardRepository.save(board);
        }
    }
}
