
import axios from 'axios'

import token from './token.js'

export function getJobs(pagenum) {
    var prefix = "/arrangement"
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
                {"page_count": pagenum?pagenum:0},
            url: prefix + "/merchant/jobs"
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


export function getJobsByShop(pagenum,shop_id) {
    var prefix = "/arrangement"
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
                {"page_count": pagenum?pagenum:0,
                    "shop_id": shop_id
                },
            url: prefix + "/merchant/jobs"
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
