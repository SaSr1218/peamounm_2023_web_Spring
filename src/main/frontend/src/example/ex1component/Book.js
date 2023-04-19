/*
컴포넌트[함수] 생성하는 방법
    1. react 라이브러리 필요
        import React from 'react'
    2. 컴포넌트 [ 함수 ] 선언
        주의 할 점
            1. 첫글자는 대문자로
                function Book( ) { }
            2. 렌더링[DOM] 할 부분 return 에 포함
            3. return 주의할 점
                1. ( 시작 ) 끝 구성
                2. <div> 혹은 <> 전체 감싸는 태그 필수
*/
import React from 'react'
function Book(){ return ( <> 처음 만든 리액트 컴포넌트 </> ) }
export default Book
