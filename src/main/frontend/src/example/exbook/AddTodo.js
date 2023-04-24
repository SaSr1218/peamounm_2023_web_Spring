import React , { useState } from 'react';
import { Button , Grid , TextField } from "@mui/material";

export default function AddTodo( props ){

    // 사용자가 입력한 데이터를 저장할 상태변수
    const [ item , setItem ] = useState ({ title : "" })

    // 1. onInputChange 함수 작성 [ 사용자가 입력한 데이터 가져오기 ]
    const onInputChange = (e) => {
        setItem({title: e.target.value}); // react에서만 쓰임!!!!!
        console.log(item);
    }

    // 2. AppTodo 로부터 전달 받은 addItem 함수
    const addItem = props.addItem;

    // 3. onButtonClick 함수 작성 [ 버튼 클릭시 입력한 데이터 추가하기 ]
    const onButtonClick = () => {
        addItem(item); // addItem 함수 사용
        setItem({ title: ""}); // 상태변수 초기화
    }

    // 4. enterKeyEventHandler 함수 [ 엔터 키 입력시 버튼 클릭한 것과 같은 효과 ]
    const enterKeyEventHandler = (e) => {
        if ( e.key === 'Enter' ) {
            onButtonClick();
        }
    }

    return( <>
        <Grid container style={{ marginTop: 20 }} >
            <Grid xs={11} md={11} item style={{ paddingRight: 16}}>

                <TextField
                    placeholder="여기에 Todo 작성"
                    fullWidth
                    onChange={onInputChange}
                    onKeyPress={enterKeyEventHandler}
                    value={item.title}
                />

            </Grid>
            <Grid xs={1} md={1} item >
                <Button
                    fullWidth
                    style={{ height: '100%' }}
                    color="secondary"
                    variant="outlined"
                    onClick={onButtonClick}>
                    +
                </Button>
            </Grid>
        </Grid>

    </> )
}