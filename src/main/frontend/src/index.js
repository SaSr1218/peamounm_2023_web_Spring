import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';    // 1. import 이용해 App 컴포넌트[함수]를 불러온다.
import reportWebVitals from './reportWebVitals';
// ---------------------------------------------------------- //
// import 컴포넌트명 from 'index.js'파일 기준으로 상대경로
import Book from './example/ex1component/Book';
import Product from './example/ex1component/Product';
import ProductList from './example/ex1component/ProductList';
import Clock from './example/ex1component/Clock';
import Comment from './example/ex2css/Comment';
import CommentList from './example/ex2css/CommentList';

// 1. HTML에 존재하는 div 가져오기 [ document.getElementById('root') ]
// 2. ReachDOM.createRoot ( 해당 div ) : 해당 div를 리액트 root 로 사용하여 root 객체 생성
const root = ReactDOM.createRoot(document.getElementById('root'));

// 5. 예제5 css 적용
root.render(
  <React.StrictMode>
    <CommentList />
  </React.StrictMode>
);

/*
// 4. 예제4 렌더링 반복
// 1초마다 해당 코드 실행 : setInterval
setInterval( () => {
    root.render(
      <React.StrictMode>
        <Clock />
      </React.StrictMode>
    );
} , 1000 );
*/

/*
// 3. 예제3 컴포넌트에 컴포넌트 포함 하기 정의 컴포넌트 랜더링
root.render(
  <React.StrictMode>
    <ProductList />
  </React.StrictMode>
);
*/

/*
// 2. 예제2 개발자 정의 컴포넌트 랜더링
root.render(
  <React.StrictMode>
    <Product />
  </React.StrictMode>
);
*/

/*
//1. 예제1 개발자 정의 컴포넌트 랜더링
root.render(
  <React.StrictMode>
    <Book />
  </React.StrictMode>
);
*/

/*
// 4. root.render( ) : 해당 root 객체 ( 우리가 가져온 div ) 의 컴포넌트 랜더링
root.render(
  <React.StrictMode>
    <App /> // 4. APP 컴포넌트에 render 함수에 포함 [ App 포함하는 방법 : 상단에 import ]
  </React.StrictMode>
);
*/

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
