import React , { useState , useEffect } from 'react'
import axios from 'axios'

export default function Signup( props ){


        // 0. ID 중복 체크
        const checkId = () => {
            console.log("checkId 들어옴")

            axios.get("http://localhost:8080/member/find", {params : {memail : document.querySelector(".memail").value}})
            .then(r => {
                if(r.data == false){
                    document.querySelector(".checkIdtxt").innerHTML = `중복된 아이디입니다.`;
                }else{
                    document.querySelector(".checkIdtxt").innerHTML = `O`;
                }
            }).catch(err => {console.log(err)})
        }

        // function onSignup(){} --> 변수형 익명함수 전환
        // function onSignup(){} --> const 변수 = () => { }

        // 1. 회원가입
        const onSignup = () => {
            console.log("onSign 확인")

            let checkTxt = document.querySelector(".checkIdtxt").innerHTML;

            let info = {
                memail : document.querySelector(".memail").value,
                mpassword : document.querySelector(".mpassword").value,
                mname : document.querySelector(".mname").value,
                mphone : document.querySelector(".mphone").value
            }

          if(checkTxt == `O`){
                axios.post("http://localhost:8080/member/info", info)
                .then(r => {
                    if(r.data == true){
                        alert('회원가입이 완료되었습니다.')
                        window.location.href = "/login"; // window.location.href ="이동할경로";
                    }
                }).catch(err => {console.log(err)})
            }else{
                 alert("중복된 아이디입니다. 다시 입력해주세요")
                 document.querySelector(".memail").value = ``;
                 return false;
             }
        }

    return (<>
            <h3> 회원가입 페이지 </h3>
             <form>
                아이디[이메일] : <input onKeyUp={checkId} type="text" name = "memail" className = "memail"/>
                <span className = "checkIdtxt"></span><br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                전화번호 : <input type="text" name = "mphone" className = "mphone"/><br/>
                이름 : <input type="text" name = "mname" className = "mname"/><br/>
                <button type = "button" onClick={onSignup}>가입</button>
             </form>
    </>)
}

/*
    HTML ---> JSX
        1. <> </>
        2. class -> className
        3. style -> style={{ }}
        4. 카멜표기법 :
            onclick -> onClick
            margin-top -> marginTop
*/