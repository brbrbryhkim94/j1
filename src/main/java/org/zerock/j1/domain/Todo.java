package org.zerock.j1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_todo2") // 여기에 인덱스도 자동으로 잡게 해줄수있음
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter // @Data말고 @Getter을 씀 -> 가능하면 @Setter쓰지않는다.
public class Todo {

    // jpa면접문제: dirtyread

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue(strategy = GenerationType.IDENTITY) => Auto_Increment라는뜻
    private Long tno;

    @Column(length = 300, nullable = false)
    private String title;

    public void changeTitle(String title) {
        this.title=title;
    }

}
