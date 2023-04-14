getMember();
// 0. 아이디 중복 체크
function checkId(){
    $.ajax({
        url : "/member/find",
        method : "get",
        data : {"memail" : document.querySelector(".memail").value},
        success : (r) =>{
            console.log(r);
            if(r == false){
                document.querySelector(".confirmId").innerHTML = `중복된 아이디입니다.`;
            }else{
                document.querySelector(".confirmId").innerHTML = `O`;
            }
        }
    })
}

// 1. 회원가입
function onSignup(){

    let confirmId = document.querySelector(".confirmId").innerHTML;

    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }

    if ( confirmId == 'O'){
        $.ajax({
            url : "/member/info",
            method : "post" ,
            data : JSON.stringify(info) ,
            contentType : "application/json" ,
            success : (r) => {
                console.log(r);
                if ( r == true ) { alert('가입 성공')}
            }
        })
    } else {
        alert('아이디가 중복됩니다.')
    }
}

// 2. 로그인하기
/* 시큐리티 사용하므로 아래 코드 사용 X -> 폼 전송으로 로그인 요청
function onLogin(){
    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value
    }

    $.ajax({
        url : "/member/login",
        method : "post" ,
        data : JSON.stringify(info) ,
        contentType : "application/json" ,
        success : (r) => {
            console.log(r);
            if ( r== true ){ alert('로그인성공')}
        }
    })
}*/

// 3. 회원정보 호출
function getMember(){
    $.ajax({
        url : "/member/info",
        method : "get" ,
        success : (r) => {
            if( r.mname != undefined){
            document.querySelector('.infobox').innerHTML = `${r.mname}님`
            document.querySelector('.login_member').innerHTML =
            `<a href="/member/logout"> <button type="button">로그아웃</button></a>
            <button onclick="confirmPwd()" type="button">회원탈퇴</button>
            <a href="/member/update"> <button type="button">회원수정</button></a>`
            }
        }
    })
}

/*
// 4. 로그아웃
function getLogout(){

    $.ajax({
        url : "/member/logout",
        method : "get" ,
        success : (r) => {
            console.log(r);
            location.href="/";
        }
    })
}
*/

// 3. 아이디찾기
function onfindId(){
    let info = {
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }
    $.ajax({
        url : "/member/find",
        method : "post" ,
        data : JSON.stringify(info),
        contentType : "application/json",
        success : (r) => {
            console.log(r);
            document.querySelector('.getfindidbox').innerHTML = `찾은 아이디 : ${r}`;

        }
    })
}

/*function checkPwd(){
    document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" class = "mpassword"/><button type = "button" onclick="onDelete()">계정 삭제</button><br/>`;
}*/

// 4. 비밀번호찾기
function onfindPassword(){
    let info = {
        memail : document.querySelector(".memail").value,
        mphone : document.querySelector(".mphone").value
    }
    $.ajax({
        url : "/member/find",
        method : "put" ,
        data : JSON.stringify(info),
        contentType : "application/json",
        success : (r) => {
           console.log(r);
           if( r != null) {
            document.querySelector('.newPwd').innerHTML = `임시 회원번호 : ${r}`
           }else { alert("해당 정보가 없습니다.")}

        }
    })
}

// 5. 회원수정
function onUpdate(){
    let info = {
        mphone : document.querySelector(".mphone").value,
        mname : document.querySelector(".mname").value
    }
    $.ajax({
        url : "/member/info" ,
        method : "put" ,
        data : JSON.stringify(info),
        contentType : "application/json",
        success : (r) => {
            if( r == true ){
                getMember();
                location.href="/"
            }

        }
    })

}
function confirmPwd(){
    document.querySelector('.checkPwd').innerHTML = `비밀번호 확인 : <input type="text" name="mpassword" class="mpassword"><button onclick="onDelete()" type="button"> 계정탈퇴 </button>`
}

// 6. 회원삭제
function onDelete(){
   let mpassword = document.querySelector(".mpassword").value;

   $.ajax({
        url : "/member/info",
        method : "delete",
        data : {"mpassword" : mpassword},
        success : function(data){
                if(data == true){
                       alert('계정탈퇴 성공');
                       location.href = "/member/logout"
                }
        }
   })
}