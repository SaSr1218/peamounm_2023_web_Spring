/* 컴포넌트 원형 */
/*
import React from 'react'
export default function 컴포넌트(){ return ( <> </> ) }
*/

// props : propertices 약자

import React from 'react'
export default function Product( props ){
    console.log( props )
    // <Product name = '코카콜라' price = '1000' />
    // props = {name : '코카콜라' , price : '1000' }
    return ( <>
        <div>
            <h5>  제품명 : { props.name } </h5>
            <h6> 가격 : { props.price }  </h6>
        </div>
    </> )

}