import React , { useState , useEffect } from 'react'
import axios from 'axios'
import styles from '../../css/member/login.css';

export default function Login( props ){

    //로그인
    const onLogin = (e) =>{ console.log("onLogin 실행")

        let loginform = document.querySelectorAll(".loginForm")[0];
        let loginFormData = new FormData(loginform);
        /*
            axios에서 form 전송
            login은 스프링 시큐리티가 처리한다.
        */
        axios.post("http://localhost:8080/member/login", loginFormData)
        .then(r => {
            if(r.data == false){
                alert('동일한 회원정보가 없습니다.')
            }else{
               //console.log(r.data)
               alert('[로그인 성공] 환영합니다.')
               // JS 로컬 스토리지에 로그인 성공한 흔적 남기기
               // value에 객체 대입시 [ Object ] ??? 객체처럼 사용불가
               // JSON.stringify ( 객체 ) : 해당 객체를 String 타입의 json 형식
                    // JSON.stringify ( ) : Object --> String
                    // JSON.parse ( )     : String --> Object

               //localhostStorage.setItem("key", value) : String타입
               //localhostStorage : 브라우저가 모두 닫혀도 사라지지 않고, 도메인마다 따로 저장된다.
               //localStorage.setItem("login_token", JSON.stringify(r.data)); //JS 로컬 스토리지에 로그인 성공한 흔적 남기기

                //JS 세션 스토리지[ 브라우저 모두 닫히면 사라진다. 다른 도메인과 같이 저장된다. ]
                sessionStorage.setItem("login_token", JSON.stringify(r.data));

               window.location.href = "/"; //location하면 r.data(로그인한 정보)사라짐 => Login 함수안에 있으니까 지역변수
            }
        })
    }


    return (<>
            <form className ="loginForm">
                아이디[이메일] : <input type="text" name = "memail" className = "memail"/><br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                <button onClick = {onLogin} type = "button">로그인</button>
                <a href = "http://localhost:8080/oauth2/authorization/google">구글로그인</a>
                <a href = "http://localhost:8080/oauth2/authorization/kakao">카카오로그인</a>
                <a href = "http://localhost:8080/oauth2/authorization/naver">네이버로그인</a>
            </form>
    </>)
}