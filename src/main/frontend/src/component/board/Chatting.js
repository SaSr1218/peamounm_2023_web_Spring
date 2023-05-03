import React , { useEffect , useState , useRef } from 'react';
import Container from '@mui/material/Container';

export default function Chatting( props ){

    let msgInput = useRef(null); // 채팅입력창[input] DOM 객체
    let [ msgContent , setMsgContent ] = useState([]); // 현재 채팅중인 메시지를 저장하는 변수

    // 1. 재랜더링 될때마다 새로운 접속
    // let webSocket = useRef( new WebSocket("ws/localgost:8080/chat") );

    // 2. 재랜더링 될때 데이터 상태 유지
    let webSocket = useRef( new WebSocket("ws://localhost:8080/chat") );

    // 3. 서버소켓에 접속했을때 이벤트
    webSocket.current.onopen = () => { console.log("서버 접속했습니다.");}
    // 3. 서버소켓에 접속 끊겼을때 이벤트
    webSocket.current.onclose = (e) => { console.log("서버 나갔습니다.");}
    // 3. 서버소켓과 오류 발생 이벤트
    webSocket.current.onerror = (e) => { console.log("접속 에러입니다.");}
    // 3. 서버소켓으로부터 메시지 받았을때 이벤트
    webSocket.current.onmessage = (e) => {
        console.log("서버소켓으로부터 메시지 전달받았습니다."); console.log(e); console.log(e.data);
        // msgContent.push(e.data);            // 배열에 내용 추가 ( 1번 방법 )
        // setMsgContent( [...msgContent] );   // 재랜더링        ( 1번 방법 )
        setMsgContent ( [...msgContent , e.data ] ); // 재랜더링 ( 2번 방법 )
    }

    const onSend = () => {
        let msg = msgInput.current.value; // msgInput 변수가 참조중인 <input ref={ msgInput } /> 해당 input 를 DOM 객체로 호출
        webSocket.current.send( msg ); // 클라이언트가 서버에게 메시지 전송 [ .send( ) ]
        msgInput.current.value = '';
    }

    return( <>
        <Container>
            <h6> 익명 채팅방 </h6>
            <div className="chatContentBox">
            {
                msgContent.map( (m) => {
                    return (<>
                                <div>
                                    { m }
                                </div>
                            </>)
                })
            }
            </div>
            <div className="chatInputBox">
                <span> 익명94 </span>
                <input ref={ msgInput } type="text" />
                <button onClick = { onSend } > 전송 </button>
            </div>
        </Container>
    </>)
}