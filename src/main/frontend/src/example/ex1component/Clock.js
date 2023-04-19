import React from 'react'

export default function Clock ( props ){

    // 함수 안에서 js 문법은 자유적으로 작성가능
    // [ex] let clock = new Date().toLocalTimeString

    // 함수 안에 return ( ) 안에서 js 문법은 { } 처리
    // [ex] <h4> 현재 시간 : { new Date().toLocalTimeString } </h4>

    return ( <>
        <div>
            <h3> 리액트 시계 </h3>
            <h4> 현재 시간 : { new Date().toLocaleTimeString() } </h4>
        </div>

    </>);
}