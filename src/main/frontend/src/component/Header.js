import React, {useState} from 'react';
import axios from 'axios';

export default function Header(props){
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));
    let [login, setLogin] = useState(JSON.parse(sessionStorage.getItem('login_token')));

    const loginOut = () => {
        sessionStorage.setItem('login_token', null);

        //백엔드의 인증세션 지우기
        axios.get('http://localhost:8080/member/logout').then(r => {console.log(r)})

        window.location.href ="/login"
    }
    //실질적인 삭제
    const accountDelete = () => {
       let mpassword = document.querySelector(".mpassword").value;

        axios.delete('http://localhost:8080/member/info', {params : {"mpassword" : mpassword}})
        .then(r => {
            if(r.data == true){
                alert('계정 삭제 성공되었습니다.');
                loginOut();
            }
        })
    }

    //삭제전 비밀번호 확인
    const checkPwd = () => {
        document.querySelector(".etcDiv").innerHTML = `  비밀번호 입력 : <input type="text" name = "mpassword" class = "mpassword"/>
                <button type = "button" onClick = {accountDelete}>계정 삭제</button><br/>`;
    }


    return(<>
        <div>
            <a href = "/" > Home  </a>
            <a href="/board/list/"> 게시판 </a>
            <a href="/admin/dashboard"> 관리자 </a>

            <div className = "etcDiv"></div>
            { login == null ?
                ( <>
                    <a href = "/member/login" > login</a>
                    <a href = "/member/signup" > signup</a>
                 </>)
                 : (<>
                    <button onClick = {loginOut}>로그아웃</button>
                    <button onClick = {checkPwd}>회원탈퇴</button>
                    </>)}
        </div>
    </>)
}