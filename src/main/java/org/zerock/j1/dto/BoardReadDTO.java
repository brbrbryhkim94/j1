package org.zerock.j1.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface BoardReadDTO { // JOIN을 많이쓰면 이 방식이 좋음
    Long getBno();
    String getTitle();
    String getContent();
    
    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss")
    LocalDateTime getRegDate();
    
    @JsonFormat(pattern = "yyyy-MM-dd MM:mm:ss")
    LocalDateTime getModDate();
    
}
