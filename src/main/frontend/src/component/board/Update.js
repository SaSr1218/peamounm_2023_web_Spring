import React,{ useState , useEffect } from 'react';
import axios from 'axios';
import { useSearchParams  } from 'react-router-dom';

import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CategoryList from './CategoryList'

export default function Update( props ) {
   const [ searchParams , setSearchParams ]  = useSearchParams();
   const [ board , setBoard ] = useState( {} );
   let [ cno , setCno ] = useState( 0 );

    // 기존 게시물 가져오기
   useEffect( ()=>{
        axios.get("/board/getboard" , { params : { bno : searchParams.get("bno") }})
            .then( (r) => {
                console.log( r.data );
                setBoard( r.data );
                setCno( r.data.cno )
            })
   } , [ ] )

    // 수정 함수
    const onUpdate  = () => {
            let info = {
                btitle : board.btitle ,
                bcontent : board.bcontent,
                bno : searchParams.get("bno"),
                cno : cno
            }
            console.log(info)

            axios.put("/board", info)
            .then(r => {
                if ( r.data == true ){
                    alert('게시물수정완료')
                    window.location.href="/board/view/"+board.bno;
                } else {
                    alert('수정에 실패했습니다.')
                }
            })
        // axios
    }
    // 카테고리 변경 함수
    const categoryChange = (cno) =>{   setCno( cno );   }

    //  제목 입력 이벤트
    const inputTitle = (e) => {
        console.log(e.target.value );
        board.btitle = e.target.value;
        setBoard( {...board} )
    }
    // 내용 입력 이벤트
     const inputContent = (e) => {
        console.log(e.target.value );
        board.bcontent = e.target.value;
        setBoard( {...board} )
    }
    return (<>
         <Container>
             <CategoryList categoryChange={  categoryChange } />
             <TextField fullWidth value={ board.btitle } onChange={ inputTitle  } className="btitle"     id="btitle"  label="제목" variant="standard" />
             <TextField fullWidth value={ board.bcontent } onChange={  inputContent } className="bcontent"   id="bcontent" label="내용" multiline rows={10} variant="standard" />
             <Button variant="outlined" type="button" onClick={  onUpdate }> 수정 </Button>
             <Button variant="outlined" type="button" > 취소 </Button>
         </Container>
    </>)
}