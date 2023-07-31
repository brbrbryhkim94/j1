package org.zerock.j1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class); // super안에 도메인 클래스

    }

    @Override
    public Page<Board> search1(String searchType, String keyword, Pageable pageable) { // 2 메소드추가

        QBoard board = QBoard.board; // QBoard가 있는 상태에서 코딩해야함

        JPQLQuery<Board> query = from(board); // sql 문을 객채화시킨거라고 생각하면됨

        if (keyword != null && searchType != null) {

            // tc-> [t,c]
            String[] searchArr = searchType.split("");

            // BooleanBuilder는 괄호 열고 괄호 닫고의 역할을함
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch (type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            query.where(searchBuilder);

        } // querydsl 목표는 자바로 동적쿼리를 만드는것

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch(); // 목록데이터를 가져오는게 fetch()임

        long count = query.fetchCount();

        log.info(list);

        log.info("count : " + count);
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        if (keyword != null && searchType != null) {

            // tc-> [t,c]
            String[] searchArr = searchType.split("");

            // BooleanBuilder는 괄호 열고 괄호 닫고의 역할을함
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch (type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for
            query.where(searchBuilder);
        }

        query.groupBy(board);

        JPQLQuery<Tuple> tupleQuery = query.select(board.bno, board.title, board.writer, reply.countDistinct());

        this.getQuerydsl().applyPagination(pageable, tupleQuery);
        log.info("1------------------------------------------");

        List<Tuple> tuples = tupleQuery.fetch();
        List<Object[]> arrList = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());
        log.info("2------------------------------------------");
        log.info(tuples);
        log.info("3------------------------------------------");
        Long count = tupleQuery.fetchCount();
        log.info("count: " + count);

        return new PageImpl<>(arrList, pageable, count);
    }

    @Override
    public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {
        Pageable pageable = makePageable(requestDTO);

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));
        String keyword = requestDTO.getKeyword();
        String searchType = requestDTO.getType();

        if (keyword != null && searchType != null) {

            // tc-> [t,c]
            String[] searchArr = searchType.split("");

            // BooleanBuilder는 괄호 열고 괄호 닫고의 역할을함
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchArr) {
                switch (type) {
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }
            } // end for

            query.where(searchBuilder);
        }
        this.getQuerydsl().applyPagination(pageable, query);
        query.groupBy(board);
        JPQLQuery<BoardListRcntDTO> listQuery = query
                .select(Projections.bean(BoardListRcntDTO.class, board.bno, board.title, board.writer,
                        reply.countDistinct().as("replyCount")));

        List<BoardListRcntDTO> list = listQuery.fetch();
        log.info("-----------------------------------------");
        log.info(list);
        long totalCount = listQuery.fetchCount();
        return new PageResponseDTO<>(list, totalCount, requestDTO);
    }
}
