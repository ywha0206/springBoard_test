package com.springboard.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {
    @Id
    private int id;
    private String title;
    private String content;
    private String writer;
    private String regIp;
    private int comments;
    private int views;

    @CreationTimestamp
    private LocalDateTime regDate;
}
