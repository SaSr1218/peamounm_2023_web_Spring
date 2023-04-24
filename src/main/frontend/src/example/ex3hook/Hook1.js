import React , { useState , useEffect } from 'react'

/*
// 2. 예제 훅[ useState() ] 을 사용 안했을때
export default function Hook1( props ){
    let count = 0;

    const countHandler = () => {
        alert('countHandler 함수 실행');
        count++;
        alert('현재 count : ' + count );
    }

    return( <>
        <div>
            <p> 총  {count}  번 클릭했습니다 </p>
            <button onClick={countHandler}>
                증가
            </button>
        </div>
    </>)*/

// 2. 예제 훅[ useState() ] 을 사용했을때
export default function Hook1( props ){
    let [count , setCount ] = useState(0);

    // 1. useEffect() 함수
        /*
            컴포넌트 생명주기 [ Life Cycle ]
            mount : 생성 , update : 업데이트 , unmount : 제거
            1. useEffect ( () => { } );
            2. mount, update 시 실행되는 함수
            3. 하나의 컴포넌트에서 여러번 사용 가능
            4. unmount 작동할 경우 return 사용
        */

    useEffect( () => {
        console.log( "useEffect1() 실행 총 클릭수 : " + count );
    })

    useEffect( () => {
        console.log( "useEffect2() 실행 총 클릭수 : " + count );
    })
    useEffect( () => {
        console.log( "useEffect3() 실행 총 클릭수 : " + count );
        return () => {} // unmount
    })

    const countHandler = () => { alert('countHandler 함수 실행');
        setCount( count+1);
    }

    return( <>
        <div>
            <p> 총  {count}  번 클릭했습니다 </p>
            <button onClick={countHandler}>
                증가
            </button>
        </div>
    </>)

}