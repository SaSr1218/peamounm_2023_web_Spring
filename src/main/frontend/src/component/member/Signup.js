import React, {useState, useEffect} from 'react';
import axios from 'axios';

export default function Signup(props){

        let [ memailMsg , setMemailMsg ] = useState('');
        let [ mphoneMsg , setMphoneMsg ] = useState('');

        const checkId = (e) => {
            axios.get("/member/idcheck", {params : {memail : e.target.value}})
                .then(r => {
                    if(r.data == false){  setMemailMsg('중복된 아이디입니다.');  }
                    else{ setMemailMsg('사용가능한 아이디입니다.'); }
                }).catch(err => {console.log(err)})
        }

        const checkPhone = (e) => {
            axios.get("/member/phonecheck" , {params : {mphone : e.target.value}})
                .then(r => {
                    if(r.data == false){  setMphoneMsg('중복된 전화번호입니다.');  }
                    else{ setMphoneMsg('사용가능한 전화번호입니다.'); }
                }).catch(err => {console.log(err)})
        }

        const onSignup = () => {
            console.log("onSignup 확인")

            let info = {
                memail : document.querySelector(".memail").value,
                mpassword : document.querySelector(".mpassword").value,
                mname : document.querySelector(".mname").value,
                mphone : document.querySelector(".mphone").value
            }

          if(memailMsg == '사용가능한 아이디입니다.' && mphoneMsg == '사용가능한 전화번호입니다.'){
                axios.post("/member/info", info)
                .then(r => {
                    if(r.data == true){
                        alert('회원가입이 완료되었습니다.')
                        window.location.href = "/member/login";
                    }
                }).catch(err => {console.log(err)})
            }else{
                 alert("중복된 아이디이거나 사용중인 전화번호입니다.")
                 document.querySelector(".memail").value = ``;
                 document.querySelector(".mphone").value = ``;
                 return false;
             }
        }


    return (<>
             <form>
                아이디[이메일] : <input onChange={checkId} type="text" name = "memail" className = "memail"/>
                <span>  { memailMsg } </span> <br/>
                비밀번호 : <input type="text" name = "mpassword" className = "mpassword"/><br/>
                전화번호 : <input onChange={checkPhone} type="text" name = "mphone" className = "mphone"/><br/>
                <span>  { mphoneMsg } </span> <br/>
                이름 : <input type="text" name = "mname" className = "mname"/><br/>
                <button type = "button" onClick={onSignup}>가입</button>
             </form>
    </>)
}

/*
   HTML ---> JSX
       1. <> </>
       2. class -> className
       3. style -> style={{}}
       4. 카멜표기법
        onclick (x) -> onClick(o)
        margin-top(x) -> marginTop(o)
*/