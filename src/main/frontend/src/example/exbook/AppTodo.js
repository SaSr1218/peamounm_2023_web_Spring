// 교재 APP 컴포넌트 --> AppTodo
import React , { useState }  from 'react';
import { Container, List, Paper } from "@mui/material";
import Todo from './Todo';
import AddTodo from './AddTodo';

export default function AppTodo( props ){

    // 1.
    // item 객체에 { id : "0" , title : "Hello World 1" , done : true } 초기값을 대입한 것
    const [ items , setItems ] = useState (
        [ // array start
            {
                id : "0" ,
                title : "Hello World 1" ,
                done : true
            } ,
            {
                id : "1" ,
                title : "Hello World 2" ,
                done : true
            }
        ] // array end
    ) // useState 함수 end

    const addItem = (item) => {
        item.id = "ID-" + items.length; // key 를 위한 id
        item.done = false; // done 초기화
        // 업데이트는 반드시 setItems로 하고 새 배열을 만들어야 한다.
        setItems([...items, item]);
        console.log("items : " , items );

    };

    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
            <Paper style={{ margin : 16 }} >
                <List>{
                        items.map( (i) =>
                            <Todo item = { i } key = { i.id } />
                        )
                    }
                </List>
            </Paper>

    return ( <>
        <div className="App">
            <Container maxWidth="md">
                <AddTodo addItem={addItem} />
            <div className="TodoList"> { TodoItems } </div>
            </Container>
        </div>
    </> );
}