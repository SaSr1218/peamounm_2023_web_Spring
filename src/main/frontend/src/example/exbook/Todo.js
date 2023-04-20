// p.183 컴포넌트 만들기
import React , { useState } from 'react';
import { ListItem , ListItemText , InputBase ,
         Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
// 삭제 아이콘
import DeleteOutlined from '@mui/icons-material/DeleteOutlined'
import axios from 'axios';

export default function Todo(props){ // 3번 !_!
    console.log( props )

    // 1. HOOK 상태관리 userState           // 4번 !_!
    const [ item, setItem ] = useState( props.item );

    // 2. props 전달된 삭제함수 변수로 이동
    const deleteItem = props.삭제함수;
    // 3. 삭제함수 이벤트처리 핸들러
    const deleteEventHandler = () => {
        deleteItem(item); // 5번 !_!
    }

    // 4. readOnly = true 초기화가 된 필드/변수 와 해당 필드를 수정할 수 있는 함수
    const [ readOnly , setReadOnly ] = useState(true);

    // 5. 읽기모드 해제 -> 수정 가능
    const turnOffReadOnly = () => {
        setReadOnly(false);
    }

    // 6. 읽기모드 켜기 -> 수정 금지
    const turnOnReadOnly = (e) => {
        if (e.key === "Enter"){
            setReadOnly(true);
            axios.put("http://localhost:8080/todo",{ id : item.id, done : item.done ,title : item.title }).then(r=>{console.log(r)});
        }
    }

    // 7. 입력받은 값을 변경
    let editItem = props.수정함수;

    const editEventHandler = (e) => {
        item.title = e.target.value;
        editItem();
    }

    // 8. 체크박스 수정업데이트
    const checkboxEventHandler = (e) => {
        item.done = e.target.checked;
        editItem();
    }

    return( <>
        <ListItem>
            <Checkbox checked = {item.done} onChange={checkboxEventHandler} />
            <ListItemText>
                <InputBase inputProps={{ readOnly: readOnly }}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnReadOnly}
                    onChange = { editEventHandler }
                    type="text"
                    id={ item.id }
                    name={ item.name }
                    value={ item.title }
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton onClick={deleteEventHandler} >
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    </> );

}