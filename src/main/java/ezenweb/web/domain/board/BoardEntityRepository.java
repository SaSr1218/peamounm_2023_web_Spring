package ezenweb.web.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardEntityRepository extends JpaRepository< BoardEntity , Integer> {

    @Query(
            value = "select * from board where if( :cno = 0 , cno like '%%' , cno = :cno ) and " +
                    " if( :key = '' , true , " +
                        "if( :key = 'btitle' , btitle like %:keyword% , bcontent like %:keyword% ) )"
            , nativeQuery = true ) // JPA 형식 X 순수 SQL 적용하는 함수 정의
    Page<BoardEntity> findBySearch(@Param( "cno" ) int cno , @Param("key") String key , @Param("keyword") String keyword , Pageable pageable);

    // 1. 동일한 cno 찾기
        // select * from board where cno = ?
    // 2. 동일한 필드에서 검색어[포함 like %%] 찾기
        // select * from board where btitle = ?
        // select * from board where bcontent = ?
}

// [JSP] select * from board where cno = ?
// ps.setInt ( 1 , cno );
// [JPA] select * from board where cno = :cno
// :cno ( 해당 함수의 매개변수 이름 )

    /* cno가 0(전체보기)면 cno가 0인경우 조건이 존재하지 않는다.
     cno가 0이면 전체보기하는 query문을 추가해줘야함! where if ( 조건 , 참 , 거짓 )
     cno like '%%' = 전체보기라는 뜻
    */