console.log("board.js")
// 1. 카테고리 등록
function setCategory(){  console.log("setCategory()")
    let cname = document.querySelector(".cname").value;
    $.ajax({ // ajax start
        url : "/board/category/write",
        method : "POST",
        data : JSON.stringify( {"cname" : cname} ),
        contentType : "application/json" ,
        success : (r)=>{
            console.log(r)
            if( r == true ) getCategory();
            document.querySelector(".cname").innerHTML = '';
        } // success end
    }) // ajax end
} // setCategory end

// 2. 카테고리 모든 출력
getCategory()
function getCategory(){
    console.log("getCategory()")
    $.ajax({
        url : "/board/category/list",
        method : "get",
        success : (r)=>{
            console.log(r);
            let html = `<button onclick="selectorCno(0)" type="button">전체보기</button>`;
            for( let cno in r  ){
                console.log(" 키/필드 : " + cno);
                console.log(" 키/필드 에 저장된 값  : " + r[cno] );
                html += `<button onclick="selectorCno(${cno})" type="button">${ r[cno] }</button>`;
            } //for end
            document.querySelector('.categorylistbox').innerHTML = html;
        }
    })
}

// 3. 카테고리 선택
let selectCno = 0; // 선택된 카레고리 번호[ 기본값 = 0 ( 전체보기 ) ]
function selectorCno( cno ){
    console.log( cno +" 의 카테고리 선택");
    selectCno = cno; // 이벤트로 선택한 카테고리 번호를 전역변수에 대입
    getBoard ( cno ); // 선택된 카테고리 기준으로 게시물 출력
}

// 4. 게시물 쓰기
function setBoard(){
    if ( selectCno == 0 ){
        alert('작성할 게시물의 카테고리를 선택해주세요.'); return;
    }

    let info = { // @RequestBody 이용한 json 요청값을 자동으로 dto 매핑하기 위해서 조건 [ 필드명 동일 ]
        btitle : document.querySelector(".btitle").value,
        bcontent : document.querySelector(".bcontent").value,
        cno : selectCno
    }

    $.ajax({
        url : "/board/write" ,
        method : "post" ,
        data : JSON.stringify( info ) ,
        contentType : "application/json" ,
        success : (r) => {
                if ( r == 4 ) {
                alert("글쓰기성공");
                document.querySelector(".btitle").value = '';
                document.querySelector(".bcontent").value = '';
                getBoard( selectCno ) // 현재 선택된 카테고리 기준으로 게시물 재출력
            }
        }
    })
}

// 5. 게시물 리스트 출력 [ 선택된 카테고리의 게시물 출력 ]
getBoard(0)
function getBoard( cno ){
    selectCno = cno;
    $.ajax({
        url : "/board/list" ,
        method : "get" ,
        data : { "cno" : selectCno } ,
        success : (r) => {
            console.log(r);
            let html = ` <tr> <th> 번호 </th><th> 제목 </th><th> 작성자 </th>
                            <th> 작성일 </th> <th> 조회수 </th>
                        </tr>`;
            r.forEach( ( o ) => {
                html += ` <tr>
                            <td><button onclick="boardclick(${ o.bno })" type="button"> ${ o.bno } </button> </td> <td> ${ o.btitle } </td>
                            <td> ${ o.mname } </td>  <td> ${ o.bdate } </td>
                            <td> ${ o.bview } </td>
                        </tr>
                `
            })
            document.querySelector('.boardlistbox').innerHTML = html;
        }
    })
}

// 6. 내가 작성한(로그인 되어있는 가정) 게시물
function myboards(){
    $.ajax({
        url : "/board/myboards" ,
        method : "get" ,
        success : (r) => {
            console.log(r);
            let html = ` <tr> <th> 번호 </th><th> 제목 </th><th> 작성자 </th>
                            <th> 작성일 </th> <th> 조회수 </th>
                        </tr>`;
            r.forEach( ( o ) => {
                html += ` <tr> <td> ${ o.bno } </td> <td> ${ o.btitle } </td>
                            <td> ${ o.mname } </td>  <td> ${ o.bdate } </td>
                            <td> ${ o.bview } </td>
                        </tr>
                `
            })
            document.querySelector('.boardlistbox').innerHTML = html;
        }
    })
}

// 7. 게시물 선택번호전달
let selectBno = 0;
function selectorBno( bno ){
    console.log( bno +"의 게시물 선택")
    selectBno = bno;
}

// 8. 게시물 선택
function boardclick( bno ){
    selectBno = bno;
    $.ajax({
        url : "/board/click" ,
        method : "get" ,
        data : { "bno" : selectBno } ,
        success : (r) => {
            console.log(r);
            let html = ` <tr>
                            <th> 게시물번호 </th><th> 게시물제목 </th><th> 게시물내용 </th>
                            <th> 게시물조회수 </th><th> 게시물등록일 </th> <th> 작성자 </th>
                            <th> 비고 </th>
                        </tr>`;

                html += ` <tr>
                            <td> ${ r.bno }</td> <td> ${ r.btitle } </td> <td> ${ r.bcontent } </td>
                            <td> ${ r.bview } </td> <td> ${ r.bdate } </td> <td> ${ r.mname } </td>
                            <td> <button onclick="boardDelete(${r.bno})"> 삭제 </button> </td>
                        </tr>
                `
            document.querySelector('.boardbox').innerHTML = html;
        }
    })
}

// 9. 게시물삭제
function boardDelete(bno){
    $.ajax({
        url : "/board/click",
        method : "delete",
        data : {"bno" : selectBno } ,
        success : (r) => {
            console.log(r);
            if (r == 0){
                alert('삭제성공')
            } else if ( r == 1 ){
                alert('삭제 할 수 없습니다.')
            } else if ( r == 2 ){
                alert('삭제권한이 없습니다.')
            }

        }
    })
}



/*
    해당 변수의 자료형 확인 Prototype
    Array : forEach() 가능
        { object , object , object  }
    object : forEach() 불가능 --->  for( let key in object ){ } : 객체내 key를 하나씩 출력
        {
            필드명 : 값 ,
            필드명 : 값 ,
            필드명 : 값
        }
        object[필드명] : 해당 필드의 값 호출
*/