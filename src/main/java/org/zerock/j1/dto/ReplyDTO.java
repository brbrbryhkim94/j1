package org.zerock.j1.dto;

import lombok.Data;

@Data
public class ReplyDTO { // 만든이유 서비스에서 리턴타입으로 ReplyDTO을 이용하려고
    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

    private Long bno;

   
}
