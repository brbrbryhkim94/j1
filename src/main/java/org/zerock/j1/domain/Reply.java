package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") // *연관관계를 걸면 ToString쓸때 exclude로 뺀다
public class Reply {
    // 연관관계는 FK쪽 있는곳으로 설계

    // 우리가 짠 논리로 코드를작성
    // ERD로 @(Annotation)판단
    // 연관관계에서는 @Tostring조심
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long rno;

    private String replyText;

    private String replyFile;

    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY) // fetch는 데이터를 가져온다는뜻 ////// FetchType.LAZY는 내가 필요한 순간까지는 데이터를 조회하지않는다는뜻
                                       // *연관관계를 주려면 무조건 기본적으로 FectchType.LAZY로함 why?) 쓸데없이 join이나 select가 안날라게하려고

    // no Session -> 추가적인 DB연결이 필요한데 그 db연결이없어서 오류가 나옴 -> 즉 , 무조건 트랜잭션이 필요하다고 생각하면됨

    // 메인테이블: 가장 많은 정보가있는 테이블
    // 보조테이블:
    // join할때 메인테이블과 보조테이블

    private Board board;

    public void changeText(String text) {
        this.replyText = text;

    }

    public void changeFile(String fileName) {
        this.replyFile = fileName;
    }
}