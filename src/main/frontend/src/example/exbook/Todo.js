// p.183 컴포넌트 만들기
import React , { useState } from 'react';
import { ListItem , ListItemText , InputBase ,
         Checkbox , ListItemSecondaryAction , IconButton } from '@mui/material';
// 삭제 아이콘
import DeleteOutlined from '@mui/icons-material/DeleteOutlined'

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

    return( <>
        <ListItem>
            <Checkbox checked = { item.done } />
            <ListItemText>
                <InputBase
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