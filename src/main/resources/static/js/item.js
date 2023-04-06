// 1. 등록
function onwrite(){
    $.ajax({
        url : "/item/write" ,
        method : "post" ,
        data : JSON.stringify({"pname" : document.querySelector(".pname").value ,
                               "pcontent" : document.querySelector(".pcontent").value}) ,
        contentType : "application/json" ,
        success : (r) => {
            console.log(r);
            if ( r == true) { alert("물품등록 성공"); onget();
            document.querySelector(".pname").value = ''
            document.querySelector(".pcontent").value = ''
            } else { alert("글쓰기 실패"); }
        }

    })
}

// 2. 출력
onget();
function onget(){
    $.ajax({
    url : "/item/get" ,
    method : "get" ,
    success : (r) => {

            let html = `<tr>
                            <th>물품이름</th> <th> 물품소개 </th> <th> 비고 </th>
                        </tr>`
        r.forEach( (p) => {
                html += `<tr>
                            <td>${p.pname}</td>
                           <td>${p.pcontent}</td>
                           <td>
                                <button onclick="onupdate(${p.pno})" type="button"> 수정 </button>
                                <button onclick="ondelete(${p.pno})" type="button"> 삭제 </button>
                           </td>
\                        </tr>`
            })
            document.querySelector('.itemTable').innerHTML = html;
        }
    })
}


// 3. 수정
function onupdate( pno ){
    let pname = prompt("수정할 물품이름");
    let pcontent = prompt("수정할 물품설명");
    $.ajax({
        url : "/item/update" ,
        method : "put" ,
        data : JSON.stringify({ "pno" : pno , "pname" : pname , "pcontent" : pcontent}) ,
        contentType : "application/json" ,
        success : (r) => {
            if (r == true) { alert('수정 성공'); onget(); }
            else { alert('수정실패');}
        }
    })
}


// 4. 삭제
function ondelete( pno ){
    $.ajax({
        url : "/item/delete" ,
        method : "delete" ,
        data : { "pno" : pno } ,
        success : (r) => {
            if ( r == true) { alert('삭제성공'); onget(); }
            else { alert('삭제실패'); }
        }
    })
}