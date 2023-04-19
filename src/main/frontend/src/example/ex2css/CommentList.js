// CommentList.js
import React from 'react'
import Comment from './Comment';
export default function CommentList( props ){

    // 서버로부터 받은 데이터[JSON] 예시
    let r = [
        { name : "유재석1" , "comment" : "안녕하세요1" } ,
        { name : "유재석2" , "comment" : "안녕하세요2" } ,
        { name : "유재석3" , "comment" : "안녕하세요3" }
    ]
    console.log(r);
    // return 안에서 js 문 사용시 { }
    // map [ return 가능 ]  vs forEach [ return 불가능 ]

    return ( <>
        <div>

            { /* jsx 시작 */
                r.map( (c) => {
                    return ( <Comment name={ c.name } comment={ c.comment } />)
                })
            }

        </div>
    </> );

}