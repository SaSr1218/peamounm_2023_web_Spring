import React , { useState , useEffect } from 'react'
import axios from 'axios'

export default function Find( props ){

    let [ findId , setFindId ] = useState('');

    let [ findPwd , setFindPwd ] = useState('');

    // 1. 아이디찾기
    const onfindId = () => {
        console.log("onfindId 확인")
        let info = {
            mname : document.querySelector(".mname").value,
            mphone : document.querySelector(".mphone").value
        }
        axios.post("/member/findid", info)
        .then(r => { console.log(r);
            if(r.data != ''){ setFindId(r.data)}
        })
    }

    // 2. 비밀번호찾기
    const onfindPassword = () => {
        let info = {
            memail : document.querySelector(".memail").value,
            mphone : document.querySelector(".mphone").value
        }
        axios.put("/member/findpwd", info)
        .then(r => { console.log(r);
            if ( r.data != '') { setFindPwd(r.data) }
        })
    }



    return (<>
        <h3> 아이디 찾기 </h3>
            <form>
                이름 : <input type="text" name="mname" className="mname" /> <br/>
                전화번호 : <input type="text" name="mphone" className="mphone" /> <br/>
                <button onClick={onfindId} type="button"> 아이디찾기 </button>
                <div> { findId }</div>
            </form>

        <h3> 비밀번호 찾기 </h3>
              <form>
                  아이디[이메일] : <input type="text" name="memail" className="memail" /> <br/>
                  전화번호 : <input type="text" name="mphone" className="mphone" /> <br/>
                  <button onClick={onfindPassword} type="button"> 비밀번호찾기 </button>
                  <div> { findPwd } </div>
              </form>
    </>)


}