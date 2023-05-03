import React , { useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';
import styles from '../../css/board/chatting.css';

export default function Chatting( props ){

    let [ id , setId ] = useState('');   // 익명채팅방에서 사용할 id [ 난수 저장 ]
    let [ msgContent , setMsgContent ] = useState([]); // 현재 채팅중인 메시지를 저장하는 변수
    let msgInput = useRef(null);        // 채팅입력창[input] DOM 객체


    // 1. 재랜더링 될때마다 새로운 접속
    // let webSocket = useRef( new WebSocket("ws/localgost:8080/chat") );

    // 2. 재랜더링 될때 데이터 상태 유지
    let ws = useRef( null ); // 1. 모든 함수 사용할 클라이언트소켓 변수

    useEffect( () => {       // 2. 재랜더링시 1번만 실행
        if( !ws.current ){   // 3. 클라이언트소켓이 접속 되어있지 않을때 [ 유효성 검사 ]

            ws.current =  new WebSocket("ws://localhost:8080/chat"); // 서버소켓에 접속

            // 3. 서버소켓에 접속했을때 이벤트
            ws.current.onopen = () => { console.log("서버 접속했습니다.");
                let randId = Math.floor( Math.random() * ( 9999-1 ) +1 ); // 1 ~ 9999 난수값 생성
                setId( '익명' + randId);
            }
            // 3. 서버소켓에 접속 끊겼을때 이벤트
            ws.current.onclose = (e) => { console.log("서버 나갔습니다.");}
            // 3. 서버소켓과 오류 발생 이벤트
            ws.current.onerror = (e) => { console.log("접속 에러입니다.");}
            // 3. 서버소켓으로부터 메시지 받았을때 이벤트
            ws.current.onmessage = (e) => {
                console.log("서버소켓으로부터 메시지 전달받았습니다."); console.log(e); console.log(e.data);
                // msgContent.push(e.data);            // 배열에 내용 추가 ( 1번 방법 )
                // setMsgContent( [...msgContent] );   // 재랜더링        ( 1번 방법 )
                let data = JSON.parse( e.data )
                setMsgContent ( (msgContent) => [...msgContent , data ] ); // 재랜더링 ( 2번 방법 )
            }
        }
    }) ;




    // 4. 메시지 전송
    const onSend = () => {
        // let msg = msgInput.current.value; // msgInput 변수가 참조중인 <input ref={ msgInput } /> 해당 input 를 DOM 객체로 호출
        let msgBox = {
            id : id , // 보낸 사람
            msg : msgInput.current.value , // 보낸 내용
            time : new Date().toLocaleTimeString()  // 현재 시간만
        }
        ws.current.send( JSON.stringify( msgBox ) ); // 클라이언트가 서버에게 메시지 전송 [ .send( ) ]
        msgInput.current.value = '';
    }

    // 5. 메시지 받을 때마다 스크롤 가장 하단으로 내리기
    useEffect ( () => {
        document.querySelector('.chatContentBox').scrollTop = document.querySelector('.chatContentBox').scrollHeight
    } , [msgContent])

    return( <>
        <Container>
            <h6> 익명 채팅방 </h6>
            <div className="chatContentBox">
            {
                msgContent.map( (m) => {
                    return (<>
                        {/* 조건 스타일링 : style={ 조건 ? { true } , { false } */}
                        <div className="chatContent" style={ m.id == id ? { backgroundColor : '#fbceb1'} : { } } >
                            <span> {m.id} </span>
                            <span> {m.time} </span>
                            <span> {m.msg} </span>
                        </div>
                    </>)
                })
            }
            </div>
            <div className="chatInputBox">
                <span> { id }  </span>
                <input className="msgInput" ref={ msgInput } type="text" />
                <button onClick = { onSend } > 전송 </button>
            </div>
        </Container>
    </>)
}