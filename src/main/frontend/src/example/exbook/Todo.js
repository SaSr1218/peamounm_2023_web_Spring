// p.183 컴포넌트 만들기
import React , { useState } from 'react';
import { ListItem , ListItemText , InputBase , Checkbox } from '@mui/material';

export default function Todo(props){
    console.log( props )

    // 1. HOOK 상태관리 userState
    const [ item, setItem ] = useState( props.item );

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
        </ListItem>
    </> );

}