import React from 'react'
import Product from './Product' // 컴포넌트 호출
export default function Productlist(){
    return ( <>
        <Product name = '코카콜라' price = '1000' />
        <Product name = '사이다' price = '500' />
    </>);
}