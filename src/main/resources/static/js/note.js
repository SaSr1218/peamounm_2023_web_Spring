// 1. 등록 [ JSON.stringify() : json타입에서 문자열타입으로 변환 , JSON.parse : 문자열타입에서 json타입으로 변환 ]
function onwrite(){
    // AJAX 이용한 @PostMapping 에게 요청응답
    $.ajax({
        url : "/note/write" ,
        method : "post" ,
        data : JSON.stringify({ "ncontents" : document.querySelector(".ncontents").value }) ,
        contentType : "application/json" ,
        success : (r) => {
            console.log(r);
            if ( r == true ) { alert("글쓰기 성공"); onget();
            document.querySelector(".ncontents").value = ''}
            else { alert("글쓰기 실패"); }
        }
    })
} // 등록 end


// 2. 출력
onget();
function onget(){
// AJAX 이용한 @GetMapping 에게 요청응답
    $.ajax({
        url : "/note/get" ,
        method : "get" ,
        success : (r) => {
        console.log(r)
        let html =
                    `<tr>
                        <th> 번호 </th> <th> 내용 </th> <th> 비고 </th>
                    </tr>`;

            r.forEach( (n) => {
                html += `
                        <tr>
                            <td>${n.nno}</td>
                            <td>${n.ncontents}</td>
                            <td>
                                <button onclick="onupdate(${n.nno})" type="button"> 수정
                                <button onclick="ondelete(${n.nno})" type="button"> 삭제
                            </td>
                         </tr>`;
            })
            document.querySelector('.noteTable').innerHTML = html;

        }
    })
} // 출력 end

// 3. 수정
function onupdate( nno ){
    let ncontents = prompt("수정할내용 : ");
    $.ajax({
    url : "/note/update" ,
    method : "put" ,
    data : JSON.stringify({ "nno" : nno , "ncontents" : ncontents }) ,
    contentType : "application/json" ,
    success : (r) => { console.log(r);
            if ( r == true ) { alert('수정성공'); onget(); }
            else { alert('수정실패'); }
        }
    })
}

// 4. 삭제
function ondelete( nno ){
    $.ajax({
    url : "/note/delete" ,
    method : "delete" ,
    data : { "nno" : nno  } ,
    success : (r) => { console.log(r);
            if ( r == true) { alert('삭제성공'); onget(); }
            else { alert('삭제실패'); }
        }
    })
}