import React , { useState , useEffect } from 'react'

export default function AddTodo( props ){

    let [ value , setValue ] = useState(0);

    console.log(value);

    useEffect( () => { // mount, update 될 때 실행되는 이벤트 함수
        console.log(' [] 없는 useEffect1 실행')
        return () => {
            console.log(" [] 없는 useEffect1 종료되면서 실행")
        }
    } )

    useEffect( () => { // mount 될때 1번 실행되는 이벤트 함수
        console.log(' [] 있는 useEffect2 실행')
        return () => {
            console.log(" [] 있는 useEffect2 종료되면서 실행")
        }
    }, [ ])

    useEffect( () => { // mount, 특정 상태변수가 update 될 때 실행되는 이벤트 함수
        console.log(' [value] 있는 useEffect3 실행')
        return () => {
            console.log(" [value] 있는 useEffect3 종료되면서 실행")
        }
    }, [ value ])

    return (<>
                <p> {value}</p>
                <button onClick={ () => setValue( value +1) }>
                +
                </button>

            </> );
}