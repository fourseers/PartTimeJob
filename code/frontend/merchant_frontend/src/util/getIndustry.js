import axios from 'axios'
import token from './token.js'
export function getIndustry() {
    var prefix = "/warehouse"
    return new Promise((resolve, reject) => {
        axios({
            headers: {
                'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                'Content-type': 'application/json',
                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                'x-access-token': token.loadToken().access_token,
            },
            method: 'get',
            url: prefix + "/merchant/industry",
        }).then( ({ status, data }) => {
                resolve(data);
        })
            .catch(error => {

                reject( error);
            })
    })
}
