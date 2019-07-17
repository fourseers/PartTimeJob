
import axios from 'axios'

import token from './token.js'
export function getShops() {
    var prefix = "/warehouse"
//测试用的url
    return new Promise((resolve, reject) => {
        axios({
            headers: {
                'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                'Content-type': 'application/json',
                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                'x-access-token': token.loadToken().access_token,
            },
            method: 'get',
            params:
                {"page_count": 0},
            url: prefix + "/merchant/shops"
        }).then( ({ status, data }) => {
            if (status === 200) {
                resolve(data);
            } else {
                reject( data);
            }
        })
            .catch(error => {

                reject( error);
            })
    })
}
