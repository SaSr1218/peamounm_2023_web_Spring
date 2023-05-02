package ezenweb.web.service;

import ezenweb.web.domain.board.*;
import ezenweb.web.domain.member.MemberDto;
import ezenweb.web.domain.member.MemberEntity;
import ezenweb.web.domain.member.MemberEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class BoardService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BoardEntityRepository boardEntityRepository;
    @Autowired private MemberEntityRepository memberEntityRepository;

    // 1. 카테고리 등록
    @Transactional
    public boolean categoryWrite( BoardDto boardDto ){ log.info("s board dto : " + boardDto );
        CategoryEntity entity = categoryRepository.save( boardDto.toCategoryEntity() ); // 1. 입력받은 cname 을 Dto 에서 카테고리 entity 형변환 해서 save
        if( entity.getCno() >= 1 ){ return true; }  // 2. 만약에 생성된 엔티티의 pk가 1보다 크면 save 성공
        return false;
    }
    // 2. 모든 카테고리 출력
    @Transactional
    public List<CategoryDto> categoryList() { log.info("c categoryList : " );
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        // 형변환 List<엔티티> --> MAP
        List<CategoryDto> list = new ArrayList<>();
        categoryEntityList.forEach( (e)-> {
            list.add(  new CategoryDto( e.getCno() , e.getCname()) );
        });
        return list;
    }

    // 3. 게시물 쓰기
    @Transactional
    public byte write( BoardDto boardDto ){
        log.info("s board dto : " + boardDto );
        // 1. 카테고리 엔티티 찾기
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById( boardDto.getCno() ); // 1. 선택된 카테고리 번호를 이용한 카테고리 엔티티 찾기
        if( !categoryEntityOptional.isPresent() ){ return 1; }         // 2. 만액에 선택된 카테고리가 존재하지 않으면  리턴
        CategoryEntity categoryEntity = categoryEntityOptional.get();    // 3. 카테고리 엔티티 추출
        // 2.로그인된 회원의 엔티티 찾기
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 1. 인증된 인증 정보  찾기
        if( o.equals("anonymousUser") ){ return 2;}
        MemberDto loginDto = (MemberDto)o;  // 2. 형변환
        MemberEntity memberEntity = memberEntityRepository.findByMemail( loginDto.getMemail() ).get(); // 3. 회원엔티티 찾기
        // 3. 게시물 쓰기
        BoardEntity boardEntity = boardEntityRepository.save( boardDto.toBoardEntity() );
        if( boardEntity.getBno() < 1 ){ return  3; }
        // 4. 양방향 관계 [ 카테고리안에 게시물 힙[주소] 대입, 게시물 안에 카테고리 힙[주소] 대입 ]
        categoryEntity.getBoardEntityList().add( boardEntity ); // 1. 카테고리 엔티티에 생성된 게시물 등록
        boardEntity.setCategoryEntity( categoryEntity ); // 2. 생성된 게시물에 카테고리 엔티티 등록
        // 5. 양방향 관계 [ 게시물 안에 멤버 대입 , 멤버 안에 게시물 대입 ]
        boardEntity.setMemberEntity( memberEntity ); // 1. 생성된 게시물 엔티티에 로그인된 회원 등록
        memberEntity.getBoardEntityList().add(boardEntity);  // 2. 로그인된 회원 엔티티에 생성된 게시물 엔티티 등록

        // 6. 공지사항 게시물 정보 확인
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(1) ;
        log.info( "공지사항 확인 : " + optionalCategoryEntity.get() );

        return 4;
    }

    // 4. 카테고리별 게시물 출력
    @Transactional
    public PageDto list(PageDto pageDto){
        // 1. pageable 인터페이스 [ 페이징처리 관련 api ]
            // import org.springframework.data.domain.Pageable;
        Pageable pageable = PageRequest.of(pageDto.getPage()-1 , 5 , Sort.by(Sort.Direction.DESC, "bno") );
        // PageRqeust.of( 페이지번호[0부터 시작] , 페이지당표시개수 , Sort.by(Sort.Direction.ASC/DESC , '정렬기준필드명' ) );
        Page<BoardEntity> entityPage = boardEntityRepository.findBySearch(
                pageDto.getCno(), pageDto.getKey() , pageDto.getKeyword() , pageable );
        //
        List<BoardDto> boardDtoList = new ArrayList<>();
        entityPage.forEach( (b) -> {  boardDtoList.add( b.toDto() ); });
        pageDto.setBoardDtoList( boardDtoList );
        pageDto.setTotalPage(entityPage.getTotalPages());
        pageDto.setTotalCount(entityPage.getTotalElements() );
        return pageDto;

        // log.info("총 게시물수 : " + entityPage.getTotalElements() );
        // log.info("총 페이지수 : " + entityPage.getTotalPages() );

    }

    // 5. 내가 쓴 게시물 출력
    public List<BoardDto> myboards(){ log.info("s myboards : ");
        // 1. 로그인 인증 세션 호출 [ object ] --> dto 강제형변환
        MemberDto memberDto = (MemberDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // 일반회원 : 모든정보 vs oauth회원 : memail , mname , mrole
        // 2. 회원 엔티티 찾기
        MemberEntity entity =  memberEntityRepository.findByMemail( memberDto.getMemail() ).get();
        // 3. 회원 엔티티 내 게시물리스트 반복문 돌려서 dto 리스트 로 변환
        List<BoardDto> list = new ArrayList<>();
        entity.getBoardEntityList().forEach((e) -> {
            list.add( e.toDto() );
        });
        return list;
    }

    // 6. 개별 게시물 출력( + 댓글 출력 )
    @Transactional
    public BoardDto getboard( int bno ){ log.info("s list bno : " + bno);
        Optional<BoardEntity> optionalBoardEntity =  boardEntityRepository.findById(bno);
        if (optionalBoardEntity.isPresent() ) {
            BoardEntity boardEntity = optionalBoardEntity.get();

            // 상위 댓글
            List<ReplyDto> list = new ArrayList<>(); // 상위 댓글 리스트
            boardEntity.getReplyEntityList().forEach( (r) -> {
                if( r.getRindex() == 0 ){ // 상위 댓글 필터
                    list.add( r.toDto() );
                    // 하위 답글
                    boardEntity.getReplyEntityList().forEach( (r2) -> {
                        if(r.getRno() == r2.getRindex() ){
                            // 리스트길이-1 : 최근에 리스트에 등록한 인덱스 번호 = 마지막 인덱스
                            list.get( list.size()-1 ).getRereplyDtoList().add(r2.toDto());

                        }
                    });
                }
            });

            BoardDto boardDto = boardEntity.toDto();
            boardDto.setReplyDtoList( list );
            return boardDto;
        }
        return null;
    }

    // 7. 개별 게시물 삭제
    @Transactional
    public int delete( int bno  ){

        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( bno );
        if( optionalBoardEntity.isPresent() ){
            boardEntityRepository.delete( optionalBoardEntity.get() );
            return 0;
        }
        return 1;
    }

    // 8. 개별 게시물 수정 [ 진행중 ]
    @Transactional
    public boolean boardUpdate( BoardDto boardDto ){
        // 게시물 bno 확인
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById(boardDto.getBno() );
        // 카테고리 cno 확인
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(boardDto.getCno());

        if ( optionalBoardEntity.isPresent() && optionalCategoryEntity.isPresent() ){
            BoardEntity entity = optionalBoardEntity.get();

            entity.setBtitle(boardDto.getBtitle() );
            entity.setBcontent(boardDto.getBcontent());

            entity.setCategoryEntity(optionalCategoryEntity.get());

            return true;
        }
        return false;
    }


    // ---------------------------- 댓글 쪽 ------------------------ //

    @Autowired
    private ReplyEntityRepository replyEntityRepository;
    @Transactional
    public boolean postReply ( ReplyDto replyDto ){
        // -1. 로그인 했는지 회원정보 호출 [ 댓글 작성자 ]
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( o.equals("anonymousUser")) { return false; }
        MemberDto memberDto = (MemberDto)o;
        MemberEntity memberEntity = memberEntityRepository.findById( memberDto.getMno() ).get();

        // 0. 댓글 작성할 게시물 호출
        Optional<BoardEntity> optionalBoardEntity = boardEntityRepository.findById( replyDto.getBno() );
        if ( !optionalBoardEntity.isPresent() ) { return false; }
        BoardEntity boardEntity = optionalBoardEntity.get();

        // 1. 댓글 작성
        ReplyEntity replyEntity = replyEntityRepository.save( replyDto.toEntity() );
        if( replyEntity.getRno() < 1 ) { return false; }

        // 2. 댓글과 회원이 양방향 관계 [ 댓글 -> 회원 and 회원 -> 댓글 = 양방향! ]
        replyEntity.setMemberEntity( memberEntity );
        memberEntity.getReplyEntityList().add( replyEntity );

        // 3. 댓글과 게시물의 양방향 관계 [ 댓글 -> 게시물 and 게시물 -> 댓글 ]
        replyEntity.setBoardEntity( boardEntity );
        boardEntity.getReplyEntityList().add( replyEntity );
        return true;
    }

    public boolean getReply() {
        return true;
    }
    // 11. 댓글 수정 [ U ]
    @Transactional
    public boolean putReply(ReplyDto replyDto) {
        Optional< ReplyEntity > optionalReplyEntity = replyEntityRepository.findById( replyDto.getRno() );
        if (optionalReplyEntity.isPresent() ){
            optionalReplyEntity.get().setRcontent( replyDto.getRcontent() );
            return true;
        }
        return false;
    }
    // 12. 댓글 삭제 [ D ]
    @Transactional
    public boolean deleteReply( int rno ) {
        Optional< ReplyEntity > optionalReplyEntity = replyEntityRepository.findById( rno );

        if ( optionalReplyEntity.isPresent() ){
            replyEntityRepository.delete( optionalReplyEntity.get() );
            return true;
        }
        return false;
    }


}
