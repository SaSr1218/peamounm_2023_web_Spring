// Comment.js
    // class -> className
import React from 'react';
import styles from './Comment.css'; // css 파일 가져오기
import logo from '../../logo.svg' // img 파일 가져오기

export default function Comment ( props ){
    return ( <>
        <div className="wrapper">
            <div>
                <img src={ logo } className="logoimg" />
            </div>

            <div className="contentContainer">
                <div className="nameText"> { props.name } </div>
                <div className="commentText"> { props.comment } </div>
            </div>

        </div>
    </> );
}