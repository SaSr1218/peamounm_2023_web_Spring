import React,{ useState , useEffect } from 'react';
import axios from 'axios';
export default function DashBoard( props ) {

  const setCategory = () => {
    console.log('setCategory 실행');
    let cname = document.querySelector('.cname');
    axios
      .post('http://localhost:8080/board/category/write', { "cname": cname.value })
      .then((r) => {
        if (r.data === true) {
          alert('카테고리 등록 성공');
          cname.value = '';
        }
      });
  };


    return(<>
                <h3> 관리자 페이지 </h3>
                <h6> 게시판 카테고리 추가 </h6>
                <input type="text" className="cname" />
                <button onClick={setCategory} type="button"> 카테고리 등록</button>
    </>)

}