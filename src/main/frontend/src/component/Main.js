import React,{useState} from 'react';

export default function Main(props){
    //localStorage.getItem('login_token') String형으로 변환했기 때문에 객체로 바꿔야한다.
    //
    //let [login, setLogin] = useState(JSON.parse(localStorage.getItem('login_token')));

    return(<>
        <h5>Main Page</h5>
    </>)
}