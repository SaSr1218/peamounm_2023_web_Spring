// 1. 회원가입
function onSignup(){
    let info = {
        memail : document.querySelector(".memail").value,
        mpassword : document.querySelector(".mpassword").value,
        mname : document.querySelector(".mname").value,
        mphone : document.querySelector(".mphone").value
    }

    $.ajax({
        url : "/member/info",
        method : "post" ,
        data : JSON.stringify(info) ,
        contentType : "application/json" ,
        success : (r) => {
            console.log(r);
            if ( r== true ){ alert('가입성공')}
        }
    })
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

getMember();
// 3. 회원정보 호출
function getMember(){
    $.ajax({
        url : "/member/info",
        method : "get" ,
        success : (r) => {
            document.querySelector('.infobox').innerHTML = `${r.mname}님`
            document.querySelector('.infobox').innerHTML += `<button onclick="getLogout()" type="button">로그아웃</button>`
        }
    })
}

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

// 5. 아이디찾기
/*function findId(){
    $.ajax({
        url : "/member/findId",
        method : "get" ,
        success : (r) => {
            console.log(r);

        }
    })
}*/

// 6. 비밀번호찾기
/*
function findPassword(){
    $.ajax({
        url : "/member/findPassword",
        method : "get" ,
        success : (r) => {
            console.log(r);

        }
    })
}*/
