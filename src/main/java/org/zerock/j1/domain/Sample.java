package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // @Entity가 붙으면 "엔티티 클래스"라고 부름 (엔티티 클래스랑 엔티티 인스턴스는 다른거임)
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Sample {
    // <JPA>
    // 데이터베이스 버전에 맞는 드라이버를 항상 써줘야함. 최신버전쓴다고 좋은것은아님
    // JPA가 추구하는것은 데이터베이스 독립적인것
    // JPA가 관리하고있는 인스턴스를 entitiy라고 부름
    // 도메인은 엔티티들의 구성
    // 기업에서 전문적으로 다루는 데이터를 도메인이라고 부름

    // 1. 하나의 도메인은 여러개의 엔티티로 구성될수있다.
    // 2. 엔티티는 각자 자기의 식별자가 있다.

    // insert와 update는 -> save이용
    // delete는 -> delete 있음
    // 조회할때는 -> findById사용 => 이거에 리턴타입은 optional타입으로나옴

    // JPA가 추구하는 바는 데이터베이스의 정보와 자기가 관리하는 Persistance Context가 일치하길 원함
    // 그래서 JPA는항상 DB와 싱크를 맞추려고함
    // 엔티티객체는 db라고생각

    @Id // @Id 가 식별자임
    private String keyCol; // key만 사용하면 예약어로쓰여서 error발생시킴
    private String first;
    private String last; // 컬럼이름은 데이터베이스 예약어에 중복되지 않는지 신경써서 만들어야함

    private String addr;
    private String zipCode;
    private String city;
}
