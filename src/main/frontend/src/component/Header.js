import React, { useState , useEffect } from 'react';
import axios from 'axios';

export default function Header(props){
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));
    let [login, setLogin] = useState( null ); // 로그인 상태

    // 로그아웃
    const loginOut = () => {
        sessionStorage.setItem('login_token', null);
        //백엔드의 인증세션 지우기
        axios.get('/member/logout').then(r => {console.log(r)})
        window.location.href ="/member/login";
        setLogin( null );
    }

    // 로그인 상태 호출
    useEffect( () => {
        axios.get("/member/info")
            .then(r => { console.log(r.data);
                if ( r.data != '') { // 로그인 되어 있으면 // 서비스에서 null 값을 줌(비로그인시) -> js에서는 ''이다.
                    // JS 세션 스토리지에 저장
                    sessionStorage.setItem("login_token", JSON.stringify(r.data));
                    // 상태변수에 세션 스토리지 호출해서 상태변수에 데이터 저장 [ 렌더링 하기 위함 ]
                    setLogin( JSON.parse( sessionStorage.getItem("login_token") ) );
                }
             })
    }, [])

    // 계정 탈퇴
    const accountDelete = () => {
       let mpassword = document.querySelector(".mpassword").value;

        axios.delete('/member/info', {params : {"mpassword" : mpassword}})
        .then(r => {
            if(r.data == true){
                alert('계정 삭제 성공되었습니다.');
                loginOut();
            }
        })
    }

    // 삭제전 비밀번호 확인
    const checkPwd = () => {
        document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" class = "mpassword"/>
                <button type = "button" onClick = {accountDelete}>계정 삭제</button><br/>`;
    }


    return(<>
        <div>
            <a href = "/" > Home  </a>
            <a href="/board/list/"> 게시판 </a>
            <a href="/admin/dashboard"> 관리자 </a>
            {/* AppTodo 연결하기! */}
            <a href="/chatting/home"> 익명채팅방 </a>

            <div className = "etcDiv"></div>
            { login == null ?
                ( <>
                    <a href = "/member/login" > 로그인 </a>
                    <a href = "/member/signup" > 회원가입 </a>
                 </>)
                 : (<>
                    <button onClick = {loginOut}> 로그아웃 </button>
                    <button onClick = {checkPwd}> 회원탈퇴 </button>
                    </>)}
        </div>
    </>)
}