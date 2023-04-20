// 교재 APP 컴포넌트 --> AppTodo
import React , { useState }  from 'react';
import { Container, List, Paper } from "@mui/material";
import Todo from './Todo';
import AddTodo from './AddTodo';

export default function AppTodo( props ){

    // 1. // item 객체에 { id : "0" , title : "Hello World 1" , done : true } 초기값을 대입한 것
    const [ items , setItems ] = useState (           // 1번!_!
        [ // array start

            /* {
                id : "0" ,
                title : "Hello World 1" ,
                done : true
            } ,
            {
                id : "1" ,
                title : "Hello World 2" ,
                done : true
            } */
        ] // array end
    ) // useState 함수 end

    // 2. items에 새로운 item 등록하는 함수
    const addItem = (item) => {
        item.id = "ID-" + items.length; // key 를 위한 id
        item.done = false;
        setItems([...items, item]); // 기존 상태 items 에 item 추가
        // setItems ( [ ...상태명, 추가할 데이터 ] );
    };

    // 3. items에 저장된 item 삭제하는 함수
    const deleteItem = (item) => {
        // 만약에 items에 있는 아이템 중 id와 삭제할 id와 다른 경우 해당 item 반환
        const newItems = items.filter( i=> i.id !== item.id);
            // * 삭제할 id를 제외한 새로운 newItems 배열에 선언
        setItems([...newItems]);
    }

    // 4. 수정 함수
    const editItem = () => {
        setItems([...items]);
    }

    // 반복문 이용한 Todo 컴포넌트 생성
    let TodoItems =
            <Paper style={{ margin : 16 }} >
                <List>{
                        items.map( (i) =>   // 2번 !_!
                            <Todo
                                item = { i }
                                key = { i.id }
                                수정함수={editItem}
                                삭제함수 = {deleteItem} />
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

        // JS 반복문 함수 제공
            // r = [ 1, 2, 3 ]
            // 배열/리스트.forEach( (o) => { } );    : 반복문만 가능 [ return X ]
                // let array = r.forEach( ( o ) => { return o+3 } );
                // 반복문이 끝나면 array에는 아무것도 들어있지 않다.
            // 배열/리스트.map ( (o) => { } );       : return 값을 새로운 배열에 저장
                // let array = r.map( ( o ) => { return o+3 } );
                // 반복문이 끝나면 array에는 [ 4 , 5 , 6 ] 이 담겨져 있음.
            // 배열/리스트.filter ( (o) => { } )     : 조건충족할경우 객체 반환
                // let array = r.filter( ( o ) => { o >= 3 } );
                // 반복문이 끝나면 array에는 [3] 이 담겨져 있음.