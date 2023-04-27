package ezenweb.web.domain.board;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Page와 관련된 정보를 모으기 위함 [ 자료형이 제각각임, dto , long int ]
public class PageDto {
    private Long totalCount;                 // 1. 전체 게시물수
    private int totalPage;                   // 2. 전체 페이지수
    private List<BoardDto> boardDtoList;     // 3. 현재 페이지의 게시물 dto들
    private int page;                        // 4. 현재 페이지번호
    private int cno;                         // 5. 현재 카테고리번호
    private String key;                      // 6. 검색할 주제
    private String keyword;                  // 7. 검색할 키워드

}
