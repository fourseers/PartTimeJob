
import axios from 'axios'

import token from './token.js'
export function getShops(pagenum) {
    var prefix = "/warehouse"
//测试用的url
    return new Promise((resolve, reject) => {
        axios({
            headers: {
                'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                'Content-type': 'application/json',
                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                'x-access-token': token.loadToken().access_token,
            },
            method: 'get',
            params:
                {"page_count": pagenum},
            url: prefix + "/merchant/shops"
        }).then( ({ status, data }) => {
                resolve(data);
        })
            .catch(error => {
                reject( error);
            })
    })
}
